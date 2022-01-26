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
package com.ciat.bim.server.edge.rpc.processor;

import com.ciat.bim.server.queue.util.TbCoreComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@TbCoreComponent
public class RelationEdgeProcessor extends BaseEdgeProcessor {

//    public ListenableFuture<Void> processRelationFromEdge(TenantId tenantId, RelationUpdateMsg relationUpdateMsg) {
//        log.trace("[{}] onRelationUpdate [{}]", tenantId, relationUpdateMsg);
//        try {
//            EntityRelation entityRelation = new EntityRelation();
//
//            UUID fromUUID = new UUID(relationUpdateMsg.getFromIdMSB(), relationUpdateMsg.getFromIdLSB());
//            EntityId fromId = EntityIdFactory.getByTypeAndUuid(EntityType.valueOf(relationUpdateMsg.getFromEntityType()), fromUUID);
//            entityRelation.setFrom(fromId);
//
//            UUID toUUID = new UUID(relationUpdateMsg.getToIdMSB(), relationUpdateMsg.getToIdLSB());
//            EntityId toId = EntityIdFactory.getByTypeAndUuid(EntityType.valueOf(relationUpdateMsg.getToEntityType()), toUUID);
//            entityRelation.setTo(toId);
//
//            entityRelation.setType(relationUpdateMsg.getType());
//            if (relationUpdateMsg.hasTypeGroup()) {
//                entityRelation.setTypeGroup(RelationTypeGroup.valueOf(relationUpdateMsg.getTypeGroup()));
//            }
//            entityRelation.setAdditionalInfo(mapper.readTree(relationUpdateMsg.getAdditionalInfo()));
//            switch (relationUpdateMsg.getMsgType()) {
//                case ENTITY_CREATED_RPC_MESSAGE:
//                case ENTITY_UPDATED_RPC_MESSAGE:
//                    if (isEntityExists(tenantId, entityRelation.getTo())
//                            && isEntityExists(tenantId, entityRelation.getFrom())) {
//                        relationService.saveRelationAsync(tenantId, entityRelation);
//                    }
//                    break;
//                case ENTITY_DELETED_RPC_MESSAGE:
//                    relationService.deleteRelation(tenantId, entityRelation);
//                    break;
//                case UNRECOGNIZED:
//                    log.error("Unsupported msg type");
//            }
//            return Futures.immediateFuture(null);
//        } catch (Exception e) {
//            log.error("Failed to process relation update msg [{}]", relationUpdateMsg, e);
//            return Futures.immediateFailedFuture(new RuntimeException("Failed to process relation update msg", e));
//        }
//    }
//
//
//    private boolean isEntityExists(TenantId tenantId, EntityId entityId) throws ThingsboardException {
//        switch (entityId.getEntityType()) {
//            case DEVICE:
//                return deviceService.findDeviceById(tenantId, new DeviceId(entityId.getId())) != null;
//            case ASSET:
//                return assetService.findAssetById(tenantId, new AssetId(entityId.getId())) != null;
//            case ENTITY_VIEW:
//                return entityViewService.findEntityViewById(tenantId, new EntityViewId(entityId.getId())) != null;
//            case CUSTOMER:
//                return customerService.findCustomerById(tenantId, new CustomerId(entityId.getId())) != null;
//            case USER:
//                return userService.findUserById(tenantId, new UserId(entityId.getId())) != null;
//            case DASHBOARD:
//                return dashboardService.findDashboardById(tenantId, new DashboardId(entityId.getId())) != null;
//            default:
//                throw new ThingsboardException("Unsupported entity type " + entityId.getEntityType(), ThingsboardErrorCode.INVALID_ARGUMENTS);
//        }
//    }
//
//    public DownlinkMsg processRelationToEdge(EdgeEvent edgeEvent, UpdateMsgType msgType) {
//        EntityRelation entityRelation = mapper.convertValue(edgeEvent.getBody(), EntityRelation.class);
//        RelationUpdateMsg relationUpdateMsg = relationMsgConstructor.constructRelationUpdatedMsg(msgType, entityRelation);
//        return DownlinkMsg.newBuilder()
//                .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                .addRelationUpdateMsg(relationUpdateMsg)
//                .build();
//    }
//
//    public void processRelationNotification(TenantId tenantId, TransportProtos.EdgeNotificationMsgProto edgeNotificationMsg) throws JsonProcessingException {
//        EntityRelation relation = mapper.readValue(edgeNotificationMsg.getBody(), EntityRelation.class);
//        if (!relation.getFrom().getEntityType().equals(EntityType.EDGE) &&
//                !relation.getTo().getEntityType().equals(EntityType.EDGE)) {
//            Set<EdgeId> uniqueEdgeIds = new HashSet<>();
//            uniqueEdgeIds.addAll(findRelatedEdgeIds(tenantId, relation.getTo()));
//            uniqueEdgeIds.addAll(findRelatedEdgeIds(tenantId, relation.getFrom()));
//            if (!uniqueEdgeIds.isEmpty()) {
//                for (EdgeId edgeId : uniqueEdgeIds) {
//                    saveEdgeEvent(tenantId,
//                            edgeId,
//                            EdgeEventType.RELATION,
//                            EdgeEventActionType.valueOf(edgeNotificationMsg.getAction()),
//                            null,
//                            mapper.valueToTree(relation));
//                }
//            }
//        }
//    }
//
//    private List<EdgeId> findRelatedEdgeIds(TenantId tenantId, EntityId entityId) {
//        List<EdgeId> result = new ArrayList<>();
//        PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//        PageData<EdgeId> pageData;
//        do {
//            pageData = edgeService.findRelatedEdgeIdsByEntityId(tenantId, entityId, pageLink);
//            if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                result.addAll(pageData.getData());
//                if (pageData.hasNext()) {
//                    pageLink = pageLink.nextPageLink();
//                }
//            }
//        } while (pageData != null && pageData.hasNext());
//        return result;
//    }
}
