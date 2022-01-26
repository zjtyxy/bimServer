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
package com.ciat.bim.server.subscription;

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.kv.Aggregation;
import com.ciat.bim.server.common.data.kv.BaseReadTsKvQuery;
import com.ciat.bim.server.common.data.kv.ReadTsKvQuery;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.PartitionService;
import com.ciat.bim.server.queue.discovery.TbApplicationEventListener;
import com.ciat.bim.server.queue.discovery.TbServiceInfoProvider;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.queue.provider.TbQueueProducerProvider;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.state.DefaultDeviceStateService;
import com.ciat.bim.server.state.DeviceStateService;
import com.ciat.bim.server.telemetry.sub.AlarmSubscriptionUpdate;
import com.ciat.bim.server.telemetry.sub.TelemetrySubscriptionUpdate;
import com.ciat.bim.server.timeseries.TimeseriesService;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.server.utils.JacksonUtil;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;

@Slf4j
@TbCoreComponent
@Service
public class DefaultSubscriptionManagerService extends TbApplicationEventListener<PartitionChangeEvent> implements SubscriptionManagerService {


//    @Autowired
//    private IAttributeKvService attrService;
//
//    @Autowired
//    private TimeseriesService tsService;
//
    @Autowired
    private PartitionService partitionService;
//
//    @Autowired
//    private TbServiceInfoProvider serviceInfoProvider;
//
//    @Autowired
//    private TbQueueProducerProvider producerProvider;
//
//    @Autowired
//    private TbLocalSubscriptionService localSubscriptionService;
//
//    @Autowired
//    private DeviceStateService deviceStateService;
//
//    @Autowired
//    private TbClusterService clusterService;
//
      private final Map<EntityId, Set<TbSubscription>> subscriptionsByEntityId = new ConcurrentHashMap<>();
      private final Map<String, Map<Integer, TbSubscription>> subscriptionsByWsSessionId = new ConcurrentHashMap<>();
      private final ConcurrentMap<TopicPartitionInfo, Set<TbSubscription>> partitionedSubscriptions = new ConcurrentHashMap<>();
      private final Set<TopicPartitionInfo> currentPartitions = ConcurrentHashMap.newKeySet();
//
//    private ExecutorService tsCallBackExecutor;
//    private String serviceId;
//    private TbQueueProducer<TbProtoQueueMsg<ToCoreNotificationMsg>> toCoreNotificationsProducer;
//
//    @PostConstruct
//    public void initExecutor() {
//        tsCallBackExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("ts-sub-callback"));
//        serviceId = serviceInfoProvider.getServiceId();
//        toCoreNotificationsProducer = producerProvider.getTbCoreNotificationsMsgProducer();
//    }
//
//    @PreDestroy
//    public void shutdownExecutor() {
//        if (tsCallBackExecutor != null) {
//            tsCallBackExecutor.shutdownNow();
//        }
//    }
//
    @Override
    public void addSubscription(TbSubscription subscription, TbCallback callback) {
        log.trace("[{}][{}][{}] Registering subscription for entity [{}]",
                subscription.getServiceId(), subscription.getSessionId(), subscription.getSubscriptionId(), subscription.getEntityId());
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, subscription.getTenantId(), subscription.getEntityId());
        if (currentPartitions.contains(tpi)) {
            partitionedSubscriptions.computeIfAbsent(tpi, k -> ConcurrentHashMap.newKeySet()).add(subscription);
            callback.onSuccess();
        } else {
            log.warn("[{}][{}] Entity belongs to external partition. Probably rebalancing is in progress. Topic: {}"
                    , subscription.getTenantId(), subscription.getEntityId(), tpi.getFullTopicName());
            callback.onFailure(new RuntimeException("Entity belongs to external partition " + tpi.getFullTopicName() + "!"));
        }
        boolean newSubscription = subscriptionsByEntityId
                .computeIfAbsent(subscription.getEntityId(), k -> ConcurrentHashMap.newKeySet()).add(subscription);
        subscriptionsByWsSessionId.computeIfAbsent(subscription.getSessionId(), k -> new ConcurrentHashMap<>()).put(subscription.getSubscriptionId(), subscription);
        if (newSubscription) {
//            switch (subscription.getType()) {
//                case TIMESERIES:
//                    handleNewTelemetrySubscription((TbTimeseriesSubscription) subscription);
//                    break;
//                case ATTRIBUTES:
//                    handleNewAttributeSubscription((TbAttributeSubscription) subscription);
//                    break;
//                case ALARMS:
//                    handleNewAlarmsSubscription((TbAlarmsSubscription) subscription);
//                    break;
//            }
        }
    }

    @Override
    public void cancelSubscription(String sessionId, int subscriptionId, TbCallback callback) {
        log.debug("[{}][{}] Going to remove subscription.", sessionId, subscriptionId);
        Map<Integer, TbSubscription> sessionSubscriptions = subscriptionsByWsSessionId.get(sessionId);
        if (sessionSubscriptions != null) {
            TbSubscription subscription = sessionSubscriptions.remove(subscriptionId);
            if (subscription != null) {
                removeSubscriptionFromEntityMap(subscription);
                removeSubscriptionFromPartitionMap(subscription);
                if (sessionSubscriptions.isEmpty()) {
                    subscriptionsByWsSessionId.remove(sessionId);
                }
            } else {
                log.debug("[{}][{}] Subscription not found!", sessionId, subscriptionId);
            }
        } else {
            log.debug("[{}] No session subscriptions found!", sessionId);
        }
        callback.onSuccess();
    }

    @Override
    protected void onTbApplicationEvent(PartitionChangeEvent partitionChangeEvent) {
//        if (ServiceType.TB_CORE.equals(partitionChangeEvent.getServiceType())) {
//            Set<TopicPartitionInfo> removedPartitions = new HashSet<>(currentPartitions);
//            removedPartitions.removeAll(partitionChangeEvent.getPartitions());
//
//            currentPartitions.clear();
//            currentPartitions.addAll(partitionChangeEvent.getPartitions());
//
//            // We no longer manage current partition of devices;
//            removedPartitions.forEach(partition -> {
//                Set<TbSubscription> subs = partitionedSubscriptions.remove(partition);
//                if (subs != null) {
//                    subs.forEach(sub -> {
//                        if (!serviceId.equals(sub.getServiceId())) {
//                            removeSubscriptionFromEntityMap(sub);
//                        }
//                    });
//                }
//            });
//        }
    }

//    @Override
//    public void onTimeSeriesUpdate(TenantId tenantId, EntityId entityId, List<AttributeKv> ts, TbCallback callback) {
//        onLocalTelemetrySubUpdate(entityId,
//                s -> {
//                    if (TbSubscriptionType.TIMESERIES.equals(s.getType())) {
//                        return (TbTimeseriesSubscription) s;
//                    } else {
//                        return null;
//                    }
//                }, s -> true, s -> {
//                    List<AttributeKv> subscriptionUpdate = null;
//                    for (AttributeKv kv : ts) {
//                        if ((s.isAllKeys() || s.getKeyStates().containsKey((kv.getKey())))) {
//                            if (subscriptionUpdate == null) {
//                                subscriptionUpdate = new ArrayList<>();
//                            }
//                            subscriptionUpdate.add(kv);
//                        }
//                    }
//                    return subscriptionUpdate;
//                });
//        if (entityId.getEntityType() == EntityType.DEVICE) {
//            updateDeviceInactivityTimeout(tenantId, entityId, ts);
//        }
//        callback.onSuccess();
//    }
//
//    @Override
//    public void onAttributesUpdate(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, TbCallback callback) {
//        onAttributesUpdate(tenantId, entityId, scope, attributes, true, callback);
//    }
//
//    @Override
//    public void onAttributesUpdate(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice, TbCallback callback) {
//        onLocalTelemetrySubUpdate(entityId,
//                s -> {
//                    if (TbSubscriptionType.ATTRIBUTES.equals(s.getType())) {
//                        return (TbAttributeSubscription) s;
//                    } else {
//                        return null;
//                    }
//                },
//                s -> (TbAttributeSubscriptionScope.ANY_SCOPE.equals(s.getScope()) || scope.equals(s.getScope().name())),
//                s -> {
//                    List<TsKvEntry> subscriptionUpdate = null;
//                    for (AttributeKv kv : attributes) {
//                        if (s.isAllKeys() || s.getKeyStates().containsKey(kv.getKey())) {
//                            if (subscriptionUpdate == null) {
//                                subscriptionUpdate = new ArrayList<>();
//                            }
//                            subscriptionUpdate.add(new BasicTsKvEntry(kv.getLastUpdateTs(), kv));
//                        }
//                    }
//                    return subscriptionUpdate;
//                });
//        if (entityId.getEntityType() == EntityType.DEVICE) {
//            if (TbAttributeSubscriptionScope.SERVER_SCOPE.name().equalsIgnoreCase(scope)) {
//                updateDeviceInactivityTimeout(tenantId, entityId, attributes);
//            } else if (TbAttributeSubscriptionScope.SHARED_SCOPE.name().equalsIgnoreCase(scope) && notifyDevice) {
//                clusterService.pushMsgToCore(DeviceAttributesEventNotificationMsg.onUpdate(tenantId,
//                        new DeviceId(entityId.getId()), DataConstants.SHARED_SCOPE, new ArrayList<>(attributes))
//                        , null);
//            }
//        }
//        callback.onSuccess();
//    }
//
//    private void updateDeviceInactivityTimeout(TenantId tenantId, EntityId entityId, List<? extends AttributeKv> kvEntries) {
//        for (AttributeKv kvEntry : kvEntries) {
//            if (kvEntry.getKey().equals(DefaultDeviceStateService.INACTIVITY_TIMEOUT)) {
//                deviceStateService.onDeviceInactivityTimeoutUpdate(tenantId, new DeviceId(entityId.getId()), kvEntry.getLongvalue().orElse(0L));
//            }
//        }
//    }
//
//    @Override
//    public void onAlarmUpdate(TenantId tenantId, EntityId entityId, Alarm alarm, TbCallback callback) {
//        onLocalAlarmSubUpdate(entityId,
//                s -> {
//                    if (TbSubscriptionType.ALARMS.equals(s.getType())) {
//                        return (TbAlarmsSubscription) s;
//                    } else {
//                        return null;
//                    }
//                },
//                s -> alarm.getCreatedTime() >= s.getTs(),
//                s -> alarm,
//                false
//        );
//        callback.onSuccess();
//    }
//
//    @Override
//    public void onAlarmDeleted(TenantId tenantId, EntityId entityId, Alarm alarm, TbCallback callback) {
//        onLocalAlarmSubUpdate(entityId,
//                s -> {
//                    if (TbSubscriptionType.ALARMS.equals(s.getType())) {
//                        return (TbAlarmsSubscription) s;
//                    } else {
//                        return null;
//                    }
//                },
//                s -> alarm.getCreatedTime() >= s.getTs(),
//                s -> alarm,
//                true
//        );
//        callback.onSuccess();
//    }
//
//    @Override
//    public void onAttributesDelete(TenantId tenantId, EntityId entityId, String scope, List<String> keys, TbCallback callback) {
//        onLocalTelemetrySubUpdate(entityId,
//                s -> {
//                    if (TbSubscriptionType.ATTRIBUTES.equals(s.getType())) {
//                        return (TbAttributeSubscription) s;
//                    } else {
//                        return null;
//                    }
//                },
//                s -> (TbAttributeSubscriptionScope.ANY_SCOPE.equals(s.getScope()) || scope.equals(s.getScope().name())),
//                s -> {
//                    List<TsKvEntry> subscriptionUpdate = null;
//                    for (String key : keys) {
//                        if (s.isAllKeys() || s.getKeyStates().containsKey(key)) {
//                            if (subscriptionUpdate == null) {
//                                subscriptionUpdate = new ArrayList<>();
//                            }
//                            subscriptionUpdate.add(new BasicTsKvEntry(0, new StringDataEntry(key, null)));
//                        }
//                    }
//                    return subscriptionUpdate;
//                });
//        callback.onSuccess();
//    }
//
//    private <T extends TbSubscription> void onLocalTelemetrySubUpdate(EntityId entityId,
//                                                                      Function<TbSubscription, T> castFunction,
//                                                                      Predicate<T> filterFunction,
//                                                                      Function<T, List<AttributeKv>> processFunction) {
//        Set<TbSubscription> entitySubscriptions = subscriptionsByEntityId.get(entityId);
//        if (entitySubscriptions != null) {
//            entitySubscriptions.stream().map(castFunction).filter(Objects::nonNull).filter(filterFunction).forEach(s -> {
//                List<TsKvEntry> subscriptionUpdate = processFunction.apply(s);
//                if (subscriptionUpdate != null && !subscriptionUpdate.isEmpty()) {
//                    if (serviceId.equals(s.getServiceId())) {
//                        TelemetrySubscriptionUpdate update = new TelemetrySubscriptionUpdate(s.getSubscriptionId(), subscriptionUpdate);
//                        localSubscriptionService.onSubscriptionUpdate(s.getSessionId(), update, TbCallback.EMPTY);
//                    } else {
//                        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, s.getServiceId());
//                        toCoreNotificationsProducer.send(tpi, toProto(s, subscriptionUpdate), null);
//                    }
//                }
//            });
//        } else {
//            log.debug("[{}] No device subscriptions to process!", entityId);
//        }
//    }
//
//    private void onLocalAlarmSubUpdate(EntityId entityId,
//                                       Function<TbSubscription, TbAlarmsSubscription> castFunction,
//                                       Predicate<TbAlarmsSubscription> filterFunction,
//                                       Function<TbAlarmsSubscription, Alarm> processFunction, boolean deleted) {
//        Set<TbSubscription> entitySubscriptions = subscriptionsByEntityId.get(entityId);
//        if (entitySubscriptions != null) {
//            entitySubscriptions.stream().map(castFunction).filter(Objects::nonNull).filter(filterFunction).forEach(s -> {
//                Alarm alarm = processFunction.apply(s);
//                if (alarm != null) {
//                    if (serviceId.equals(s.getServiceId())) {
//                        AlarmSubscriptionUpdate update = new AlarmSubscriptionUpdate(s.getSubscriptionId(), alarm, deleted);
//                        localSubscriptionService.onSubscriptionUpdate(s.getSessionId(), update, TbCallback.EMPTY);
//                    } else {
//                        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, s.getServiceId());
//                        toCoreNotificationsProducer.send(tpi, toProto(s, alarm, deleted), null);
//                    }
//                }
//            });
//        } else {
//            log.debug("[{}] No device subscriptions to process!", entityId);
//        }
//    }

    private void removeSubscriptionFromEntityMap(TbSubscription sub) {
        Set<TbSubscription> entitySubSet = subscriptionsByEntityId.get(sub.getEntityId());
        if (entitySubSet != null) {
            entitySubSet.remove(sub);
            if (entitySubSet.isEmpty()) {
                subscriptionsByEntityId.remove(sub.getEntityId());
            }
        }
    }

    private void removeSubscriptionFromPartitionMap(TbSubscription sub) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, sub.getTenantId(), sub.getEntityId());
        Set<TbSubscription> subs = partitionedSubscriptions.get(tpi);
        if (subs != null) {
            subs.remove(sub);
        }
    }
//
//    private void handleNewAttributeSubscription(TbAttributeSubscription subscription) {
//        log.trace("[{}][{}][{}] Processing remote attribute subscription for entity [{}]",
//                serviceId, subscription.getSessionId(), subscription.getSubscriptionId(), subscription.getEntityId());
//
//        final Map<String, Long> keyStates = subscription.getKeyStates();
//        DonAsynchron.withCallback(attrService.find(subscription.getTenantId(), subscription.getEntityId(), DataConstants.CLIENT_SCOPE, keyStates.keySet()), values -> {
//                    List<TsKvEntry> missedUpdates = new ArrayList<>();
//                    values.forEach(latestEntry -> {
//                        if (latestEntry.getLastUpdateTs() > keyStates.get(latestEntry.getKey())) {
//                            missedUpdates.add(new BasicTsKvEntry(latestEntry.getLastUpdateTs(), latestEntry));
//                        }
//                    });
//                    if (!missedUpdates.isEmpty()) {
//                        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, subscription.getServiceId());
//                        toCoreNotificationsProducer.send(tpi, toProto(subscription, missedUpdates), null);
//                    }
//                },
//                e -> log.error("Failed to fetch missed updates.", e), tsCallBackExecutor);
//    }
//
//    private void handleNewAlarmsSubscription(TbAlarmsSubscription subscription) {
//        log.trace("[{}][{}][{}] Processing remote alarm subscription for entity [{}]",
//                serviceId, subscription.getSessionId(), subscription.getSubscriptionId(), subscription.getEntityId());
//        //TODO: @dlandiak search all new alarms for this entity.
//    }
//
//    private void handleNewTelemetrySubscription(TbTimeseriesSubscription subscription) {
//        log.trace("[{}][{}][{}] Processing remote telemetry subscription for entity [{}]",
//                serviceId, subscription.getSessionId(), subscription.getSubscriptionId(), subscription.getEntityId());
//
//        long curTs = System.currentTimeMillis();
//
//        if (subscription.isLatestValues()) {
//            DonAsynchron.withCallback(tsService.findLatest(subscription.getTenantId(), subscription.getEntityId(), subscription.getKeyStates().keySet()),
//                    missedUpdates -> {
//                        if (missedUpdates != null && !missedUpdates.isEmpty()) {
//                            TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, subscription.getServiceId());
//                            toCoreNotificationsProducer.send(tpi, toProto(subscription, missedUpdates), null);
//                        }
//                    },
//                    e -> log.error("Failed to fetch missed updates.", e),
//                    tsCallBackExecutor);
//        } else {
//            List<ReadTsKvQuery> queries = new ArrayList<>();
//            subscription.getKeyStates().forEach((key, value) -> {
//                if (curTs > value) {
//                    long startTs = subscription.getStartTime() > 0 ? Math.max(subscription.getStartTime(), value + 1L) : (value + 1L);
//                    long endTs = subscription.getEndTime() > 0 ? Math.min(subscription.getEndTime(), curTs) : curTs;
//                    queries.add(new BaseReadTsKvQuery(key, startTs, endTs, 0, 1000, Aggregation.NONE));
//                }
//            });
//            if (!queries.isEmpty()) {
//                DonAsynchron.withCallback(tsService.findAll(subscription.getTenantId(), subscription.getEntityId(), queries),
//                        missedUpdates -> {
//                            if (missedUpdates != null && !missedUpdates.isEmpty()) {
//                                TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, subscription.getServiceId());
//                                toCoreNotificationsProducer.send(tpi, toProto(subscription, missedUpdates), null);
//                            }
//                        },
//                        e -> log.error("Failed to fetch missed updates.", e),
//                        tsCallBackExecutor);
//            }
//        }
//    }
//
//    private TbProtoQueueMsg<ToCoreNotificationMsg> toProto(TbSubscription subscription, List<AttributeKv> updates) {
//        TbSubscriptionUpdateProto.Builder builder = TbSubscriptionUpdateProto.newBuilder();
//
//        builder.setSessionId(subscription.getSessionId());
//        builder.setSubscriptionId(subscription.getSubscriptionId());
//
//        Map<String, List<Object>> data = new TreeMap<>();
//        for (AttributeKv tsEntry : updates) {
//            List<Object> values = data.computeIfAbsent(tsEntry.getKey(), k -> new ArrayList<>());
//            Object[] value = new Object[2];
//            value[0] = tsEntry.getTs();
//            value[1] = tsEntry.getValueAsString();
//            values.add(value);
//        }
//
//        data.forEach((key, value) -> {
//            TbSubscriptionUpdateValueListProto.Builder dataBuilder = TbSubscriptionUpdateValueListProto.newBuilder();
//            dataBuilder.setKey(key);
//            boolean hasData = false;
//            for (Object v : value) {
//                Object[] array = (Object[]) v;
//                dataBuilder.addTs((long) array[0]);
//                String strVal = (String) array[1];
//                if (strVal != null) {
//                    hasData = true;
//                    dataBuilder.addValue(strVal);
//                }
//            }
//            if (hasData) {
//                builder.addData(dataBuilder.build());
//            }
//        });
//
//        ToCoreNotificationMsg toCoreMsg = ToCoreNotificationMsg.newBuilder().setToLocalSubscriptionServiceMsg(
//                LocalSubscriptionServiceMsgProto.newBuilder().setSubUpdate(builder.build()).build())
//                .build();
//        return new TbProtoQueueMsg<>(subscription.getEntityId().getId(), toCoreMsg);
//    }
//
//    private TbProtoQueueMsg<ToCoreNotificationMsg> toProto(TbSubscription subscription, Alarm alarm, boolean deleted) {
//        TbAlarmSubscriptionUpdateProto.Builder builder = TbAlarmSubscriptionUpdateProto.newBuilder();
//
//        builder.setSessionId(subscription.getSessionId());
//        builder.setSubscriptionId(subscription.getSubscriptionId());
//        builder.setAlarm(JacksonUtil.toString(alarm));
//        builder.setDeleted(deleted);
//
//        ToCoreNotificationMsg toCoreMsg = ToCoreNotificationMsg.newBuilder().setToLocalSubscriptionServiceMsg(
//                LocalSubscriptionServiceMsgProto.newBuilder()
//                        .setAlarmSubUpdate(builder.build()).build())
//                .build();
//        return new TbProtoQueueMsg<>(subscription.getEntityId().getId(), toCoreMsg);
//    }

}
