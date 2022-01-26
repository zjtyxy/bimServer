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
package com.ciat.bim.server.importing;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.HasId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.data.id.UUIDBased;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.server.security.SecurityUser;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.ciat.bim.tenant.HasTenantId;
import com.ciat.bim.tenant.TbTenantProfileCache;
import com.ciat.bim.tenant.entity.DefaultTenantProfileConfiguration;
import com.google.common.util.concurrent.FutureCallback;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import lombok.Data;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractBulkImportService<E extends HasId<? extends EntityId> & HasTenantId> {
//    @Autowired
//    private TelemetrySubscriptionService tsSubscriptionService;
//    @Autowired
//    private TbTenantProfileCache tenantProfileCache;
//    @Autowired
//    private AccessControlService accessControlService;
//    @Autowired
//    private AccessValidator accessValidator;
//    @Autowired
//    private EntityActionService entityActionService;

    private static ThreadPoolExecutor executor;

    @PostConstruct
    private void initExecutor() {
        if (executor == null) {
            executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
                    60L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(150_000),
                    ThingsBoardThreadFactory.forName("bulk-import"), new ThreadPoolExecutor.CallerRunsPolicy());
            executor.allowCoreThreadTimeOut(true);
        }
    }

    public final BulkImportResult<E> processBulkImport(BulkImportRequest request, SecurityUser user, Consumer<ImportedEntityInfo<E>> onEntityImported) throws Exception {
     //   List<EntityData> entitiesData = parseData(request);

        BulkImportResult<E> result = new BulkImportResult<>();
     //   CountDownLatch completionLatch = new CountDownLatch(entitiesData.size());

//        SecurityContext securityContext = SecurityContextHolder.getContext();
//
//        entitiesData.forEach(entityData -> DonAsynchron.submit(() -> {
//                    SecurityContextHolder.setContext(securityContext);
//
//                    ImportedEntityInfo<E> importedEntityInfo =  saveEntity(entityData.getFields(), user);
//                    E entity = importedEntityInfo.getEntity();
//
//                    onEntityImported.accept(importedEntityInfo);
//                    saveKvs(user, entity, entityData.getKvs());
//
//                    return importedEntityInfo;
//                },
//                importedEntityInfo -> {
//                    if (importedEntityInfo.isUpdated()) {
//                        result.getUpdated().incrementAndGet();
//                    } else {
//                        result.getCreated().incrementAndGet();
//                    }
//                    completionLatch.countDown();
//                },
//                throwable -> {
//                    result.getErrors().incrementAndGet();
//                    result.getErrorsList().add(String.format("Line %d: %s", entityData.getLineNumber(), ExceptionUtils.getRootCauseMessage(throwable)));
//                    completionLatch.countDown();
//                },
//                executor));
//
//        completionLatch.await();
        return result;
    }

    @SneakyThrows
    private ImportedEntityInfo<E> saveEntity(Map<BulkImportColumnType, String> fields, SecurityUser user) {
        ImportedEntityInfo<E> importedEntityInfo = new ImportedEntityInfo<>();

//        E entity = findOrCreateEntity(user.getTenantId(), fields.get(BulkImportColumnType.NAME));
//        if (entity.getId() != null) {
//            importedEntityInfo.setOldEntity((E) entity.getClass().getConstructor(entity.getClass()).newInstance(entity));
//            importedEntityInfo.setUpdated(true);
//        } else {
//            setOwners(entity, user);
//        }
//
//        setEntityFields(entity, fields);
//        accessControlService.checkPermission(user, Resource.of(getEntityType()), Operation.WRITE, entity.getId(), entity);
//
//        E savedEntity = saveEntity(entity, fields);
//
//        importedEntityInfo.setEntity(savedEntity);
        return importedEntityInfo;
    }


//    protected abstract E findOrCreateEntity(TenantId tenantId, String name);
//
//    protected abstract void setOwners(E entity, SecurityUser user);
//
//    protected abstract void setEntityFields(E entity, Map<BulkImportColumnType, String> fields);
//
//    protected abstract E saveEntity(E entity, Map<BulkImportColumnType, String> fields);
//
//    protected abstract EntityType getEntityType();

//
//    private void saveKvs(SecurityUser user, E entity, Map<BulkImportRequest.ColumnMapping, ParsedValue> data) {
//        Arrays.stream(BulkImportColumnType.values())
//                .filter(BulkImportColumnType::isKv)
//                .map(kvType -> {
//                    JsonObject kvs = new JsonObject();
//                    data.entrySet().stream()
//                            .filter(dataEntry -> dataEntry.getKey().getType() == kvType &&
//                                    StringUtils.isNotEmpty(dataEntry.getKey().getKey()))
//                            .forEach(dataEntry -> kvs.add(dataEntry.getKey().getKey(), dataEntry.getValue().toJsonPrimitive()));
//                    return Map.entry(kvType, kvs);
//                })
//                .filter(kvsEntry -> kvsEntry.getValue().entrySet().size() > 0)
//                .forEach(kvsEntry -> {
//                    BulkImportColumnType kvType = kvsEntry.getKey();
//                    if (kvType == BulkImportColumnType.SHARED_ATTRIBUTE || kvType == BulkImportColumnType.SERVER_ATTRIBUTE) {
//                        saveAttributes(user, entity, kvsEntry, kvType);
//                    } else {
//                        saveTelemetry(user, entity, kvsEntry);
//                    }
//                });
//    }
//
//    @SneakyThrows
//    private void saveTelemetry(SecurityUser user, E entity, Map.Entry<BulkImportColumnType, JsonObject> kvsEntry) {
//        List<TsKvEntry> timeseries = JsonConverter.convertToTelemetry(kvsEntry.getValue(), System.currentTimeMillis())
//                .entrySet().stream()
//                .flatMap(entry -> entry.getValue().stream().map(kvEntry -> new BasicTsKvEntry(entry.getKey(), kvEntry)))
//                .collect(Collectors.toList());
//
//        accessValidator.validateEntityAndCallback(user, Operation.WRITE_TELEMETRY, entity.getId(), (result, tenantId, entityId) -> {
//            TenantProfile tenantProfile = tenantProfileCache.get(tenantId);
//            long tenantTtl = TimeUnit.DAYS.toSeconds(((DefaultTenantProfileConfiguration) tenantProfile.getProfileData().getConfiguration()).getDefaultStorageTtlDays());
//            tsSubscriptionService.saveAndNotify(tenantId, user.getCustomerId(), entityId, timeseries, tenantTtl, new FutureCallback<Void>() {
//                @Override
//                public void onSuccess(@Nullable Void tmp) {
//                    entityActionService.logEntityAction(user, (UUIDBased & EntityId) entityId, null, null,
//                            ActionType.TIMESERIES_UPDATED, null, timeseries);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    entityActionService.logEntityAction(user, (UUIDBased & EntityId) entityId, null, null,
//                            ActionType.TIMESERIES_UPDATED, BaseController.toException(t), timeseries);
//                    throw new RuntimeException(t);
//                }
//            });
//        });
//    }
//
//    @SneakyThrows
//    private void saveAttributes(SecurityUser user, E entity, Map.Entry<BulkImportColumnType, JsonObject> kvsEntry, BulkImportColumnType kvType) {
//        String scope = kvType.getKey();
//        List<AttributeKvEntry> attributes = new ArrayList<>(JsonConverter.convertToAttributes(kvsEntry.getValue()));
//
//        accessValidator.validateEntityAndCallback(user, Operation.WRITE_ATTRIBUTES, entity.getId(), (result, tenantId, entityId) -> {
//            tsSubscriptionService.saveAndNotify(tenantId, entityId, scope, attributes, new FutureCallback<>() {
//
//                @Override
//                public void onSuccess(Void unused) {
//                    entityActionService.logEntityAction(user, (UUIDBased & EntityId) entityId, null,
//                            null, ActionType.ATTRIBUTES_UPDATED, null, scope, attributes);
//                }
//
//                @Override
//                public void onFailure(Throwable throwable) {
//                    entityActionService.logEntityAction(user, (UUIDBased & EntityId) entityId, null,
//                            null, ActionType.ATTRIBUTES_UPDATED, BaseController.toException(throwable),
//                            scope, attributes);
//                    throw new RuntimeException(throwable);
//                }
//
//            });
//        });
//    }
//
//    private List<EntityData> parseData(BulkImportRequest request) throws Exception {
//        List<List<String>> records = CsvUtils.parseCsv(request.getFile(), request.getMapping().getDelimiter());
//        AtomicInteger linesCounter = new AtomicInteger(0);
//
//        if (request.getMapping().getHeader()) {
//            records.remove(0);
//            linesCounter.incrementAndGet();
//        }
//
//        List<ColumnMapping> columnsMappings = request.getMapping().getColumns();
//        return records.stream()
//                .map(record -> {
//                    EntityData entityData = new EntityData();
//                    Stream.iterate(0, i -> i < record.size(), i -> i + 1)
//                            .map(i -> Map.entry(columnsMappings.get(i), record.get(i)))
//                            .filter(entry -> StringUtils.isNotEmpty(entry.getValue()))
//                            .forEach(entry -> {
//                                if (!entry.getKey().getType().isKv()) {
//                                    entityData.getFields().put(entry.getKey().getType(), entry.getValue());
//                                } else {
//                                    Map.Entry<DataType, Object> castResult = TypeCastUtil.castValue(entry.getValue());
//                                    entityData.getKvs().put(entry.getKey(), new ParsedValue(castResult.getValue(), castResult.getKey()));
//                                }
//                            });
//                    entityData.setLineNumber(linesCounter.incrementAndGet());
//                    return entityData;
//                })
//                .collect(Collectors.toList());
//    }

    @PreDestroy
    private void shutdownExecutor() {
        if (!executor.isTerminating()) {
            executor.shutdown();
        }
    }

    @Data
    protected static class EntityData {
        private final Map<BulkImportColumnType, String> fields = new LinkedHashMap<>();
        private final Map<BulkImportRequest.ColumnMapping, ParsedValue> kvs = new LinkedHashMap<>();
        private int lineNumber;
    }

    @Data
    protected static class ParsedValue {
        private final Object value;
        private final DataType dataType;

        public JsonPrimitive toJsonPrimitive() {
            switch (dataType) {
                case STRING:
                    return new JsonPrimitive((String) value);
//                case LONG:
//                    return new JsonPrimitive((Long) value);
//                case DOUBLE:
//                    return new JsonPrimitive((Double) value);
//                case BOOLEAN:
//                    return new JsonPrimitive((Boolean) value);
                default:
                    return null;
            }
        }

        public String stringValue() {
            return value.toString();
        }

    }

}
