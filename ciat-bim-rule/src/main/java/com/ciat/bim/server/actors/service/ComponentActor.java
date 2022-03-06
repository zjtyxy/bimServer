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
package com.ciat.bim.server.actors.service;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.PartitionChangeMsg;
import com.ciat.bim.server.ContextAwareActor;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActorCtx;
import com.ciat.bim.server.actors.TbActorException;
import com.ciat.bim.server.actors.TbRuleNodeUpdateException;
import com.ciat.bim.server.actors.shared.ComponentMsgProcessor;
import com.ciat.bim.server.common.data.ComponentLifecycleEvent;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import com.ciat.bim.server.stats.StatsPersistMsg;
import lombok.extern.slf4j.Slf4j;


/**
 * @author Andrew Shvayka
 */
@Slf4j
public abstract class ComponentActor<T extends EntityId, P extends ComponentMsgProcessor<T>> extends ContextAwareActor {

    private long lastPersistedErrorTs = 0L;
    protected final String tenantId;
    protected final String id;
    protected P processor;
    private long messagesProcessed;
    private long errorsOccurred;

    public ComponentActor(ActorSystemContext systemContext, String tenantId, String id) {
        super(systemContext);
        this.tenantId = tenantId;
        this.id = id;
    }

    abstract protected P createProcessor(TbActorCtx ctx);

    @Override
    public void init(TbActorCtx ctx) throws TbActorException {
        super.init(ctx);
        this.processor = createProcessor(ctx);
        initProcessor(ctx);
    }

    protected void initProcessor(TbActorCtx ctx) throws TbActorException {
        try {
            //log.debug("[{}][{}][{}] Starting processor.", tenantId, id, id.getEntityType());
            processor.start(ctx);
            logLifecycleEvent(ComponentLifecycleEvent.STARTED);
            if (systemContext.isStatisticsEnabled()) {
                scheduleStatsPersistTick();
            }
        } catch (Exception e) {
            //log.debug("[{}][{}] Failed to start {} processor.", tenantId, id, id.getEntityType(), e);
            logAndPersist("OnStart", e, true);
            logLifecycleEvent(ComponentLifecycleEvent.STARTED, e);
            throw new TbActorException("Failed to init actor", e);
        }
    }

    private void scheduleStatsPersistTick() {
        try {
            processor.scheduleStatsPersistTick(ctx, systemContext.getStatisticsPersistFrequency());
        } catch (Exception e) {
            log.error("[{}][{}] Failed to schedule statistics store message. No statistics is going to be stored: {}", tenantId, id, e.getMessage());
            logAndPersist("onScheduleStatsPersistMsg", e);
        }
    }

    @Override
    public void destroy() {
        try {
           // log.debug("[{}][{}][{}] Stopping processor.", tenantId, id, id.getEntityType());
            if (processor != null) {
                processor.stop(ctx);
            }
            logLifecycleEvent(ComponentLifecycleEvent.STOPPED);
        } catch (Exception e) {
          //  log.warn("[{}][{}] Failed to stop {} processor: {}", tenantId, id, id.getEntityType(), e.getMessage());
            logAndPersist("OnStop", e, true);
            logLifecycleEvent(ComponentLifecycleEvent.STOPPED, e);
        }
    }

    protected void onComponentLifecycleMsg(ComponentLifecycleMsg msg) {
       // log.debug("[{}][{}][{}] onComponentLifecycleMsg: [{}]", tenantId, id, id.getEntityType(), msg.getEvent());
        try {
            switch (msg.getEvent()) {
                case CREATED:
                    processor.onCreated(ctx);
                    break;
                case UPDATED:
                    processor.onUpdate(ctx);
                    break;
                case ACTIVATED:
                    processor.onActivate(ctx);
                    break;
                case SUSPENDED:
                    processor.onSuspend(ctx);
                    break;
                case DELETED:
                    processor.onStop(ctx);
                    ctx.stop(ctx.getSelf());
                    break;
                default:
                    break;
            }
            logLifecycleEvent(msg.getEvent());
        } catch (Exception e) {
            logAndPersist("onLifecycleMsg", e, true);
            logLifecycleEvent(msg.getEvent(), e);
            if (e instanceof TbRuleNodeUpdateException) {
                throw (TbRuleNodeUpdateException) e;
            }
        }
    }

    protected void onClusterEventMsg(PartitionChangeMsg msg) {
        try {
            processor.onPartitionChangeMsg(msg);
        } catch (Exception e) {
            logAndPersist("onClusterEventMsg", e);
        }
    }

    protected void onStatsPersistTick(String entityId) {
        try {
            systemContext.getStatsActor().tell(new StatsPersistMsg(messagesProcessed, errorsOccurred, tenantId, entityId));
            resetStatsCounters();
        } catch (Exception e) {
            logAndPersist("onStatsPersistTick", e);
        }
    }

    private void resetStatsCounters() {
        messagesProcessed = 0;
        errorsOccurred = 0;
    }

    protected void increaseMessagesProcessedCount() {
        messagesProcessed++;
    }

    protected void logAndPersist(String method, Exception e) {
        logAndPersist(method, e, false);
    }

    private void logAndPersist(String method, Exception e, boolean critical) {
        errorsOccurred++;
        String componentName = processor != null ? processor.getComponentName() : "Unknown";
        if (critical) {
            log.debug("[{}][{}][{}] Failed to process method: {}", id, tenantId, componentName, method);
            log.debug("Critical Error: ", e);
        } else {
            log.trace("[{}][{}][{}] Failed to process method: {}", id, tenantId, componentName, method);
            log.trace("Debug Error: ", e);
        }
        long ts = System.currentTimeMillis();
        if (ts - lastPersistedErrorTs > getErrorPersistFrequency()) {
            systemContext.persistError(tenantId, id, method, e);
            lastPersistedErrorTs = ts;
        }
    }

    private void logLifecycleEvent(ComponentLifecycleEvent event) {
        logLifecycleEvent(event, null);
    }

    private void logLifecycleEvent(ComponentLifecycleEvent event, Exception e) {
        systemContext.persistLifecycleEvent(tenantId, id, event, e);
    }

    protected abstract long getErrorPersistFrequency();
}
