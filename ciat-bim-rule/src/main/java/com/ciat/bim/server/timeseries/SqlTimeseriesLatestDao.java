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
package com.ciat.bim.server.timeseries;

import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.kv.Aggregation;
import com.ciat.bim.server.common.data.kv.BaseReadTsKvQuery;
import com.ciat.bim.server.common.data.kv.DeleteTsKvQuery;
import com.ciat.bim.server.common.data.kv.ReadTsKvQuery;
import com.ciat.bim.server.common.stats.StatsFactory;
import com.ciat.bim.server.dao.DaoUtil;
import com.ciat.bim.server.dao.sql.ScheduledLogExecutorComponent;
import com.ciat.bim.server.dao.sql.TbSqlBlockingQueueParams;
import com.ciat.bim.server.dao.sql.TbSqlBlockingQueueWrapper;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.StringDataEntry;
import org.jeecg.modules.device.entity.TsKey;
import org.jeecg.modules.device.entity.TsKv;
import org.jeecg.modules.device.entity.TsKvLatest;
import org.jeecg.modules.device.service.ITsKvLatestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component

public class SqlTimeseriesLatestDao extends BaseAbstractSqlTimeseriesDao implements TimeseriesLatestDao {

    private static final String DESC_ORDER = "DESC";
//
    @Autowired
    private ITsKvLatestService tsKvLatestRepository;

    @Autowired
    protected AggregationTimeseriesDao aggregationTimeseriesDao;

//    @Autowired
//    private SearchTsKvLatestRepository searchTsKvLatestRepository;
//
//    @Autowired
//    private ITsKvLatestService insertLatestTsRepository;

    private TbSqlBlockingQueueWrapper<TsKvLatest> tsLatestQueue;

    @Value("${sql.ts_latest.batch_size:1000}")
    private int tsLatestBatchSize;

    @Value("${sql.ts_latest.batch_max_delay:100}")
    private long tsLatestMaxDelay;

    @Value("${sql.ts_latest.stats_print_interval_ms:1000}")
    private long tsLatestStatsPrintIntervalMs;

    @Value("${sql.ts_latest.batch_threads:4}")
    private int tsLatestBatchThreads;

    @Value("${sql.batch_sort:false}")
    protected boolean batchSortEnabled;

    @Autowired
    protected ScheduledLogExecutorComponent logExecutor;

    @Autowired
    private StatsFactory statsFactory;

    @PostConstruct
    protected void init() {
        TbSqlBlockingQueueParams tsLatestParams = TbSqlBlockingQueueParams.builder()
                .logName("TS Latest")
                .batchSize(tsLatestBatchSize)
                .maxDelay(tsLatestMaxDelay)
                .statsPrintIntervalMs(tsLatestStatsPrintIntervalMs)
                .statsNamePrefix("ts.latest")
                .batchSortEnabled(false)
                .build();

        Function<TsKvLatest, Integer> hashcodeFunction = entity -> entity.getEntityId().hashCode();
        tsLatestQueue = new TbSqlBlockingQueueWrapper<>(tsLatestParams, hashcodeFunction, tsLatestBatchThreads, statsFactory);

        tsLatestQueue.init(logExecutor, v -> {
            Map<TsKey, TsKvLatest> trueLatest = new HashMap<>();
            v.forEach(ts -> {
                TsKey key = new TsKey(ts.getEntityId(), ts.getEntityKey());
                trueLatest.merge(key, ts, (oldTs, newTs) -> oldTs.getTs().getTime() <= newTs.getTs().getTime() ? newTs : oldTs);
            });
            List<TsKvLatest> latestEntities = new ArrayList<>(trueLatest.values());
            if (batchSortEnabled) {
                latestEntities.sort(Comparator.comparing((Function<TsKvLatest, String>) TsKvLatest::getEntityId)
                        .thenComparing(TsKvLatest::getEntityKey));
            }
            tsKvLatestRepository.saveOrUpdateBatchByMultiId(latestEntities);
        }, (l, r) -> 0);
    }

    @PreDestroy
    protected void destroy() {
        if (tsLatestQueue != null) {
            tsLatestQueue.destroy();
        }
    }

    @Override
    public ListenableFuture<Void> saveLatest(TenantId tenantId, EntityId entityId, TsKv tsKvEntry) {
        return getSaveLatestFuture(entityId, tsKvEntry);
    }

    @Override
    public ListenableFuture<Void> removeLatest(TenantId tenantId, EntityId entityId, DeleteTsKvQuery query) {
        return getRemoveLatestFuture(tenantId, entityId, query);
    }

    @Override
    public ListenableFuture<TsKv> findLatest(TenantId tenantId, EntityId entityId, String key) {
        return getFindLatestFuture(entityId, key);
    }

    @Override
    public ListenableFuture<List<TsKv>> findAllLatest(TenantId tenantId, EntityId entityId) {
        return getFindAllLatestFuture(entityId);
    }

    @Override
    public List<String> findAllKeysByDeviceProfileId(TenantId tenantId, DeviceProfileId deviceProfileId) {
        if (deviceProfileId != null) {
            return tsKvLatestRepository.getKeysByDeviceProfileId(tenantId.getId(), deviceProfileId.getId());
        } else {
            return tsKvLatestRepository.getKeysByTenantId(tenantId.getId());
        }
    }

    @Override
    public List<String> findAllKeysByEntityIds(TenantId tenantId, List<EntityId> entityIds) {
        return tsKvLatestRepository.findAllKeysByEntityIds(entityIds.stream().map(EntityId::getId).collect(Collectors.toList()));
    }

    private ListenableFuture<Void> getNewLatestEntryFuture(TenantId tenantId, EntityId entityId, DeleteTsKvQuery query) {
        ListenableFuture<List<TsKv>> future = findNewLatestEntryFuture(tenantId, entityId, query);
        return Futures.transformAsync(future, entryList -> {
            if (entryList.size() == 1) {
                return getSaveLatestFuture(entityId, entryList.get(0));
            } else {
                log.trace("Could not find new latest value for [{}], key - {}", entityId, query.getKey());
            }
            return Futures.immediateFuture(null);
        }, service);
    }

    private ListenableFuture<List<TsKv>> findNewLatestEntryFuture(TenantId tenantId, EntityId entityId, DeleteTsKvQuery query) {
        long startTs = 0;
        long endTs = query.getStartTs() - 1;
        ReadTsKvQuery findNewLatestQuery = new BaseReadTsKvQuery(query.getKey(), startTs, endTs, endTs - startTs, 1,
                Aggregation.NONE, DESC_ORDER);
        return aggregationTimeseriesDao.findAllAsync(tenantId, entityId, findNewLatestQuery);
    }

    protected ListenableFuture<TsKv> getFindLatestFuture(EntityId entityId, String key) {
        TsKvLatest compositeKey = new TsKvLatest();
        compositeKey.setEntityKey(key);
        compositeKey.setEntityId(entityId.getId());
      TsKvLatest entry = tsKvLatestRepository.selectByMultiId(compositeKey);
        TsKv result;
        if (entry != null) {
            TsKvLatest tsKvLatestEntity = entry;
            tsKvLatestEntity.setEntityKey(key);
            result = DaoUtil.getData(tsKvLatestEntity);
        } else {
            result = new TsKv(new StringDataEntry(key, null),System.currentTimeMillis());
        }
        return Futures.immediateFuture(result);
    }

    protected ListenableFuture<Void> getRemoveLatestFuture(TenantId tenantId, EntityId entityId, DeleteTsKvQuery query) {
        ListenableFuture<TsKv> latestFuture = getFindLatestFuture(entityId, query.getKey());

        ListenableFuture<Boolean> booleanFuture = Futures.transform(latestFuture, tsKvEntry -> {
            long ts = tsKvEntry.getTs().getTime();
            return ts > query.getStartTs() && ts <= query.getEndTs();
        }, service);

        ListenableFuture<Void> removedLatestFuture = Futures.transformAsync(booleanFuture, isRemove -> {
            if (isRemove) {
                TsKvLatest latestEntity = new TsKvLatest();
                latestEntity.setEntityId(entityId.getId());
                latestEntity.setEntityKey(query.getKey());
                return service.submit(() -> {
                    tsKvLatestRepository.deleteByMultiId(latestEntity);
                    return null;
                });
            }
            return Futures.immediateFuture(null);
        }, service);

        final SimpleListenableFuture<Void> resultFuture = new SimpleListenableFuture<>();
        Futures.addCallback(removedLatestFuture, new FutureCallback<Void>() {
            @Override
            public void onSuccess(@Nullable Void result) {
                if (query.getRewriteLatestIfDeleted()) {
                    ListenableFuture<Void> savedLatestFuture = Futures.transformAsync(booleanFuture, isRemove -> {
                        if (isRemove) {
                            return getNewLatestEntryFuture(tenantId, entityId, query);
                        }
                        return Futures.immediateFuture(null);
                    }, service);

                    try {
                        resultFuture.set(savedLatestFuture.get());
                    } catch (InterruptedException | ExecutionException e) {
                        log.warn("Could not get latest saved value for [{}], {}", entityId, query.getKey(), e);
                    }
                } else {
                    resultFuture.set(null);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                log.warn("[{}] Failed to process remove of the latest value", entityId, t);
            }
        }, MoreExecutors.directExecutor());
        return resultFuture;
    }

    protected ListenableFuture<List<TsKv>> getFindAllLatestFuture(EntityId entityId) {
        return Futures.immediateFuture(
                DaoUtil.convertDataList(Lists.newArrayList(
                        tsKvLatestRepository.findAllByEntityId(entityId.getId()))));
    }

    protected ListenableFuture<Void> getSaveLatestFuture(EntityId entityId, TsKv tsKvEntry) {
        TsKvLatest latestEntity = new TsKvLatest();
        latestEntity.setEntityId(entityId.getId());
        latestEntity.setTs(tsKvEntry.getTs());
        latestEntity.setEntityKey(tsKvEntry.getKey());
        latestEntity.setStrValue(tsKvEntry.getStrValue());
        latestEntity.setDoubleValue(tsKvEntry.getDoubleValue());
        latestEntity.setLongValue(tsKvEntry.getLongValue());
        latestEntity.setBooleanValue(tsKvEntry.getBooleanValue());
        latestEntity.setJsonValue(tsKvEntry.getJsonValue());

        return tsLatestQueue.add(latestEntity);
    }

}
