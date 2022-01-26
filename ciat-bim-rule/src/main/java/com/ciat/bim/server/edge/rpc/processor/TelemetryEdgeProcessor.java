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

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.device.profile.DeviceProfile;
import com.ciat.bim.data.id.*;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceQueue;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.edge.EdgeEvent;
import com.ciat.bim.server.edge.gen.AttributeDeleteMsg;
import com.ciat.bim.server.edge.gen.DownlinkMsg;
import com.ciat.bim.server.edge.gen.EntityDataProto;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.queue.TbQueueMsgMetadata;
import com.ciat.bim.server.queue.util.EdgeUtils;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.transport.TransportProtos;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.micrometer.core.instrument.util.JsonUtils;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jeecg.modules.device.entity.Device;
import org.springframework.stereotype.Component;


import javax.annotation.Nullable;
import java.util.*;

@Component
@Slf4j
@TbCoreComponent
public class TelemetryEdgeProcessor extends BaseEdgeProcessor {
//
//    private final Gson gson = new Gson();
//
//    public List<ListenableFuture<Void>> processTelemetryFromEdge(TenantId tenantId, CustomerId customerId, EntityDataProto entityData) {
//        log.trace("[{}] onTelemetryUpdate [{}]", tenantId, entityData);
//        List<ListenableFuture<Void>> result = new ArrayList<>();
//        EntityId entityId = constructEntityId(entityData);
//        if ((entityData.hasPostAttributesMsg() || entityData.hasPostTelemetryMsg() || entityData.hasAttributesUpdatedMsg()) && entityId != null) {
//            // @voba - in terms of performance we should not fetch device from DB by id
//            // TbMsgMetaData metaData = constructBaseMsgMetadata(tenantId, entityId);
//            TbMsgMetaData metaData = new TbMsgMetaData();
//            metaData.putValue(DataConstants.MSG_SOURCE_KEY, DataConstants.EDGE_MSG_SOURCE);
//            if (entityData.hasPostAttributesMsg()) {
//                result.add(processPostAttributes(tenantId, customerId, entityId, entityData.getPostAttributesMsg(), metaData));
//            }
//            if (entityData.hasAttributesUpdatedMsg()) {
//                metaData.putValue("scope", entityData.getPostAttributeScope());
//                result.add(processAttributesUpdate(tenantId, customerId, entityId, entityData.getAttributesUpdatedMsg(), metaData));
//            }
//            if (entityData.hasPostTelemetryMsg()) {
//                result.add(processPostTelemetry(tenantId, customerId, entityId, entityData.getPostTelemetryMsg(), metaData));
//            }
//        }
//        if (entityData.hasAttributeDeleteMsg()) {
//            result.add(processAttributeDeleteMsg(tenantId, entityId, entityData.getAttributeDeleteMsg(), entityData.getEntityType()));
//        }
//        return result;
//    }
//
//    private TbMsgMetaData constructBaseMsgMetadata(TenantId tenantId, EntityId entityId) {
//        TbMsgMetaData metaData = new TbMsgMetaData();
//        switch (entityId.getEntityType()) {
//            case DEVICE:
//                Device device = deviceService.findDeviceById(tenantId, new DeviceId(entityId.getId()));
//                if (device != null) {
//                    metaData.putValue("deviceName", device.getName());
//                    metaData.putValue("deviceType", device.getType());
//                }
//                break;
//            case ASSET:
//                Asset asset = assetService.findAssetById(tenantId, new AssetId(entityId.getId()));
//                if (asset != null) {
//                    metaData.putValue("assetName", asset.getName());
//                    metaData.putValue("assetType", asset.getType());
//                }
//                break;
//            case ENTITY_VIEW:
//                EntityView entityView = entityViewService.findEntityViewById(tenantId, new EntityViewId(entityId.getId()));
//                if (entityView != null) {
//                    metaData.putValue("entityViewName", entityView.getName());
//                    metaData.putValue("entityViewType", entityView.getType());
//                }
//                break;
//            default:
//                log.debug("Using empty metadata for entityId [{}]", entityId);
//                break;
//        }
//        return metaData;
//    }
//
//    private Pair<String, RuleChainId> getDefaultQueueNameAndRuleChainId(TenantId tenantId, EntityId entityId) {
//        if (EntityType.DEVICE.equals(entityId.getEntityType())) {
//            DeviceProfile deviceProfile = deviceProfileCache.get(tenantId, new DeviceId(entityId.getId()));
//            RuleChainId ruleChainId;
//            String queueName;
//
//            if (deviceProfile == null) {
//                log.warn("[{}] Device profile is null!", entityId);
//                ruleChainId = null;
//                queueName = ServiceQueue.MAIN;
//            } else {
//                ruleChainId = deviceProfile.getDefaultRuleChainId();
//                String defaultQueueName = deviceProfile.getDefaultQueueName();
//                queueName = defaultQueueName != null ? defaultQueueName : ServiceQueue.MAIN;
//            }
//            return new ImmutablePair<>(queueName, ruleChainId);
//        } else {
//            return new ImmutablePair<>(ServiceQueue.MAIN, null);
//        }
//    }
//
//    private ListenableFuture<Void> processPostTelemetry(TenantId tenantId, CustomerId customerId, EntityId entityId, TransportProtos.PostTelemetryMsg msg, TbMsgMetaData metaData) {
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        for (TransportProtos.TsKvListProto tsKv : msg.getTsKvListList()) {
//            JsonObject json = JsonUtils.getJsonObject(tsKv.getKvList());
//            metaData.putValue("ts", tsKv.getTs() + "");
//            Pair<String, RuleChainId> defaultQueueAndRuleChain = getDefaultQueueNameAndRuleChainId(tenantId, entityId);
//            String queueName = defaultQueueAndRuleChain.getKey();
//            RuleChainId ruleChainId = defaultQueueAndRuleChain.getValue();
//            TbMsg tbMsg = TbMsg.newMsg(queueName, SessionMsgType.POST_TELEMETRY_REQUEST.name(), entityId, customerId, metaData, gson.toJson(json), ruleChainId, null);
//            tbClusterService.pushMsgToRuleEngine(tenantId, tbMsg.getOriginator(), tbMsg, new TbQueueCallback() {
//                @Override
//                public void onSuccess(TbQueueMsgMetadata metadata) {
//                    futureToSet.set(null);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error("Can't process post telemetry [{}]", msg, t);
//                    futureToSet.setException(t);
//                }
//            });
//        }
//        return futureToSet;
//    }
//
//    private ListenableFuture<Void> processPostAttributes(TenantId tenantId, CustomerId customerId, EntityId entityId, TransportProtos.PostAttributeMsg msg, TbMsgMetaData metaData) {
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        JsonObject json = JsonUtils.getJsonObject(msg.getKvList());
//        Pair<String, RuleChainId> defaultQueueAndRuleChain = getDefaultQueueNameAndRuleChainId(tenantId, entityId);
//        String queueName = defaultQueueAndRuleChain.getKey();
//        RuleChainId ruleChainId = defaultQueueAndRuleChain.getValue();
//        TbMsg tbMsg = TbMsg.newMsg(queueName, SessionMsgType.POST_ATTRIBUTES_REQUEST.name(), entityId, customerId, metaData, gson.toJson(json), ruleChainId, null);
//        tbClusterService.pushMsgToRuleEngine(tenantId, tbMsg.getOriginator(), tbMsg, new TbQueueCallback() {
//            @Override
//            public void onSuccess(TbQueueMsgMetadata metadata) {
//                futureToSet.set(null);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("Can't process post attributes [{}]", msg, t);
//                futureToSet.setException(t);
//            }
//        });
//        return futureToSet;
//    }
//
//    private ListenableFuture<Void> processAttributesUpdate(TenantId tenantId, CustomerId customerId, EntityId entityId, TransportProtos.PostAttributeMsg msg, TbMsgMetaData metaData) {
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        JsonObject json = JsonUtils.getJsonObject(msg.getKvList());
//        Set<AttributeKvEntry> attributes = JsonConverter.convertToAttributes(json);
//        ListenableFuture<List<Void>> future = attributesService.save(tenantId, entityId, metaData.getValue("scope"), new ArrayList<>(attributes));
//        Futures.addCallback(future, new FutureCallback<List<Void>>() {
//            @Override
//            public void onSuccess(@Nullable List<Void> voids) {
//                Pair<String, RuleChainId> defaultQueueAndRuleChain = getDefaultQueueNameAndRuleChainId(tenantId, entityId);
//                String queueName = defaultQueueAndRuleChain.getKey();
//                RuleChainId ruleChainId = defaultQueueAndRuleChain.getValue();
//                TbMsg tbMsg = TbMsg.newMsg(queueName, DataConstants.ATTRIBUTES_UPDATED, entityId, customerId, metaData, gson.toJson(json), ruleChainId, null);
//                tbClusterService.pushMsgToRuleEngine(tenantId, tbMsg.getOriginator(), tbMsg, new TbQueueCallback() {
//                    @Override
//                    public void onSuccess(TbQueueMsgMetadata metadata) {
//                        futureToSet.set(null);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        log.error("Can't process attributes update [{}]", msg, t);
//                        futureToSet.setException(t);
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("Can't process attributes update [{}]", msg, t);
//                futureToSet.setException(t);
//            }
//        }, dbCallbackExecutorService);
//        return futureToSet;
//    }
//
//    private ListenableFuture<Void> processAttributeDeleteMsg(TenantId tenantId, EntityId entityId, AttributeDeleteMsg attributeDeleteMsg, String entityType) {
//        SettableFuture<Void> futureToSet = SettableFuture.create();
//        String scope = attributeDeleteMsg.getScope();
//        List<String> attributeNames = attributeDeleteMsg.getAttributeNamesList();
//        attributesService.removeAll(tenantId, entityId, scope, attributeNames);
//        if (EntityType.DEVICE.name().equals(entityType)) {
//            Set<AttributeKey> attributeKeys = new HashSet<>();
//            for (String attributeName : attributeNames) {
//                attributeKeys.add(new AttributeKey(scope, attributeName));
//            }
//            tbClusterService.pushMsgToCore(DeviceAttributesEventNotificationMsg.onDelete(
//                    tenantId, (DeviceId) entityId, attributeKeys), new TbQueueCallback() {
//                @Override
//                public void onSuccess(TbQueueMsgMetadata metadata) {
//                    futureToSet.set(null);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error("Can't process attribute delete msg [{}]", attributeDeleteMsg, t);
//                    futureToSet.setException(t);
//                }
//            });
//        }
//        return futureToSet;
//    }
//
//    private EntityId constructEntityId(EntityDataProto entityData) {
//        EntityType entityType = EntityType.valueOf(entityData.getEntityType());
//        switch (entityType) {
//            case DEVICE:
//                return new DeviceId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            case ASSET:
//                return new AssetId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            case ENTITY_VIEW:
//                return new EntityViewId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            case DASHBOARD:
//                return new DashboardId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            case TENANT:
//                return new TenantId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            case CUSTOMER:
//                return new CustomerId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            case USER:
//                return new UserId(new UUID(entityData.getEntityIdMSB(), entityData.getEntityIdLSB()));
//            default:
//                log.warn("Unsupported entity type [{}] during construct of entity id. EntityDataProto [{}]", entityData.getEntityType(), entityData);
//                return null;
//        }
//    }
//
//    public DownlinkMsg processTelemetryMessageToEdge(EdgeEvent edgeEvent) throws JsonProcessingException {
//        EntityId entityId;
//        switch (edgeEvent.getType()) {
//            case DEVICE:
//                entityId = new DeviceId(edgeEvent.getEntityId());
//                break;
//            case ASSET:
//                entityId = new AssetId(edgeEvent.getEntityId());
//                break;
//            case ENTITY_VIEW:
//                entityId = new EntityViewId(edgeEvent.getEntityId());
//                break;
//            case DASHBOARD:
//                entityId = new DashboardId(edgeEvent.getEntityId());
//                break;
//            case TENANT:
//                entityId = new TenantId(edgeEvent.getEntityId());
//                break;
//            case CUSTOMER:
//                entityId = new CustomerId(edgeEvent.getEntityId());
//                break;
//            case EDGE:
//                entityId = new EdgeId(edgeEvent.getEntityId());
//                break;
//            default:
//                log.warn("Unsupported edge event type [{}]", edgeEvent);
//                return null;
//        }
//        return constructEntityDataProtoMsg(entityId, edgeEvent.getAction(), JsonUtils.parse(mapper.writeValueAsString(edgeEvent.getBody())));
//    }
//
//    private DownlinkMsg constructEntityDataProtoMsg(EntityId entityId, EdgeEventActionType actionType, JsonElement entityData) {
//        EntityDataProto entityDataProto = entityDataMsgConstructor.constructEntityDataMsg(entityId, actionType, entityData);
//        return DownlinkMsg.newBuilder()
//                .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                .addEntityData(entityDataProto)
//                .build();
//    }

}
