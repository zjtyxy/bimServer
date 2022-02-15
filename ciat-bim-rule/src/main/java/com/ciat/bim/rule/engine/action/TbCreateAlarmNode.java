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
package com.ciat.bim.rule.engine.action;

import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.data.device.AlarmSeverity;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.RuleNode;
import com.ciat.bim.rule.TbContext;
import com.ciat.bim.rule.TbNodeConfiguration;
import com.ciat.bim.rule.TbNodeException;
import com.ciat.bim.rule.util.TbNodeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.EnumUtils;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.entity.AlarmStatus;


import java.io.IOException;
import java.util.Date;
import java.util.List;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "create alarm", relationTypes = {"Created", "Updated", "False"},
        configClazz = TbCreateAlarmNodeConfiguration.class,
        nodeDescription = "Create or Update Alarm",
        nodeDetails =
                "Details - JS function that creates JSON object based on incoming message. This object will be added into Alarm.details field.\n" +
                        "Node output:\n" +
                        "If alarm was not created, original message is returned. Otherwise new Message returned with type 'ALARM', Alarm object in 'msg' property and 'metadata' will contains one of those properties 'isNewAlarm/isExistingAlarm'. " +
                        "Message payload can be accessed via <code>msg</code> property. For example <code>'temperature = ' + msg.temperature ;</code>. " +
                        "Message metadata can be accessed via <code>metadata</code> property. For example <code>'name = ' + metadata.customerName;</code>.",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbActionNodeCreateAlarmConfig",
        icon = "notifications_active"
)
public class TbCreateAlarmNode extends TbAbstractAlarmNode<TbCreateAlarmNodeConfiguration> {

    private static ObjectMapper mapper = new ObjectMapper();
    private List<String> relationTypes;
    private AlarmSeverity notDynamicAlarmSeverity;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        super.init(ctx, configuration);
        if(!this.config.isDynamicSeverity()) {
            this.notDynamicAlarmSeverity = EnumUtils.getEnum(AlarmSeverity.class, this.config.getSeverity());
            if(this.notDynamicAlarmSeverity == null) {
                throw new TbNodeException("Incorrect Alarm Severity value: " + this.config.getSeverity());
            }
        }
    }


    @Override
    protected TbCreateAlarmNodeConfiguration loadAlarmNodeConfig(TbNodeConfiguration configuration) throws TbNodeException {
        TbCreateAlarmNodeConfiguration nodeConfiguration = TbNodeUtils.convert(configuration, TbCreateAlarmNodeConfiguration.class);
        if(nodeConfiguration == null)
        {
            nodeConfiguration =  new TbCreateAlarmNodeConfiguration().defaultConfiguration();
        }
        relationTypes = nodeConfiguration.getRelationTypes();
        return nodeConfiguration;
    }

    @Override
    protected ListenableFuture<TbAlarmResult> processAlarm(TbContext ctx, TbMsg msg) {
        String alarmType;
        final Alarm msgAlarm;

        if (!config.isUseMessageAlarmData()) {
            alarmType = TbNodeUtils.processPattern(this.config.getAlarmType(), msg);
            msgAlarm = null;
        } else {
            try {
                msgAlarm = getAlarmFromMessage(ctx, msg);
                alarmType = msgAlarm.getType();
            } catch (IOException e) {
                ctx.tellFailure(msg, e);
                return null;
            }
        }

        ListenableFuture<Alarm> latest = ctx.getAlarmService().findLatestByOriginatorAndType(ctx.getTenantId(), msg.getOriginator(), alarmType);
        return Futures.transformAsync(latest, existingAlarm -> {
            if (existingAlarm == null || existingAlarm.getStatus().isCleared()) {
                return createNewAlarm(ctx, msg, msgAlarm);
            } else {
                return updateAlarm(ctx, msg, existingAlarm, msgAlarm);
            }
        }, ctx.getDbCallbackExecutor());

    }

    private Alarm getAlarmFromMessage(TbContext ctx, TbMsg msg) throws IOException {
        Alarm msgAlarm;
        msgAlarm = mapper.readValue(msg.getData(), Alarm.class);
        msgAlarm.setTenantId(ctx.getTenantId());
        if (msgAlarm.getOriginator() == null) {
            msgAlarm.setOriginator(msg.getOriginator());
        }
        if (msgAlarm.getStatus() == null) {
            msgAlarm.setStatus(AlarmStatus.ACTIVE_UNACK);
        }
        return msgAlarm;
    }

    private ListenableFuture<TbAlarmResult> createNewAlarm(TbContext ctx, TbMsg msg, Alarm msgAlarm) {
        ListenableFuture<Alarm> asyncAlarm;
        if (msgAlarm != null) {
            asyncAlarm = Futures.immediateFuture(msgAlarm);
        } else {
            ctx.logJsEvalRequest();
            asyncAlarm = Futures.transform(buildAlarmDetails(ctx, msg, null),
                    details -> {
                        ctx.logJsEvalResponse();
                        return buildAlarm(msg, details,
                                TenantId.fromString(ctx.getTenantId()));
                    }, MoreExecutors.directExecutor());
        }
        ListenableFuture<Alarm> asyncCreated = Futures.transform(asyncAlarm,
                alarm -> ctx.getAlarmService().saveOrUpdateL(alarm), ctx.getDbCallbackExecutor());
        return Futures.transform(asyncCreated, alarm -> new TbAlarmResult(true, false, false, alarm), MoreExecutors.directExecutor());
    }

    private ListenableFuture<TbAlarmResult> updateAlarm(TbContext ctx, TbMsg msg, Alarm existingAlarm, Alarm msgAlarm) {
        ctx.logJsEvalRequest();
        ListenableFuture<Alarm> asyncUpdated = Futures.transform(buildAlarmDetails(ctx, msg, existingAlarm.getDetails()), (Function<JsonNode, Alarm>) details -> {
            ctx.logJsEvalResponse();
            if (msgAlarm != null) {
                existingAlarm.setSeverity(msgAlarm.getSeverity());
                existingAlarm.setPropagate(msgAlarm.getPropagate());
                existingAlarm.setPropagateRelationTypes(msgAlarm.getPropagateRelationTypes());
            } else {
                existingAlarm.setSeverity(processAlarmSeverity(msg));
                existingAlarm.setPropagate(config.isPropagate()? "Y":"N");

                existingAlarm.setPropagateRelationTypes(ArrayUtils.toString(relationTypes));
            }
            existingAlarm.setDetails(details.asText());
            existingAlarm.setEndTs(new Date(System.currentTimeMillis()));
            return ctx.getAlarmService().saveOrUpdateL(existingAlarm);
        }, ctx.getDbCallbackExecutor());

        return Futures.transform(asyncUpdated, a -> new TbAlarmResult(false, true, false, a), MoreExecutors.directExecutor());
    }

    private Alarm buildAlarm(TbMsg msg, JsonNode details, TenantId tenantId) {
        Alarm alarm =new Alarm();

        alarm.setTenantId(tenantId.getId());
        alarm.setOriginatorId(msg.getOriginator().getId());
        alarm.setStatus(AlarmStatus.ACTIVE_UNACK);
        alarm.setSeverity(this.config.isDynamicSeverity() ? processAlarmSeverity(msg) : notDynamicAlarmSeverity);
        alarm.setPropagate(config.isPropagate()?"Y":"N");
        alarm.setType(TbNodeUtils.processPattern(this.config.getAlarmType(), msg));
        alarm.setPropagateRelationTypes(relationTypes.toString());
        alarm.setStartTs(new Date(System.currentTimeMillis()));
        alarm.setEndTs(new Date(System.currentTimeMillis()));
        alarm.setDetails(details.asText());

        return alarm;
    }

    private AlarmSeverity processAlarmSeverity(TbMsg msg) {
        AlarmSeverity severity = EnumUtils.getEnum(AlarmSeverity.class, TbNodeUtils.processPattern(this.config.getSeverity(), msg));
        if(severity == null) {
            throw new RuntimeException("Used incorrect pattern or Alarm Severity not included in message");
        }
        return severity;
    }

}
