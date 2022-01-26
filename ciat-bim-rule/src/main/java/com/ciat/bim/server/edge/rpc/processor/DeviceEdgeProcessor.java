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
public class DeviceEdgeProcessor extends BaseEdgeProcessor {
//
//    private static final ReentrantLock deviceCreationLock = new ReentrantLock();
//
//    public ListenableFuture<Void> processDeviceFromEdge(TenantId tenantId, Edge edge, DeviceUpdateMsg deviceUpdateMsg) {
//        log.trace("[{}] onDeviceUpdate [{}] from edge [{}]", tenantId, deviceUpdateMsg, edge.getName());
//        switch (deviceUpdateMsg.getMsgType()) {
//            case ENTITY_CREATED_RPC_MESSAGE:
//                String deviceName = deviceUpdateMsg.getName();
//                Device device = deviceService.findDeviceByTenantIdAndName(tenantId, deviceName);
//                if (device != null) {
//                    PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//                    PageData<EdgeId> pageData;
//                    do {
//                        pageData = edgeService.findRelatedEdgeIdsByEntityId(tenantId, device.getId(), pageLink);
//                        boolean update = false;
//                        if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                            if (pageData.getData().contains(edge.getId())) {
//                                update = true;
//                            }
//                            if (pageData.hasNext()) {
//                                pageLink = pageLink.nextPageLink();
//                            }
//                        }
//
//                        if (update) {
//                            log.info("[{}] Device with name '{}' already exists on the cloud, and related to this edge [{}]. " +
//                                    "deviceUpdateMsg [{}], Updating device", tenantId, deviceName, edge.getId(), deviceUpdateMsg);
//                            updateDevice(tenantId, edge, deviceUpdateMsg);
//                        } else {
//                            log.info("[{}] Device with name '{}' already exists on the cloud, but not related to this edge [{}]. deviceUpdateMsg [{}]." +
//                                    "Creating a new device with random prefix and relate to this edge", tenantId, deviceName, edge.getId(), deviceUpdateMsg);
//                            String newDeviceName = deviceUpdateMsg.getName() + "_" + RandomStringUtils.randomAlphabetic(15);
//                            Device newDevice = createDevice(tenantId, edge, deviceUpdateMsg, newDeviceName);
//                            ObjectNode body = mapper.createObjectNode();
//                            body.put("conflictName", deviceName);
//                            saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.DEVICE, EdgeEventActionType.ENTITY_MERGE_REQUEST, newDevice.getId(), body);
//                            saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.DEVICE, EdgeEventActionType.CREDENTIALS_REQUEST, newDevice.getId(), null);
//                        }
//                    } while (pageData != null && pageData.hasNext());
//                } else {
//                    log.info("[{}] Creating new device and replacing device entity on the edge [{}]", tenantId, deviceUpdateMsg);
//                    device = createDevice(tenantId, edge, deviceUpdateMsg, deviceUpdateMsg.getName());
//                    saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.DEVICE, EdgeEventActionType.CREDENTIALS_REQUEST, device.getId(), null);
//                }
//                break;
//            case ENTITY_UPDATED_RPC_MESSAGE:
//                updateDevice(tenantId, edge, deviceUpdateMsg);
//                break;
//            case ENTITY_DELETED_RPC_MESSAGE:
//                DeviceId deviceId = new DeviceId(new UUID(deviceUpdateMsg.getIdMSB(), deviceUpdateMsg.getIdLSB()));
//                Device deviceToDelete = deviceService.findDeviceById(tenantId, deviceId);
//                if (deviceToDelete != null) {
//                    deviceService.unassignDeviceFromEdge(tenantId, deviceId, edge.getId());
//                }
//                break;
//            case UNRECOGNIZED:
//                log.error("Unsupported msg type {}", deviceUpdateMsg.getMsgType());
//                return Futures.immediateFailedFuture(new RuntimeException("Unsupported msg type " + deviceUpdateMsg.getMsgType()));
//        }
//        return Futures.immediateFuture(null);
//    }
//
//    public ListenableFuture<Void> processDeviceCredentialsFromEdge(TenantId tenantId, DeviceCredentialsUpdateMsg deviceCredentialsUpdateMsg) {
//        log.debug("Executing onDeviceCredentialsUpdate, deviceCredentialsUpdateMsg [{}]", deviceCredentialsUpdateMsg);
//        DeviceId deviceId = new DeviceId(new UUID(deviceCredentialsUpdateMsg.getDeviceIdMSB(), deviceCredentialsUpdateMsg.getDeviceIdLSB()));
//        ListenableFuture<Device> deviceFuture = deviceService.findDeviceByIdAsync(tenantId, deviceId);
//        return Futures.transform(deviceFuture, device -> {
//            if (device != null) {
//                log.debug("Updating device credentials for device [{}]. New device credentials Id [{}], value [{}]",
//                        device.getName(), deviceCredentialsUpdateMsg.getCredentialsId(), deviceCredentialsUpdateMsg.getCredentialsValue());
//                try {
//                    DeviceCredentials deviceCredentials = deviceCredentialsService.findDeviceCredentialsByDeviceId(tenantId, device.getId());
//                    deviceCredentials.setCredentialsType(DeviceCredentialsType.valueOf(deviceCredentialsUpdateMsg.getCredentialsType()));
//                    deviceCredentials.setCredentialsId(deviceCredentialsUpdateMsg.getCredentialsId());
//                    if (deviceCredentialsUpdateMsg.hasCredentialsValue()) {
//                        deviceCredentials.setCredentialsValue(deviceCredentialsUpdateMsg.getCredentialsValue());
//                    }
//                    deviceCredentialsService.updateDeviceCredentials(tenantId, deviceCredentials);
//                } catch (Exception e) {
//                    log.error("Can't update device credentials for device [{}], deviceCredentialsUpdateMsg [{}]", device.getName(), deviceCredentialsUpdateMsg, e);
//                    throw new RuntimeException(e);
//                }
//            }
//            return null;
//        }, dbCallbackExecutorService);
//    }
//
//
//    private void updateDevice(TenantId tenantId, Edge edge, DeviceUpdateMsg deviceUpdateMsg) {
//        DeviceId deviceId = new DeviceId(new UUID(deviceUpdateMsg.getIdMSB(), deviceUpdateMsg.getIdLSB()));
//        Device device = deviceService.findDeviceById(tenantId, deviceId);
//        if (device != null) {
//            device.setName(deviceUpdateMsg.getName());
//            device.setType(deviceUpdateMsg.getType());
//            if (deviceUpdateMsg.hasLabel()) {
//                device.setLabel(deviceUpdateMsg.getLabel());
//            }
//            if (deviceUpdateMsg.hasAdditionalInfo()) {
//                device.setAdditionalInfo(JacksonUtil.toJsonNode(deviceUpdateMsg.getAdditionalInfo()));
//            }
//            if (deviceUpdateMsg.hasDeviceProfileIdMSB() && deviceUpdateMsg.hasDeviceProfileIdLSB()) {
//                DeviceProfileId deviceProfileId = new DeviceProfileId(
//                        new UUID(deviceUpdateMsg.getDeviceProfileIdMSB(),
//                                deviceUpdateMsg.getDeviceProfileIdLSB()));
//                device.setDeviceProfileId(deviceProfileId);
//            }
//            Device savedDevice = deviceService.saveDevice(device);
//            tbClusterService.onDeviceUpdated(savedDevice, device);
//            saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.DEVICE, EdgeEventActionType.CREDENTIALS_REQUEST, deviceId, null);
//        } else {
//            log.warn("[{}] can't find device [{}], edge [{}]", tenantId, deviceUpdateMsg, edge.getId());
//        }
//    }
//
//    private Device createDevice(TenantId tenantId, Edge edge, DeviceUpdateMsg deviceUpdateMsg, String deviceName) {
//        Device device;
//        deviceCreationLock.lock();
//        try {
//            log.debug("[{}] Creating device entity [{}] from edge [{}]", tenantId, deviceUpdateMsg, edge.getName());
//            DeviceId deviceId = new DeviceId(new UUID(deviceUpdateMsg.getIdMSB(), deviceUpdateMsg.getIdLSB()));
//            device = deviceService.findDeviceById(tenantId, deviceId);
//            boolean created = false;
//            if (device == null) {
//                device = new Device();
//                device.setTenantId(tenantId);
//                device.setId(deviceId);
//                device.setCreatedTime(Uuids.unixTimestamp(deviceId.getId()));
//                created = true;
//            }
//            // make device private, if edge is public
//            device.setCustomerId(getCustomerId(edge));
//            device.setName(deviceName);
//            device.setType(deviceUpdateMsg.getType());
//            if (deviceUpdateMsg.hasLabel()) {
//                device.setLabel(deviceUpdateMsg.getLabel());
//            }
//            if (deviceUpdateMsg.hasAdditionalInfo()) {
//                device.setAdditionalInfo(JacksonUtil.toJsonNode(deviceUpdateMsg.getAdditionalInfo()));
//            }
//            if (deviceUpdateMsg.hasDeviceProfileIdMSB() && deviceUpdateMsg.hasDeviceProfileIdLSB()) {
//                DeviceProfileId deviceProfileId = new DeviceProfileId(
//                        new UUID(deviceUpdateMsg.getDeviceProfileIdMSB(),
//                                deviceUpdateMsg.getDeviceProfileIdLSB()));
//                device.setDeviceProfileId(deviceProfileId);
//            }
//            Device savedDevice = deviceService.saveDevice(device, false);
//            tbClusterService.onDeviceUpdated(savedDevice, created ? null : device, false);
//            if (created) {
//                DeviceCredentials deviceCredentials = new DeviceCredentials();
//                deviceCredentials.setDeviceId(new DeviceId(savedDevice.getUuidId()));
//                deviceCredentials.setCredentialsType(DeviceCredentialsType.ACCESS_TOKEN);
//                deviceCredentials.setCredentialsId(RandomStringUtils.randomAlphanumeric(20));
//                deviceCredentialsService.createDeviceCredentials(device.getTenantId(), deviceCredentials);
//            }
//            createRelationFromEdge(tenantId, edge.getId(), device.getId());
//            pushDeviceCreatedEventToRuleEngine(tenantId, edge, device);
//            deviceService.assignDeviceToEdge(edge.getTenantId(), device.getId(), edge.getId());
//        } finally {
//            deviceCreationLock.unlock();
//        }
//        return device;
//    }
//
//    private CustomerId getCustomerId(Edge edge) {
//        if (edge.getCustomerId() == null || edge.getCustomerId().getId().equals(ModelConstants.NULL_UUID)) {
//            return edge.getCustomerId();
//        }
//        Customer publicCustomer = customerService.findOrCreatePublicCustomer(edge.getTenantId());
//        if (publicCustomer.getId().equals(edge.getCustomerId())) {
//            return null;
//        } else {
//            return edge.getCustomerId();
//        }
//    }
//
//    private void createRelationFromEdge(TenantId tenantId, EdgeId edgeId, EntityId entityId) {
//        EntityRelation relation = new EntityRelation();
//        relation.setFrom(edgeId);
//        relation.setTo(entityId);
//        relation.setTypeGroup(RelationTypeGroup.COMMON);
//        relation.setType(EntityRelation.EDGE_TYPE);
//        relationService.saveRelation(tenantId, relation);
//    }
//
//    private void pushDeviceCreatedEventToRuleEngine(TenantId tenantId, Edge edge, Device device) {
//        try {
//            DeviceId deviceId = device.getId();
//            ObjectNode entityNode = mapper.valueToTree(device);
//            TbMsg tbMsg = TbMsg.newMsg(DataConstants.ENTITY_CREATED, deviceId, device.getCustomerId(),
//                    getActionTbMsgMetaData(edge, device.getCustomerId()), TbMsgDataType.JSON, mapper.writeValueAsString(entityNode));
//            tbClusterService.pushMsgToRuleEngine(tenantId, deviceId, tbMsg, new TbQueueCallback() {
//                @Override
//                public void onSuccess(TbQueueMsgMetadata metadata) {
//                    log.debug("Successfully send ENTITY_CREATED EVENT to rule engine [{}]", device);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.debug("Failed to send ENTITY_CREATED EVENT to rule engine [{}]", device, t);
//                }
//            });
//        } catch (JsonProcessingException | IllegalArgumentException e) {
//            log.warn("[{}] Failed to push device action to rule engine: {}", device.getId(), DataConstants.ENTITY_CREATED, e);
//        }
//    }
//
//    private TbMsgMetaData getActionTbMsgMetaData(Edge edge, CustomerId customerId) {
//        TbMsgMetaData metaData = getTbMsgMetaData(edge);
//        if (customerId != null && !customerId.isNullUid()) {
//            metaData.putValue("customerId", customerId.toString());
//        }
//        return metaData;
//    }
//
//    private TbMsgMetaData getTbMsgMetaData(Edge edge) {
//        TbMsgMetaData metaData = new TbMsgMetaData();
//        metaData.putValue("edgeId", edge.getId().toString());
//        metaData.putValue("edgeName", edge.getName());
//        return metaData;
//    }
//
//    public ListenableFuture<Void> processDeviceRpcCallResponseFromEdge(TenantId tenantId, DeviceRpcCallMsg deviceRpcCallMsg) {
//        log.trace("[{}] processDeviceRpcCallResponseMsg [{}]", tenantId, deviceRpcCallMsg);
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        UUID requestUuid = new UUID(deviceRpcCallMsg.getRequestUuidMSB(), deviceRpcCallMsg.getRequestUuidLSB());
//        DeviceId deviceId = new DeviceId(new UUID(deviceRpcCallMsg.getDeviceIdMSB(), deviceRpcCallMsg.getDeviceIdLSB()));
//
//        FromDeviceRpcResponse response;
//        if (!StringUtils.isEmpty(deviceRpcCallMsg.getResponseMsg().getError())) {
//            response = new FromDeviceRpcResponse(requestUuid, null, RpcError.valueOf(deviceRpcCallMsg.getResponseMsg().getError()));
//        } else {
//            response = new FromDeviceRpcResponse(requestUuid, deviceRpcCallMsg.getResponseMsg().getResponse(), null);
//        }
//        TbQueueCallback callback = new TbQueueCallback() {
//            @Override
//            public void onSuccess(TbQueueMsgMetadata metadata) {
//                futureToSet.set(null);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("Can't process push notification to core [{}]", deviceRpcCallMsg, t);
//                futureToSet.setException(t);
//            }
//        };
//        FromDeviceRpcResponseActorMsg msg =
//                new FromDeviceRpcResponseActorMsg(deviceRpcCallMsg.getRequestId(),
//                        tenantId,
//                        deviceId, response);
//        tbClusterService.pushMsgToCore(msg, callback);
//        return futureToSet;
//    }
//
//    public DownlinkMsg processDeviceToEdge(Edge edge, EdgeEvent edgeEvent,
//                                           UpdateMsgType msgType, EdgeEventActionType edgeEdgeEventActionType) {
//        DeviceId deviceId = new DeviceId(edgeEvent.getEntityId());
//        DownlinkMsg downlinkMsg = null;
//        switch (edgeEdgeEventActionType) {
//            case ADDED:
//            case UPDATED:
//            case ASSIGNED_TO_EDGE:
//            case ASSIGNED_TO_CUSTOMER:
//            case UNASSIGNED_FROM_CUSTOMER:
//                Device device = deviceService.findDeviceById(edgeEvent.getTenantId(), deviceId);
//                if (device != null) {
//                    CustomerId customerId = getCustomerIdIfEdgeAssignedToCustomer(device, edge);
//                    DeviceUpdateMsg deviceUpdateMsg =
//                            deviceMsgConstructor.constructDeviceUpdatedMsg(msgType, device, customerId, null);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addDeviceUpdateMsg(deviceUpdateMsg)
//                            .build();
//                }
//                break;
//            case DELETED:
//            case UNASSIGNED_FROM_EDGE:
//                DeviceUpdateMsg deviceUpdateMsg =
//                        deviceMsgConstructor.constructDeviceDeleteMsg(deviceId);
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addDeviceUpdateMsg(deviceUpdateMsg)
//                        .build();
//                break;
//            case CREDENTIALS_UPDATED:
//                DeviceCredentials deviceCredentials = deviceCredentialsService.findDeviceCredentialsByDeviceId(edge.getTenantId(), deviceId);
//                if (deviceCredentials != null) {
//                    DeviceCredentialsUpdateMsg deviceCredentialsUpdateMsg =
//                            deviceMsgConstructor.constructDeviceCredentialsUpdatedMsg(deviceCredentials);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addDeviceCredentialsUpdateMsg(deviceCredentialsUpdateMsg)
//                            .build();
//                }
//                break;
//        }
//        return downlinkMsg;
//    }
//
//    public DownlinkMsg processRpcCallMsgToEdge(EdgeEvent edgeEvent) {
//        log.trace("Executing processRpcCall, edgeEvent [{}]", edgeEvent);
//        DeviceRpcCallMsg deviceRpcCallMsg =
//                deviceMsgConstructor.constructDeviceRpcCallMsg(edgeEvent.getEntityId(), edgeEvent.getBody());
//        return DownlinkMsg.newBuilder()
//                .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                .addDeviceRpcCallMsg(deviceRpcCallMsg)
//                .build();
//    }
}
