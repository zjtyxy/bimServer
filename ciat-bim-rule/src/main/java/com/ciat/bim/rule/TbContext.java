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
package com.ciat.bim.rule;

import com.ciat.bim.data.id.*;
import com.ciat.bim.msg.*;
import com.ciat.bim.rule.engine.RuleEngineDeviceProfileCache;
import com.ciat.bim.rule.engine.action.ScriptEngine;
import com.ciat.bim.rule.engine.api.RuleEngineTelemetryService;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.rpc.RuleEngineRpcService;
import com.ciat.bim.server.timeseries.TimeseriesService;
import com.ciat.bim.server.utils.ListeningExecutor;
import io.netty.channel.EventLoopGroup;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.alarm.service.IAlarmService;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.jeecg.modules.device.service.IDeviceService;
import org.jeecg.modules.rule.entity.RuleNode;
import org.jeecg.modules.rule.entity.RuleNodeState;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantService;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by ashvayka on 13.01.18.
 */
public interface TbContext {

    /*
     *
     *  METHODS TO CONTROL THE MESSAGE FLOW
     *
     */

    /**
     * Indicates that message was successfully processed by the rule node.
     * Sends message to all Rule Nodes in the Rule Chain
     * that are connected to the current Rule Node using "Success" relationType.
     *
     * @param msg
     */
    void tellSuccess(TbMsg msg);

    /**
     * Sends message to all Rule Nodes in the Rule Chain
     * that are connected to the current Rule Node using specified relationType.
     *
     * @param msg
     * @param relationType
     */
    void tellNext(TbMsg msg, String relationType);

    /**
     * Sends message to all Rule Nodes in the Rule Chain
     * that are connected to the current Rule Node using one of specified relationTypes.
     *
     * @param msg
     * @param relationTypes
     */
    void tellNext(TbMsg msg, Set<String> relationTypes);

    /**
     * Sends message to the current Rule Node with specified delay in milliseconds.
     * Note: this message is not queued and may be lost in case of a server restart.
     *
     * @param msg
     */
    void tellSelf(TbMsg msg, long delayMs);

    /**
     * Notifies Rule Engine about failure to process current message.
     *
     * @param msg - message
     * @param th  - exception
     */
    void tellFailure(TbMsg msg, Throwable th);

    /**
     * Puts new message to queue for processing by the Root Rule Chain
     *
     * @param msg - message
     */
    void enqueue(TbMsg msg, Runnable onSuccess, Consumer<Throwable> onFailure);

    /**
     * Puts new message to custom queue for processing
     *
     * @param msg - message
     */
    void enqueue(TbMsg msg, String queueName, Runnable onSuccess, Consumer<Throwable> onFailure);

    void enqueueForTellFailure(TbMsg msg, String failureMessage);

    void enqueueForTellNext(TbMsg msg, String relationType);

    void enqueueForTellNext(TbMsg msg, Set<String> relationTypes);

    void enqueueForTellNext(TbMsg msg, String relationType, Runnable onSuccess, Consumer<Throwable> onFailure);

    void enqueueForTellNext(TbMsg msg, Set<String> relationTypes, Runnable onSuccess, Consumer<Throwable> onFailure);

    void enqueueForTellNext(TbMsg msg, String queueName, String relationType, Runnable onSuccess, Consumer<Throwable> onFailure);

    void enqueueForTellNext(TbMsg msg, String queueName, Set<String> relationTypes, Runnable onSuccess, Consumer<Throwable> onFailure);

    void ack(TbMsg tbMsg);

    TbMsg newMsg(String queueName, String type, EntityId originator, TbMsgMetaData metaData, String data);

    TbMsg newMsg(String queueName, String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, String data);

    TbMsg transformMsg(TbMsg origMsg, String type, EntityId originator, TbMsgMetaData metaData, String data);

//    TbMsg customerCreatedMsg(Customer customer, RuleNodeId ruleNodeId);
//
//    TbMsg deviceCreatedMsg(Device device, RuleNodeId ruleNodeId);
//
//    TbMsg assetCreatedMsg(Asset asset, RuleNodeId ruleNodeId);
//
//    // TODO: Does this changes the message?
    TbMsg alarmActionMsg(Alarm alarm, RuleNodeId ruleNodeId, String action);

    void onEdgeEventUpdate(String tenantId, EdgeId edgeId);

    /*
     *
     *  METHODS TO PROCESS THE MESSAGES
     *
     */

    boolean isLocalEntity(EntityId entityId);

    RuleNodeId getSelfId();

    RuleNode getSelf();

    String getRuleChainName();

    String getTenantId();

    IAttributeKvService getAttributesService();
//
//    CustomerService getCustomerService();
//
    ITenantService getTenantService();
//
//    UserService getUserService();
//
//    AssetService getAssetService();
//
     IDeviceService getDeviceService();
//
//    TbClusterService getClusterService();
//
//    DashboardService getDashboardService();
//
      IAlarmService getAlarmService();
//
//    RuleChainService getRuleChainService();
//
    RuleEngineRpcService getRpcService();

      RuleEngineTelemetryService getTelemetryService();

    TimeseriesService getTimeseriesService();

//    RelationService getRelationService();
//
//    EntityViewService getEntityViewService();
//
//    ResourceService getResourceService();
//
//    OtaPackageService getOtaPackageService();

    RuleEngineDeviceProfileCache getDeviceProfileCache();

//    EdgeService getEdgeService();
//
//    EdgeEventService getEdgeEventService();
//
//    ListeningExecutor getMailExecutor();
//
//    ListeningExecutor getSmsExecutor();
//
      ListeningExecutor getDbCallbackExecutor();
//
//    ListeningExecutor getExternalCallExecutor();
//
//    MailService getMailService(boolean isSystem);
//
//    SmsService getSmsService();
//
//    SmsSenderFactory getSmsSenderFactory();
//
    ScriptEngine createJsScriptEngine(String script, String... argNames);

    void logJsEvalRequest();

    void logJsEvalResponse();

    void logJsEvalFailure();

    String getServiceId();
//
    EventLoopGroup getSharedEventLoop();
//
//    CassandraCluster getCassandraCluster();
//
//    TbResultSetFuture submitCassandraReadTask(CassandraStatementTask task);
//
//    TbResultSetFuture submitCassandraWriteTask(CassandraStatementTask task);

    PageData<RuleNodeState> findRuleNodeStates(PageLink pageLink);

    RuleNodeState findRuleNodeStateForEntity(EntityId entityId);

    void removeRuleNodeStateForEntity(EntityId entityId);

    RuleNodeState saveRuleNodeState(RuleNodeState state);

    void clearRuleNodeStates();

    void addTenantProfileListener(Consumer<TenantProfile> listener);

    void addDeviceProfileListeners(Consumer<DeviceProfile> listener, BiConsumer<DeviceId, DeviceProfile> deviceListener);

    void removeListeners();

    TenantProfile getTenantProfile();
}
