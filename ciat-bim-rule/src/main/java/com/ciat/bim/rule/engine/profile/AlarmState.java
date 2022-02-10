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

import com.ciat.bim.common.util.DonAsynchron;
import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.device.AlarmConditionFilter;
import com.ciat.bim.data.device.AlarmConditionKeyType;
import com.ciat.bim.data.device.AlarmConditionSpecType;
import com.ciat.bim.data.device.AlarmSeverity;
import com.ciat.bim.data.device.profile.DeviceProfileAlarm;
import com.ciat.bim.data.id.DashboardId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.msg.ServiceQueue;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.rule.TbContext;
import com.ciat.bim.rule.engine.action.TbAlarmResult;
import com.ciat.bim.rule.engine.profile.state.PersistedAlarmRuleState;
import com.ciat.bim.rule.engine.profile.state.PersistedAlarmState;
import com.ciat.bim.server.utils.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.entity.AlarmOperationResult;
import org.jeecg.modules.alarm.entity.AlarmStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.BiFunction;

@Data
@Slf4j
class AlarmState {

    private final ProfileState deviceProfile;
    private final EntityId originator;
    private DeviceProfileAlarm alarmDefinition;
    private volatile List<AlarmRuleState> createRulesSortedBySeverityDesc;
    private volatile AlarmRuleState clearState;
    private volatile Alarm currentAlarm;
    private volatile boolean initialFetchDone;
    private volatile TbMsgMetaData lastMsgMetaData;
    private volatile String lastMsgQueueName;
    private volatile DataSnapshot dataSnapshot;
    private final DynamicPredicateValueCtx dynamicPredicateValueCtx;

    AlarmState(ProfileState deviceProfile, EntityId originator, DeviceProfileAlarm alarmDefinition, PersistedAlarmState alarmState, DynamicPredicateValueCtx dynamicPredicateValueCtx) {
        this.deviceProfile = deviceProfile;
        this.originator = originator;
        this.dynamicPredicateValueCtx = dynamicPredicateValueCtx;
        this.updateState(alarmDefinition, alarmState);
    }

    public boolean process(TbContext ctx, TbMsg msg, DataSnapshot data, SnapshotUpdate update) throws ExecutionException, InterruptedException {
        initCurrentAlarm(ctx);
        lastMsgMetaData = msg.getMetaData();
        lastMsgQueueName = msg.getQueueName();
        this.dataSnapshot = data;
        return createOrClearAlarms(ctx, msg, data, update, AlarmRuleState::eval);
    }

    public boolean process(TbContext ctx, long ts) throws ExecutionException, InterruptedException {
        initCurrentAlarm(ctx);
        return createOrClearAlarms(ctx, null, ts, null, (alarmState, tsParam) -> alarmState.eval(tsParam, dataSnapshot));
    }

    public <T> boolean createOrClearAlarms(TbContext ctx, TbMsg msg, T data, SnapshotUpdate update, BiFunction<AlarmRuleState, T, AlarmEvalResult> evalFunction) {
        boolean stateUpdate = false;
        AlarmRuleState resultState = null;
        log.debug("[{}] processing update: {}", alarmDefinition.getId(), data);
        for (AlarmRuleState state : createRulesSortedBySeverityDesc) {
            if (!validateUpdate(update, state)) {
                log.debug("[{}][{}] Update is not valid for current rule state", alarmDefinition.getId(), state.getSeverity());
                continue;
            }
            AlarmEvalResult evalResult = evalFunction.apply(state, data);
            stateUpdate |= state.checkUpdate();
            if (AlarmEvalResult.TRUE.equals(evalResult)) {
                resultState = state;
                break;
            } else if (AlarmEvalResult.FALSE.equals(evalResult)) {
                stateUpdate = clearAlarmState(stateUpdate, state);
            }
        }
        if (resultState != null) {
            TbAlarmResult result = calculateAlarmResult(ctx, resultState);
            if (result != null) {
                pushMsg(ctx, msg, result, resultState);
            }
            stateUpdate = clearAlarmState(stateUpdate, clearState);
        } else if (currentAlarm != null && clearState != null) {
            if (!validateUpdate(update, clearState)) {
                log.debug("[{}] Update is not valid for current clear state", alarmDefinition.getId());
                return stateUpdate;
            }
            AlarmEvalResult evalResult = evalFunction.apply(clearState, data);
            if (AlarmEvalResult.TRUE.equals(evalResult)) {
                stateUpdate = clearAlarmState(stateUpdate, clearState);
                for (AlarmRuleState state : createRulesSortedBySeverityDesc) {
                    stateUpdate = clearAlarmState(stateUpdate, state);
                }
                ListenableFuture<AlarmOperationResult> alarmClearOperationResult = ctx.getAlarmService().clearAlarmForResult(
                        ctx.getTenantId(), currentAlarm.getId(), createDetails(clearState), System.currentTimeMillis()
                );
                DonAsynchron.withCallback(alarmClearOperationResult,
                        result -> {
                            pushMsg(ctx, msg, new TbAlarmResult(false, false, true, result.getAlarm()), clearState);
                        },
                        throwable -> {
                            throw new RuntimeException(throwable);
                        });
                currentAlarm = null;
            } else if (AlarmEvalResult.FALSE.equals(evalResult)) {
                stateUpdate = clearAlarmState(stateUpdate, clearState);
            }
        }
        return stateUpdate;
    }

    public boolean clearAlarmState(boolean stateUpdate, AlarmRuleState state) {
        if (state != null) {
            state.clear();
            stateUpdate |= state.checkUpdate();
        }
        return stateUpdate;
    }

    public boolean validateUpdate(SnapshotUpdate update, AlarmRuleState state) {
        if (update != null) {
            //Check that the update type and that keys match.
            if (update.getType().equals(AlarmConditionKeyType.TIME_SERIES)) {
                return state.validateTsUpdate(update.getKeys());
            } else if (update.getType().equals(AlarmConditionKeyType.ATTRIBUTE)) {
                return state.validateAttrUpdate(update.getKeys());
            }
        }
        return true;
    }

    public void initCurrentAlarm(TbContext ctx) throws InterruptedException, ExecutionException {
        if (!initialFetchDone) {
            Alarm alarm = ctx.getAlarmService().findLatestByOriginatorAndType(ctx.getTenantId(), originator, alarmDefinition.getAlarmType()).get();
            if (alarm != null && !alarm.getStatus().isCleared()) {
                currentAlarm = alarm;
            }
            initialFetchDone = true;
        }
    }

    public void pushMsg(TbContext ctx, TbMsg msg, TbAlarmResult alarmResult, AlarmRuleState ruleState) {
        JsonNode jsonNodes = JacksonUtil.valueToTree(alarmResult.getAlarm());
        String data = jsonNodes.toString();
        TbMsgMetaData metaData = lastMsgMetaData != null ? lastMsgMetaData.copy() : new TbMsgMetaData();
        String relationType;
        if (alarmResult.isCreated()) {
            relationType = "Alarm Created";
            metaData.putValue(DataConstants.IS_NEW_ALARM, Boolean.TRUE.toString());
        } else if (alarmResult.isUpdated()) {
            relationType = "Alarm Updated";
            metaData.putValue(DataConstants.IS_EXISTING_ALARM, Boolean.TRUE.toString());
        } else if (alarmResult.isSeverityUpdated()) {
            relationType = "Alarm Severity Updated";
            metaData.putValue(DataConstants.IS_EXISTING_ALARM, Boolean.TRUE.toString());
            metaData.putValue(DataConstants.IS_SEVERITY_UPDATED_ALARM, Boolean.TRUE.toString());
        } else {
            relationType = "Alarm Cleared";
            metaData.putValue(DataConstants.IS_CLEARED_ALARM, Boolean.TRUE.toString());
        }
        setAlarmConditionMetadata(ruleState, metaData);
        TbMsg newMsg = ctx.newMsg(lastMsgQueueName != null ? lastMsgQueueName : ServiceQueue.MAIN, "ALARM",
                originator, msg != null ? msg.getCustomerId() : null, metaData, data);
        ctx.enqueueForTellNext(newMsg, relationType);
    }

    protected void setAlarmConditionMetadata(AlarmRuleState ruleState, TbMsgMetaData metaData) {
        if (ruleState.getSpec().getType() == AlarmConditionSpecType.REPEATING) {
            metaData.putValue(DataConstants.ALARM_CONDITION_REPEATS, String.valueOf(ruleState.getState().getEventCount()));
        }
        if (ruleState.getSpec().getType() == AlarmConditionSpecType.DURATION) {
            metaData.putValue(DataConstants.ALARM_CONDITION_DURATION, String.valueOf(ruleState.getState().getDuration()));
        }
    }

    public void updateState(DeviceProfileAlarm alarm, PersistedAlarmState alarmState) {
        this.alarmDefinition = alarm;
        this.createRulesSortedBySeverityDesc = new ArrayList<>();
        alarmDefinition.getCreateRules().forEach((severity, rule) -> {
            PersistedAlarmRuleState ruleState = null;
            if (alarmState != null) {
                ruleState = alarmState.getCreateRuleStates().get(severity);
                if (ruleState == null) {
                    ruleState = new PersistedAlarmRuleState();
                    alarmState.getCreateRuleStates().put(severity, ruleState);
                }
            }
            createRulesSortedBySeverityDesc.add(new AlarmRuleState(severity, rule,
                    deviceProfile.getCreateAlarmKeys(alarm.getId(), severity), ruleState, dynamicPredicateValueCtx));
        });
        createRulesSortedBySeverityDesc.sort(Comparator.comparingInt(state -> state.getSeverity().ordinal()));
        PersistedAlarmRuleState ruleState = alarmState == null ? null : alarmState.getClearRuleState();
        if (alarmDefinition.getClearRule() != null) {
            clearState = new AlarmRuleState(null, alarmDefinition.getClearRule(), deviceProfile.getClearAlarmKeys(alarm.getId()), ruleState, dynamicPredicateValueCtx);
        }
    }

    private TbAlarmResult calculateAlarmResult(TbContext ctx, AlarmRuleState ruleState) {
        AlarmSeverity severity = ruleState.getSeverity();
        if (currentAlarm != null) {
            // TODO: In some extremely rare cases, we might miss the event of alarm clear (If one use in-mem queue and restarted the server) or (if one manipulated the rule chain).
            // Maybe we should fetch alarm every time?
            currentAlarm.setEndTs(new Date(System.currentTimeMillis()));
            AlarmSeverity oldSeverity = currentAlarm.getSeverity();
            // Skip update if severity is decreased.
            if (severity.ordinal() <= oldSeverity.ordinal()) {
                currentAlarm.setDetails(createDetails(ruleState).asText());
                if (!oldSeverity.equals(severity)) {
                    currentAlarm.setSeverity(severity);
                    ctx.getAlarmService().saveOrUpdate(currentAlarm);
                    return new TbAlarmResult(false, false, true, false, currentAlarm);
                } else {
                    ctx.getAlarmService().saveOrUpdate(currentAlarm);
                    return new TbAlarmResult(false, true, false, false, currentAlarm);
                }
            } else {
                return null;
            }
        } else {
            currentAlarm = new Alarm();
            currentAlarm.setType(alarmDefinition.getAlarmType());
            currentAlarm.setStatus(AlarmStatus.ACTIVE_UNACK);
            currentAlarm.setSeverity(severity);
            long startTs = dataSnapshot.getTs();
            if (startTs == 0L) {
                startTs = System.currentTimeMillis();
            }
            currentAlarm.setStartTs(new Date(startTs));
            currentAlarm.setEndTs(currentAlarm.getStartTs());
            currentAlarm.setDetails(createDetails(ruleState).asText());
            currentAlarm.setOriginator(originator);
            currentAlarm.setTenantId(ctx.getTenantId());
           // currentAlarm.setPropagate(alarmDefinition.isPropagate());
//            if (alarmDefinition.getPropagateRelationTypes() != null) {
//                currentAlarm.setPropagateRelationTypes(alarmDefinition.getPropagateRelationTypes());
//            }
             ctx.getAlarmService().saveOrUpdate(currentAlarm);
            boolean updated = currentAlarm.getStartTs() != currentAlarm.getEndTs();
            return new TbAlarmResult(!updated, updated, false, false, currentAlarm);
        }
    }

    private JsonNode createDetails(AlarmRuleState ruleState) {
        JsonNode alarmDetails;
        String alarmDetailsStr = ruleState.getAlarmRule().getAlarmDetails();
        DashboardId dashboardId = ruleState.getAlarmRule().getDashboardId();

        if (StringUtils.isNotEmpty(alarmDetailsStr) || dashboardId != null) {
            ObjectNode newDetails = JacksonUtil.newObjectNode();
            if (StringUtils.isNotEmpty(alarmDetailsStr)) {
                for (AlarmConditionFilter keyFilter : ruleState.getAlarmRule().getCondition().getCondition()) {
                    EntityKeyValue entityKeyValue = dataSnapshot.getValue(keyFilter.getKey());
                    if (entityKeyValue != null) {
                        alarmDetailsStr = alarmDetailsStr.replaceAll(String.format("\\$\\{%s}", keyFilter.getKey().getKey()), getValueAsString(entityKeyValue));
                    }
                }
                newDetails.put("data", alarmDetailsStr);
            }
            if (dashboardId != null) {
                newDetails.put("dashboardId", dashboardId.getId().toString());
            }
            alarmDetails = newDetails;
        } else if (currentAlarm != null) {
            alarmDetails =JacksonUtil.toJsonNode(currentAlarm.getDetails());
        } else {
            alarmDetails = JacksonUtil.newObjectNode();
        }

        return alarmDetails;
    }

    private static String getValueAsString(EntityKeyValue entityKeyValue) {
        Object result = null;
        switch (entityKeyValue.getDataType()) {
            case STRING:
                result = entityKeyValue.getStrValue();
                break;
            case JSON:
                result = entityKeyValue.getJsonValue();
                break;
            case LONG:
                result = entityKeyValue.getLngValue();
                break;
            case DOUBLE:
                result = entityKeyValue.getDblValue();
                break;
            case BOOLEAN:
                result = entityKeyValue.getBoolValue();
                break;
        }
        return String.valueOf(result);
    }

    public boolean processAlarmClear(TbContext ctx, Alarm alarmNf) {
        boolean updated = false;
        if (currentAlarm != null && currentAlarm.getId().equals(alarmNf.getId())) {
            currentAlarm = null;
            for (AlarmRuleState state : createRulesSortedBySeverityDesc) {
                updated = clearAlarmState(updated, state);
            }
        }
        return updated;
    }

    public void processAckAlarm(Alarm alarm) {
        if (currentAlarm != null && currentAlarm.getId().equals(alarm.getId())) {
            currentAlarm.setStatus(alarm.getStatus());
            currentAlarm.setAckTs(alarm.getAckTs());
        }
    }
}
