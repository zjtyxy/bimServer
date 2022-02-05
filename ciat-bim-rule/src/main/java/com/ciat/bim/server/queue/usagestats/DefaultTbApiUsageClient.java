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
package com.ciat.bim.server.queue.usagestats;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.common.data.ApiUsageRecordKey;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.PartitionService;
import com.ciat.bim.server.queue.provider.TbQueueProducerProvider;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.queue.scheduler.SchedulerComponent;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.EnumMap;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Slf4j
public class DefaultTbApiUsageClient implements TbApiUsageClient {

    @Value("${usage.stats.report.enabled:true}")
    private boolean enabled;
    @Value("${usage.stats.report.enabled_per_customer:false}")
    private boolean enabledPerCustomer;
    @Value("${usage.stats.report.interval:10}")
    private int interval;

    private final EnumMap<ApiUsageRecordKey, ConcurrentMap<OwnerId, AtomicLong>> stats = new EnumMap<>(ApiUsageRecordKey.class);

    private final PartitionService partitionService;
    private final SchedulerComponent scheduler;
    private final TbQueueProducerProvider producerProvider;
    private TbQueueProducer<TbProtoQueueMsg<ToUsageStatsServiceMsg>> msgProducer;

    public DefaultTbApiUsageClient(PartitionService partitionService, SchedulerComponent scheduler, TbQueueProducerProvider producerProvider) {
        this.partitionService = partitionService;
        this.scheduler = scheduler;
        this.producerProvider = producerProvider;
    }

    @PostConstruct
    private void init() {
        if (enabled) {
            msgProducer = this.producerProvider.getTbUsageStatsMsgProducer();
            for (ApiUsageRecordKey key : ApiUsageRecordKey.values()) {
                stats.put(key, new ConcurrentHashMap<>());
            }
            scheduler.scheduleWithFixedDelay(() -> {
                try {
                    reportStats();
                } catch (Exception e) {
                    log.warn("Failed to report statistics: ", e);
                }
            }, new Random().nextInt(interval), interval, TimeUnit.SECONDS);
        }
    }

    private void reportStats() {
        ConcurrentMap<OwnerId,ToUsageStatsServiceMsg.Builder> report = new ConcurrentHashMap<>();

        for (ApiUsageRecordKey key : ApiUsageRecordKey.values()) {
            ConcurrentMap<OwnerId, AtomicLong> statsForKey = stats.get(key);
            statsForKey.forEach((ownerId, statsValue) -> {
                long value = statsValue.get();
                if (value == 0) return;

                ToUsageStatsServiceMsg.Builder statsMsgBuilder = report.computeIfAbsent(ownerId, id -> {
                    ToUsageStatsServiceMsg.Builder newStatsMsgBuilder = ToUsageStatsServiceMsg.newBuilder();

                    String tenantId = ownerId.getTenantId();
                    newStatsMsgBuilder.setTenantIdMSB(Long.parseLong(tenantId));
                    newStatsMsgBuilder.setTenantIdLSB(Long.parseLong(tenantId));

                    EntityId entityId = ownerId.getEntityId();
                    if (entityId != null && entityId.getEntityType() == EntityType.CUSTOMER) {
                        newStatsMsgBuilder.setCustomerIdMSB(Long.parseLong(entityId.getId()));
                        newStatsMsgBuilder.setCustomerIdLSB(Long.parseLong(entityId.getId()));
                    }

                    return newStatsMsgBuilder;
                });

                statsMsgBuilder.addValues(UsageStatsKVProto.newBuilder().setKey(key.name()).setValue(value).build());
            });
            statsForKey.clear();
        }

        report.forEach(((ownerId, statsMsg) -> {
            //TODO: figure out how to minimize messages into the queue. Maybe group by 100s of messages?

            String tenantId = ownerId.getTenantId();
            EntityId entityId = Optional.ofNullable(ownerId.getEntityId()).orElse(TenantId.fromString(tenantId));

           // EntityId entityId = ownerId.getEntityId();
            TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, tenantId, entityId.getId()).newByTopic(msgProducer.getDefaultTopic());
            msgProducer.send(tpi, new TbProtoQueueMsg<>(UUID.randomUUID().toString(), statsMsg.build()), null);
        }));

        if (!report.isEmpty()) {
            log.info("Reporting API usage statistics for {} tenants and customers", report.size());
        }
    }

    @Override
    public void report(String tenantId, CustomerId customerId, ApiUsageRecordKey key, long value) {
        if (enabled) {
            ConcurrentMap<OwnerId, AtomicLong> statsForKey = stats.get(key);

            statsForKey.computeIfAbsent(new OwnerId(tenantId), id -> new AtomicLong()).addAndGet(value);
            statsForKey.computeIfAbsent(new OwnerId(TenantId.SYS_TENANT_ID), id -> new AtomicLong()).addAndGet(value);

            if (enabledPerCustomer && customerId != null && !customerId.isNullUid()) {
                statsForKey.computeIfAbsent(new OwnerId(tenantId, customerId), id -> new AtomicLong()).addAndGet(value);
            }
        }
    }

    @Override
    public void report(String tenantId, CustomerId customerId, ApiUsageRecordKey key) {
        report(tenantId, customerId, key, 1);
    }

    @Data
    private static class OwnerId {
        private String tenantId;
        private EntityId entityId;

        public OwnerId(String tenantId) {
            this.tenantId = tenantId;
        }

        public OwnerId(String tenantId, EntityId entityId) {
            this.tenantId = tenantId;
            this.entityId = entityId;
        }
    }
}
