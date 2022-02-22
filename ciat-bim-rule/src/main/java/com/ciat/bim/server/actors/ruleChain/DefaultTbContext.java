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
package com.ciat.bim.server.actors.ruleChain;

import com.ciat.bim.common.data.rule.TbRelationTypes;
import com.ciat.bim.data.DataConstants;

import com.ciat.bim.data.id.*;
import com.ciat.bim.msg.*;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.rule.TbContext;
import com.ciat.bim.rule.engine.RuleEngineDeviceProfileCache;
import com.ciat.bim.rule.engine.action.RuleNodeJsScriptEngine;
import com.ciat.bim.rule.engine.action.ScriptEngine;
import com.ciat.bim.rule.engine.api.RuleEngineTelemetryService;
import com.ciat.bim.rule.engine.mail.MailService;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActorRef;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.queue.TbQueueMsgMetadata;
import com.ciat.bim.server.rpc.RuleEngineRpcService;
import com.ciat.bim.server.timeseries.TimeseriesService;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.utils.ListeningExecutor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.service.IAlarmService;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.jeecg.modules.device.service.IDeviceService;
import org.jeecg.modules.rule.entity.RuleNode;
import org.jeecg.modules.rule.entity.RuleNodeState;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantService;
import org.springframework.util.StringUtils;

import javax.management.relation.RelationService;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by ashvayka on 19.03.18.
 */
@Slf4j
class DefaultTbContext implements TbContext {

    public final static ObjectMapper mapper = new ObjectMapper();

    private final ActorSystemContext mainCtx;
    private final String ruleChainName;
    private final RuleNodeCtx nodeCtx;

    public DefaultTbContext(ActorSystemContext mainCtx, String ruleChainName, RuleNodeCtx nodeCtx) {
        this.mainCtx = mainCtx;
        this.ruleChainName = ruleChainName;
        this.nodeCtx = nodeCtx;
    }

    @Override
    public void tellSuccess(TbMsg msg) {
        tellNext(msg, Collections.singleton(TbRelationTypes.SUCCESS), null);
    }

    @Override
    public void tellNext(TbMsg msg, String relationType) {
        tellNext(msg, Collections.singleton(relationType), null);
    }

    @Override
    public void tellNext(TbMsg msg, Set<String> relationTypes) {
        tellNext(msg, relationTypes, null);
    }

    private void tellNext(TbMsg msg, Set<String> relationTypes, Throwable th) {
//        if (nodeCtx.getSelf().isDebugMode()) {
//            relationTypes.forEach(relationType -> mainCtx.persistDebugOutput(nodeCtx.getTenantId(), nodeCtx.getSelf().getId(), msg, relationType, th));
//        }
        msg.getCallback().onProcessingEnd(RuleNodeId.fromString(nodeCtx.getSelf().getId()));
        nodeCtx.getChainActor().tell(new RuleNodeToRuleChainTellNextMsg(RuleChainId.fromString(nodeCtx.getSelf().getRuleChainId()), nodeCtx.getSelf().getId(),
                relationTypes, msg, th != null ? th.getMessage() : null));
    }

    @Override
    public void tellSelf(TbMsg msg, long delayMs) {
        //TODO: add persistence layer
        scheduleMsgWithDelay(new RuleNodeToSelfMsg(this, msg), delayMs, nodeCtx.getSelfActor());
    }

    @Override
    public void enqueue(TbMsg tbMsg, Runnable onSuccess, Consumer<Throwable> onFailure) {
        TopicPartitionInfo tpi = mainCtx.resolve(ServiceType.TB_RULE_ENGINE, getTenantId(), tbMsg.getOriginator());
        enqueue(tpi, tbMsg, onFailure, onSuccess);
    }

    @Override
    public void enqueue(TbMsg tbMsg, String queueName, Runnable onSuccess, Consumer<Throwable> onFailure) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg, queueName);
        enqueue(tpi, tbMsg, onFailure, onSuccess);
    }

    private void enqueue(TopicPartitionInfo tpi, TbMsg tbMsg, Consumer<Throwable> onFailure, Runnable onSuccess) {
        TransportProtos.ToRuleEngineMsg msg = TransportProtos.ToRuleEngineMsg.newBuilder()
                .setTenantIdMSB(Long.parseLong(getTenantId()))
                .setTenantIdLSB(Long.parseLong(getTenantId()))
                .setTbMsg(TbMsg.toByteString(tbMsg)).build();
//        if (nodeCtx.getSelf().isDebugMode()) {
//            mainCtx.persistDebugOutput(nodeCtx.getTenantId(), nodeCtx.getSelf().getId(), tbMsg, "To Root Rule Chain");
//        }
        mainCtx.getClusterService().pushMsgToRuleEngine(tpi, tbMsg.getId(), msg, new SimpleTbQueueCallback(onSuccess, onFailure));
    }

    @Override
    public void enqueueForTellFailure(TbMsg tbMsg, String failureMessage) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg);
        enqueueForTellNext(tpi, tbMsg, Collections.singleton(TbRelationTypes.FAILURE), failureMessage, null, null);
    }

    @Override
    public void enqueueForTellNext(TbMsg tbMsg, String relationType) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg);
        enqueueForTellNext(tpi, tbMsg, Collections.singleton(relationType), null, null, null);
    }

    @Override
    public void enqueueForTellNext(TbMsg tbMsg, Set<String> relationTypes) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg);
        enqueueForTellNext(tpi, tbMsg, relationTypes, null, null, null);
    }

    @Override
    public void enqueueForTellNext(TbMsg tbMsg, String relationType, Runnable onSuccess, Consumer<Throwable> onFailure) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg);
        enqueueForTellNext(tpi, tbMsg, Collections.singleton(relationType), null, onSuccess, onFailure);
    }

    @Override
    public void enqueueForTellNext(TbMsg tbMsg, Set<String> relationTypes, Runnable onSuccess, Consumer<Throwable> onFailure) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg);
        enqueueForTellNext(tpi, tbMsg, relationTypes, null, onSuccess, onFailure);
    }

    @Override
    public void enqueueForTellNext(TbMsg tbMsg, String queueName, String relationType, Runnable onSuccess, Consumer<Throwable> onFailure) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg, queueName);
        enqueueForTellNext(tpi, queueName, tbMsg, Collections.singleton(relationType), null, onSuccess, onFailure);
    }

    @Override
    public void enqueueForTellNext(TbMsg tbMsg, String queueName, Set<String> relationTypes, Runnable onSuccess, Consumer<Throwable> onFailure) {
        TopicPartitionInfo tpi = resolvePartition(tbMsg, queueName);
        enqueueForTellNext(tpi, queueName, tbMsg, relationTypes, null, onSuccess, onFailure);
    }

    private TopicPartitionInfo resolvePartition(TbMsg tbMsg, String queueName) {
        if (StringUtils.isEmpty(queueName)) {
            queueName = ServiceQueue.MAIN;
        }
        return mainCtx.resolve(ServiceType.TB_RULE_ENGINE, queueName, getTenantId(), tbMsg.getOriginator());
    }

    private TopicPartitionInfo resolvePartition(TbMsg tbMsg) {
        return resolvePartition(tbMsg, tbMsg.getQueueName());
    }

    private void enqueueForTellNext(TopicPartitionInfo tpi, TbMsg source, Set<String> relationTypes, String failureMessage, Runnable onSuccess, Consumer<Throwable> onFailure) {
        enqueueForTellNext(tpi, source.getQueueName(), source, relationTypes, failureMessage, onSuccess, onFailure);
    }

    private void enqueueForTellNext(TopicPartitionInfo tpi, String queueName, TbMsg source, Set<String> relationTypes, String failureMessage, Runnable onSuccess, Consumer<Throwable> onFailure) {
        String ruleChainId =  nodeCtx.getSelf().getRuleChainId();
        String ruleNodeId =  nodeCtx.getSelf().getId();
        TbMsg tbMsg = TbMsg.newMsg(source, queueName, ruleChainId, ruleNodeId);
        TransportProtos.ToRuleEngineMsg.Builder msg = TransportProtos.ToRuleEngineMsg.newBuilder()
                .setTenantIdMSB(Long.parseLong(getTenantId()))
                .setTenantIdLSB(Long.parseLong(getTenantId()))
                .setTbMsg(TbMsg.toByteString(tbMsg))
                .addAllRelationTypes(relationTypes);
        if (failureMessage != null) {
            msg.setFailureMessage(failureMessage);
        }
//        if (nodeCtx.getSelf().isDebugMode()) {
//            relationTypes.forEach(relationType ->
//                    mainCtx.persistDebugOutput(nodeCtx.getTenantId(), nodeCtx.getSelf().getId(), tbMsg, relationType, null, failureMessage));
//        }
        mainCtx.getClusterService().pushMsgToRuleEngine(tpi, tbMsg.getId(), msg.build(), new SimpleTbQueueCallback(onSuccess, onFailure));
    }

    @Override
    public void ack(TbMsg tbMsg) {
//        if (nodeCtx.getSelf().isDebugMode()) {
//            mainCtx.persistDebugOutput(nodeCtx.getTenantId(), nodeCtx.getSelf().getId(), tbMsg, "ACK", null);
//        }
        tbMsg.getCallback().onProcessingEnd(RuleNodeId.fromString(nodeCtx.getSelf().getId()));
        tbMsg.getCallback().onSuccess();
    }

    @Override
    public boolean isLocalEntity(EntityId entityId) {
        return mainCtx.resolve(ServiceType.TB_RULE_ENGINE, getTenantId(), entityId).isMyPartition();
    }

    private void scheduleMsgWithDelay(TbActorMsg msg, long delayInMs, TbActorRef target) {
        mainCtx.scheduleMsgWithDelay(target, msg, delayInMs);
    }

    @Override
    public void tellFailure(TbMsg msg, Throwable th) {
//        if (nodeCtx.getSelf().isDebugMode()) {
//            mainCtx.persistDebugOutput(nodeCtx.getTenantId(), nodeCtx.getSelf().getId(), msg, TbRelationTypes.FAILURE, th);
//        }
        String failureMessage;
        if (th != null) {
            if (!StringUtils.isEmpty(th.getMessage())) {
                failureMessage = th.getMessage();
            } else {
                failureMessage = th.getClass().getSimpleName();
            }
        } else {
            failureMessage = null;
        }
        nodeCtx.getChainActor().tell(new RuleNodeToRuleChainTellNextMsg(RuleChainId.fromString(nodeCtx.getSelf().getRuleChainId()),
              nodeCtx.getSelf().getId(), Collections.singleton(TbRelationTypes.FAILURE),
                msg, failureMessage));
    }

    public void updateSelf(RuleNode self) {
        nodeCtx.setSelf(self);
    }

    @Override
    public TbMsg newMsg(String queueName, String type, EntityId originator, TbMsgMetaData metaData, String data) {
        return newMsg(queueName, type, originator, null, metaData, data);
    }

    @Override
    public TbMsg newMsg(String queueName, String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, String data) {
        return TbMsg.newMsg(queueName, type, originator, customerId, metaData, data, nodeCtx.getSelf().getRuleChainId(),nodeCtx.getSelf().getId());
    }

    @Override
    public TbMsg transformMsg(TbMsg origMsg, String type, EntityId originator, TbMsgMetaData metaData, String data) {
        return TbMsg.transformMsg(origMsg, type, originator, metaData, data);
    }

//    public TbMsg customerCreatedMsg(Customer customer, RuleNodeId ruleNodeId) {
//        return entityActionMsg(customer, customer.getId(), ruleNodeId, DataConstants.ENTITY_CREATED);
//    }

    public TbMsg deviceCreatedMsg(Device device, RuleNodeId ruleNodeId) {
        RuleChainId ruleChainId = null;
        String queueName = ServiceQueue.MAIN;
        if (device.getDeviceProfileId() != null) {
            DeviceProfile deviceProfile = mainCtx.getDeviceProfileCache().find(DeviceProfileId.fromString(device.getDeviceProfileId()));
            if (deviceProfile == null) {
                log.warn("[{}] Device profile is null!", device.getDeviceProfileId());
                ruleChainId = null;
                queueName = ServiceQueue.MAIN;
            } else {
                ruleChainId =RuleChainId.fromString(deviceProfile.getDefaultRuleChainId());
                String defaultQueueName = deviceProfile.getDefaultQueueName();
                queueName = defaultQueueName != null ? defaultQueueName : ServiceQueue.MAIN;
            }
        }
        return entityActionMsg(device, DeviceId.fromString(device.getId()), ruleNodeId, DataConstants.ENTITY_CREATED, queueName, ruleChainId);
    }

//    public TbMsg assetCreatedMsg(Asset asset, RuleNodeId ruleNodeId) {
//        return entityActionMsg(asset, asset.getId(), ruleNodeId, DataConstants.ENTITY_CREATED);
//    }

    public TbMsg alarmActionMsg(Alarm alarm, RuleNodeId ruleNodeId, String action) {
        RuleChainId ruleChainId = null;
        String queueName = ServiceQueue.MAIN;
        if (EntityType.DEVICE.equals(alarm.getOriginator().getEntityType())) {
            DeviceId deviceId = new DeviceId(alarm.getOriginator().getId());
            DeviceProfile deviceProfile = mainCtx.getDeviceProfileCache().get(getTenantId(), deviceId);
            if (deviceProfile == null) {
                log.warn("[{}] Device profile is null!", deviceId);
                ruleChainId = null;
                queueName = ServiceQueue.MAIN;
            } else {
                ruleChainId =RuleChainId.fromString( deviceProfile.getDefaultRuleChainId());
                String defaultQueueName = deviceProfile.getDefaultQueueName();
                queueName = defaultQueueName != null ? defaultQueueName : ServiceQueue.MAIN;
            }
        }
        return entityActionMsg(alarm, AlarmId.fromString(alarm.getId()), ruleNodeId, action, queueName, ruleChainId);
    }

    @Override
    public void onEdgeEventUpdate(String tenantId, EdgeId edgeId) {
        mainCtx.getClusterService().onEdgeEventUpdate(tenantId, edgeId);
    }

    public <E, I extends EntityId> TbMsg entityActionMsg(E entity, I id, RuleNodeId ruleNodeId, String action) {
        return entityActionMsg(entity, id, ruleNodeId, action, ServiceQueue.MAIN, null);
    }

    public <E, I extends EntityId> TbMsg entityActionMsg(E entity, I id, RuleNodeId ruleNodeId, String action, String queueName, RuleChainId ruleChainId) {
        try {
            return TbMsg.newMsg(queueName, action, id, getActionMetaData(ruleNodeId), mapper.writeValueAsString(mapper.valueToTree(entity)), ruleChainId.getId(), null);
        } catch (JsonProcessingException | IllegalArgumentException e) {
            throw new RuntimeException("Failed to process " + id.getEntityType().name().toLowerCase() + " " + action + " msg: " + e);
        }
    }

    @Override
    public RuleNodeId getSelfId() {
        return RuleNodeId.fromString(nodeCtx.getSelf().getId());
    }

    @Override
    public RuleNode getSelf() {
        return nodeCtx.getSelf();
    }

    @Override
    public String getRuleChainName() {
        return ruleChainName;
    }

    @Override
    public String getTenantId() {
        return nodeCtx.getTenantId();
    }

    @Override
    public ListeningExecutor getMailExecutor() {
        return mainCtx.getMailExecutor();
    }

//    @Override
//    public ListeningExecutor getSmsExecutor() {
//        return mainCtx.getSmsExecutor();
//    }
//
    @Override
    public ListeningExecutor getDbCallbackExecutor() {
        return mainCtx.getDbCallbackExecutor();
    }
//
//    @Override
//    public ListeningExecutor getExternalCallExecutor() {
//        return mainCtx.getExternalCallExecutorService();
//    }
//
    @Override
    public ScriptEngine createJsScriptEngine(String script, String... argNames) {
        return new RuleNodeJsScriptEngine(TenantId.fromString(getTenantId()), mainCtx.getJsSandbox(),RuleNodeId.fromString(nodeCtx.getSelf().getId()), script, argNames);
    }

    @Override
    public void logJsEvalRequest() {
        if (mainCtx.isStatisticsEnabled()) {
            mainCtx.getJsInvokeStats().incrementRequests();
        }
    }

    @Override
    public void logJsEvalResponse() {
        if (mainCtx.isStatisticsEnabled()) {
            mainCtx.getJsInvokeStats().incrementResponses();
        }
    }

    @Override
    public void logJsEvalFailure() {
        if (mainCtx.isStatisticsEnabled()) {
            mainCtx.getJsInvokeStats().incrementFailures();
        }
    }

    @Override
    public String getServiceId() {
        return mainCtx.getServiceInfoProvider().getServiceId();
    }

    @Override
    public IAttributeKvService getAttributesService() {
        return mainCtx.getAttributesService();
    }

//    @Override
//    public CustomerService getCustomerService() {
//        return mainCtx.getCustomerService();
//    }
//
    @Override
    public ITenantService getTenantService() {
        return mainCtx.getTenantService();
    }
//
//    @Override
//    public UserService getUserService() {
//        return mainCtx.getUserService();
//    }
//
//    @Override
//    public AssetService getAssetService() {
//        return mainCtx.getAssetService();
//    }

    @Override
    public IDeviceService getDeviceService() {
        return mainCtx.getDeviceService();
    }

//    @Override
//    public TbClusterService getClusterService() {
//        return mainCtx.getClusterService();
//    }
//
//    @Override
//    public DashboardService getDashboardService() {
//        return mainCtx.getDashboardService();
//    }
//
    @Override
    public IAlarmService getAlarmService() {
        return mainCtx.getAlarmService();
    }

//    @Override
//    public RuleChainService getRuleChainService() {
//        return mainCtx.getRuleChainService();
//    }

    @Override
    public TimeseriesService getTimeseriesService() {
        return mainCtx.getTsService();
    }

    @Override
    public RuleEngineTelemetryService getTelemetryService() {
        return mainCtx.getTsSubService();
    }

//    @Override
//    public RelationService getRelationService() {
//        return mainCtx.getRelationService();
//    }
//
//    @Override
//    public EntityViewService getEntityViewService() {
//        return mainCtx.getEntityViewService();
//    }
//
//    @Override
//    public ResourceService getResourceService() {
//        return mainCtx.getResourceService();
//    }
//
//    @Override
//    public OtaPackageService getOtaPackageService() {
//        return mainCtx.getOtaPackageService();
//    }
//
    @Override
    public RuleEngineDeviceProfileCache getDeviceProfileCache() {
        return mainCtx.getDeviceProfileCache();
    }

//    @Override
//    public EdgeService getEdgeService() {
//        return mainCtx.getEdgeService();
//    }
//
//    @Override
//    public EdgeEventService getEdgeEventService() {
//        return mainCtx.getEdgeEventService();
//    }

    @Override
    public EventLoopGroup getSharedEventLoop() {
        return mainCtx.getSharedEventLoopGroupService().getSharedEventLoopGroup();
    }

    @Override
    public MailService getMailService(boolean isSystem) {
        if (!isSystem || mainCtx.isAllowSystemMailService()) {
            return mainCtx.getMailService();
        } else {
            throw new RuntimeException("Access to System Mail Service is forbidden!");
        }
    }

//    @Override
//    public SmsService getSmsService() {
//        if (mainCtx.isAllowSystemSmsService()) {
//            return mainCtx.getSmsService();
//        } else {
//            throw new RuntimeException("Access to System SMS Service is forbidden!");
//        }
//    }
//
//    @Override
//    public SmsSenderFactory getSmsSenderFactory() {
//        return mainCtx.getSmsSenderFactory();
//    }

    @Override
    public RuleEngineRpcService getRpcService() {
        return mainCtx.getTbRuleEngineDeviceRpcService();
    }

//    @Override
//    public CassandraCluster getCassandraCluster() {
//        return mainCtx.getCassandraCluster();
//    }
//
//    @Override
//    public TbResultSetFuture submitCassandraReadTask(CassandraStatementTask task) {
//        return mainCtx.getCassandraBufferedRateReadExecutor().submit(task);
//    }
//
//    @Override
//    public TbResultSetFuture submitCassandraWriteTask(CassandraStatementTask task) {
//        return mainCtx.getCassandraBufferedRateWriteExecutor().submit(task);
//    }

    @Override
    public PageData<RuleNodeState> findRuleNodeStates(PageLink pageLink) {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}] Fetch Rule Node States.", getTenantId(), getSelfId());
        }
        return mainCtx.getRuleNodeStateService().findByRuleNodeId(getTenantId(), getSelfId(), pageLink);
    }

    @Override
    public RuleNodeState findRuleNodeStateForEntity(EntityId entityId) {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}][{}] Fetch Rule Node State for entity.", getTenantId(), getSelfId(), entityId);
        }
        return mainCtx.getRuleNodeStateService().findByRuleNodeIdAndEntityId(getTenantId(), getSelfId(), entityId);
    }

    @Override
    public RuleNodeState saveRuleNodeState(RuleNodeState state) {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}][{}] Persist Rule Node State for entity: {}", getTenantId(), getSelfId(), state.getEntityId(), state.getStateData());
        }
        state.setRuleNodeId(getSelfId().getId());
        //getTenantId(),
          mainCtx.getRuleNodeStateService().save( state);
          return  state;
    }

    @Override
    public void clearRuleNodeStates() {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}] Going to clear rule node states", getTenantId(), getSelfId());
        }
        mainCtx.getRuleNodeStateService().removeByRuleNodeId(getTenantId(), getSelfId());
    }

    @Override
    public void removeRuleNodeStateForEntity(EntityId entityId) {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}][{}] Remove Rule Node State for entity.", getTenantId(), getSelfId(), entityId);
        }
        mainCtx.getRuleNodeStateService().removeByRuleNodeIdAndEntityId(getTenantId(), getSelfId(), entityId);
    }

    @Override
    public void addTenantProfileListener(Consumer<TenantProfile> listener) {
        mainCtx.getTenantProfileCache().addListener(TenantId.fromString(getTenantId()), getSelfId(), listener);
    }

    @Override
    public void addDeviceProfileListeners(Consumer<DeviceProfile> profileListener, BiConsumer<DeviceId, DeviceProfile> deviceListener) {
        mainCtx.getDeviceProfileCache().addListener(getTenantId(), getSelfId(), profileListener, deviceListener);
    }

    @Override
    public void removeListeners() {
        mainCtx.getDeviceProfileCache().removeListener(getTenantId(), getSelfId());
        mainCtx.getTenantProfileCache().removeListener(TenantId.fromString(getTenantId()), getSelfId());
    }

    @Override
    public TenantProfile getTenantProfile() {
        return mainCtx.getTenantProfileCache().get(TenantId.fromString(getTenantId()));
    }

    private TbMsgMetaData getActionMetaData(RuleNodeId ruleNodeId) {
        TbMsgMetaData metaData = new TbMsgMetaData();
        metaData.putValue("ruleNodeId", ruleNodeId.toString());
        return metaData;
    }

    private class SimpleTbQueueCallback implements TbQueueCallback {
        private final Runnable onSuccess;
        private final Consumer<Throwable> onFailure;

        public SimpleTbQueueCallback(Runnable onSuccess, Consumer<Throwable> onFailure) {
            this.onSuccess = onSuccess;
            this.onFailure = onFailure;
        }

        @Override
        public void onSuccess(TbQueueMsgMetadata metadata) {
            if (onSuccess != null) {
                onSuccess.run();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (onFailure != null) {
                onFailure.accept(t);
            } else {
                log.debug("[{}] Failed to put item into queue", nodeCtx.getTenantId(), t);
            }
        }
    }
}
