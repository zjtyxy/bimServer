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

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.device.AlarmConditionFilterKey;
import com.ciat.bim.data.device.AlarmConditionKeyType;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.TbContext;
import com.ciat.bim.rule.engine.profile.state.PersistedAlarmState;
import com.ciat.bim.rule.engine.profile.state.PersistedDeviceState;
import com.ciat.bim.rule.engine.telemetry.TbMsgTimeseriesNode;
import com.ciat.bim.server.common.data.exception.ApiUsageLimitsExceededException;
import com.ciat.bim.server.common.data.query.EntityKey;
import com.ciat.bim.server.common.data.query.EntityKeyMapping;
import com.ciat.bim.server.common.data.query.EntityKeyType;
import com.ciat.bim.server.common.msg.SessionMsgType;
import com.ciat.bim.server.common.transport.util.JsonConverter;
import com.ciat.bim.server.utils.JacksonUtil;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.entity.DeviceProfileAlarm;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.rule.entity.RuleNodeState;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
class DeviceState {

    private final boolean persistState;
    private final DeviceId deviceId;
    private final ProfileState deviceProfile;
    private RuleNodeState state;
    private PersistedDeviceState pds;
    private DataSnapshot latestValues;
    private final ConcurrentMap<String, AlarmState> alarmStates = new ConcurrentHashMap<>();
    private final DynamicPredicateValueCtx dynamicPredicateValueCtx;

    DeviceState(TbContext ctx, TbDeviceProfileNodeConfiguration config, DeviceId deviceId, ProfileState deviceProfile, RuleNodeState state) {
        this.persistState = config.isPersistAlarmRulesState();
        this.deviceId = deviceId;
        this.deviceProfile = deviceProfile;

        this.dynamicPredicateValueCtx = new DynamicPredicateValueCtxImpl(TenantId.fromString(ctx.getTenantId()), deviceId, ctx);

        if (config.isPersistAlarmRulesState()) {
            if (state != null) {
                this.state = state;
            } else {
                this.state = ctx.findRuleNodeStateForEntity(deviceId);
            }
            if (this.state != null) {
                pds = JacksonUtil.fromString(this.state.getStateData(), PersistedDeviceState.class);
            } else {
                this.state = new RuleNodeState();
                this.state.setRuleNodeId(ctx.getSelfId().getId());
                this.state.setEntityId(deviceId.getId());
                pds = new PersistedDeviceState();
                pds.setAlarmStates(new HashMap<>());
            }
        }
        if (pds != null) {
            for (DeviceProfileAlarm alarm : deviceProfile.getAlarmSettings()) {
                alarmStates.computeIfAbsent(alarm.getId(),
                        a -> new AlarmState(deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
            }
        }
    }

    public void updateProfile(TbContext ctx, DeviceProfile deviceProfile) throws ExecutionException, InterruptedException {
        //Set<AlarmConditionFilterKey> oldKeys = Set.copyOf(this.deviceProfile.getEntityKeys());
        Set<AlarmConditionFilterKey> oldKeys = this.deviceProfile.getEntityKeys();
        this.deviceProfile.updateDeviceProfile(deviceProfile);
        if (latestValues != null) {
            Set<AlarmConditionFilterKey> keysToFetch = new HashSet<>(this.deviceProfile.getEntityKeys());
            keysToFetch.removeAll(oldKeys);
            if (!keysToFetch.isEmpty()) {
                addEntityKeysToSnapshot(ctx, deviceId, keysToFetch, latestValues);
            }
        }
        Set<String> newAlarmStateIds = this.deviceProfile.getAlarmSettings().stream().map(DeviceProfileAlarm::getId).collect(Collectors.toSet());
        alarmStates.keySet().removeIf(id -> !newAlarmStateIds.contains(id));
        for (DeviceProfileAlarm alarm : this.deviceProfile.getAlarmSettings()) {
            if (alarmStates.containsKey(alarm.getId())) {
                alarmStates.get(alarm.getId()).updateState(alarm, getOrInitPersistedAlarmState(alarm));
            } else {
                alarmStates.putIfAbsent(alarm.getId(), new AlarmState(this.deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
            }
        }
    }

    public void harvestAlarms(TbContext ctx, long ts) throws ExecutionException, InterruptedException {
        log.debug("[{}] Going to harvest alarms: {}", ctx.getSelfId(), ts);
        boolean stateChanged = false;
        for (AlarmState state : alarmStates.values()) {
            stateChanged |= state.process(ctx, ts);
        }
        if (persistState && stateChanged) {
            state.setStateData(JacksonUtil.toString(pds));
            state = ctx.saveRuleNodeState(state);
        }
    }

    public void process(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        if (latestValues == null) {
            latestValues = fetchLatestValues(ctx, deviceId);
        }
        boolean stateChanged = false;
        if (msg.getType().equals(SessionMsgType.POST_TELEMETRY_REQUEST.name())) {
            stateChanged = processTelemetry(ctx, msg);
        } else if (msg.getType().equals(SessionMsgType.POST_ATTRIBUTES_REQUEST.name())) {
            stateChanged = processAttributesUpdateRequest(ctx, msg);
        } else if (msg.getType().equals(DataConstants.ACTIVITY_EVENT) || msg.getType().equals(DataConstants.INACTIVITY_EVENT)) {
            stateChanged = processDeviceActivityEvent(ctx, msg);
        } else if (msg.getType().equals(DataConstants.ATTRIBUTES_UPDATED)) {
            stateChanged = processAttributesUpdateNotification(ctx, msg);
        } else if (msg.getType().equals(DataConstants.ATTRIBUTES_DELETED)) {
            stateChanged = processAttributesDeleteNotification(ctx, msg);
        } else if (msg.getType().equals(DataConstants.ALARM_CLEAR)) {
            stateChanged = processAlarmClearNotification(ctx, msg);
        } else if (msg.getType().equals(DataConstants.ALARM_ACK)) {
            processAlarmAckNotification(ctx, msg);
        } else if (msg.getType().equals(DataConstants.ALARM_DELETE)) {
            processAlarmDeleteNotification(ctx, msg);
        } else {
            if (msg.getType().equals(DataConstants.ENTITY_ASSIGNED) || msg.getType().equals(DataConstants.ENTITY_UNASSIGNED)) {
                dynamicPredicateValueCtx.resetCustomer();
            }
            ctx.tellSuccess(msg);
        }
        if (persistState && stateChanged) {
            state.setStateData(JacksonUtil.toString(pds));
            state = ctx.saveRuleNodeState(state);
        }
    }

    private boolean processDeviceActivityEvent(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        String scope = msg.getMetaData().getValue(DataConstants.SCOPE);
        if (StringUtils.isEmpty(scope)) {
            return processTelemetry(ctx, msg);
        } else {
            return processAttributes(ctx, msg, scope);
        }
    }

    private boolean processAlarmClearNotification(TbContext ctx, TbMsg msg) {
        boolean stateChanged = false;
        Alarm alarmNf = JacksonUtil.fromString(msg.getData(), Alarm.class);
        for (DeviceProfileAlarm alarm : deviceProfile.getAlarmSettings()) {
            AlarmState alarmState = alarmStates.computeIfAbsent(alarm.getId(),
                    a -> new AlarmState(this.deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
            stateChanged |= alarmState.processAlarmClear(ctx, alarmNf);
        }
        ctx.tellSuccess(msg);
        return stateChanged;
    }

    private void processAlarmAckNotification(TbContext ctx, TbMsg msg) {
        Alarm alarmNf = JacksonUtil.fromString(msg.getData(), Alarm.class);
        for (DeviceProfileAlarm alarm : deviceProfile.getAlarmSettings()) {
            AlarmState alarmState = alarmStates.computeIfAbsent(alarm.getId(),
                    a -> new AlarmState(this.deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
            alarmState.processAckAlarm(alarmNf);
        }
        ctx.tellSuccess(msg);
    }

    private void processAlarmDeleteNotification(TbContext ctx, TbMsg msg) {
        Alarm alarm = JacksonUtil.fromString(msg.getData(), Alarm.class);
        alarmStates.values().removeIf(alarmState -> alarmState.getCurrentAlarm().getId().equals(alarm.getId()));
        ctx.tellSuccess(msg);
    }

    private boolean processAttributesUpdateNotification(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        String scope = msg.getMetaData().getValue(DataConstants.SCOPE);
        if (StringUtils.isEmpty(scope)) {
            scope = DataConstants.CLIENT_SCOPE;
        }
        return processAttributes(ctx, msg, scope);
    }

    private boolean processAttributesDeleteNotification(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        boolean stateChanged = false;
        List<String> keys = new ArrayList<>();
        new JsonParser().parse(msg.getData()).getAsJsonObject().get("attributes").getAsJsonArray().forEach(e -> keys.add(e.getAsString()));
        String scope = msg.getMetaData().getValue(DataConstants.SCOPE);
        if (StringUtils.isEmpty(scope)) {
            scope = DataConstants.CLIENT_SCOPE;
        }
        if (!keys.isEmpty()) {
            EntityKeyType keyType = getKeyTypeFromScope(scope);
            keys.forEach(key -> latestValues.removeValue(new EntityKey(keyType, key)));
            for (DeviceProfileAlarm alarm : deviceProfile.getAlarmSettings()) {
                AlarmState alarmState = alarmStates.computeIfAbsent(alarm.getId(),
                        a -> new AlarmState(this.deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
                stateChanged |= alarmState.process(ctx, msg, latestValues, null);
            }
        }
        ctx.tellSuccess(msg);
        return stateChanged;
    }

    protected boolean processAttributesUpdateRequest(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        return processAttributes(ctx, msg, DataConstants.CLIENT_SCOPE);
    }

    private boolean processAttributes(TbContext ctx, TbMsg msg, String scope) throws ExecutionException, InterruptedException {
        boolean stateChanged = false;
        Set<AttributeKv> attributes = JsonConverter.convertToAttributes(new JsonParser().parse(msg.getData()));
        if (!attributes.isEmpty()) {
            SnapshotUpdate update = merge(latestValues, attributes, scope);
            for (DeviceProfileAlarm alarm : deviceProfile.getAlarmSettings()) {
                AlarmState alarmState = alarmStates.computeIfAbsent(alarm.getId(),
                        a -> new AlarmState(this.deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
                stateChanged |= alarmState.process(ctx, msg, latestValues, update);
            }
        }
        ctx.tellSuccess(msg);
        return stateChanged;
    }

    protected boolean processTelemetry(TbContext ctx, TbMsg msg) throws ExecutionException, InterruptedException {
        boolean stateChanged = false;
        Map<Long, List<AttributeKv>> tsKvMap = JsonConverter.convertToSortedTelemetry(new JsonParser().parse(msg.getData()), TbMsgTimeseriesNode.getTs(msg));
        // iterate over data by ts (ASC order).
        for (Map.Entry<Long, List<AttributeKv>> entry : tsKvMap.entrySet()) {
            Long ts = entry.getKey();
            List<AttributeKv> data = entry.getValue();
            SnapshotUpdate update = merge(latestValues, ts, data);
            if (update.hasUpdate()) {
                for (DeviceProfileAlarm alarm : deviceProfile.getAlarmSettings()) {
                    AlarmState alarmState = alarmStates.computeIfAbsent(alarm.getId(),
                            a -> new AlarmState(this.deviceProfile, deviceId, alarm, getOrInitPersistedAlarmState(alarm), dynamicPredicateValueCtx));
                    try {
                        stateChanged |= alarmState.process(ctx, msg, latestValues, update);
                    } catch (ApiUsageLimitsExceededException e) {
                        alarmStates.remove(alarm.getId());
                        throw e;
                    }
                }
            }
        }
        ctx.tellSuccess(msg);
        return stateChanged;
    }

    private SnapshotUpdate merge(DataSnapshot latestValues, Long newTs, List<AttributeKv> data) {
        Set<AlarmConditionFilterKey> keys = new HashSet<>();
        for (AttributeKv entry : data) {
            AlarmConditionFilterKey entityKey = new AlarmConditionFilterKey(AlarmConditionKeyType.TIME_SERIES, entry.getKey());
            if (latestValues.putValue(entityKey, newTs, toEntityValue(entry))) {
                keys.add(entityKey);
            }
        }
        latestValues.setTs(newTs);
        return new SnapshotUpdate(AlarmConditionKeyType.TIME_SERIES, keys);
    }

    private SnapshotUpdate merge(DataSnapshot latestValues, Set<AttributeKv> attributes, String scope) {
        long newTs = 0;
        Set<AlarmConditionFilterKey> keys = new HashSet<>();
        for (AttributeKv entry : attributes) {
            newTs = Math.max(newTs, entry.getLastupdatets().getTime());
            AlarmConditionFilterKey entityKey = new AlarmConditionFilterKey(AlarmConditionKeyType.ATTRIBUTE, entry.getKey());
            if (latestValues.putValue(entityKey, newTs, toEntityValue(entry))) {
                keys.add(entityKey);
            }
        }
        latestValues.setTs(newTs);
        return new SnapshotUpdate(AlarmConditionKeyType.ATTRIBUTE, keys);
    }

    private static EntityKeyType getKeyTypeFromScope(String scope) {
        switch (scope) {
            case DataConstants.CLIENT_SCOPE:
                return EntityKeyType.CLIENT_ATTRIBUTE;
            case DataConstants.SHARED_SCOPE:
                return EntityKeyType.SHARED_ATTRIBUTE;
            case DataConstants.SERVER_SCOPE:
                return EntityKeyType.SERVER_ATTRIBUTE;
        }
        return EntityKeyType.ATTRIBUTE;
    }

    private DataSnapshot fetchLatestValues(TbContext ctx, EntityId originator) throws ExecutionException, InterruptedException {
        Set<AlarmConditionFilterKey> entityKeysToFetch = deviceProfile.getEntityKeys();
        DataSnapshot result = new DataSnapshot(entityKeysToFetch);
        addEntityKeysToSnapshot(ctx, originator, entityKeysToFetch, result);
        return result;
    }

    private void addEntityKeysToSnapshot(TbContext ctx, EntityId originator, Set<AlarmConditionFilterKey> entityKeysToFetch, DataSnapshot result) throws InterruptedException, ExecutionException {
        Set<String> attributeKeys = new HashSet<>();
        Set<String> latestTsKeys = new HashSet<>();

        Device device = null;
        for (AlarmConditionFilterKey entityKey : entityKeysToFetch) {
            String key = entityKey.getKey();
            switch (entityKey.getType()) {
                case ATTRIBUTE:
                    attributeKeys.add(key);
                    break;
                case TIME_SERIES:
                    latestTsKeys.add(key);
                    break;
                case ENTITY_FIELD:
                    if (device == null) {
                        device = ctx.getDeviceService().getById(originator.getId());
                    }
                    if (device != null) {
                        switch (key) {
                            case EntityKeyMapping.NAME:
                                result.putValue(entityKey, device.getCreateTime().getTime(), EntityKeyValue.fromString(device.getName()));
                                break;
                            case EntityKeyMapping.TYPE:
                                result.putValue(entityKey, device.getCreateTime().getTime(), EntityKeyValue.fromString(device.getType()));
                                break;
                            case EntityKeyMapping.CREATED_TIME:
                                result.putValue(entityKey, device.getCreateTime().getTime(), EntityKeyValue.fromLong(device.getCreateTime().getTime()));
                                break;
                            case EntityKeyMapping.LABEL:
                                result.putValue(entityKey, device.getCreateTime().getTime(), EntityKeyValue.fromString(device.getName()));
                                break;
                        }
                    }
                    break;
            }
        }

        if (!latestTsKeys.isEmpty()) {
            List<AttributeKv> data = ctx.getTimeseriesService().findLatest(TenantId.fromString(ctx.getTenantId()), originator, latestTsKeys).get();
            for (AttributeKv entry : data) {
                if (entry.getValue() != null) {
                    result.putValue(new AlarmConditionFilterKey(AlarmConditionKeyType.TIME_SERIES, entry.getKey()), entry.getCreateTime().getTime(), toEntityValue(entry));
                }
            }
        }
        if (!attributeKeys.isEmpty()) {
            //?????????????????????
            addToSnapshot(result, ctx.getAttributesService().find(ctx.getTenantId(), originator, DataConstants.CLIENT_SCOPE, attributeKeys).get());
            addToSnapshot(result, ctx.getAttributesService().find(ctx.getTenantId(), originator, DataConstants.SHARED_SCOPE, attributeKeys).get());
            addToSnapshot(result, ctx.getAttributesService().find(ctx.getTenantId(), originator, DataConstants.SERVER_SCOPE, attributeKeys).get());
        }
    }

    private void addToSnapshot(DataSnapshot snapshot, List<AttributeKv> data) {
        for (AttributeKv entry : data) {
            if (entry.getValue() != null) {
                EntityKeyValue value = toEntityValue(entry);
                snapshot.putValue(new AlarmConditionFilterKey(AlarmConditionKeyType.ATTRIBUTE, entry.getKey()), entry.getLastupdatets().getTime(), value);
            }
        }
    }

    public static EntityKeyValue toEntityValue(AttributeKv entry) {
        switch (entry.getAttributeType()) {
            case STRING:
                return EntityKeyValue.fromString(entry.getStrValue());
            case LONG:
                return EntityKeyValue.fromLong(entry.getLongValue());
            case DOUBLE:
                return EntityKeyValue.fromDouble(entry.getDoubleValue());
            case BOOLEAN:
                return EntityKeyValue.fromBool(entry.getBooleanValue()==1);
            case JSON:
                return EntityKeyValue.fromJson(entry.getJsonValue());
            default:
                throw new RuntimeException("Can't parse entry: " + entry.getAttributeType());
        }
    }

    public DeviceProfileId getProfileId() {
        return deviceProfile.getProfileId();
    }

    private PersistedAlarmState getOrInitPersistedAlarmState(DeviceProfileAlarm alarm) {
        if (pds != null) {
            PersistedAlarmState alarmState = pds.getAlarmStates().get(alarm.getId());
            if (alarmState == null) {
                alarmState = new PersistedAlarmState();
                alarmState.setCreateRuleStates(new HashMap<>());
                pds.getAlarmStates().put(alarm.getId(), alarmState);
            }
            return alarmState;
        } else {
            return null;
        }
    }

}
