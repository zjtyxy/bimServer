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
package com.ciat.bim.server.dao.attributes;

import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.server.common.stats.StatsFactory;
import com.ciat.bim.server.dao.DaoUtil;
import com.ciat.bim.server.dao.sql.JpaAbstractDaoListeningExecutorService;
import com.ciat.bim.server.dao.sql.ScheduledLogExecutorComponent;
import com.ciat.bim.server.dao.sql.TbSqlBlockingQueueParams;
import com.ciat.bim.server.dao.sql.TbSqlBlockingQueueWrapper;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.service.IAttributeKvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JpaAttributeDao extends JpaAbstractDaoListeningExecutorService implements AttributesDao {

    @Autowired
    ScheduledLogExecutorComponent logExecutor;

    @Autowired
    private IAttributeKvService attributeKvRepository;



    @Autowired
    private StatsFactory statsFactory;

    @Value("${sql.attributes.batch_size:1000}")
    private int batchSize;

    @Value("${sql.attributes.batch_max_delay:100}")
    private long maxDelay;

    @Value("${sql.attributes.stats_print_interval_ms:1000}")
    private long statsPrintIntervalMs;

    @Value("${sql.attributes.batch_threads:4}")
    private int batchThreads;

    @Value("${sql.batch_sort:false}")
    private boolean batchSortEnabled;

    private TbSqlBlockingQueueWrapper<AttributeKv> queue;

    @PostConstruct
    private void init() {
        TbSqlBlockingQueueParams params = TbSqlBlockingQueueParams.builder()
                .logName("Attributes")
                .batchSize(batchSize)
                .maxDelay(maxDelay)
                .statsPrintIntervalMs(statsPrintIntervalMs)
                .statsNamePrefix("attributes")
                .batchSortEnabled(batchSortEnabled)
                .build();

        Function<AttributeKv, Integer> hashcodeFunction = entity -> entity.hashCode();
        queue = new TbSqlBlockingQueueWrapper<>(params, hashcodeFunction, batchThreads, statsFactory);
        queue.init(logExecutor, v -> attributeKvRepository.saveOrUpdateBatch(v),
                Comparator.comparing((AttributeKv attributeKvEntity) -> attributeKvEntity.getId())
                        .thenComparing(attributeKvEntity -> attributeKvEntity.getEntityType())
                        .thenComparing(attributeKvEntity -> attributeKvEntity.getAttributeType())
                        .thenComparing(attributeKvEntity -> attributeKvEntity.getAttributeKey())
        );
    }

    @PreDestroy
    private void destroy() {
        if (queue != null) {
            queue.destroy();
        }
    }

    @Override
    public ListenableFuture<Optional<AttributeKv>> find(TenantId tenantId, EntityId entityId, String attributeType, String attributeKey) {
//        AttributeKvCompositeKey compositeKey =
//                getAttributeKvCompositeKey(entityId, attributeType, attributeKey);
        return Futures.immediateFuture(
                Optional.ofNullable(attributeKvRepository.getById(attributeKey)));
    }

    @Override
    public ListenableFuture<List<AttributeKv>> find(TenantId tenantId, EntityId entityId, String attributeType, Collection<String> attributeKeys) {
//        List<AttributeKvCompositeKey> compositeKeys =
//                attributeKeys
//                        .stream()
//                        .map(attributeKey ->
//                                getAttributeKvCompositeKey(entityId, attributeType, attributeKey))
//                        .collect(Collectors.toList());
        return Futures.immediateFuture(Lists.newArrayList(attributeKvRepository.listByIds(attributeKeys)));
    }

    @Override
    public ListenableFuture<List<AttributeKv>> findAll(TenantId tenantId, EntityId entityId, String attributeType) {
        return Futures.immediateFuture(
                Lists.newArrayList(
                        attributeKvRepository.findAllByEntityTypeAndEntityIdAndAttributeType(
                                entityId.getEntityType(),
                                entityId.getId(),
                                attributeType)));
    }

    @Override
    public List<String> findAllKeysByDeviceProfileId(TenantId tenantId, DeviceProfileId deviceProfileId) {
        if (deviceProfileId != null) {
            return attributeKvRepository.findAllKeysByDeviceProfileId(tenantId.getId(), deviceProfileId.getId());
        } else {
            return attributeKvRepository.findAllKeysByTenantId(tenantId.getId());
        }
    }

    @Override
    public List<String> findAllKeysByEntityIds(TenantId tenantId, EntityType entityType, List<EntityId> entityIds) {
        return attributeKvRepository
                .findAllKeysByEntityIds(entityType.name(), entityIds.stream().map(EntityId::getId).collect(Collectors.toList()));
    }

    @Override
    public ListenableFuture<Void> save(TenantId tenantId, EntityId entityId, String attributeType, AttributeKv attribute) {
//        AttributeKv entity = new AttributeKv();
//        entity.setId(attribute.getId());
//       // entity.setId(new AttributeKvCompositeKey(entityId.getEntityType(), entityId.getId(), attributeType, attribute.getKey()));
//        entity
//        entity.setLastupdatets(attribute.getLastupdatets());
//        entity.setStrValue(attribute.getStrValue());
//        entity.setDoubleValue(attribute.getDoubleValue());
//        entity.setLongValue(attribute.getLongValue());
//        entity.setBooleanValue(attribute.getBooleanValue());
//        entity.setJsonValue(attribute.getJsonValue());
        attribute.setEntityId(entityId.getId());
        return addToQueue(attribute);
    }

    private ListenableFuture<Void> addToQueue(AttributeKv entity) {
        return queue.add(entity);
    }

    @Override
    public ListenableFuture<List<Void>> removeAll(TenantId tenantId, EntityId entityId, String attributeType, List<String> keys) {
        return service.submit(() -> {
            keys.forEach(key ->
                    attributeKvRepository.removeById(key)
            );
            return null;
        });
    }

//    private AttributeKvCompositeKey getAttributeKvCompositeKey(EntityId entityId, String attributeType, String attributeKey) {
//        return new AttributeKvCompositeKey(
//                entityId.getEntityType(),
//                entityId.getId(),
//                attributeType,
//                attributeKey);
//    }
}
