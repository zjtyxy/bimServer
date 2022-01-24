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
package com.ciat.bim.server.cluster;


import com.ciat.bim.data.device.profile.DeviceProfile;
import com.ciat.bim.data.id.EdgeId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.common.data.edge.EdgeEventType;
import com.ciat.bim.server.common.msg.ToDeviceActorNotificationMsg;
import com.ciat.bim.server.common.msg.rpc.FromDeviceRpcResponse;
import com.ciat.bim.server.queue.ComponentLifecycleEvent;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.ToTransportMsg;
import com.ciat.bim.server.transport.TransportProtos.ToCoreMsg;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.resource.entity.TbResource;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;

import java.util.UUID;

public interface TbClusterService {

    void pushMsgToCore(TopicPartitionInfo tpi, UUID msgKey, ToCoreMsg msg, TbQueueCallback callback);

    void pushMsgToCore(TenantId tenantId, EntityId entityId, ToCoreMsg msg, TbQueueCallback callback);

    void pushMsgToCore(ToDeviceActorNotificationMsg msg, TbQueueCallback callback);

    void pushNotificationToCore(String targetServiceId, FromDeviceRpcResponse response, TbQueueCallback callback);

    void pushMsgToRuleEngine(TopicPartitionInfo tpi, UUID msgId, TransportProtos.ToRuleEngineMsg msg, TbQueueCallback callback);

    void pushMsgToRuleEngine(TenantId tenantId, EntityId entityId, TbMsg msg, TbQueueCallback callback);

    void pushNotificationToRuleEngine(String targetServiceId, FromDeviceRpcResponse response, TbQueueCallback callback);

    void pushNotificationToTransport(String targetServiceId, ToTransportMsg response, TbQueueCallback callback);

    void broadcastEntityStateChangeEvent(TenantId tenantId, EntityId entityId, ComponentLifecycleEvent state);

    void onDeviceProfileChange(DeviceProfile deviceProfile, TbQueueCallback callback);

    void onDeviceProfileDelete(DeviceProfile deviceProfile, TbQueueCallback callback);

    void onTenantProfileChange(TenantProfile tenantProfile, TbQueueCallback callback);

    void onTenantProfileDelete(TenantProfile tenantProfile, TbQueueCallback callback);

    void onTenantChange(Tenant tenant, TbQueueCallback callback);

    void onTenantDelete(Tenant tenant, TbQueueCallback callback);

    void onApiStateChange(ApiUsageState apiUsageState, TbQueueCallback callback);

    void onDeviceUpdated(Device device, Device old);

    void onDeviceUpdated(Device device, Device old, boolean notifyEdge);

    void onDeviceDeleted(Device device, TbQueueCallback callback);

    void onResourceChange(TbResource resource, TbQueueCallback callback);

    void onResourceDeleted(TbResource resource, TbQueueCallback callback);

    void onEdgeEventUpdate(TenantId tenantId, EdgeId edgeId);

    void sendNotificationMsgToEdgeService(TenantId tenantId, EdgeId edgeId, EntityId entityId, String body, EdgeEventType type, EdgeEventActionType action);
}
