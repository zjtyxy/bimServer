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
package com.ciat.bim.server.edge.rpc.sync;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultEdgeRequestsService implements EdgeRequestsService {
//
//    private static final ObjectMapper mapper = new ObjectMapper();
//
//    private static final int DEFAULT_PAGE_SIZE = 1000;
//
//    @Autowired
//    private EdgeEventService edgeEventService;
//
//    @Autowired
//    private AttributesService attributesService;
//
//    @Autowired
//    private RelationService relationService;
//
//    @Autowired
//    private DeviceService deviceService;
//
//    @Autowired
//    private EntityViewService entityViewService;
//
//    @Autowired
//    private DeviceProfileService deviceProfileService;
//
//    @Autowired
//    private WidgetsBundleService widgetsBundleService;
//
//    @Autowired
//    private WidgetTypeService widgetTypeService;
//
//    @Autowired
//    private DbCallbackExecutorService dbCallbackExecutorService;
//
//    @Autowired
//    private TbClusterService tbClusterService;
//
//    @Override
//    public ListenableFuture<Void> processRuleChainMetadataRequestMsg(TenantId tenantId, Edge edge, RuleChainMetadataRequestMsg ruleChainMetadataRequestMsg) {
//        log.trace("[{}] processRuleChainMetadataRequestMsg [{}][{}]", tenantId, edge.getName(), ruleChainMetadataRequestMsg);
//        if (ruleChainMetadataRequestMsg.getRuleChainIdMSB() != 0 && ruleChainMetadataRequestMsg.getRuleChainIdLSB() != 0) {
//            RuleChainId ruleChainId =
//                    new RuleChainId(new UUID(ruleChainMetadataRequestMsg.getRuleChainIdMSB(), ruleChainMetadataRequestMsg.getRuleChainIdLSB()));
//            saveEdgeEvent(tenantId, edge.getId(),
//                    EdgeEventType.RULE_CHAIN_METADATA, EdgeEventActionType.ADDED, ruleChainId, null);
//        }
//        return Futures.immediateFuture(null);
//    }
//
//    @Override
//    public ListenableFuture<Void> processAttributesRequestMsg(TenantId tenantId, Edge edge, AttributesRequestMsg attributesRequestMsg) {
//        log.trace("[{}] processAttributesRequestMsg [{}][{}]", tenantId, edge.getName(), attributesRequestMsg);
//        EntityId entityId = EntityIdFactory.getByTypeAndUuid(
//                EntityType.valueOf(attributesRequestMsg.getEntityType()),
//                new UUID(attributesRequestMsg.getEntityIdMSB(), attributesRequestMsg.getEntityIdLSB()));
//        final EdgeEventType type = EdgeUtils.getEdgeEventTypeByEntityType(entityId.getEntityType());
//        if (type != null) {
//            SettableFuture<Void> futureToSet = SettableFuture.create();
//            String scope = attributesRequestMsg.getScope();
//            ListenableFuture<List<AttributeKvEntry>> findAttrFuture = attributesService.findAll(tenantId, entityId, scope);
//            Futures.addCallback(findAttrFuture, new FutureCallback<List<AttributeKvEntry>>() {
//                @Override
//                public void onSuccess(@Nullable List<AttributeKvEntry> ssAttributes) {
//                    if (ssAttributes != null && !ssAttributes.isEmpty()) {
//                        try {
//                            Map<String, Object> entityData = new HashMap<>();
//                            ObjectNode attributes = mapper.createObjectNode();
//                            for (AttributeKvEntry attr : ssAttributes) {
//                                if (attr.getDataType() == DataType.BOOLEAN && attr.getBooleanValue().isPresent()) {
//                                    attributes.put(attr.getKey(), attr.getBooleanValue().get());
//                                } else if (attr.getDataType() == DataType.DOUBLE && attr.getDoubleValue().isPresent()) {
//                                    attributes.put(attr.getKey(), attr.getDoubleValue().get());
//                                } else if (attr.getDataType() == DataType.LONG && attr.getLongValue().isPresent()) {
//                                    attributes.put(attr.getKey(), attr.getLongValue().get());
//                                } else {
//                                    attributes.put(attr.getKey(), attr.getValueAsString());
//                                }
//                            }
//                            entityData.put("kv", attributes);
//                            entityData.put("scope", scope);
//                            JsonNode body = mapper.valueToTree(entityData);
//                            log.debug("Sending attributes data msg, entityId [{}], attributes [{}]", entityId, body);
//                            saveEdgeEvent(tenantId,
//                                    edge.getId(),
//                                    type,
//                                    EdgeEventActionType.ATTRIBUTES_UPDATED,
//                                    entityId,
//                                    body);
//                        } catch (Exception e) {
//                            log.error("[{}] Failed to save attribute updates to the edge", edge.getName(), e);
//                            futureToSet.setException(new RuntimeException("[" + edge.getName() + "] Failed to send attribute updates to the edge", e));
//                            return;
//                        }
//                    } else {
//                        log.trace("[{}][{}] No attributes found for entity {} [{}]", tenantId,
//                                edge.getName(),
//                                entityId.getEntityType(),
//                                entityId.getId());
//                    }
//                    futureToSet.set(null);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error("Can't find attributes [{}]", attributesRequestMsg, t);
//                    futureToSet.setException(t);
//                }
//            }, dbCallbackExecutorService);
//            return futureToSet;
//        } else {
//            log.warn("[{}] Type doesn't supported {}", tenantId, entityId.getEntityType());
//            return Futures.immediateFuture(null);
//        }
//    }
//
//    @Override
//    public ListenableFuture<Void> processRelationRequestMsg(TenantId tenantId, Edge edge, RelationRequestMsg relationRequestMsg) {
//        log.trace("[{}] processRelationRequestMsg [{}][{}]", tenantId, edge.getName(), relationRequestMsg);
//        EntityId entityId = EntityIdFactory.getByTypeAndUuid(
//                EntityType.valueOf(relationRequestMsg.getEntityType()),
//                new UUID(relationRequestMsg.getEntityIdMSB(), relationRequestMsg.getEntityIdLSB()));
//
//        List<ListenableFuture<List<EntityRelation>>> futures = new ArrayList<>();
//        futures.add(findRelationByQuery(tenantId, edge, entityId, EntitySearchDirection.FROM));
//        futures.add(findRelationByQuery(tenantId, edge, entityId, EntitySearchDirection.TO));
//        ListenableFuture<List<List<EntityRelation>>> relationsListFuture = Futures.allAsList(futures);
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        Futures.addCallback(relationsListFuture, new FutureCallback<List<List<EntityRelation>>>() {
//            @Override
//            public void onSuccess(@Nullable List<List<EntityRelation>> relationsList) {
//                try {
//                    if (relationsList != null && !relationsList.isEmpty()) {
//                        for (List<EntityRelation> entityRelations : relationsList) {
//                            log.trace("[{}] [{}] [{}] relation(s) are going to be pushed to edge.", edge.getId(), entityId, entityRelations.size());
//                            for (EntityRelation relation : entityRelations) {
//                                try {
//                                    if (!relation.getFrom().getEntityType().equals(EntityType.EDGE) &&
//                                            !relation.getTo().getEntityType().equals(EntityType.EDGE)) {
//                                        saveEdgeEvent(tenantId,
//                                                edge.getId(),
//                                                EdgeEventType.RELATION,
//                                                EdgeEventActionType.ADDED,
//                                                null,
//                                                mapper.valueToTree(relation));
//                                    }
//                                } catch (Exception e) {
//                                    log.error("Exception during loading relation [{}] to edge on sync!", relation, e);
//                                    futureToSet.setException(e);
//                                    return;
//                                }
//                            }
//                        }
//                    }
//                    futureToSet.set(null);
//                } catch (Exception e) {
//                    log.error("Exception during loading relation(s) to edge on sync!", e);
//                    futureToSet.setException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("[{}] Can't find relation by query. Entity id [{}]", tenantId, entityId, t);
//                futureToSet.setException(t);
//            }
//        }, dbCallbackExecutorService);
//        return futureToSet;
//    }
//
//    private ListenableFuture<List<EntityRelation>> findRelationByQuery(TenantId tenantId, Edge edge,
//                                                                       EntityId entityId, EntitySearchDirection direction) {
//        EntityRelationsQuery query = new EntityRelationsQuery();
//        query.setParameters(new RelationsSearchParameters(entityId, direction, -1, false));
//        return relationService.findByQuery(tenantId, query);
//    }
//
//    @Override
//    public ListenableFuture<Void> processDeviceCredentialsRequestMsg(TenantId tenantId, Edge edge, DeviceCredentialsRequestMsg deviceCredentialsRequestMsg) {
//        log.trace("[{}] processDeviceCredentialsRequestMsg [{}][{}]", tenantId, edge.getName(), deviceCredentialsRequestMsg);
//        if (deviceCredentialsRequestMsg.getDeviceIdMSB() != 0 && deviceCredentialsRequestMsg.getDeviceIdLSB() != 0) {
//            DeviceId deviceId = new DeviceId(new UUID(deviceCredentialsRequestMsg.getDeviceIdMSB(), deviceCredentialsRequestMsg.getDeviceIdLSB()));
//            saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.DEVICE,
//                    EdgeEventActionType.CREDENTIALS_UPDATED, deviceId, null);
//        }
//        return Futures.immediateFuture(null);
//    }
//
//    @Override
//    public ListenableFuture<Void> processUserCredentialsRequestMsg(TenantId tenantId, Edge edge, UserCredentialsRequestMsg userCredentialsRequestMsg) {
//        log.trace("[{}] processUserCredentialsRequestMsg [{}][{}]", tenantId, edge.getName(), userCredentialsRequestMsg);
//        if (userCredentialsRequestMsg.getUserIdMSB() != 0 && userCredentialsRequestMsg.getUserIdLSB() != 0) {
//            UserId userId = new UserId(new UUID(userCredentialsRequestMsg.getUserIdMSB(), userCredentialsRequestMsg.getUserIdLSB()));
//            saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.USER,
//                    EdgeEventActionType.CREDENTIALS_UPDATED, userId, null);
//        }
//        return Futures.immediateFuture(null);
//    }
//
//    @Override
//    public ListenableFuture<Void> processDeviceProfileDevicesRequestMsg(TenantId tenantId, Edge edge, DeviceProfileDevicesRequestMsg deviceProfileDevicesRequestMsg) {
//        log.trace("[{}] processDeviceProfileDevicesRequestMsg [{}][{}]", tenantId, edge.getName(), deviceProfileDevicesRequestMsg);
//        if (deviceProfileDevicesRequestMsg.getDeviceProfileIdMSB() != 0 && deviceProfileDevicesRequestMsg.getDeviceProfileIdLSB() != 0) {
//            DeviceProfileId deviceProfileId = new DeviceProfileId(new UUID(deviceProfileDevicesRequestMsg.getDeviceProfileIdMSB(), deviceProfileDevicesRequestMsg.getDeviceProfileIdLSB()));
//            DeviceProfile deviceProfileById = deviceProfileService.findDeviceProfileById(tenantId, deviceProfileId);
//            if (deviceProfileById != null) {
//                syncDevices(tenantId, edge, deviceProfileById.getName());
//            }
//        }
//        return Futures.immediateFuture(null);
//    }
//
//    private void syncDevices(TenantId tenantId, Edge edge, String deviceType) {
//        log.trace("[{}] syncDevices [{}][{}]", tenantId, edge.getName(), deviceType);
//        try {
//            PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//            PageData<Device> pageData;
//            do {
//                pageData = deviceService.findDevicesByTenantIdAndEdgeIdAndType(tenantId, edge.getId(), deviceType, pageLink);
//                if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                    log.trace("[{}] [{}] device(s) are going to be pushed to edge.", edge.getId(), pageData.getData().size());
//                    for (Device device : pageData.getData()) {
//                        saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.DEVICE, EdgeEventActionType.ADDED, device.getId(), null);
//                    }
//                    if (pageData.hasNext()) {
//                        pageLink = pageLink.nextPageLink();
//                    }
//                }
//            } while (pageData != null && pageData.hasNext());
//        } catch (Exception e) {
//            log.error("Exception during loading edge device(s) on sync!", e);
//        }
//    }
//
//    @Override
//    public ListenableFuture<Void> processWidgetBundleTypesRequestMsg(TenantId tenantId, Edge edge,
//                                                                     WidgetBundleTypesRequestMsg widgetBundleTypesRequestMsg) {
//        log.trace("[{}] processWidgetBundleTypesRequestMsg [{}][{}]", tenantId, edge.getName(), widgetBundleTypesRequestMsg);
//        if (widgetBundleTypesRequestMsg.getWidgetBundleIdMSB() != 0 && widgetBundleTypesRequestMsg.getWidgetBundleIdLSB() != 0) {
//            WidgetsBundleId widgetsBundleId = new WidgetsBundleId(new UUID(widgetBundleTypesRequestMsg.getWidgetBundleIdMSB(), widgetBundleTypesRequestMsg.getWidgetBundleIdLSB()));
//            WidgetsBundle widgetsBundleById = widgetsBundleService.findWidgetsBundleById(tenantId, widgetsBundleId);
//            if (widgetsBundleById != null) {
//                List<WidgetType> widgetTypesToPush =
//                        widgetTypeService.findWidgetTypesByTenantIdAndBundleAlias(widgetsBundleById.getTenantId(), widgetsBundleById.getAlias());
//
//                for (WidgetType widgetType : widgetTypesToPush) {
//                    saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.WIDGET_TYPE, EdgeEventActionType.ADDED, widgetType.getId(), null);
//                }
//            }
//        }
//        return Futures.immediateFuture(null);
//    }
//
//    @Override
//    public ListenableFuture<Void> processEntityViewsRequestMsg(TenantId tenantId, Edge edge, EntityViewsRequestMsg entityViewsRequestMsg) {
//        log.trace("[{}] processEntityViewsRequestMsg [{}][{}]", tenantId, edge.getName(), entityViewsRequestMsg);
//        EntityId entityId = EntityIdFactory.getByTypeAndUuid(
//                EntityType.valueOf(entityViewsRequestMsg.getEntityType()),
//                new UUID(entityViewsRequestMsg.getEntityIdMSB(), entityViewsRequestMsg.getEntityIdLSB()));
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        Futures.addCallback(entityViewService.findEntityViewsByTenantIdAndEntityIdAsync(tenantId, entityId), new FutureCallback<>() {
//            @Override
//            public void onSuccess(@Nullable List<EntityView> entityViews) {
//                try {
//                    if (entityViews != null && !entityViews.isEmpty()) {
//                        List<ListenableFuture<Boolean>> futures = new ArrayList<>();
//                        for (EntityView entityView : entityViews) {
//                            ListenableFuture<Boolean> future = relationService.checkRelation(tenantId, edge.getId(), entityView.getId(),
//                                    EntityRelation.CONTAINS_TYPE, RelationTypeGroup.EDGE);
//                            futures.add(future);
//                            Futures.addCallback(future, new FutureCallback<>() {
//                                @Override
//                                public void onSuccess(@Nullable Boolean result) {
//                                    if (Boolean.TRUE.equals(result)) {
//                                        saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.ENTITY_VIEW,
//                                                EdgeEventActionType.ADDED, entityView.getId(), null);
//                                    }
//                                }
//                                @Override
//                                public void onFailure(Throwable t) {
//                                    // Do nothing - error handles in allAsList
//                                }
//                            }, dbCallbackExecutorService);
//                        }
//                        Futures.addCallback(Futures.allAsList(futures), new FutureCallback<>() {
//                            @Override
//                            public void onSuccess(@Nullable List<Boolean> result) {
//                                futureToSet.set(null);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t) {
//                                log.error("Exception during loading relation [{}] to edge on sync!", t, t);
//                                futureToSet.setException(t);
//                            }
//                        }, dbCallbackExecutorService);
//                    } else {
//                        futureToSet.set(null);
//                    }
//                } catch (Exception e) {
//                    log.error("Exception during loading relation(s) to edge on sync!", e);
//                    futureToSet.setException(e);
//                }
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("[{}] Can't find entity views by entity id [{}]", tenantId, entityId, t);
//                futureToSet.setException(t);
//            }
//        }, dbCallbackExecutorService);
//        return futureToSet;
//    }
//
//    private void saveEdgeEvent(TenantId tenantId,
//                               EdgeId edgeId,
//                               EdgeEventType type,
//                               EdgeEventActionType action,
//                               EntityId entityId,
//                               JsonNode body) {
//        log.trace("Pushing edge event to edge queue. tenantId [{}], edgeId [{}], type [{}], action[{}], entityId [{}], body [{}]",
//                tenantId, edgeId, type, action, entityId, body);
//
//        EdgeEvent edgeEvent = EdgeEventUtils.constructEdgeEvent(tenantId, edgeId, type, action, entityId, body);
//
//        edgeEventService.save(edgeEvent);
//        tbClusterService.onEdgeEventUpdate(tenantId, edgeId);
//    }

}
