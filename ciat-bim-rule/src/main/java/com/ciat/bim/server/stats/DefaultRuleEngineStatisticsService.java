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
package com.ciat.bim.server.stats;

import com.ciat.bim.data.id.AssetId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.exception.DataValidationException;
import com.ciat.bim.server.queue.TbRuleEngineConsumerStats;
import com.ciat.bim.server.queue.discovery.TbServiceInfoProvider;
import com.ciat.bim.server.queue.util.TbRuleEngineComponent;
import com.ciat.bim.server.telemetry.TelemetrySubscriptionService;
import com.google.common.util.concurrent.FutureCallback;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.JsonDataEntry;
import org.jeecg.modules.device.entity.LongDataEntry;
import org.jeecg.modules.device.entity.TsKv;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@TbRuleEngineComponent
@Service
@Slf4j
public class DefaultRuleEngineStatisticsService implements RuleEngineStatisticsService {

    public static final String TB_SERVICE_QUEUE = "TbServiceQueue";
    public static final FutureCallback<Integer> CALLBACK = new FutureCallback<Integer>() {
        @Override
        public void onSuccess(@Nullable Integer result) {

        }

        @Override
        public void onFailure(Throwable t) {
            log.warn("Failed to persist statistics", t);
        }
    };

    private final TbServiceInfoProvider serviceInfoProvider;
    private final TelemetrySubscriptionService tsService;
    private final Lock lock = new ReentrantLock();
    //private final AssetService assetService;
    private final ConcurrentMap<TenantQueueKey, AssetId> tenantQueueAssets;

    public DefaultRuleEngineStatisticsService(TelemetrySubscriptionService tsService, TbServiceInfoProvider serviceInfoProvider
                                      //        ,AssetService assetService
    ) {
        this.tsService = tsService;
        this.serviceInfoProvider = serviceInfoProvider;
        //this.assetService = assetService;
        this.tenantQueueAssets = new ConcurrentHashMap<>();
    }

    @Override
    public void reportQueueStats(long ts, TbRuleEngineConsumerStats ruleEngineStats) {
        String queueName = ruleEngineStats.getQueueName();
        ruleEngineStats.getTenantStats().forEach((id, stats) -> {
            TenantId tenantId = new TenantId(id.toString());
            try {
                AssetId serviceAssetId = getServiceAssetId(tenantId, queueName);
                if (stats.getTotalMsgCounter().get() > 0) {
                    List<TsKv> tsList = stats.getCounters().entrySet().stream()
                            .map(kv -> new TsKv( new LongDataEntry(kv.getKey(), (long) kv.getValue().get()),ts))
                            .collect(Collectors.toList());
                    if (!tsList.isEmpty()) {
                        tsService.saveAndNotifyInternal(tenantId, serviceAssetId, tsList, CALLBACK);
                    }
                }
            } catch (DataValidationException e) {
                if (!e.getMessage().equalsIgnoreCase("Asset is referencing to non-existent tenant!")) {
                    throw e;
                }
            }
        });
        ruleEngineStats.getTenantExceptions().forEach((tenantId, e) -> {
            TsKv tsKv = new TsKv( new JsonDataEntry("ruleEngineException", e.toJsonString()),ts);
            try {
                tsService.saveAndNotifyInternal(tenantId, getServiceAssetId(tenantId, queueName), Collections.singletonList(tsKv), CALLBACK);
            } catch (DataValidationException e2) {
                if (!e2.getMessage().equalsIgnoreCase("Asset is referencing to non-existent tenant!")) {
                    throw e2;
                }
            }
        });
    }

    private AssetId getServiceAssetId(TenantId tenantId, String queueName) {
        TenantQueueKey key = new TenantQueueKey(tenantId, queueName);
        AssetId assetId = tenantQueueAssets.get(key);
        if (assetId == null) {
            lock.lock();
            try {
                assetId = tenantQueueAssets.get(key);
                if (assetId == null) {
//                    Asset asset = assetService.findAssetByTenantIdAndName(tenantId, queueName + "_" + serviceInfoProvider.getServiceId());
//                    if (asset == null) {
//                        asset = new Asset();
//                        asset.setTenantId(tenantId);
//                        asset.setName(queueName + "_" + serviceInfoProvider.getServiceId());
//                        asset.setType(TB_SERVICE_QUEUE);
//                        asset = assetService.saveAsset(asset);
//                    }
//                    assetId = asset.getId();
//                    tenantQueueAssets.put(key, assetId);
                }
            } finally {
                lock.unlock();
            }
        }
        return assetId;
    }

    @Data
    private static class TenantQueueKey {
        private final TenantId tenantId;
        private final String queueName;
    }
}
