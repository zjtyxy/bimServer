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

import com.ciat.bim.data.id.TbEntityActorId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.PartitionChangeMsg;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.ContextBasedCreator;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActor;
import com.ciat.bim.server.actors.TbActorCtx;
import com.ciat.bim.server.actors.TbActorId;
import com.ciat.bim.server.actors.service.ComponentActor;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class RuleNodeActor extends ComponentActor<RuleNodeId, RuleNodeActorMessageProcessor> {

    private final String ruleChainName;
    private final String ruleChainId;
    private final String ruleNodeId;

    private RuleNodeActor(ActorSystemContext systemContext, String tenantId, String ruleChainId, String ruleChainName, String ruleNodeId) {
        super(systemContext, tenantId, ruleNodeId);
        this.ruleChainName = ruleChainName;
        this.ruleChainId = ruleChainId;
        this.ruleNodeId = ruleNodeId;
    }

    @Override
    protected RuleNodeActorMessageProcessor createProcessor(TbActorCtx ctx) {
        return new RuleNodeActorMessageProcessor(tenantId, this.ruleChainName, ruleNodeId, systemContext, ctx.getParentRef(), ctx);
    }

    @Override
    protected boolean doProcess(TbActorMsg msg) {
        switch (msg.getMsgType()) {
            case COMPONENT_LIFE_CYCLE_MSG:
            case RULE_NODE_UPDATED_MSG:
                onComponentLifecycleMsg((ComponentLifecycleMsg) msg);
                break;
            case RULE_CHAIN_TO_RULE_MSG:
                onRuleChainToRuleNodeMsg((RuleChainToRuleNodeMsg) msg);
                break;
            case RULE_TO_SELF_MSG:
                onRuleNodeToSelfMsg((RuleNodeToSelfMsg) msg);
                break;
            case STATS_PERSIST_TICK_MSG:
                onStatsPersistTick(id);
                break;
            case PARTITION_CHANGE_MSG:
                onClusterEventMsg((PartitionChangeMsg) msg);
                break;
            default:
                return false;
        }
        return true;
    }

    private void onRuleNodeToSelfMsg(RuleNodeToSelfMsg msg) {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}][{}] Going to process rule msg: {}", ruleChainId, id, processor.getComponentName(), msg.getMsg());
        }
        try {
            processor.onRuleToSelfMsg(msg);
            increaseMessagesProcessedCount();
        } catch (Exception e) {
            logAndPersist("onRuleMsg", e);
        }
    }

    private void onRuleChainToRuleNodeMsg(RuleChainToRuleNodeMsg msg) {
        if (log.isDebugEnabled()) {
            log.debug("[{}][{}][{}] Going to process rule msg: {}", ruleChainId, id, processor.getComponentName(), msg.getMsg());
        }
        try {
            processor.onRuleChainToRuleNodeMsg(msg);
            increaseMessagesProcessedCount();
        } catch (Exception e) {
            logAndPersist("onRuleMsg", e);
        }
    }

    public static class ActorCreator extends ContextBasedCreator {

        private final String tenantId;
        private final String ruleChainId;
        private final String ruleChainName;
        private final String ruleNodeId;

        public ActorCreator(ActorSystemContext context, String tenantId, String ruleChainId, String ruleChainName, String ruleNodeId) {
            super(context);
            this.tenantId = tenantId;
            this.ruleChainId = ruleChainId;
            this.ruleChainName = ruleChainName;
            this.ruleNodeId = ruleNodeId;

        }

        @Override
        public TbActorId createActorId() {
            return new TbEntityActorId(RuleNodeId.fromString(ruleNodeId));
        }

        @Override
        public TbActor createActor() {
            return new RuleNodeActor(context, tenantId, ruleChainId, ruleChainName, ruleNodeId);
        }
    }

    @Override
    protected long getErrorPersistFrequency() {
        return systemContext.getRuleNodeErrorPersistFrequency();
    }

}
