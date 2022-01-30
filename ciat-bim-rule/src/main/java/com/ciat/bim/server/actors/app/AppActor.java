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
package com.ciat.bim.server.actors.app;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TbEntityActorId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.*;
import com.ciat.bim.rule.RuleEngineException;
import com.ciat.bim.server.ContextAwareActor;
import com.ciat.bim.server.ContextBasedCreator;
import com.ciat.bim.server.DefaultActorService;
import com.ciat.bim.server.ModelConstants;
import com.ciat.bim.server.actors.*;
import com.ciat.bim.server.common.data.ComponentLifecycleEvent;
import com.ciat.bim.server.common.data.page.PageDataIterable;
import com.ciat.bim.server.common.msg.aware.TenantAwareMsg;
import com.ciat.bim.server.common.msg.edge.EdgeEventUpdateMsg;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import com.ciat.bim.server.common.msg.queue.QueueToRuleEngineMsg;
import com.ciat.bim.server.transport.TransportToDeviceActorMsgWrapper;
import com.ciat.bim.tenant.TbTenantProfileCache;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class AppActor extends ContextAwareActor {

    private final TbTenantProfileCache tenantProfileCache;
    private final ITenantService tenantService;
    private final Set<String> deletedTenants;
    private volatile boolean ruleChainsInitialized;

    private AppActor(ActorSystemContext systemContext) {
        super(systemContext);
        this.tenantProfileCache = systemContext.getTenantProfileCache();
        this.tenantService = systemContext.getTenantService();
        this.deletedTenants = new HashSet<>();
    }

    @Override
    protected boolean doProcess(TbActorMsg msg) {
        if (!ruleChainsInitialized) {
            initTenantActors();
            ruleChainsInitialized = true;
            if (msg.getMsgType() != MsgType.APP_INIT_MSG && msg.getMsgType() != MsgType.PARTITION_CHANGE_MSG) {
                log.warn("Rule Chains initialized by unexpected message: {}", msg);
            }
        }
        switch (msg.getMsgType()) {
            case APP_INIT_MSG:
                break;
            case PARTITION_CHANGE_MSG:
                ctx.broadcastToChildren(msg);
                break;
            case COMPONENT_LIFE_CYCLE_MSG:
                onComponentLifecycleMsg((ComponentLifecycleMsg) msg);
                break;
            case QUEUE_TO_RULE_ENGINE_MSG:
                onQueueToRuleEngineMsg((QueueToRuleEngineMsg) msg);
                break;
            case TRANSPORT_TO_DEVICE_ACTOR_MSG:
                onToDeviceActorMsg((TenantAwareMsg) msg, false);
                break;
            case DEVICE_ATTRIBUTES_UPDATE_TO_DEVICE_ACTOR_MSG:
            case DEVICE_CREDENTIALS_UPDATE_TO_DEVICE_ACTOR_MSG:
            case DEVICE_NAME_OR_TYPE_UPDATE_TO_DEVICE_ACTOR_MSG:
            case DEVICE_EDGE_UPDATE_TO_DEVICE_ACTOR_MSG:
            case DEVICE_RPC_REQUEST_TO_DEVICE_ACTOR_MSG:
            case DEVICE_RPC_RESPONSE_TO_DEVICE_ACTOR_MSG:
            case SERVER_RPC_RESPONSE_TO_DEVICE_ACTOR_MSG:
            case REMOVE_RPC_TO_DEVICE_ACTOR_MSG:
                onToDeviceActorMsg((TenantAwareMsg) msg, true);
                break;
            case EDGE_EVENT_UPDATE_TO_EDGE_SESSION_MSG:
                onToTenantActorMsg((EdgeEventUpdateMsg) msg);
                break;
            default:
                return false;
        }
        return true;
    }

    private void initTenantActors() {
        log.info("Starting main system actor.");
        try {
            // This Service may be started for specific tenant only.
            Optional<TenantId> isolatedTenantId = systemContext.getServiceInfoProvider().getIsolatedTenant();
            if (isolatedTenantId.isPresent()) {
                Tenant tenant = systemContext.getTenantService().getById(isolatedTenantId.get());
                if (tenant != null) {
                    log.debug("[{}] Creating tenant actor", tenant.getId());
                    getOrCreateTenantActor(tenant.getId());
                    log.debug("Tenant actor created.");
                } else {
                    log.error("[{}] Tenant with such ID does not exist", isolatedTenantId.get());
                }
            } else if (systemContext.isTenantComponentsInitEnabled()) {
               // PageDataIterable<Tenant> tenantIterator = new PageDataIterable<Tenant>(tenantService::findTenants, ENTITY_PACK_LIMIT);
                List<Tenant> tenantIterator = tenantService.list();
                boolean isRuleEngine = systemContext.getServiceInfoProvider().isService(ServiceType.TB_RULE_ENGINE);
                boolean isCore = systemContext.getServiceInfoProvider().isService(ServiceType.TB_CORE);
                for (Tenant tenant : tenantIterator) {
                    TenantProfile tenantProfile = tenantProfileCache.get(tenant.getTenantProfileId());
                    if (isCore || (isRuleEngine && !tenantProfile.isIsolatedTbRuleEngine())) {
                        log.debug("[{}] Creating tenant actor", tenant.getId());
                        getOrCreateTenantActor(tenant.getId());
                        log.debug("[{}] Tenant actor created.", tenant.getId());
                    }
                }
            }
            log.info("Main system actor started.");
        } catch (Exception e) {
            log.warn("Unknown failure", e);
        }
    }

    private void onQueueToRuleEngineMsg(QueueToRuleEngineMsg msg) {
        if (TenantId.SYS_TENANT_ID.equals(msg.getTenantId())) {
            msg.getMsg().getCallback().onFailure(new RuleEngineException("Message has system tenant id!"));
        } else {
            if (!deletedTenants.contains(msg.getTenantId())) {
                getOrCreateTenantActor(msg.getTenantId()).tell(msg);
            } else {
                msg.getMsg().getCallback().onSuccess();
            }
        }
    }

    private void onComponentLifecycleMsg(ComponentLifecycleMsg msg) {
        TbActorRef target = null;
        if (TenantId.SYS_TENANT_ID.equals(msg.getTenantId())) {
            if (!EntityType.TENANT_PROFILE.equals(msg.getEntityId().getEntityType())) {
                log.warn("Message has system tenant id: {}", msg);
            }
        } else {
            if (EntityType.TENANT.equals(msg.getEntityId().getEntityType())) {
                String tenantId = msg.getEntityId().getId();
                if (msg.getEvent() == ComponentLifecycleEvent.DELETED) {
                    log.info("[{}] Handling tenant deleted notification: {}", msg.getTenantId(), msg);
                    deletedTenants.add(tenantId);
                    ctx.stop(new TbEntityActorId(TenantId.fromString(tenantId)));
                } else {
                    target = getOrCreateTenantActor(msg.getTenantId());
                }
            } else {
                target = getOrCreateTenantActor(msg.getTenantId());
            }
        }
        if (target != null) {
            target.tellWithHighPriority(msg);
        } else {
            log.debug("[{}] Invalid component lifecycle msg: {}", msg.getTenantId(), msg);
        }
    }

    private void onToDeviceActorMsg(TenantAwareMsg msg, boolean priority) {
        if (!deletedTenants.contains(msg.getTenantId())) {
            TbActorRef tenantActor = getOrCreateTenantActor(msg.getTenantId());
            if (priority) {
                tenantActor.tellWithHighPriority(msg);
            } else {
                tenantActor.tell(msg);
            }
        } else {
            if (msg instanceof TransportToDeviceActorMsgWrapper) {
                ((TransportToDeviceActorMsgWrapper) msg).getCallback().onSuccess();
            }
        }
    }

    private TbActorRef getOrCreateTenantActor(String tenantId) {
        return ctx.getOrCreateChildActor(new TbEntityActorId(TenantId.fromString(tenantId)),
                () -> DefaultActorService.TENANT_DISPATCHER_NAME,
                () -> new TenantActor.ActorCreator(systemContext, tenantId));
    }

    private void onToTenantActorMsg(EdgeEventUpdateMsg msg) {
        TbActorRef target = null;
        if (ModelConstants.SYSTEM_TENANT.equals(msg.getTenantId())) {
            log.warn("Message has system tenant id: {}", msg);
        } else {
            target = getOrCreateTenantActor(msg.getTenantId());
        }
        if (target != null) {
            target.tellWithHighPriority(msg);
        } else {
            log.debug("[{}] Invalid edge event update msg: {}", msg.getTenantId(), msg);
        }
    }

    public static class ActorCreator extends ContextBasedCreator {

        public ActorCreator(ActorSystemContext context) {
            super(context);
        }

        @Override
        public TbActorId createActorId() {
            return new TbEntityActorId(TenantId.fromString(EntityId.NULL_UUID.toString()));
        }

        @Override
        public TbActor createActor() {
            return new AppActor(context);
        }
    }

}
