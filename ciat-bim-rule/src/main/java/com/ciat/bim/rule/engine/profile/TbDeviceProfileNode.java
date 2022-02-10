/**
 * Copyright © 2016-2021 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ciat.bim.rule.engine.profile;

import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.PartitionChangeMsg;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.rule.*;
import com.ciat.bim.rule.engine.RuleEngineDeviceProfileCache;
import com.ciat.bim.rule.util.TbNodeUtils;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.utils.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.rule.entity.RuleNodeState;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "device profile",
        customRelations = true,
        relationTypes = {"Alarm Created", "Alarm Updated", "Alarm Severity Updated", "Alarm Cleared", "Success", "Failure"},
        configClazz = TbDeviceProfileNodeConfiguration.class,
        nodeDescription = "Process device messages based on device profile settings",
        nodeDetails = "Create and clear alarms based on alarm rules defined in device profile. The output relation type is either " +
                "'Alarm Created', 'Alarm Updated', 'Alarm Severity Updated' and 'Alarm Cleared' or simply 'Success' if no alarms were affected.",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbDeviceProfileConfig"
)
public class TbDeviceProfileNode implements TbNode {
    private static final String PERIODIC_MSG_TYPE = "TbDeviceProfilePeriodicMsg";
    private static final String PROFILE_UPDATE_MSG_TYPE = "TbDeviceProfileUpdateMsg";
    private static final String DEVICE_UPDATE_MSG_TYPE = "TbDeviceUpdateMsg";

    private TbDeviceProfileNodeConfiguration config;
    private RuleEngineDeviceProfileCache cache;
    private TbContext ctx;
    private final Map<DeviceId, DeviceState> deviceStates = new ConcurrentHashMap<>();

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbDeviceProfileNodeConfiguration.class);
        this.cache = ctx.getDeviceProfileCache();
        this.ctx = ctx;
        scheduleAlarmHarvesting(ctx, null);
        ctx.addDeviceProfileListeners(this::onProfileUpdate, this::onDeviceUpdate);
        if (config.isFetchAlarmRulesStateOnStart()) {
            log.info("[{}] Fetching alarm rule state", ctx.getSelfId());
            int fetchCount = 0;
            PageLink pageLink = new PageLink(1024);
            while (true) {
                PageData<RuleNodeState> states = ctx.findRuleNodeStates(pageLink);
                if (!states.getData().isEmpty()) {
                    for (RuleNodeState rns : states.getData()) {
                        fetchCount++;

                        if (rns.fetchEntityId().getEntityType().equals(EntityType.DEVICE) && ctx.isLocalEntity(rns.fetchEntityId())) {
                            getOrCreateDeviceState(ctx, new DeviceId(rns.getEntityId()), rns);
                        }
                    }
                }
                if (!states.hasNext()) {
                    break;
                } else {
                    pageLink = pageLink.nextPageLink();
                }
            }
            log.info("[{}] Fetched alarm rule state for {} entities", ctx.getSelfId(), fetchCount);
        }
        if (!config.isPersistAlarmRulesState() && ctx.isLocalEntity(ctx.getSelfId())) {
            log.info("[{}] Going to cleanup rule node states", ctx.getSelfId());
            ctx.clearRuleNodeStates();
        }
    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        EntityType originatorType = msg.getOriginator().getEntityType();
        if (msg.getType().equals(PERIODIC_MSG_TYPE)) {
            scheduleAlarmHarvesting(ctx, msg);
            harvestAlarms(ctx, System.currentTimeMillis());
        } else if (msg.getType().equals(PROFILE_UPDATE_MSG_TYPE)) {
            updateProfile(ctx, new DeviceProfileId(msg.getData()));
        } else if (msg.getType().equals(DEVICE_UPDATE_MSG_TYPE)) {
            JsonNode data = JacksonUtil.toJsonNode(msg.getData());
            DeviceId deviceId = new DeviceId(data.get("deviceId").asText());
            if (data.has("profileId")) {
                invalidateDeviceProfileCache(deviceId, new DeviceProfileId(data.get("deviceProfileId").asText()));
            } else {
                removeDeviceState(deviceId);
            }
        } else {
            if (EntityType.DEVICE.equals(originatorType)) {
                DeviceId deviceId = new DeviceId(msg.getOriginator().getId());
                if (msg.getType().equals(DataConstants.ENTITY_UPDATED)) {
                    invalidateDeviceProfileCache(deviceId, msg.getData());
                    ctx.tellSuccess(msg);
                } else if (msg.getType().equals(DataConstants.ENTITY_DELETED)) {
                    removeDeviceState(deviceId);
                    ctx.tellSuccess(msg);
                } else {
                    DeviceState deviceState = getOrCreateDeviceState(ctx, deviceId, null);
                    if (deviceState != null) {
                        deviceState.process(ctx, msg);
                    } else {
                        log.info("Device was not found! Most probably device [" + deviceId + "] has been removed from the database. Acknowledging msg.");
                        ctx.ack(msg);
                    }
                }
            } else {
                ctx.tellSuccess(msg);
            }
        }
    }

    @Override
    public void onPartitionChangeMsg(TbContext ctx, PartitionChangeMsg msg) {
        // Cleanup the cache for all entities that are no longer assigned to current server partitions
        deviceStates.entrySet().removeIf(entry -> !ctx.isLocalEntity(entry.getKey()));
    }

    @Override
    public void destroy() {
        ctx.removeListeners();
        deviceStates.clear();
    }

    protected DeviceState getOrCreateDeviceState(TbContext ctx, DeviceId deviceId, RuleNodeState rns) {
        DeviceState deviceState = deviceStates.get(deviceId);
        if (deviceState == null) {
            DeviceProfile deviceProfile = cache.get(ctx.getTenantId(), deviceId);
            if (deviceProfile != null) {
                deviceState = new DeviceState(ctx, config, deviceId, new ProfileState(deviceProfile), rns);
                deviceStates.put(deviceId, deviceState);
            }
        }
        return deviceState;
    }

    protected void scheduleAlarmHarvesting(TbContext ctx, TbMsg msg) {
        TbMsg periodicCheck = TbMsg.newMsg(PERIODIC_MSG_TYPE, ctx.getTenantId(), msg != null ? msg.getCustomerId() : null, TbMsgMetaData.EMPTY, "{}");
        ctx.tellSelf(periodicCheck, TimeUnit.MINUTES.toMillis(1));
    }

    protected void harvestAlarms(TbContext ctx, long ts) throws ExecutionException, InterruptedException {
        for (DeviceState state : deviceStates.values()) {
            state.harvestAlarms(ctx, ts);
        }
    }

    protected void updateProfile(TbContext ctx, DeviceProfileId deviceProfileId) throws ExecutionException, InterruptedException {
        DeviceProfile deviceProfile = cache.get(ctx.getTenantId(), deviceProfileId);
        if (deviceProfile != null) {
            log.debug("[{}] Received device profile update notification: {}", ctx.getSelfId(), deviceProfile);
            for (DeviceState state : deviceStates.values()) {
                if (deviceProfile.getId().equals(state.getProfileId())) {
                    state.updateProfile(ctx, deviceProfile);
                }
            }
        } else {
            log.debug("[{}] Received stale profile update notification: [{}]", ctx.getSelfId(), deviceProfileId);
        }
    }

    protected void onProfileUpdate(DeviceProfile profile) {
        ctx.tellSelf(TbMsg.newMsg(PROFILE_UPDATE_MSG_TYPE, TenantId.fromString(ctx.getTenantId()), TbMsgMetaData.EMPTY, profile.getId()), 0L);
    }

    private void onDeviceUpdate(DeviceId deviceId, DeviceProfile deviceProfile) {
        ObjectNode msgData = JacksonUtil.newObjectNode();
        msgData.put("deviceId", deviceId.getId().toString());
        if (deviceProfile != null) {
            msgData.put("deviceProfileId", deviceProfile.getId());
        }
        ctx.tellSelf(TbMsg.newMsg(DEVICE_UPDATE_MSG_TYPE, TenantId.fromString(ctx.getTenantId()), TbMsgMetaData.EMPTY, JacksonUtil.toString(msgData)), 0L);
    }

    protected void invalidateDeviceProfileCache(DeviceId deviceId, String deviceJson) {
        DeviceState deviceState = deviceStates.get(deviceId);
        if (deviceState != null) {
            DeviceProfileId currentProfileId = deviceState.getProfileId();
            Device device = JacksonUtil.fromString(deviceJson, Device.class);
            if (!currentProfileId.equals(device.getDeviceProfileId())) {
                removeDeviceState(deviceId);
            }
        }
    }

    protected void invalidateDeviceProfileCache(DeviceId deviceId, DeviceProfileId deviceProfileId) {
        DeviceState deviceState = deviceStates.get(deviceId);
        if (deviceState != null) {
            if (!deviceState.getProfileId().equals(deviceProfileId)) {
                removeDeviceState(deviceId);
            }
        }
    }

    private void removeDeviceState(DeviceId deviceId) {
        DeviceState state = deviceStates.remove(deviceId);
        if (config.isPersistAlarmRulesState() && (state != null || !config.isFetchAlarmRulesStateOnStart())) {
            ctx.removeRuleNodeStateForEntity(deviceId);
        }
    }
}
