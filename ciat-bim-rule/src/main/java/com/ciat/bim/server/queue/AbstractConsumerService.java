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
package com.ciat.bim.server.queue;

import com.ciat.bim.data.id.*;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.apiusage.TbApiUsageStateService;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.profile.TbDeviceProfileCache;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.TbApplicationEventListener;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.ciat.bim.tenant.TbTenantProfileCache;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;


import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractConsumerService<N extends com.google.protobuf.GeneratedMessageV3> extends TbApplicationEventListener<PartitionChangeEvent> {

    protected volatile ExecutorService consumersExecutor;
    protected volatile ExecutorService notificationsConsumerExecutor;
    protected volatile boolean stopped = false;

    protected  ActorSystemContext actorContext;
    protected  DataDecodingEncodingService encodingService;
    protected  TbTenantProfileCache tenantProfileCache;
    protected  TbDeviceProfileCache deviceProfileCache;
    protected  TbApiUsageStateService apiUsageStateService;

    protected  TbQueueConsumer<TbProtoQueueMsg<N>> nfConsumer;
////
//    public AbstractConsumerService(ActorSystemContext actorContext, DataDecodingEncodingService encodingService,
//                                   TbTenantProfileCache tenantProfileCache, TbDeviceProfileCache deviceProfileCache,
//                                   TbApiUsageStateService apiUsageStateService, TbQueueConsumer<TbProtoQueueMsg<N>> nfConsumer) {
//        this.actorContext = actorContext;
//        this.encodingService = encodingService;
//        this.tenantProfileCache = tenantProfileCache;
//        this.deviceProfileCache = deviceProfileCache;
//        this.apiUsageStateService = apiUsageStateService;
//        this.nfConsumer = nfConsumer;
//    }

    public void init(String mainConsumerThreadName, String nfConsumerThreadName) {
        this.consumersExecutor = Executors.newCachedThreadPool(ThingsBoardThreadFactory.forName(mainConsumerThreadName));
        this.notificationsConsumerExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName(nfConsumerThreadName));
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(value = 2)
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        log.info("Subscribing to notifications: {}", nfConsumer.getTopic());
//        this.nfConsumer.subscribe();
//        launchNotificationsConsumer()4772333333333333333333333333333333333333333331;
//        launchMainConsumers();
    }

    protected abstract ServiceType getServiceType();

    protected abstract void launchMainConsumers();

    protected abstract void stopMainConsumers();

    protected abstract long getNotificationPollDuration();

    protected abstract long getNotificationPackProcessingTimeout();

    protected void launchNotificationsConsumer() {
//        notificationsConsumerExecutor.submit(() -> {
//            while (!stopped) {
//                try {
//                    List<TbProtoQueueMsg<N>> msgs = nfConsumer.poll(getNotificationPollDuration());
//                    if (msgs.isEmpty()) {
//                        continue;
//                    }
//                    ConcurrentMap<UUID, TbProtoQueueMsg<N>> pendingMap = msgs.stream().collect(
//                            Collectors.toConcurrentMap(s -> UUID.randomUUID(), Function.identity()));
//                    CountDownLatch processingTimeoutLatch = new CountDownLatch(1);
//                    TbPackProcessingContext<TbProtoQueueMsg<N>> ctx = new TbPackProcessingContext<>(
//                            processingTimeoutLatch, pendingMap, new ConcurrentHashMap<>());
//                    pendingMap.forEach((id, msg) -> {
//                        log.trace("[{}] Creating notification callback for message: {}", id, msg.getValue());
//                        TbCallback callback = new TbPackCallback<>(id, ctx);
//                        try {
//                            handleNotification(id, msg, callback);
//                        } catch (Throwable e) {
//                            log.warn("[{}] Failed to process notification: {}", id, msg, e);
//                            callback.onFailure(e);
//                        }
//                    });
//                    if (!processingTimeoutLatch.await(getNotificationPackProcessingTimeout(), TimeUnit.MILLISECONDS)) {
//                        ctx.getAckMap().forEach((id, msg) -> log.warn("[{}] Timeout to process notification: {}", id, msg.getValue()));
//                        ctx.getFailedMap().forEach((id, msg) -> log.warn("[{}] Failed to process notification: {}", id, msg.getValue()));
//                    }
//                    nfConsumer.commit();
//                } catch (Exception e) {
//                    if (!stopped) {
//                        log.warn("Failed to obtain notifications from queue.", e);
//                        try {
//                            Thread.sleep(getNotificationPollDuration());
//                        } catch (InterruptedException e2) {
//                            log.trace("Failed to wait until the server has capacity to handle new notifications", e2);
//                        }
//                    }
//                }
//            }
//            log.info("TB Notifications Consumer stopped.");
//        });
    }

    protected void handleComponentLifecycleMsg(UUID id, ByteString nfMsg) {
//        Optional<TbActorMsg> actorMsgOpt = encodingService.decode(nfMsg.toByteArray());
//        if (actorMsgOpt.isPresent()) {
//            TbActorMsg actorMsg = actorMsgOpt.get();
//            if (actorMsg instanceof ComponentLifecycleMsg) {
//                ComponentLifecycleMsg componentLifecycleMsg = (ComponentLifecycleMsg) actorMsg;
//                log.info("[{}][{}][{}] Received Lifecycle event: {}", componentLifecycleMsg.getTenantId(), componentLifecycleMsg.getEntityId().getEntityType(),
//                        componentLifecycleMsg.getEntityId(), componentLifecycleMsg.getEvent());
//                if (EntityType.TENANT_PROFILE.equals(componentLifecycleMsg.getEntityId().getEntityType())) {
//                    TenantProfileId tenantProfileId = new TenantProfileId(componentLifecycleMsg.getEntityId().getId());
//                    tenantProfileCache.evict(tenantProfileId);
//                    if (componentLifecycleMsg.getEvent().equals(ComponentLifecycleEvent.UPDATED)) {
//                        apiUsageStateService.onTenantProfileUpdate(tenantProfileId);
//                    }
//                } else if (EntityType.TENANT.equals(componentLifecycleMsg.getEntityId().getEntityType())) {
//                    tenantProfileCache.evict(componentLifecycleMsg.getTenantId());
//                    if (componentLifecycleMsg.getEvent().equals(ComponentLifecycleEvent.UPDATED)) {
//                        apiUsageStateService.onTenantUpdate(componentLifecycleMsg.getTenantId());
//                    } else if (componentLifecycleMsg.getEvent().equals(ComponentLifecycleEvent.DELETED)) {
//                        apiUsageStateService.onTenantDelete((TenantId) componentLifecycleMsg.getEntityId());
//                    }
//                } else if (EntityType.DEVICE_PROFILE.equals(componentLifecycleMsg.getEntityId().getEntityType())) {
//                    deviceProfileCache.evict(componentLifecycleMsg.getTenantId(), new DeviceProfileId(componentLifecycleMsg.getEntityId().getId()));
//                } else if (EntityType.DEVICE.equals(componentLifecycleMsg.getEntityId().getEntityType())) {
//                    deviceProfileCache.evict(componentLifecycleMsg.getTenantId(), new DeviceId(componentLifecycleMsg.getEntityId().getId()));
//                } else if (EntityType.API_USAGE_STATE.equals(componentLifecycleMsg.getEntityId().getEntityType())) {
//                    apiUsageStateService.onApiUsageStateUpdate(componentLifecycleMsg.getTenantId());
//                } else if (EntityType.CUSTOMER.equals(componentLifecycleMsg.getEntityId().getEntityType())) {
//                    if (componentLifecycleMsg.getEvent() == ComponentLifecycleEvent.DELETED) {
//                        apiUsageStateService.onCustomerDelete((CustomerId) componentLifecycleMsg.getEntityId());
//                    }
//                }
//            }
//            log.trace("[{}] Forwarding message to App Actor {}", id, actorMsg);
//            actorContext.tellWithHighPriority(actorMsg);
//        }
    }

    protected abstract void handleNotification(UUID id, TbProtoQueueMsg<N> msg, TbCallback callback) throws Exception;

    @PreDestroy
    public void destroy() {
        stopped = true;
        stopMainConsumers();
        if (nfConsumer != null) {
            nfConsumer.unsubscribe();
        }
        if (consumersExecutor != null) {
            consumersExecutor.shutdownNow();
        }
        if (notificationsConsumerExecutor != null) {
            notificationsConsumerExecutor.shutdownNow();
        }
    }
}
