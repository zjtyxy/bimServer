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


import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.PartitionChangeMsg;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.rule.RuleNodeInfo;
import com.ciat.bim.rule.TbNode;
import com.ciat.bim.rule.TbNodeConfiguration;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActorCtx;
import com.ciat.bim.server.actors.TbActorRef;
import com.ciat.bim.server.actors.TbRuleNodeUpdateException;
import com.ciat.bim.server.actors.shared.ComponentLifecycleState;
import com.ciat.bim.server.actors.shared.ComponentMsgProcessor;
import com.ciat.bim.server.common.data.ApiUsageRecordKey;
import com.ciat.bim.server.common.msg.queue.RuleNodeException;
import com.ciat.bim.server.queue.usagestats.TbApiUsageClient;
import com.ciat.bim.server.utils.RuleNodeTypeUtils;
import org.jeecg.modules.rule.entity.RuleNode;

/**
 * @author Andrew Shvayka
 */
public class RuleNodeActorMessageProcessor extends ComponentMsgProcessor<RuleNodeId> {

    private final String ruleChainName;
    private final TbActorRef self;
    private final TbApiUsageClient apiUsageClient;
    private RuleNode ruleNode;
    private TbNode tbNode;
    private DefaultTbContext defaultCtx;
    private RuleNodeInfo info;

    RuleNodeActorMessageProcessor(String tenantId, String ruleChainName, String ruleNodeId, ActorSystemContext systemContext
            , TbActorRef parent, TbActorRef self) {
        super(systemContext, tenantId, ruleNodeId);
        this.apiUsageClient = systemContext.getApiUsageClient();
        this.ruleChainName = ruleChainName;
        this.self = self;
        this.ruleNode = systemContext.getRuleChainService().findRuleNodeById(tenantId, entityId);
        this.defaultCtx = new DefaultTbContext(systemContext, ruleChainName, new RuleNodeCtx(tenantId, parent, self, ruleNode));
        this.info = new RuleNodeInfo(ruleNodeId, ruleChainName, ruleNode != null ? ruleNode.getName() : "Unknown");
    }

    @Override
    public void start(TbActorCtx context) throws Exception {
        tbNode = initComponent(ruleNode);
        if (tbNode != null) {
            state = ComponentLifecycleState.ACTIVE;
        }
    }

    @Override
    public void onUpdate(TbActorCtx context) throws Exception {
        RuleNode newRuleNode = systemContext.getRuleChainService().findRuleNodeById(tenantId, entityId);
        this.info = new RuleNodeInfo(entityId, ruleChainName, newRuleNode != null ? newRuleNode.getName() : "Unknown");
        boolean restartRequired = state != ComponentLifecycleState.ACTIVE ||
                !(ruleNode.getType().equals(newRuleNode.getType()) && ruleNode.getConfiguration().equals(newRuleNode.getConfiguration()));
        this.ruleNode = newRuleNode;
        this.defaultCtx.updateSelf(newRuleNode);
        if (restartRequired) {
            if (tbNode != null) {
                tbNode.destroy();
            }
            try {
                start(context);
            } catch (Exception e) {
                throw new TbRuleNodeUpdateException("Failed to update rule node", e);
            }
        }
    }

    @Override
    public void stop(TbActorCtx context) {
        if (tbNode != null) {
            tbNode.destroy();
            state = ComponentLifecycleState.SUSPENDED;
        }
    }

    @Override
    public void onPartitionChangeMsg(PartitionChangeMsg msg) {
        if (tbNode != null) {
            tbNode.onPartitionChangeMsg(defaultCtx, msg);
        }
    }

    public void onRuleToSelfMsg(RuleNodeToSelfMsg msg) throws Exception {
        checkActive(msg.getMsg());
        TbMsg tbMsg = msg.getMsg();
        int ruleNodeCount = tbMsg.getAndIncrementRuleNodeCounter();
        int maxRuleNodeExecutionsPerMessage = getTenantProfileConfiguration().getMaxRuleNodeExecsPerMessage();
        if (maxRuleNodeExecutionsPerMessage == 0 || ruleNodeCount < maxRuleNodeExecutionsPerMessage) {
            apiUsageClient.report(tenantId, tbMsg.getCustomerId(), ApiUsageRecordKey.RE_EXEC_COUNT);
//            if (ruleNode.isDebugMode()) {
//                systemContext.persistDebugInput(tenantId, entityId, msg.getMsg(), "Self");
//            }
            try {
                tbNode.onMsg(defaultCtx, msg.getMsg());
            } catch (Exception e) {
                defaultCtx.tellFailure(msg.getMsg(), e);
            }
        } else {
            tbMsg.getCallback().onFailure(new RuleNodeException("Message is processed by more then " + maxRuleNodeExecutionsPerMessage + " rule nodes!", ruleChainName, ruleNode));
        }
    }

    void onRuleChainToRuleNodeMsg(RuleChainToRuleNodeMsg msg) throws Exception {
        msg.getMsg().getCallback().onProcessingStart(info);
        checkActive(msg.getMsg());
        TbMsg tbMsg = msg.getMsg();
        int ruleNodeCount = tbMsg.getAndIncrementRuleNodeCounter();
        int maxRuleNodeExecutionsPerMessage = getTenantProfileConfiguration().getMaxRuleNodeExecsPerMessage();
        if (maxRuleNodeExecutionsPerMessage == 0 || ruleNodeCount < maxRuleNodeExecutionsPerMessage) {
            apiUsageClient.report(tenantId, tbMsg.getCustomerId(), ApiUsageRecordKey.RE_EXEC_COUNT);
//            if (ruleNode.isDebugMode()) {
//                systemContext.persistDebugInput(tenantId, entityId, msg.getMsg(), msg.getFromRelationType());
//            }
            try {
                tbNode.onMsg(msg.getCtx(), msg.getMsg());
            } catch (Exception e) {
                msg.getCtx().tellFailure(msg.getMsg(), e);
            }
        } else {
            tbMsg.getCallback().onFailure(new RuleNodeException("Message is processed by more then " + maxRuleNodeExecutionsPerMessage + " rule nodes!", ruleChainName, ruleNode));
        }
    }

    @Override
    public String getComponentName() {
        return ruleNode.getName();
    }

    /***
     * 创建RuleNode
     * @param ruleNode
     * @return
     * @throws Exception
     */
    private TbNode initComponent(RuleNode ruleNode) throws Exception {
        TbNode tbNode = null;
        if (ruleNode != null) {
          //  Class<?> componentClazz = Class.forName(ruleNode.getType());
            Class<?> componentClazz =  RuleNodeTypeUtils.getNodeType(ruleNode.getType());
            tbNode = (TbNode) (componentClazz.getDeclaredConstructor().newInstance());
            tbNode.init(defaultCtx, new TbNodeConfiguration(ruleNode.getConfiguration()));
        }
        return tbNode;
    }

    @Override
    protected RuleNodeException getInactiveException() {
        return new RuleNodeException("Rule Node is not active! Failed to initialize.", ruleChainName, ruleNode);
    }
}
