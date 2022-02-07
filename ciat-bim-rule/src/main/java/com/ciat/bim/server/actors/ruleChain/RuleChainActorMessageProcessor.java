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
import com.ciat.bim.common.data.rule.TbRelationTypes;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TbEntityActorId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.*;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleEngineException;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.DefaultActorService;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActorCtx;
import com.ciat.bim.server.actors.TbActorRef;
import com.ciat.bim.server.actors.shared.ComponentLifecycleState;
import com.ciat.bim.server.actors.shared.ComponentMsgProcessor;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.ComponentLifecycleEvent;
import com.ciat.bim.server.common.data.relation.EntityRelation;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import com.ciat.bim.server.common.msg.plugin.RuleNodeUpdatedMsg;
import com.ciat.bim.server.common.msg.queue.QueueToRuleEngineMsg;
import com.ciat.bim.server.common.msg.queue.RuleNodeException;
import com.ciat.bim.server.queue.common.MultipleTbQueueTbMsgCallbackWrapper;
import com.ciat.bim.server.queue.common.TbQueueTbMsgCallbackWrapper;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.usagestats.TbApiUsageClient;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.rule.entity.RuleChain;
import org.jeecg.modules.rule.entity.RuleNode;
import org.jeecg.modules.rule.service.IRuleChainService;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Andrew Shvayka
 * 会负责消息的具体处理
 */
@Slf4j
public class RuleChainActorMessageProcessor extends ComponentMsgProcessor<RuleChainId> {

    private final TbActorRef parent;
    private final TbActorRef self;
    private final Map<String, RuleNodeCtx> nodeActors;
    private final Map<String, List<RuleNodeRelation>> nodeRoutes;
    private final IRuleChainService service;
    private final TbClusterService clusterService;
    private final TbApiUsageClient apiUsageClient;
    private String ruleChainName;

    private String firstId;
    private RuleNodeCtx firstNode;
    private boolean started;

    RuleChainActorMessageProcessor(String tenantId, RuleChain ruleChain, ActorSystemContext systemContext, TbActorRef parent, TbActorRef self) {
        super(systemContext, tenantId,
                ruleChain.getId());
        this.apiUsageClient = systemContext.getApiUsageClient();
        this.ruleChainName = ruleChain.getName();
        this.parent = parent;
        this.self = self;
        this.nodeActors = new HashMap<>();
        this.nodeRoutes = new HashMap<>();
        this.service = systemContext.getRuleChainService();
        this.clusterService = systemContext.getClusterService();
    }

    @Override
    public String getComponentName() {
        return null;
    }
    /*
    初始化NodeActor
     */
    @Override
    public void start(TbActorCtx context) {
        if (!started) {
            RuleChain ruleChain = service.getById(entityId);
            if (ruleChain != null && RuleChainType.CORE.equals(ruleChain.getType())) {
                List<RuleNode> ruleNodeList = service.getRuleChainNodes(tenantId, entityId);
                log.trace("[{}][{}] Starting rule chain with {} nodes", tenantId, entityId, ruleNodeList.size());
                // Creating and starting the actors;
                for (RuleNode ruleNode : ruleNodeList) {
                    log.trace("[{}][{}] Creating rule node [{}]: {}", entityId, ruleNode.getId(), ruleNode.getName(), ruleNode);
                    TbActorRef ruleNodeActor = createRuleNodeActor(context, ruleNode);
                    nodeActors.put(ruleNode.getId(), new RuleNodeCtx(tenantId, self, ruleNodeActor, ruleNode));
                }
                initRoutes(ruleChain, ruleNodeList);
                started = true;
            }
        } else {
            onUpdate(context);
        }
    }

    @Override
    public void onUpdate(TbActorCtx context) {
        RuleChain ruleChain = service.getById( entityId);
        if (ruleChain != null && RuleChainType.CORE.equals(ruleChain.getType())) {
            ruleChainName = ruleChain.getName();
            List<RuleNode> ruleNodeList = service.getRuleChainNodes(tenantId, entityId);
            log.trace("[{}][{}] Updating rule chain with {} nodes", tenantId, entityId, ruleNodeList.size());
            for (RuleNode ruleNode : ruleNodeList) {
                RuleNodeCtx existing = nodeActors.get(ruleNode.getId());
                if (existing == null) {
                    log.trace("[{}][{}] Creating rule node [{}]: {}", entityId, ruleNode.getId(), ruleNode.getName(), ruleNode);
                    TbActorRef ruleNodeActor = createRuleNodeActor(context, ruleNode);
                    nodeActors.put(ruleNode.getId(), new RuleNodeCtx(tenantId, self, ruleNodeActor, ruleNode));
                } else {
                    log.trace("[{}][{}] Updating rule node [{}]: {}", entityId, ruleNode.getId(), ruleNode.getName(), ruleNode);
                    existing.setSelf(ruleNode);
                    existing.getSelfActor().tellWithHighPriority(new RuleNodeUpdatedMsg(tenantId,RuleNodeId.fromString(existing.getSelf().getId())));
                }
            }

            Set<String> existingNodes = ruleNodeList.stream().map(RuleNode::getId).collect(Collectors.toSet());
            List<String> removedRules = nodeActors.keySet().stream().filter(node -> !existingNodes.contains(node)).collect(Collectors.toList());
            removedRules.forEach(ruleNodeId -> {
                log.trace("[{}][{}] Removing rule node [{}]", tenantId, entityId, ruleNodeId);
                RuleNodeCtx removed = nodeActors.remove(ruleNodeId);
                removed.getSelfActor().tellWithHighPriority(new ComponentLifecycleMsg(tenantId,RuleNodeId.fromString(removed.getSelf().getId()), ComponentLifecycleEvent.DELETED));
            });

            initRoutes(ruleChain, ruleNodeList);
        }
    }

    @Override
    public void stop(TbActorCtx ctx) {
        log.trace("[{}][{}] Stopping rule chain with {} nodes", tenantId, entityId, nodeActors.size());
        nodeActors.values().stream().map(RuleNodeCtx::getSelfActor).map(TbActorRef::getActorId).forEach(ctx::stop);
        nodeActors.clear();
        nodeRoutes.clear();
        started = false;
    }

    @Override
    public void onPartitionChangeMsg(PartitionChangeMsg msg) {
        nodeActors.values().stream().map(RuleNodeCtx::getSelfActor).forEach(actorRef -> actorRef.tellWithHighPriority(msg));
    }

    private TbActorRef createRuleNodeActor(TbActorCtx ctx, RuleNode ruleNode) {
        return ctx.getOrCreateChildActor(new TbEntityActorId(RuleNodeId.fromString(ruleNode.getId())),
                () -> DefaultActorService.RULE_DISPATCHER_NAME,
                () -> new RuleNodeActor.ActorCreator(systemContext, tenantId, entityId, ruleChainName, ruleNode.getId()));
    }

    private void initRoutes(RuleChain ruleChain, List<RuleNode> ruleNodeList) {
        nodeRoutes.clear();
        // Populating the routes map;
        for (RuleNode ruleNode : ruleNodeList) {
            List<EntityRelation> relations = service.getRuleNodeRelations(TenantId.SYS_TENANT_ID, ruleNode.getId());
            log.trace("[{}][{}][{}] Processing rule node relations [{}]", tenantId, entityId, ruleNode.getId(), relations.size());
            if (relations.size() == 0) {
                nodeRoutes.put(ruleNode.getId(), Collections.emptyList());
            } else {
                for (EntityRelation relation : relations) {
                    log.trace("[{}][{}][{}] Processing rule node relation [{}]", tenantId, entityId, ruleNode.getId(), relation.getTo());
                    if (relation.getTo().getEntityType() == EntityType.RULE_NODE) {
                        RuleNodeCtx ruleNodeCtx = nodeActors.get(relation.getTo().getId());
                        if (ruleNodeCtx == null) {
                            throw new IllegalArgumentException("Rule Node [" + relation.getFrom() + "] has invalid relation to Rule node [" + relation.getTo() + "]");
                        }
                    }
                    nodeRoutes.computeIfAbsent(ruleNode.getId(), k -> new ArrayList<>())
                            .add(new RuleNodeRelation(RuleNodeId.fromString(ruleNode.getId()), relation.getTo(), relation.getType()));
                }
            }
        }

        firstId = ruleChain.getRuleNodeId();
        firstNode = nodeActors.get(firstId);
        state = ComponentLifecycleState.ACTIVE;
    }

    void onQueueToRuleEngineMsg(QueueToRuleEngineMsg envelope) {
        TbMsg msg = envelope.getMsg();
        log.trace("[{}][{}] Processing message [{}]: {}", entityId, firstId, msg.getId(), msg);
        if (envelope.getRelationTypes() == null || envelope.getRelationTypes().isEmpty()) {
            try {
                checkActive(envelope.getMsg());
                String targetId = msg.getRuleNodeId();
                RuleNodeCtx targetCtx;
                if (targetId == null || targetId.equals("0")) //收割节点
                {
                    targetCtx = firstNode;
                    msg = msg.copyWithRuleChainId(entityId);
                } else {
                    targetCtx = nodeActors.get(targetId);
                }
                if (targetCtx != null) {
                    log.trace("[{}][{}] Pushing message to target rule node", entityId, targetId);
                    pushMsgToNode(targetCtx, msg, "");
                } else {
                    log.trace("[{}][{}] Rule node does not exist. Probably old message", entityId, targetId);
                    msg.getCallback().onSuccess();
                }
            } catch (RuleNodeException rne) {
                envelope.getMsg().getCallback().onFailure(rne);
            } catch (Exception e) {
                envelope.getMsg().getCallback().onFailure(new RuleEngineException(e.getMessage()));
            }
        } else {
            onTellNext(envelope.getMsg(), envelope.getMsg().getRuleNodeId(), envelope.getRelationTypes(), envelope.getFailureMessage());
        }
    }

    void onRuleChainToRuleChainMsg(RuleChainToRuleChainMsg envelope) {
        try {
            checkActive(envelope.getMsg());
            if (firstNode != null) {
                pushMsgToNode(firstNode, envelope.getMsg(), envelope.getFromRelationType());
            } else {
                envelope.getMsg().getCallback().onSuccess();
            }
        } catch (RuleNodeException e) {
            //log.debug("Rule Chain is not active. Current state [{}] for processor [{}][{}] tenant [{}]", state, entityId.getEntityType(), entityId, tenantId);
        }
    }

    void onTellNext(RuleNodeToRuleChainTellNextMsg envelope) {
        onTellNext(envelope.getMsg(), envelope.getOriginator(), envelope.getRelationTypes(), envelope.getFailureMessage());
    }

    private void onTellNext(TbMsg msg, String originatorNodeId, Set<String> relationTypes, String failureMessage) {
        try {
            checkActive(msg);
            EntityId entityId = msg.getOriginator();
            TopicPartitionInfo tpi = systemContext.resolve(ServiceType.TB_RULE_ENGINE, msg.getQueueName(), tenantId, entityId);

            List<RuleNodeRelation> ruleNodeRelations = nodeRoutes.get(originatorNodeId);
            if (ruleNodeRelations == null) { // When unchecked, this will cause NullPointerException when rule node doesn't exist anymore
                log.warn("[{}][{}][{}] No outbound relations (null). Probably rule node does not exist. Probably old message.", tenantId, entityId, msg.getId());
                ruleNodeRelations = Collections.emptyList();
            }

            List<RuleNodeRelation> relationsByTypes = ruleNodeRelations.stream()
                    .filter(r -> contains(relationTypes, r.getType()))
                    .collect(Collectors.toList());
            int relationsCount = relationsByTypes.size();
            if (relationsCount == 0) {
                log.trace("[{}][{}][{}] No outbound relations to process", tenantId, entityId, msg.getId());
                if (relationTypes.contains(TbRelationTypes.FAILURE)) {
                    RuleNodeCtx ruleNodeCtx = nodeActors.get(originatorNodeId);
                    if (ruleNodeCtx != null) {
                        msg.getCallback().onFailure(new RuleNodeException(failureMessage, ruleChainName, ruleNodeCtx.getSelf()));
                    } else {
                        log.debug("[{}] Failure during message processing by Rule Node [{}]. Enable and see debug events for more info", entityId, originatorNodeId);
                        msg.getCallback().onFailure(new RuleEngineException("Failure during message processing by Rule Node [" + originatorNodeId + "]"));
                    }
                } else {
                    msg.getCallback().onSuccess();
                }
            } else if (relationsCount == 1) {
                for (RuleNodeRelation relation : relationsByTypes) {
                    log.trace("[{}][{}][{}] Pushing message to single target: [{}]", tenantId, entityId, msg.getId(), relation.getOut());
                    pushToTarget(tpi, msg, relation.getOut(), relation.getType());
                }
            } else {
                MultipleTbQueueTbMsgCallbackWrapper callbackWrapper = new MultipleTbQueueTbMsgCallbackWrapper(relationsCount, msg.getCallback());
                log.trace("[{}][{}][{}] Pushing message to multiple targets: [{}]", tenantId, entityId, msg.getId(), relationsByTypes);
                for (RuleNodeRelation relation : relationsByTypes) {
                    EntityId target = relation.getOut();
                    putToQueue(tpi, msg, callbackWrapper, target);
                }
            }
        } catch (RuleNodeException rne) {
            msg.getCallback().onFailure(rne);
        } catch (Exception e) {
            log.warn("[" + tenantId + "]" + "[" + entityId + "]" + "[" + msg.getId() + "]" + " onTellNext failure", e);
            msg.getCallback().onFailure(new RuleEngineException("onTellNext - " + e.getMessage()));
        }
    }

    private void putToQueue(TopicPartitionInfo tpi, TbMsg msg, TbQueueCallback callbackWrapper, EntityId target) {
        switch (target.getEntityType()) {
            case RULE_NODE:
                putToQueue(tpi, msg.copyWithRuleNodeId(entityId, target.getId(), UUID.randomUUID().toString()), callbackWrapper);
                break;
            case RULE_CHAIN:
                putToQueue(tpi, msg.copyWithRuleChainId(target.getId(), UUID.randomUUID().toString()), callbackWrapper);
                break;
        }
    }

    private void pushToTarget(TopicPartitionInfo tpi, TbMsg msg, EntityId target, String fromRelationType) {
        if (tpi.isMyPartition()) {
            switch (target.getEntityType()) {
                case RULE_NODE:
                    pushMsgToNode(nodeActors.get(target.getId()), msg, fromRelationType);
                    break;
                case RULE_CHAIN:
                    parent.tell(new RuleChainToRuleChainMsg(target.getId(), entityId, msg, fromRelationType));
                    break;
            }
        } else {
            putToQueue(tpi, msg, new TbQueueTbMsgCallbackWrapper(msg.getCallback()), target);
        }
    }

    private void putToQueue(TopicPartitionInfo tpi, TbMsg newMsg, TbQueueCallback callbackWrapper) {
        TransportProtos.ToRuleEngineMsg toQueueMsg = ToRuleEngineMsg.newBuilder()
                .setTenantIdMSB(Long.parseLong(tenantId))
                .setTenantIdLSB(Long.parseLong(tenantId))
                .setTbMsg(TbMsg.toByteString(newMsg))
                .build();
        clusterService.pushMsgToRuleEngine(tpi, newMsg.getId(), toQueueMsg, callbackWrapper);
    }

    private boolean contains(Set<String> relationTypes, String type) {
        if (relationTypes == null) {
            return true;
        }
        for (String relationType : relationTypes) {
            if (relationType.equalsIgnoreCase(type)) {
                return true;
            }
        }
        return false;
    }

    private void pushMsgToNode(RuleNodeCtx nodeCtx, TbMsg msg, String fromRelationType) {
        if (nodeCtx != null) {
            nodeCtx.getSelfActor().tell(new RuleChainToRuleNodeMsg(new DefaultTbContext(systemContext, ruleChainName, nodeCtx), msg, fromRelationType));
        } else {
            log.error("[{}][{}] RuleNodeCtx is empty", entityId, ruleChainName);
            msg.getCallback().onFailure(new RuleEngineException("Rule Node CTX is empty"));
        }
    }

    @Override
    protected RuleNodeException getInactiveException() {
        RuleNode firstRuleNode = firstNode != null ? firstNode.getSelf() : null;
        return new RuleNodeException("Rule Chain is not active!  Failed to initialize.", ruleChainName, firstRuleNode);
    }
}
