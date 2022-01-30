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

import com.ciat.bim.common.data.rule.RuleChainType;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TbEntityActorId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.server.ContextAwareActor;
import com.ciat.bim.server.DefaultActorService;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActorRef;
import com.ciat.bim.server.actors.TbEntityTypeActorIdPredicate;
import com.ciat.bim.server.common.data.page.PageDataIterable;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.rule.entity.RuleChain;
import org.jeecg.modules.rule.service.IRuleChainService;


import java.util.function.Function;

/**
 * Created by ashvayka on 15.03.18.
 */
@Slf4j
public abstract class RuleChainManagerActor extends ContextAwareActor {

    protected final String tenantId;
    private final IRuleChainService ruleChainService;
    @Getter
    protected RuleChain rootChain;
    @Getter
    protected TbActorRef rootChainActor;

    public RuleChainManagerActor(ActorSystemContext systemContext, String tenantId) {
        super(systemContext);
        this.tenantId = tenantId;
        this.ruleChainService = systemContext.getRuleChainService();
    }

    protected void initRuleChains() {
        for (RuleChain ruleChain : new PageDataIterable<>(link -> ruleChainService.findTenantRuleChainsByType(tenantId, RuleChainType.CORE, link), ContextAwareActor.ENTITY_PACK_LIMIT)) {
            String ruleChainId = ruleChain.getId();
            log.debug("[{}] Creating rule chain actor", ruleChain.getId());
            TbActorRef actorRef = getOrCreateActor(ruleChainId, id -> ruleChain);
            visit(ruleChain, actorRef);
            log.debug("[{}] Rule Chain actor created.",ruleChainId);
        }
    }

    protected void destroyRuleChains() {
        for (RuleChain ruleChain : new PageDataIterable<RuleChain>(link -> ruleChainService.findTenantRuleChainsByType(tenantId, RuleChainType.CORE, link), ContextAwareActor.ENTITY_PACK_LIMIT)) {
            ctx.stop(new TbEntityActorId(RuleChainId.fromString(ruleChain.getId())));
        }
    }

    protected void visit(RuleChain entity, TbActorRef actorRef) {
        if (entity != null && entity.isRoot() && entity.getType().equals(RuleChainType.CORE)) {
            rootChain = entity;
            rootChainActor = actorRef;
        }
    }

    protected TbActorRef getOrCreateActor(String ruleChainId) {
        return getOrCreateActor(ruleChainId, eId -> ruleChainService.getById(eId));
    }

    protected TbActorRef getOrCreateActor(String ruleChainId, Function<String, RuleChain> provider) {
        return ctx.getOrCreateChildActor(new TbEntityActorId(RuleChainId.fromString(ruleChainId)),
                () -> DefaultActorService.RULE_DISPATCHER_NAME,
                () -> {
                    RuleChain ruleChain = provider.apply(ruleChainId);
                    return new RuleChainActor.ActorCreator(systemContext, tenantId, ruleChain);
                });
    }

    protected TbActorRef getEntityActorRef(EntityId entityId) {
        TbActorRef target = null;
        if (entityId.getEntityType() == EntityType.RULE_CHAIN) {
            target = getOrCreateActor(entityId.getId());
        }
        return target;
    }

    protected void broadcast(TbActorMsg msg) {
        ctx.broadcastToChildren(msg, new TbEntityTypeActorIdPredicate(EntityType.RULE_CHAIN));
    }
}
