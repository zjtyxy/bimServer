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
public class EntityEdgeProcessor extends BaseEdgeProcessor {
//
//    public DownlinkMsg processEntityMergeRequestMessageToEdge(Edge edge, EdgeEvent edgeEvent) {
//        DownlinkMsg downlinkMsg = null;
//        if (EdgeEventType.DEVICE.equals(edgeEvent.getType())) {
//            DeviceId deviceId = new DeviceId(edgeEvent.getEntityId());
//            Device device = deviceService.findDeviceById(edge.getTenantId(), deviceId);
//            CustomerId customerId = getCustomerIdIfEdgeAssignedToCustomer(device, edge);
//            String conflictName = null;
//            if(edgeEvent.getBody() != null) {
//                conflictName = edgeEvent.getBody().get("conflictName").asText();
//            }
//            DeviceUpdateMsg deviceUpdateMsg = deviceMsgConstructor
//                    .constructDeviceUpdatedMsg(UpdateMsgType.ENTITY_MERGE_RPC_MESSAGE, device, customerId, conflictName);
//            downlinkMsg = DownlinkMsg.newBuilder()
//                    .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                    .addDeviceUpdateMsg(deviceUpdateMsg)
//                    .build();
//        }
//        return downlinkMsg;
//    }
//
//    public DownlinkMsg processCredentialsRequestMessageToEdge(EdgeEvent edgeEvent) {
//        DownlinkMsg downlinkMsg = null;
//        if (EdgeEventType.DEVICE.equals(edgeEvent.getType())) {
//            DeviceId deviceId = new DeviceId(edgeEvent.getEntityId());
//            DeviceCredentialsRequestMsg deviceCredentialsRequestMsg = DeviceCredentialsRequestMsg.newBuilder()
//                    .setDeviceIdMSB(deviceId.getId().getMostSignificantBits())
//                    .setDeviceIdLSB(deviceId.getId().getLeastSignificantBits())
//                    .build();
//            DownlinkMsg.Builder builder = DownlinkMsg.newBuilder()
//                    .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                    .addDeviceCredentialsRequestMsg(deviceCredentialsRequestMsg);
//            downlinkMsg = builder.build();
//        }
//        return downlinkMsg;
//    }
//
//    public void processEntityNotification(TenantId tenantId, TransportProtos.EdgeNotificationMsgProto edgeNotificationMsg) {
//        EdgeEventActionType actionType = EdgeEventActionType.valueOf(edgeNotificationMsg.getAction());
//        EdgeEventType type = EdgeEventType.valueOf(edgeNotificationMsg.getType());
//        EntityId entityId = EntityIdFactory.getByEdgeEventTypeAndUuid(type,
//                new UUID(edgeNotificationMsg.getEntityIdMSB(), edgeNotificationMsg.getEntityIdLSB()));
//        EdgeId edgeId = new EdgeId(new UUID(edgeNotificationMsg.getEdgeIdMSB(), edgeNotificationMsg.getEdgeIdLSB()));
//        PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//        PageData<EdgeId> pageData;
//        switch (actionType) {
//            case ADDED: // used only for USER entity
//            case UPDATED:
//            case CREDENTIALS_UPDATED:
//                do {
//                    pageData = edgeService.findRelatedEdgeIdsByEntityId(tenantId, entityId, pageLink);
//                    if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                        for (EdgeId relatedEdgeId : pageData.getData()) {
//                            saveEdgeEvent(tenantId, relatedEdgeId, type, actionType, entityId, null);
//                        }
//                        if (pageData.hasNext()) {
//                            pageLink = pageLink.nextPageLink();
//                        }
//                    }
//                } while (pageData != null && pageData.hasNext());
//                break;
//            case ASSIGNED_TO_CUSTOMER:
//            case UNASSIGNED_FROM_CUSTOMER:
//                do {
//                    pageData = edgeService.findRelatedEdgeIdsByEntityId(tenantId, entityId, pageLink);
//                    if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                        for (EdgeId relatedEdgeId : pageData.getData()) {
//                            try {
//                                CustomerId customerId = mapper.readValue(edgeNotificationMsg.getBody(), CustomerId.class);
//                                ListenableFuture<Edge> future = edgeService.findEdgeByIdAsync(tenantId, relatedEdgeId);
//                                Futures.addCallback(future, new FutureCallback<>() {
//                                    @Override
//                                    public void onSuccess(@Nullable Edge edge) {
//                                        if (edge != null && edge.getCustomerId() != null &&
//                                                !edge.getCustomerId().isNullUid() && edge.getCustomerId().equals(customerId)) {
//                                            saveEdgeEvent(tenantId, relatedEdgeId, type, actionType, entityId, null);
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onFailure(Throwable t) {
//                                        log.error("Failed to find edge by id [{}] {}", edgeNotificationMsg, t);
//                                    }
//                                }, dbCallbackExecutorService);
//                            } catch (Exception e) {
//                                log.error("Can't parse customer id from entity body [{}]", edgeNotificationMsg, e);
//                            }
//                        }
//                        if (pageData.hasNext()) {
//                            pageLink = pageLink.nextPageLink();
//                        }
//                    }
//                } while (pageData != null && pageData.hasNext());
//                break;
//            case DELETED:
//                saveEdgeEvent(tenantId, edgeId, type, actionType, entityId, null);
//                break;
//            case ASSIGNED_TO_EDGE:
//            case UNASSIGNED_FROM_EDGE:
//                saveEdgeEvent(tenantId, edgeId, type, actionType, entityId, null);
//                if (type.equals(EdgeEventType.RULE_CHAIN)) {
//                    updateDependentRuleChains(tenantId, new RuleChainId(entityId.getId()), edgeId);
//                }
//                break;
//        }
//    }
//
//    private void updateDependentRuleChains(TenantId tenantId, RuleChainId processingRuleChainId, EdgeId edgeId) {
//        PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//        PageData<RuleChain> pageData;
//        do {
//            pageData = ruleChainService.findRuleChainsByTenantIdAndEdgeId(tenantId, edgeId, pageLink);
//            if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                for (RuleChain ruleChain : pageData.getData()) {
//                    if (!ruleChain.getId().equals(processingRuleChainId)) {
//                        List<RuleChainConnectionInfo> connectionInfos =
//                                ruleChainService.loadRuleChainMetaData(ruleChain.getTenantId(), ruleChain.getId()).getRuleChainConnections();
//                        if (connectionInfos != null && !connectionInfos.isEmpty()) {
//                            for (RuleChainConnectionInfo connectionInfo : connectionInfos) {
//                                if (connectionInfo.getTargetRuleChainId().equals(processingRuleChainId)) {
//                                    saveEdgeEvent(tenantId,
//                                            edgeId,
//                                            EdgeEventType.RULE_CHAIN_METADATA,
//                                            EdgeEventActionType.UPDATED,
//                                            ruleChain.getId(),
//                                            null);
//                                }
//                            }
//                        }
//                    }
//                }
//                if (pageData.hasNext()) {
//                    pageLink = pageLink.nextPageLink();
//                }
//            }
//        } while (pageData != null && pageData.hasNext());
//    }
//
//    public void processEntityNotificationForAllEdges(TenantId tenantId, TransportProtos.EdgeNotificationMsgProto edgeNotificationMsg) {
//        EdgeEventActionType actionType = EdgeEventActionType.valueOf(edgeNotificationMsg.getAction());
//        EdgeEventType type = EdgeEventType.valueOf(edgeNotificationMsg.getType());
//        EntityId entityId = EntityIdFactory.getByEdgeEventTypeAndUuid(type, new UUID(edgeNotificationMsg.getEntityIdMSB(), edgeNotificationMsg.getEntityIdLSB()));
//        switch (actionType) {
//            case ADDED:
//            case UPDATED:
//            case DELETED:
//                processActionForAllEdges(tenantId, type, actionType, entityId);
//                break;
//        }
//    }
}

