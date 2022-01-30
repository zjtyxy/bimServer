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
import com.ciat.bim.server.ContextBasedCreator;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActor;
import com.ciat.bim.server.actors.TbActorCtx;
import com.ciat.bim.server.actors.TbActorId;
import com.ciat.bim.server.actors.service.ComponentActor;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import com.ciat.bim.server.common.msg.queue.QueueToRuleEngineMsg;
import org.jeecg.modules.rule.entity.RuleChain;


public class RuleChainActor extends ComponentActor<RuleChainId, RuleChainActorMessageProcessor> {

    private final RuleChain ruleChain;

    private RuleChainActor(ActorSystemContext systemContext, String tenantId, RuleChain ruleChain) {
        super(systemContext, tenantId,ruleChain.getId());
        this.ruleChain = ruleChain;
    }

    @Override
    protected RuleChainActorMessageProcessor createProcessor(TbActorCtx ctx) {
        return new RuleChainActorMessageProcessor(tenantId, ruleChain, systemContext,
                ctx.getParentRef(), ctx);
    }

    @Override
    protected boolean doProcess(TbActorMsg msg) {
        switch (msg.getMsgType()) {
            case COMPONENT_LIFE_CYCLE_MSG:
                onComponentLifecycleMsg((ComponentLifecycleMsg) msg);
                break;
            case QUEUE_TO_RULE_ENGINE_MSG:
                processor.onQueueToRuleEngineMsg((QueueToRuleEngineMsg) msg);
                break;
            case RULE_TO_RULE_CHAIN_TELL_NEXT_MSG:
                processor.onTellNext((RuleNodeToRuleChainTellNextMsg) msg);
                break;
            case RULE_CHAIN_TO_RULE_CHAIN_MSG:
                processor.onRuleChainToRuleChainMsg((RuleChainToRuleChainMsg) msg);
                break;
            case PARTITION_CHANGE_MSG:
                processor.onPartitionChangeMsg((PartitionChangeMsg) msg);
                break;
            case STATS_PERSIST_TICK_MSG:
                onStatsPersistTick(id);
                break;
            default:
                return false;
        }
        return true;
    }

    public static class ActorCreator extends ContextBasedCreator {
        private static final long serialVersionUID = 1L;

        private final String tenantId;
        private final RuleChain ruleChain;

        public ActorCreator(ActorSystemContext context, String tenantId, RuleChain ruleChain) {
            super(context);
            this.tenantId = tenantId;
            this.ruleChain = ruleChain;
        }

        @Override
        public TbActorId createActorId() {
            return new TbEntityActorId(RuleChainId.fromString(ruleChain.getId()));
        }

        @Override
        public TbActor createActor() {
            return new RuleChainActor(context, tenantId, ruleChain);
        }
    }

    @Override
    protected long getErrorPersistFrequency() {
        return systemContext.getRuleChainErrorPersistFrequency();
    }

}
