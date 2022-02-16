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

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.kv.Aggregation;
import com.ciat.bim.server.common.data.kv.DeleteTsKvQuery;
import com.ciat.bim.server.common.data.kv.ReadTsKvQuery;
import com.ciat.bim.server.common.stats.StatsFactory;
import com.ciat.bim.server.dao.DaoUtil;
import com.ciat.bim.server.dao.sql.TbSqlBlockingQueueParams;
import com.ciat.bim.server.dao.sql.TbSqlBlockingQueueWrapper;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.SettableFuture;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.TsKv;
import org.jeecg.modules.device.service.ITsKvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractChunkedAggregationTimeseriesDao extends AbstractSqlTimeseriesDao implements TimeseriesDao {

    @Autowired
    protected ITsKvService tsKvRepository;

//    @Autowired
//    protected InsertTsRepository<TsKv> insertRepository;

    protected TbSqlBlockingQueueWrapper<TsKv> tsQueue;
    @Autowired
    private StatsFactory statsFactory;

    @PostConstruct
    protected void init() {
        TbSqlBlockingQueueParams tsParams = TbSqlBlockingQueueParams.builder()
                .logName("TS")
                .batchSize(tsBatchSize)
                .maxDelay(tsMaxDelay)
                .statsPrintIntervalMs(tsStatsPrintIntervalMs)
                .statsNamePrefix("ts")
                .batchSortEnabled(batchSortEnabled)
                .build();

        Function<TsKv, Integer> hashcodeFunction = entity -> entity.getEntityId().hashCode();
        tsQueue = new TbSqlBlockingQueueWrapper<>(tsParams, hashcodeFunction, tsBatchThreads, statsFactory);
        tsQueue.init(logExecutor, v -> tsKvRepository.saveOrUpdateBatch(v),
                Comparator.comparing((Function<TsKv, String>) TsKv::getEntityId)
                        .thenComparing(TsKv::getKey)
                        .thenComparing(TsKv::getTs)
                );
    }

    @PreDestroy
    protected void destroy() {
        if (tsQueue != null) {
            tsQueue.destroy();
        }
    }

    @Override
    public ListenableFuture<Void> remove(TenantId tenantId, EntityId entityId, DeleteTsKvQuery query) {
        return service.submit(() -> {
            tsKvRepository.removeById(
                    entityId.getId());
            return null;
        });
    }

    @Override
    public ListenableFuture<Integer> savePartition(TenantId tenantId, EntityId entityId, long tsKvEntryTs, String key) {
        return Futures.immediateFuture(null);
    }

    @Override
    public ListenableFuture<Void> removePartition(TenantId tenantId, EntityId entityId, DeleteTsKvQuery query) {
        return Futures.immediateFuture(null);
    }

    @Override
    public ListenableFuture<List<TsKv>> findAllAsync(TenantId tenantId, EntityId entityId, List<ReadTsKvQuery> queries) {
        return processFindAllAsync(tenantId, entityId, queries);
    }

    @Override
    public ListenableFuture<List<TsKv>> findAllAsync(TenantId tenantId, EntityId entityId, ReadTsKvQuery query) {
//        if (query.getAggregation() == Aggregation.NONE) {
//         //   return findAllAsyncWithLimit(entityId, query);
//        } else
//        {
            long stepTs = query.getStartTs();
            List<ListenableFuture<Optional<TsKv>>> futures = new ArrayList<>();
//            while (stepTs < query.getEndTs()) {
//                long startTs = stepTs;
//                long endTs = stepTs + query.getInterval();
//                long ts = startTs + (endTs - startTs) / 2;
//                futures.add(findAndAggregateAsync(entityId, query.getKey(), startTs, endTs, ts, query.getAggregation()));
//                stepTs = endTs;
//            }
            return getTskvEntriesFuture(Futures.allAsList(futures));
//        }
    }

//    private ListenableFuture<List<TsKv>> findAllAsyncWithLimit(EntityId entityId, ReadTsKvQuery query) {
//        Integer keyId = getOrSaveKeyId(query.getKey());
//        List<TsKv> tsKvEntities = tsKvRepository.findAllWithLimit(
//                entityId.getId(),
//                keyId,
//                query.getStartTs(),
//                query.getEndTs(),
//                PageRequest.of(0, query.getLimit(),
//                        Sort.by(Sort.Direction.fromString(
//                                query.getOrder()), "ts")));
//        tsKvEntities.forEach(tsKvEntity -> tsKvEntity.setStrKey(query.getKey()));
//        return Futures.immediateFuture(DaoUtil.convertDataList(tsKvEntities));
//    }
//
//    private ListenableFuture<Optional<TsKv>> findAndAggregateAsync(EntityId entityId, String key, long startTs, long endTs, long ts, Aggregation aggregation) {
//        List<CompletableFuture<TsKv>> entitiesFutures = new ArrayList<>();
//        switchAggregation(entityId, key, startTs, endTs, aggregation, entitiesFutures);
//        return Futures.transform(setFutures(entitiesFutures), entity -> {
//            if (entity != null && entity.isNotEmpty()) {
//                entity.setEntityId(entityId.getId());
//                entity.setStrKey(key);
//                entity.setTs(ts);
//                return Optional.of(DaoUtil.getData(entity));
//            } else {
//                return Optional.empty();
//            }
//        }, MoreExecutors.directExecutor());
//    }

//    protected void switchAggregation(EntityId entityId, String key, long startTs, long endTs, Aggregation aggregation, List<CompletableFuture<TsKvEntity>> entitiesFutures) {
//        switch (aggregation) {
//            case AVG:
//                findAvg(entityId, key, startTs, endTs, entitiesFutures);
//                break;
//            case MAX:
//                findMax(entityId, key, startTs, endTs, entitiesFutures);
//                break;
//            case MIN:
//                findMin(entityId, key, startTs, endTs, entitiesFutures);
//                break;
//            case SUM:
//                findSum(entityId, key, startTs, endTs, entitiesFutures);
//                break;
//            case COUNT:
//                findCount(entityId, key, startTs, endTs, entitiesFutures);
//                break;
//            default:
//                throw new IllegalArgumentException("Not supported aggregation type: " + aggregation);
//        }
//    }
//
//    protected void findCount(EntityId entityId, String key, long startTs, long endTs, List<CompletableFuture<TsKvEntity>> entitiesFutures) {
//        Integer keyId = getOrSaveKeyId(key);
//        entitiesFutures.add(tsKvRepository.findCount(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//    }
//
//    protected void findSum(EntityId entityId, String key, long startTs, long endTs, List<CompletableFuture<TsKvEntity>> entitiesFutures) {
//        Integer keyId = getOrSaveKeyId(key);
//        entitiesFutures.add(tsKvRepository.findSum(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//    }
//
//    protected void findMin(EntityId entityId, String key, long startTs, long endTs, List<CompletableFuture<TsKvEntity>> entitiesFutures) {
//        Integer keyId = getOrSaveKeyId(key);
//        entitiesFutures.add(tsKvRepository.findStringMin(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//        entitiesFutures.add(tsKvRepository.findNumericMin(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//    }
//
//    protected void findMax(EntityId entityId, String key, long startTs, long endTs, List<CompletableFuture<TsKvEntity>> entitiesFutures) {
//        Integer keyId = getOrSaveKeyId(key);
//        entitiesFutures.add(tsKvRepository.findStringMax(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//        entitiesFutures.add(tsKvRepository.findNumericMax(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//    }
//
//    protected void findAvg(EntityId entityId, String key, long startTs, long endTs, List<CompletableFuture<TsKvEntity>> entitiesFutures) {
//        Integer keyId = getOrSaveKeyId(key);
//        entitiesFutures.add(tsKvRepository.findAvg(
//                entityId.getId(),
//                keyId,
//                startTs,
//                endTs));
//    }

    protected SettableFuture<TsKv> setFutures(List<CompletableFuture<TsKv>> entitiesFutures) {
        SettableFuture<TsKv> listenableFuture = SettableFuture.create();
        CompletableFuture<List<TsKv>> entities =
                CompletableFuture.allOf(entitiesFutures.toArray(new CompletableFuture[entitiesFutures.size()]))
                        .thenApply(v -> entitiesFutures.stream()
                                .map(CompletableFuture::join)
                                .collect(Collectors.toList()));

        entities.whenComplete((tsKvEntities, throwable) -> {
            if (throwable != null) {
                listenableFuture.setException(throwable);
            } else {
                TsKv result = null;
                for (TsKv entity : tsKvEntities) {

                        result = entity;
                        break;

                }
                listenableFuture.set(result);
            }
        });
        return listenableFuture;
    }
}
