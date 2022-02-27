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
package com.ciat.bim.server.telemetry;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.apiusage.TbApiUsageStateService;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.ApiUsageRecordKey;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.queue.discovery.PartitionService;
import com.ciat.bim.server.queue.usagestats.TbApiUsageClient;
import com.ciat.bim.server.timeseries.TimeseriesService;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.utils.TbSubscriptionUtils;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.*;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.springframework.stereotype.Service;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ashvayka on 27.03.18.
 */
@Service
@Slf4j
public class DefaultTelemetrySubscriptionService extends AbstractSubscriptionService implements TelemetrySubscriptionService {

    private final IAttributeKvService attrService;
    private final TimeseriesService tsService;
   // private final EntityViewService entityViewService;
    private final TbApiUsageClient apiUsageClient;
    private final TbApiUsageStateService apiUsageStateService;

    private ExecutorService tsCallBackExecutor;

    public DefaultTelemetrySubscriptionService(IAttributeKvService attrService,
                                               TimeseriesService tsService,
                                              // EntityViewService entityViewService,
                                               TbClusterService clusterService,
                                               PartitionService partitionService,
                                               TbApiUsageClient apiUsageClient,
                                               TbApiUsageStateService apiUsageStateService) {
        super(clusterService, partitionService);
        this.attrService = attrService;
        this.tsService = tsService;
      //  this.entityViewService = entityViewService;
        this.apiUsageClient = apiUsageClient;
        this.apiUsageStateService = apiUsageStateService;
    }

    @PostConstruct
    public void initExecutor() {
        super.initExecutor();
        tsCallBackExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("ts-service-ts-callback"));
    }

    @Override
    protected String getExecutorPrefix() {
        return "ts";
    }

    @PreDestroy
    public void shutdownExecutor() {
        if (tsCallBackExecutor != null) {
            tsCallBackExecutor.shutdownNow();
        }
        super.shutdownExecutor();
    }

    @Override
    public void saveAndNotify(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, null, entityId, ts, 0L, callback);
    }

    @Override
    public void saveAndNotify(TenantId tenantId, CustomerId customerId, EntityId entityId, List<TsKv> ts, long ttl, FutureCallback<Void> callback) {
        checkInternalEntity(entityId);
        boolean sysTenant = TenantId.SYS_TENANT_ID.equals(tenantId) || tenantId == null;
        if (sysTenant || apiUsageStateService.getApiUsageState(tenantId).isDbStorageEnabled()) {
            saveAndNotifyInternal(tenantId, entityId, ts, ttl, new FutureCallback<Integer>() {
                @Override
                public void onSuccess(Integer result) {
                    if (!sysTenant && result != null && result > 0) {
                        apiUsageClient.report(tenantId.getId(), customerId, ApiUsageRecordKey.STORAGE_DP_COUNT, result);
                    }
                    callback.onSuccess(null);
                }

                @Override
                public void onFailure(Throwable t) {
                    callback.onFailure(t);
                }
            });
        } else {
            callback.onFailure(new RuntimeException("DB storage writes are disabled due to API limits!"));
        }
    }

    @Override
    public void saveAndNotifyInternal(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Integer> callback) {
        saveAndNotifyInternal(tenantId, entityId, ts, 0L, callback);
    }

    @Override
    public void saveAndNotifyInternal(TenantId tenantId, EntityId entityId, List<TsKv> ts, long ttl, FutureCallback<Integer> callback) {
        ListenableFuture<Integer> saveFuture = tsService.save(tenantId, entityId, ts, ttl);
        addMainCallback(saveFuture, callback);
        addWsCallback(saveFuture, success -> onTimeSeriesUpdate(tenantId, entityId, ts));
//        if (EntityType.DEVICE.equals(entityId.getEntityType()) || EntityType.ASSET.equals(entityId.getEntityType())) {
//            Futures.addCallback(this.entityViewService.findEntityViewsByTenantIdAndEntityIdAsync(tenantId, entityId),
//                    new FutureCallback<List<EntityView>>() {
//                        @Override
//                        public void onSuccess(@Nullable List<EntityView> result) {
//                            if (result != null) {
//                                Map<String, List<TsKvEntry>> tsMap = new HashMap<>();
//                                for (TsKvEntry entry : ts) {
//                                    tsMap.computeIfAbsent(entry.getKey(), s -> new ArrayList<>()).add(entry);
//                                }
//                                for (EntityView entityView : result) {
//                                    List<String> keys = entityView.getKeys() != null && entityView.getKeys().getTimeseries() != null ?
//                                            entityView.getKeys().getTimeseries() : new ArrayList<>(tsMap.keySet());
//                                    List<TsKvEntry> entityViewLatest = new ArrayList<>();
//                                    long startTs = entityView.getStartTimeMs();
//                                    long endTs = entityView.getEndTimeMs() == 0 ? Long.MAX_VALUE : entityView.getEndTimeMs();
//                                    for (String key : keys) {
//                                        List<TsKvEntry> entries = tsMap.get(key);
//                                        if (entries != null) {
//                                            Optional<TsKvEntry> tsKvEntry = entries.stream()
//                                                    .filter(entry -> entry.getTs() > startTs && entry.getTs() <= endTs)
//                                                    .max(Comparator.comparingLong(TsKvEntry::getTs));
//                                            tsKvEntry.ifPresent(entityViewLatest::add);
//                                        }
//                                    }
//                                    if (!entityViewLatest.isEmpty()) {
//                                        saveLatestAndNotify(tenantId, entityView.getId(), entityViewLatest, new FutureCallback<Void>() {
//                                            @Override
//                                            public void onSuccess(@Nullable Void tmp) {
//                                            }
//
//                                            @Override
//                                            public void onFailure(Throwable t) {
//                                            }
//                                        });
//                                    }
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Throwable t) {
//                            log.error("Error while finding entity views by tenantId and entityId", t);
//                        }
//                    }, MoreExecutors.directExecutor());
//        }
    }

    @Override

    public void saveAndNotify(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, entityId, scope, attributes, true, callback);
    }

    @Override
    public void saveAndNotify(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice, FutureCallback<Void> callback) {
        checkInternalEntity(entityId);
        saveAndNotifyInternal(tenantId, entityId, scope, attributes, notifyDevice, callback);
    }

    @Override
    public void saveAndNotifyInternal(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice, FutureCallback<Void> callback) {
        ListenableFuture<List<Void>> saveFuture = attrService.save(tenantId, entityId, scope, attributes);
        addVoidCallback(saveFuture, callback);
        addWsCallback(saveFuture, success -> onAttributesUpdate(tenantId, entityId, scope, attributes, notifyDevice));
    }

    @Override
    public void saveLatestAndNotify(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Void> callback) {
        checkInternalEntity(entityId);
        saveLatestAndNotifyInternal(tenantId, entityId, ts, callback);
    }

    @Override
    public void saveLatestAndNotifyInternal(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Void> callback) {
        ListenableFuture<List<Void>> saveFuture = tsService.saveLatest(tenantId, entityId, ts);
        addVoidCallback(saveFuture, callback);
        addWsCallback(saveFuture, success -> onTimeSeriesUpdate(tenantId, entityId, ts));
    }

    @Override
    public void deleteAndNotify(TenantId tenantId, EntityId entityId, String scope, List<String> keys, FutureCallback<Void> callback) {
        checkInternalEntity(entityId);
        deleteAndNotifyInternal(tenantId, entityId, scope, keys, callback);
    }

    @Override
    public void deleteAndNotifyInternal(TenantId tenantId, EntityId entityId, String scope, List<String> keys, FutureCallback<Void> callback) {
        ListenableFuture<List<Void>> deleteFuture = attrService.removeAll(tenantId, entityId, scope, keys);
        addVoidCallback(deleteFuture, callback);
        addWsCallback(deleteFuture, success -> onAttributesDelete(tenantId, entityId, scope, keys));
    }

    @Override
    public void deleteLatest(TenantId tenantId, EntityId entityId, List<String> keys, FutureCallback<Void> callback) {
        checkInternalEntity(entityId);
        deleteLatestInternal(tenantId, entityId, keys, callback);
    }

    @Override
    public void deleteLatestInternal(TenantId tenantId, EntityId entityId, List<String> keys, FutureCallback<Void> callback) {
        ListenableFuture<List<Void>> deleteFuture = tsService.removeLatest(tenantId, entityId, keys);
        addVoidCallback(deleteFuture, callback);
    }

    @Override
    public void deleteAllLatest(TenantId tenantId, EntityId entityId, FutureCallback<Collection<String>> callback) {
        ListenableFuture<Collection<String>> deleteFuture = tsService.removeAllLatest(tenantId, entityId);
        Futures.addCallback(deleteFuture, new FutureCallback<Collection<String>>() {
            @Override
            public void onSuccess(@Nullable Collection<String> result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        }, tsCallBackExecutor);
    }

    @Override
    public void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, long value, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, entityId, scope, Collections.singletonList(new AttributeKv(new LongDataEntry(key, value)
                , System.currentTimeMillis())), callback);
    }

    @Override
    public void saveAttrTimeAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, long value, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, entityId, scope, Collections.singletonList(new AttributeKv(new DateTimeDataEntry(key, value)
                , System.currentTimeMillis())), callback);
    }

    @Override
    public void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, String value, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, entityId, scope, Collections.singletonList(new AttributeKv(new StringDataEntry(key, value)
                , System.currentTimeMillis())), callback);
    }

    @Override
    public void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, double value, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, entityId, scope, Collections.singletonList(new AttributeKv(new DoubleDataEntry(key, value)
                , System.currentTimeMillis())), callback);
    }

    @Override
    public void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, boolean value, FutureCallback<Void> callback) {
        saveAndNotify(tenantId, entityId, scope, Collections.singletonList(new AttributeKv(new BooleanDataEntry(key, value)
                , System.currentTimeMillis())), callback);
    }

    private void onAttributesUpdate(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, tenantId.getId(), entityId.getId());
        if (currentPartitions.contains(tpi)) {
            if (subscriptionManagerService.isPresent()) {
                subscriptionManagerService.get().onAttributesUpdate(tenantId, entityId, scope, attributes, notifyDevice, TbCallback.EMPTY);
            } else {
                log.warn("Possible misconfiguration because subscriptionManagerService is null!");
            }
        } else {
            TransportProtos.ToCoreMsg toCoreMsg = TbSubscriptionUtils.toAttributesUpdateProto(tenantId, entityId, scope, attributes);
            clusterService.pushMsgToCore(tpi, entityId.getId(), toCoreMsg, null);
        }
    }

    private void onAttributesDelete(TenantId tenantId, EntityId entityId, String scope, List<String> keys) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, tenantId.getId(), entityId.getId());
        if (currentPartitions.contains(tpi)) {
            if (subscriptionManagerService.isPresent()) {
                subscriptionManagerService.get().onAttributesDelete(tenantId, entityId, scope, keys, TbCallback.EMPTY);
            } else {
                log.warn("Possible misconfiguration because subscriptionManagerService is null!");
            }
        } else {
            TransportProtos.ToCoreMsg toCoreMsg = TbSubscriptionUtils.toAttributesDeleteProto(tenantId, entityId, scope, keys);
            clusterService.pushMsgToCore(tpi, entityId.getId(), toCoreMsg, null);
        }
    }

    private void onTimeSeriesUpdate(TenantId tenantId, EntityId entityId, List<TsKv> ts) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, tenantId.getId(), entityId.getId());
        if (currentPartitions.contains(tpi)) {
            if (subscriptionManagerService.isPresent()) {
                subscriptionManagerService.get().onTimeSeriesUpdate(tenantId, entityId, ts, TbCallback.EMPTY);
            } else {
                log.warn("Possible misconfiguration because subscriptionManagerService is null!");
            }
        } else {
            TransportProtos.ToCoreMsg toCoreMsg = TbSubscriptionUtils.toTimeseriesUpdateProto(tenantId, entityId, ts);
            clusterService.pushMsgToCore(tpi, entityId.getId(), toCoreMsg, null);
        }
    }

    private <S> void addVoidCallback(ListenableFuture<S> saveFuture, final FutureCallback<Void> callback) {
        Futures.addCallback(saveFuture, new FutureCallback<S>() {
            @Override
            public void onSuccess(@Nullable S result) {
                callback.onSuccess(null);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        }, tsCallBackExecutor);
    }

    private <S> void addMainCallback(ListenableFuture<S> saveFuture, final FutureCallback<S> callback) {
        Futures.addCallback(saveFuture, new FutureCallback<S>() {
            @Override
            public void onSuccess(@Nullable S result) {
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onFailure(t);
            }
        }, tsCallBackExecutor);
    }

    private void checkInternalEntity(EntityId entityId) {
        if (EntityType.API_USAGE_STATE.equals(entityId.getEntityType())) {
            throw new RuntimeException("Can't update API Usage State!");
        }
    }

}
