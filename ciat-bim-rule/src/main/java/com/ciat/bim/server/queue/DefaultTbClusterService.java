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
package com.ciat.bim.server.queue;

import com.ciat.bim.data.device.profile.DeviceProfile;
import com.ciat.bim.data.id.*;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.engine.api.msg.DeviceNameOrTypeUpdateMsg;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.common.data.edge.EdgeEventType;
import com.ciat.bim.server.common.msg.ToDeviceActorNotificationMsg;
import com.ciat.bim.server.common.msg.edge.EdgeEventUpdateMsg;
import com.ciat.bim.server.common.msg.plugin.ComponentLifecycleMsg;
import com.ciat.bim.server.common.msg.rpc.FromDeviceRpcResponse;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.ota.OtaPackageStateService;
import com.ciat.bim.server.profile.TbDeviceProfileCache;
import com.ciat.bim.server.queue.common.MultipleTbQueueCallbackWrapper;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.PartitionService;
import com.ciat.bim.server.queue.provider.TbQueueProducerProvider;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.queue.util.EdgeUtils;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.ToTransportMsg;
import com.ciat.bim.server.transport.TransportProtos.ToRuleEngineNotificationMsg;
import com.ciat.bim.server.transport.TransportProtos.ToRuleEngineMsg;
import com.ciat.bim.server.transport.TransportProtos.ToCoreNotificationMsg;
import com.ciat.bim.server.transport.TransportProtos.FromDeviceRPCResponseProto;
import com.ciat.bim.server.transport.TransportProtos.ToCoreMsg;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.resource.entity.TbResource;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
@RequiredArgsConstructor
public class DefaultTbClusterService implements TbClusterService {

    @Value("${cluster.stats.enabled:false}")
    private boolean statsEnabled;
    @Value("${edges.enabled}")
    protected boolean edgesEnabled;

    private final AtomicInteger toCoreMsgs = new AtomicInteger(0);
    private final AtomicInteger toCoreNfs = new AtomicInteger(0);
    private final AtomicInteger toRuleEngineMsgs = new AtomicInteger(0);
    private final AtomicInteger toRuleEngineNfs = new AtomicInteger(0);
    private final AtomicInteger toTransportNfs = new AtomicInteger(0);
    @Autowired
    private final TbQueueProducerProvider producerProvider;
    @Autowired
    private final PartitionService partitionService;
    @Autowired
    @Lazy
    private  DataDecodingEncodingService encodingService;
    @Autowired
    @Lazy
    private  TbDeviceProfileCache deviceProfileCache;
    @Autowired
    @Lazy
    private  OtaPackageStateService otaPackageStateService;

    @Override
    public void pushMsgToCore(TenantId tenantId, EntityId entityId, ToCoreMsg msg, TbQueueCallback callback) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, tenantId, entityId);
        producerProvider.getTbCoreMsgProducer().send(tpi, new TbProtoQueueMsg<>(UUID.randomUUID(), msg), callback);
        toCoreMsgs.incrementAndGet();
    }

    @Override
    public void pushMsgToCore(TopicPartitionInfo tpi, UUID msgId, ToCoreMsg msg, TbQueueCallback callback) {
        producerProvider.getTbCoreMsgProducer().send(tpi, new TbProtoQueueMsg<>(msgId, msg), callback);
        toCoreMsgs.incrementAndGet();
    }

    @Override
    public void pushMsgToCore(ToDeviceActorNotificationMsg msg, TbQueueCallback callback) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, msg.getTenantId(), msg.getDeviceId());
        log.trace("PUSHING msg: {} to:{}", msg, tpi);
        byte[] msgBytes = encodingService.encode(msg);
        ToCoreMsg toCoreMsg = ToCoreMsg.newBuilder().setToDeviceActorNotificationMsg(ByteString.copyFrom(msgBytes)).build();
        producerProvider.getTbCoreMsgProducer().send(tpi, new TbProtoQueueMsg<>(msg.getDeviceId().getId(), toCoreMsg), callback);
        toCoreMsgs.incrementAndGet();
    }

    @Override
    public void pushNotificationToCore(String serviceId, FromDeviceRpcResponse response, TbQueueCallback callback) {
        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, serviceId);
        log.trace("PUSHING msg: {} to:{}", response, tpi);
        FromDeviceRPCResponseProto.Builder builder = FromDeviceRPCResponseProto.newBuilder()
                .setRequestIdMSB(response.getId().getMostSignificantBits())
                .setRequestIdLSB(response.getId().getLeastSignificantBits())
                .setError(response.getError().isPresent() ? response.getError().get().ordinal() : -1);
        response.getResponse().ifPresent(builder::setResponse);
        ToCoreNotificationMsg msg = ToCoreNotificationMsg.newBuilder().setFromDeviceRpcResponse(builder).build();
        producerProvider.getTbCoreNotificationsMsgProducer().send(tpi, new TbProtoQueueMsg<>(response.getId(), msg), callback);
        toCoreNfs.incrementAndGet();
    }

    @Override
    public void pushMsgToRuleEngine(TopicPartitionInfo tpi, UUID msgId, ToRuleEngineMsg msg, TbQueueCallback callback) {
        log.trace("PUSHING msg: {} to:{}", msg, tpi);
        producerProvider.getRuleEngineMsgProducer().send(tpi, new TbProtoQueueMsg<>(msgId, msg), callback);
        toRuleEngineMsgs.incrementAndGet();
    }

    @Override
    public void pushMsgToRuleEngine(TenantId tenantId, EntityId entityId, TbMsg tbMsg, TbQueueCallback callback) {
        if (tenantId.isNullUid()) {
            if (entityId.getEntityType().equals(EntityType.TENANT)) {
                tenantId = new TenantId(entityId.getId());
            } else {
                log.warn("[{}][{}] Received invalid message: {}", tenantId, entityId, tbMsg);
                return;
            }
        } else {
            if (entityId.getEntityType().equals(EntityType.DEVICE)) {
                tbMsg = transformMsg(tbMsg, deviceProfileCache.get(tenantId, new DeviceId(entityId.getId())));
            } else if (entityId.getEntityType().equals(EntityType.DEVICE_PROFILE)) {
                tbMsg = transformMsg(tbMsg, deviceProfileCache.get(tenantId, new DeviceProfileId(entityId.getId())));
            }
        }
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_RULE_ENGINE, tbMsg.getQueueName(), tenantId, entityId);
        log.trace("PUSHING msg: {} to:{}", tbMsg, tpi);
        ToRuleEngineMsg msg = ToRuleEngineMsg.newBuilder()
                .setTenantIdMSB(tenantId.getId().getMostSignificantBits())
                .setTenantIdLSB(tenantId.getId().getLeastSignificantBits())
                .setTbMsg(TbMsg.toByteString(tbMsg)).build();
        producerProvider.getRuleEngineMsgProducer().send(tpi, new TbProtoQueueMsg<>(tbMsg.getId(), msg), callback);
        toRuleEngineMsgs.incrementAndGet();
    }

    private TbMsg transformMsg(TbMsg tbMsg, DeviceProfile deviceProfile) {
        if (deviceProfile != null) {
            RuleChainId targetRuleChainId = deviceProfile.getDefaultRuleChainId();
            String targetQueueName = deviceProfile.getDefaultQueueName();
            boolean isRuleChainTransform = targetRuleChainId != null && !targetRuleChainId.equals(tbMsg.getRuleChainId());
            boolean isQueueTransform = targetQueueName != null && !targetQueueName.equals(tbMsg.getQueueName());

            if (isRuleChainTransform && isQueueTransform) {
                tbMsg = TbMsg.transformMsg(tbMsg, targetRuleChainId, targetQueueName);
            } else if (isRuleChainTransform) {
                tbMsg = TbMsg.transformMsg(tbMsg, targetRuleChainId);
            } else if (isQueueTransform) {
                tbMsg = TbMsg.transformMsg(tbMsg, targetQueueName);
            }
        }
        return tbMsg;
    }

    @Override
    public void pushNotificationToRuleEngine(String serviceId, FromDeviceRpcResponse response, TbQueueCallback callback) {
        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_RULE_ENGINE, serviceId);
        log.trace("PUSHING msg: {} to:{}", response, tpi);
        FromDeviceRPCResponseProto.Builder builder = FromDeviceRPCResponseProto.newBuilder()
                .setRequestIdMSB(response.getId().getMostSignificantBits())
                .setRequestIdLSB(response.getId().getLeastSignificantBits())
                .setError(response.getError().isPresent() ? response.getError().get().ordinal() : -1);
        response.getResponse().ifPresent(builder::setResponse);
        ToRuleEngineNotificationMsg msg = ToRuleEngineNotificationMsg.newBuilder().setFromDeviceRpcResponse(builder).build();
        producerProvider.getRuleEngineNotificationsMsgProducer().send(tpi, new TbProtoQueueMsg<>(response.getId(), msg), callback);
        toRuleEngineNfs.incrementAndGet();
    }

    @Override
    public void pushNotificationToTransport(String serviceId, ToTransportMsg response, TbQueueCallback callback) {
        if (serviceId == null || serviceId.isEmpty()){
            log.trace("pushNotificationToTransport: skipping message without serviceId [{}], (ToTransportMsg) response [{}]", serviceId, response);
            if (callback != null) {
                callback.onSuccess(null); //callback that message already sent, no useful payload expected
            }
            return;
        }
        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_TRANSPORT, serviceId);
        log.trace("PUSHING msg: {} to:{}", response, tpi);
        producerProvider.getTransportNotificationsMsgProducer().send(tpi, new TbProtoQueueMsg<>(UUID.randomUUID(), response), callback);
        toTransportNfs.incrementAndGet();
    }

    @Override
    public void broadcastEntityStateChangeEvent(TenantId tenantId, EntityId entityId, ComponentLifecycleEvent state) {
        log.trace("[{}] Processing {} state change event: {}", tenantId, entityId.getEntityType(), state);
        broadcast(new ComponentLifecycleMsg(tenantId, entityId, state));
    }

    @Override
    public void onDeviceProfileChange(DeviceProfile deviceProfile, TbQueueCallback callback) {
        broadcastEntityChangeToTransport(deviceProfile.getTenantId(), deviceProfile.getId(), deviceProfile, callback);
    }

    @Override
    public void onTenantProfileChange(TenantProfile tenantProfile, TbQueueCallback callback) {
        broadcastEntityChangeToTransport(TenantId.SYS_TENANT_ID,  TenantProfileId.fromString(tenantProfile.getId()), tenantProfile, callback);
    }

    @Override
    public void onTenantChange(Tenant tenant, TbQueueCallback callback) {
        broadcastEntityChangeToTransport(TenantId.SYS_TENANT_ID,TenantId.fromString(tenant.getId()), tenant, callback);
    }

    @Override
    public void onApiStateChange(ApiUsageState apiUsageState, TbQueueCallback callback) {
        broadcastEntityChangeToTransport(apiUsageState.getTenantId(), apiUsageState.getId(), apiUsageState, callback);
        broadcast(new ComponentLifecycleMsg(apiUsageState.getTenantId(), apiUsageState.getId(), ComponentLifecycleEvent.UPDATED));
    }

    @Override
    public void onDeviceProfileDelete(DeviceProfile entity, TbQueueCallback callback) {
        broadcastEntityDeleteToTransport(entity.getTenantId(), entity.getId(), entity.getName(), callback);
    }

    @Override
    public void onTenantProfileDelete(TenantProfile entity, TbQueueCallback callback) {
        broadcastEntityDeleteToTransport(TenantId.SYS_TENANT_ID, TenantProfileId.fromString( entity.getId()), entity.getName(), callback);
    }

    @Override
    public void onTenantDelete(Tenant entity, TbQueueCallback callback) {
        broadcastEntityDeleteToTransport(TenantId.SYS_TENANT_ID, TenantId.fromString(entity.getId()) , entity.getTitle(), callback);
    }

    @Override
    public void onDeviceDeleted(Device device, TbQueueCallback callback) {
        broadcastEntityDeleteToTransport(TenantId.fromString(device.getTenantId()),DeviceId.fromString(device.getId()), device.getName(), callback);
        sendDeviceStateServiceEvent(TenantId.fromString(device.getTenantId()), DeviceId.fromString(device.getId()), false, false, true);
        broadcastEntityStateChangeEvent(TenantId.fromString(device.getTenantId()), DeviceId.fromString(device.getId()), ComponentLifecycleEvent.DELETED);
    }

    @Override
    public void onResourceChange(TbResource resource, TbQueueCallback callback) {
        TenantId tenantId = TenantId.fromString(resource.getTenantId());
        log.trace("[{}][{}][{}] Processing change resource", tenantId, resource.getResourceType(), resource.getResourceKey());
        TransportProtos.ResourceUpdateMsg resourceUpdateMsg = TransportProtos.ResourceUpdateMsg.newBuilder()
                .setTenantIdMSB(tenantId.getId().getMostSignificantBits())
                .setTenantIdLSB(tenantId.getId().getLeastSignificantBits())
                .setResourceType(resource.getResourceType())
                .setResourceKey(resource.getResourceKey())
                .build();
        ToTransportMsg transportMsg = ToTransportMsg.newBuilder().setResourceUpdateMsg(resourceUpdateMsg).build();
        broadcast(transportMsg, callback);
    }

    @Override
    public void onResourceDeleted(TbResource resource, TbQueueCallback callback) {
        log.trace("[{}] Processing delete resource", resource);
        TransportProtos.ResourceDeleteMsg resourceUpdateMsg = TransportProtos.ResourceDeleteMsg.newBuilder()
                .setTenantIdMSB(TenantId.fromString(resource.getTenantId()).getId().getMostSignificantBits())
                .setTenantIdLSB(TenantId.fromString(resource.getTenantId()).getId().getLeastSignificantBits())
                .setResourceType(resource.getResourceType())
                .setResourceKey(resource.getResourceKey())
                .build();
        ToTransportMsg transportMsg = ToTransportMsg.newBuilder().setResourceDeleteMsg(resourceUpdateMsg).build();
        broadcast(transportMsg, callback);
    }

    public <T> void broadcastEntityChangeToTransport(TenantId tenantId, EntityId entityid, T entity, TbQueueCallback callback) {
        String entityName = (entity instanceof HasName) ? ((HasName) entity).getName() : entity.getClass().getName();
        log.trace("[{}][{}][{}] Processing [{}] change event", tenantId, entityid.getEntityType(), entityid.getId(), entityName);
        TransportProtos.EntityUpdateMsg entityUpdateMsg = TransportProtos.EntityUpdateMsg.newBuilder()
                .setEntityType(entityid.getEntityType().name())
                .setData(ByteString.copyFrom(encodingService.encode(entity))).build();
        ToTransportMsg transportMsg = ToTransportMsg.newBuilder().setEntityUpdateMsg(entityUpdateMsg).build();
        broadcast(transportMsg, callback);
    }

    private void broadcastEntityDeleteToTransport(TenantId tenantId, EntityId entityId, String name, TbQueueCallback callback) {
        log.trace("[{}][{}][{}] Processing [{}] delete event", tenantId, entityId.getEntityType(), entityId.getId(), name);
        TransportProtos.EntityDeleteMsg entityDeleteMsg = TransportProtos.EntityDeleteMsg.newBuilder()
                .setEntityType(entityId.getEntityType().name())
                .setEntityIdMSB(entityId.getId().getMostSignificantBits())
                .setEntityIdLSB(entityId.getId().getLeastSignificantBits())
                .build();
        ToTransportMsg transportMsg = ToTransportMsg.newBuilder().setEntityDeleteMsg(entityDeleteMsg).build();
        broadcast(transportMsg, callback);
    }

    private void broadcast(ToTransportMsg transportMsg, TbQueueCallback callback) {
        TbQueueProducer<TbProtoQueueMsg<ToTransportMsg>> toTransportNfProducer = producerProvider.getTransportNotificationsMsgProducer();
        Set<String> tbTransportServices = partitionService.getAllServiceIds(ServiceType.TB_TRANSPORT);
        TbQueueCallback proxyCallback = callback != null ? new MultipleTbQueueCallbackWrapper(tbTransportServices.size(), callback) : null;
        for (String transportServiceId : tbTransportServices) {
            TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_TRANSPORT, transportServiceId);
            toTransportNfProducer.send(tpi, new TbProtoQueueMsg<>(UUID.randomUUID(), transportMsg), proxyCallback);
            toTransportNfs.incrementAndGet();
        }
    }

    @Override
    public void onEdgeEventUpdate(TenantId tenantId, EdgeId edgeId) {
        log.trace("[{}] Processing edge {} event update ", tenantId, edgeId);
        EdgeEventUpdateMsg msg = new EdgeEventUpdateMsg(tenantId, edgeId);
        byte[] msgBytes = encodingService.encode(msg);
        TbQueueProducer<TbProtoQueueMsg<ToCoreNotificationMsg>> toCoreNfProducer = producerProvider.getTbCoreNotificationsMsgProducer();
        Set<String> tbCoreServices = partitionService.getAllServiceIds(ServiceType.TB_CORE);
        for (String serviceId : tbCoreServices) {
            TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, serviceId);
            ToCoreNotificationMsg toCoreMsg = ToCoreNotificationMsg.newBuilder().setEdgeEventUpdateMsg(ByteString.copyFrom(msgBytes)).build();
            toCoreNfProducer.send(tpi, new TbProtoQueueMsg<>(msg.getEdgeId().getId(), toCoreMsg), null);
            toCoreNfs.incrementAndGet();
        }
    }

    private void broadcast(ComponentLifecycleMsg msg) {
        byte[] msgBytes = encodingService.encode(msg);
        TbQueueProducer<TbProtoQueueMsg<ToRuleEngineNotificationMsg>> toRuleEngineProducer = producerProvider.getRuleEngineNotificationsMsgProducer();
        Set<String> tbRuleEngineServices = new HashSet<>(partitionService.getAllServiceIds(ServiceType.TB_RULE_ENGINE));
        EntityType entityType = msg.getEntityId().getEntityType();
        if (entityType.equals(EntityType.TENANT)
                || entityType.equals(EntityType.TENANT_PROFILE)
                || entityType.equals(EntityType.DEVICE_PROFILE)
                || entityType.equals(EntityType.API_USAGE_STATE)
                || (entityType.equals(EntityType.DEVICE) && msg.getEvent() == ComponentLifecycleEvent.UPDATED)
                || entityType.equals(EntityType.EDGE)) {
            TbQueueProducer<TbProtoQueueMsg<ToCoreNotificationMsg>> toCoreNfProducer = producerProvider.getTbCoreNotificationsMsgProducer();
            Set<String> tbCoreServices = partitionService.getAllServiceIds(ServiceType.TB_CORE);
            for (String serviceId : tbCoreServices) {
                TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_CORE, serviceId);
                ToCoreNotificationMsg toCoreMsg = ToCoreNotificationMsg.newBuilder().setComponentLifecycleMsg(ByteString.copyFrom(msgBytes)).build();
                toCoreNfProducer.send(tpi, new TbProtoQueueMsg<>(msg.getEntityId().getId(), toCoreMsg), null);
                toCoreNfs.incrementAndGet();
            }
            // No need to push notifications twice
            tbRuleEngineServices.removeAll(tbCoreServices);
        }
        for (String serviceId : tbRuleEngineServices) {
            TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_RULE_ENGINE, serviceId);
            ToRuleEngineNotificationMsg toRuleEngineMsg = ToRuleEngineNotificationMsg.newBuilder().setComponentLifecycleMsg(ByteString.copyFrom(msgBytes)).build();
            toRuleEngineProducer.send(tpi, new TbProtoQueueMsg<>(msg.getEntityId().getId(), toRuleEngineMsg), null);
            toRuleEngineNfs.incrementAndGet();
        }
    }

    @Scheduled(fixedDelayString = "${cluster.stats.print_interval_ms}")
    public void printStats() {
        if (statsEnabled) {
            int toCoreMsgCnt = toCoreMsgs.getAndSet(0);
            int toCoreNfsCnt = toCoreNfs.getAndSet(0);
            int toRuleEngineMsgsCnt = toRuleEngineMsgs.getAndSet(0);
            int toRuleEngineNfsCnt = toRuleEngineNfs.getAndSet(0);
            int toTransportNfsCnt = toTransportNfs.getAndSet(0);
            if (toCoreMsgCnt > 0 || toCoreNfsCnt > 0 || toRuleEngineMsgsCnt > 0 || toRuleEngineNfsCnt > 0 || toTransportNfsCnt > 0) {
                log.info("To TbCore: [{}] messages [{}] notifications; To TbRuleEngine: [{}] messages [{}] notifications; To Transport: [{}] notifications",
                        toCoreMsgCnt, toCoreNfsCnt, toRuleEngineMsgsCnt, toRuleEngineNfsCnt, toTransportNfsCnt);
            }
        }
    }

    private void sendDeviceStateServiceEvent(TenantId tenantId, DeviceId deviceId, boolean added, boolean updated, boolean deleted) {
        TransportProtos.DeviceStateServiceMsgProto.Builder builder = TransportProtos.DeviceStateServiceMsgProto.newBuilder();
        builder.setTenantIdMSB(tenantId.getId().getMostSignificantBits());
        builder.setTenantIdLSB(tenantId.getId().getLeastSignificantBits());
        builder.setDeviceIdMSB(deviceId.getId().getMostSignificantBits());
        builder.setDeviceIdLSB(deviceId.getId().getLeastSignificantBits());
        builder.setAdded(added);
        builder.setUpdated(updated);
        builder.setDeleted(deleted);
        TransportProtos.DeviceStateServiceMsgProto msg = builder.build();
        pushMsgToCore(tenantId, deviceId, TransportProtos.ToCoreMsg.newBuilder().setDeviceStateServiceMsg(msg).build(), null);
    }

    @Override
    public void onDeviceUpdated(Device device, Device old) {
        onDeviceUpdated(device, old, true);
    }

    @Override
    public void onDeviceUpdated(Device device, Device old, boolean notifyEdge) {
//        var created = old == null;
//        broadcastEntityChangeToTransport(device.getTenantId(), device.getId(), device, null);
//        if (old != null && (!device.getName().equals(old.getName()) || !device.getType().equals(old.getType()))) {
//            pushMsgToCore(new DeviceNameOrTypeUpdateMsg(device.getTenantId(), device.getId(), device.getName(), device.getType()), null);
//        }
//        broadcastEntityStateChangeEvent(device.getTenantId(), device.getId(), created ? ComponentLifecycleEvent.CREATED : ComponentLifecycleEvent.UPDATED);
//        sendDeviceStateServiceEvent(device.getTenantId(), device.getId(), created, !created, false);
//        otaPackageStateService.update(device, old);
//        if (!created && notifyEdge) {
//            sendNotificationMsgToEdgeService(device.getTenantId(), null, device.getId(), null, null, EdgeEventActionType.UPDATED);
//        }
    }

    @Override
    public void sendNotificationMsgToEdgeService(TenantId tenantId, EdgeId edgeId, EntityId entityId, String body, EdgeEventType type, EdgeEventActionType action) {
        if (!edgesEnabled) {
            return;
        }
        if (type == null) {
            if (entityId != null) {
                type = EdgeUtils.getEdgeEventTypeByEntityType(entityId.getEntityType());
            } else {
                log.trace("[{}] entity id and type are null. Ignoring this notification", tenantId);
                return;
            }
            if (type == null) {
                log.trace("[{}] edge event type is null. Ignoring this notification [{}]", tenantId, entityId);
                return;
            }
        }
        TransportProtos.EdgeNotificationMsgProto.Builder builder = TransportProtos.EdgeNotificationMsgProto.newBuilder();
        builder.setTenantIdMSB(tenantId.getId().getMostSignificantBits());
        builder.setTenantIdLSB(tenantId.getId().getLeastSignificantBits());
        builder.setType(type.name());
        builder.setAction(action.name());
        if (entityId != null) {
            builder.setEntityIdMSB(entityId.getId().getMostSignificantBits());
            builder.setEntityIdLSB(entityId.getId().getLeastSignificantBits());
            builder.setEntityType(entityId.getEntityType().name());
        }
        if (edgeId != null) {
            builder.setEdgeIdMSB(edgeId.getId().getMostSignificantBits());
            builder.setEdgeIdLSB(edgeId.getId().getLeastSignificantBits());
        }
        if (body != null) {
            builder.setBody(body);
        }
        TransportProtos.EdgeNotificationMsgProto msg = builder.build();
        log.trace("[{}] sending notification to edge service {}", tenantId.getId(), msg);
        pushMsgToCore(tenantId, entityId != null ? entityId : tenantId, TransportProtos.ToCoreMsg.newBuilder().setEdgeNotificationMsg(msg).build(), null);
    }

}
