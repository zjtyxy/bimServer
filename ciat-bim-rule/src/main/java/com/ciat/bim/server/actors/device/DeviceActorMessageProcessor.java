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
package com.ciat.bim.server.actors.device;

import com.ciat.bim.common.util.LinkedHashMapRemoveEldest;
import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.EdgeId;
import com.ciat.bim.data.id.RpcId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.data.security.DeviceCredentialsType;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.rule.engine.api.msg.DeviceAttributesEventNotificationMsg;
import com.ciat.bim.rule.engine.api.msg.DeviceEdgeUpdateMsg;
import com.ciat.bim.rule.engine.api.msg.DeviceNameOrTypeUpdateMsg;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActorCtx;
import com.ciat.bim.server.actors.shared.AbstractContextAwareMsgProcessor;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.common.data.edge.EdgeEventType;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.common.data.relation.EntityRelation;
import com.ciat.bim.server.common.data.relation.RelationTypeGroup;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.common.msg.rpc.FromDeviceRpcResponse;
import com.ciat.bim.server.common.msg.rpc.RpcError;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequest;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequestBody;
import com.ciat.bim.server.common.msg.timeout.DeviceActorServerSideRpcTimeoutMsg;
import com.ciat.bim.server.edge.EdgeEvent;
import com.ciat.bim.server.rpc.*;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.server.transport.TransportToDeviceActorMsgWrapper;
import com.ciat.bim.server.utils.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.InvalidProtocolBufferException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceCredentials;


import javax.annotation.Nullable;
import javax.swing.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;


/**
 * @author Andrew Shvayka
 */
@Slf4j
class DeviceActorMessageProcessor extends AbstractContextAwareMsgProcessor {

    final TenantId tenantId;
    final DeviceId deviceId;
    final LinkedHashMapRemoveEldest<UUID, SessionInfoMetaData> sessions;
    private final Map<UUID, SessionInfo> attributeSubscriptions;
    private final Map<UUID, SessionInfo> rpcSubscriptions;
    private final Map<Integer, ToDeviceRpcRequestMetadata> toDeviceRpcPendingMap;
    private final boolean rpcSequential;

    private int rpcSeq = 0;
    private String deviceName;
    private String deviceType;
    private TbMsgMetaData defaultMetaData;
    private EdgeId edgeId;

    DeviceActorMessageProcessor(ActorSystemContext systemContext, TenantId tenantId, DeviceId deviceId) {
        super(systemContext);
        this.tenantId = tenantId;
        this.deviceId = deviceId;
        this.rpcSequential = systemContext.isRpcSequential();
        this.attributeSubscriptions = new HashMap<>();
        this.rpcSubscriptions = new HashMap<>();
        this.toDeviceRpcPendingMap = new LinkedHashMap<>();
        this.sessions = new LinkedHashMapRemoveEldest<>(systemContext.getMaxConcurrentSessionsPerDevice(), this::notifyTransportAboutClosedSessionMaxSessionsLimit);
        if (initAttributes()) {
            restoreSessions();
        }
    }

    boolean initAttributes() {
        Device device = systemContext.getDeviceService().getById(deviceId);
        if (device != null) {
            this.deviceName = device.getName();
            this.deviceType = device.getType();
            this.defaultMetaData = new TbMsgMetaData();
            this.defaultMetaData.putValue("deviceName", deviceName);
            this.defaultMetaData.putValue("deviceType", deviceType);
            if (systemContext.isEdgesEnabled()) {
                this.edgeId = findRelatedEdgeId();
            }
            return true;
        } else {
            return false;
        }
    }

    private EdgeId findRelatedEdgeId() {
//        List<EntityRelation> result =
//                systemContext.getRelationService().findByToAndType(tenantId, deviceId, EntityRelation.EDGE_TYPE, RelationTypeGroup.COMMON);
//        if (result != null && result.size() > 0) {
//            EntityRelation relationToEdge = result.get(0);
//            if (relationToEdge.getFrom() != null && relationToEdge.getFrom().getId() != null) {
//                log.trace("[{}][{}] found edge [{}] for device", tenantId, deviceId, relationToEdge.getFrom().getId());
//                return new EdgeId(relationToEdge.getFrom().getId());
//            } else {
//                log.trace("[{}][{}] edge relation is empty {}", tenantId, deviceId, relationToEdge);
//            }
//        } else {
//            log.trace("[{}][{}] device doesn't have any related edge", tenantId, deviceId);
//        }
        return null;
    }

    void processRpcRequest(TbActorCtx context, ToDeviceRpcRequestActorMsg msg) {
        ToDeviceRpcRequest request = msg.getMsg();
        TransportProtos.ToDeviceRpcRequestMsg rpcRequest = creteToDeviceRpcRequestMsg(request);

        long timeout = request.getExpirationTime() - System.currentTimeMillis();
        boolean persisted = request.isPersisted();

        if (timeout <= 0) {
            log.debug("[{}][{}] Ignoring message due to exp time reached, {}", deviceId, request.getId(), request.getExpirationTime());
            if (persisted) {
                createRpc(request, RpcStatus.EXPIRED);
            }
            return;
        } else if (persisted) {
            createRpc(request, RpcStatus.QUEUED);
        }

        boolean sent = false;
        if (systemContext.isEdgesEnabled() && edgeId != null) {
            log.debug("[{}][{}] device is related to edge [{}]. Saving RPC request to edge queue", tenantId, deviceId, edgeId.getId());
            saveRpcRequestToEdgeQueue(request, rpcRequest.getRequestId());
            sent = true;
        } else if (isSendNewRpcAvailable()) {
            sent = rpcSubscriptions.size() > 0;
            Set<UUID> syncSessionSet = new HashSet<>();
            rpcSubscriptions.forEach((key, value) -> {
                sendToTransport(rpcRequest, key, value.getNodeId());
                if (SessionType.SYNC == value.getType()) {
                    syncSessionSet.add(key);
                }
            });
            log.trace("Rpc syncSessionSet [{}] subscription after sent [{}]", syncSessionSet, rpcSubscriptions);
            syncSessionSet.forEach(rpcSubscriptions::remove);
        }

//        if (persisted) {
//            ObjectNode response = JacksonUtil.newObjectNode();
//            response.put("rpcId", request.getId().toString());
//            systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(new FromDeviceRpcResponse(msg.getMsg().getId(), JacksonUtil.toString(response), null));
//        }
//
//        if (!persisted && request.isOneway() && sent) {
//            log.debug("[{}] Rpc command response sent [{}]!", deviceId, request.getId());
//            systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(new FromDeviceRpcResponse(msg.getMsg().getId(), null, null));
//        } else {
//            registerPendingRpcRequest(context, msg, sent, rpcRequest, timeout);
//        }
        if (sent) {
            log.debug("[{}] RPC request {} is sent!", deviceId, request.getId());
        } else {
            log.debug("[{}] RPC request {} is NOT sent!", deviceId, request.getId());
        }
    }

    private boolean isSendNewRpcAvailable() {
        return  false;
       // return !rpcSequential || toDeviceRpcPendingMap.values().stream().filter(md -> !md.isDelivered()).findAny().isEmpty();
    }

    private Rpc createRpc(ToDeviceRpcRequest request, RpcStatus status) {
        Rpc rpc = new Rpc(new RpcId(request.getId().toString()));
        rpc.setCreatedTime(System.currentTimeMillis());
        rpc.setTenantId(tenantId);
        rpc.setDeviceId(deviceId);
        rpc.setExpirationTime(request.getExpirationTime());
        rpc.setRequest(JacksonUtil.valueToTree(request));
        rpc.setStatus(status);
        rpc.setAdditionalInfo(JacksonUtil.toJsonNode(request.getAdditionalInfo()));
        return rpc;
       // return systemContext.getTbRpcService().save(tenantId, rpc);
    }

    private ToDeviceRpcRequestMsg creteToDeviceRpcRequestMsg(ToDeviceRpcRequest request) {
        ToDeviceRpcRequestBody body = request.getBody();
        return ToDeviceRpcRequestMsg.newBuilder()
                .setRequestId(rpcSeq++)
                .setMethodName(body.getMethod())
                .setParams(body.getParams())
                .setExpirationTime(request.getExpirationTime())
                .setRequestIdMSB(request.getId().getMostSignificantBits())
                .setRequestIdLSB(request.getId().getLeastSignificantBits())
                .setOneway(request.isOneway())
                .setPersisted(request.isPersisted())
                .build();
    }

    void processRpcResponsesFromEdge(TbActorCtx context, FromDeviceRpcResponseActorMsg responseMsg) {
//        log.debug("[{}] Processing rpc command response from edge session", deviceId);
//        ToDeviceRpcRequestMetadata requestMd = toDeviceRpcPendingMap.remove(responseMsg.getRequestId());
//        boolean success = requestMd != null;
//        if (success) {
//            systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(responseMsg.getMsg());
//        } else {
//            log.debug("[{}] Rpc command response [{}] is stale!", deviceId, responseMsg.getRequestId());
//        }
    }

    void processRemoveRpc(TbActorCtx context, RemoveRpcActorMsg msg) {
        log.debug("[{}] Processing remove rpc command", msg.getRequestId());
        Map.Entry<Integer, ToDeviceRpcRequestMetadata> entry = null;
        for (Map.Entry<Integer, ToDeviceRpcRequestMetadata> e : toDeviceRpcPendingMap.entrySet()) {
            if (e.getValue().getMsg().getMsg().getId().equals(msg.getRequestId())) {
                entry = e;
                break;
            }
        }

        if (entry != null) {
            if (entry.getValue().isDelivered()) {
                toDeviceRpcPendingMap.remove(entry.getKey());
            } else {
                Optional<Map.Entry<Integer, ToDeviceRpcRequestMetadata>> firstRpc = getFirstRpc();
                if (firstRpc.isPresent() && entry.getKey().equals(firstRpc.get().getKey())) {
                    toDeviceRpcPendingMap.remove(entry.getKey());
                    sendNextPendingRequest(context);
                } else {
                    toDeviceRpcPendingMap.remove(entry.getKey());
                }
            }
        }
    }

    private void registerPendingRpcRequest(TbActorCtx context, ToDeviceRpcRequestActorMsg msg, boolean sent, ToDeviceRpcRequestMsg rpcRequest, long timeout) {
        toDeviceRpcPendingMap.put(rpcRequest.getRequestId(), new ToDeviceRpcRequestMetadata(msg, sent));
        DeviceActorServerSideRpcTimeoutMsg timeoutMsg = new DeviceActorServerSideRpcTimeoutMsg(rpcRequest.getRequestId(), timeout);
        scheduleMsgWithDelay(context, timeoutMsg, timeoutMsg.getTimeout());
    }

    void processServerSideRpcTimeout(TbActorCtx context, DeviceActorServerSideRpcTimeoutMsg msg) {
//        ToDeviceRpcRequestMetadata requestMd = toDeviceRpcPendingMap.remove(msg.getId());
//        if (requestMd != null) {
//            log.debug("[{}] RPC request [{}] timeout detected!", deviceId, msg.getId());
//            if (requestMd.getMsg().getMsg().isPersisted()) {
//                systemContext.getTbRpcService().save(tenantId, new RpcId(requestMd.getMsg().getMsg().getId()), RpcStatus.EXPIRED, null);
//            }
//            systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(new FromDeviceRpcResponse(requestMd.getMsg().getMsg().getId(),
//                    null, requestMd.isSent() ? RpcError.TIMEOUT : RpcError.NO_ACTIVE_CONNECTION));
//            if (!requestMd.isDelivered()) {
//                sendNextPendingRequest(context);
//            }
//        }
    }

    private void sendPendingRequests(TbActorCtx context, UUID sessionId, String nodeId) {
        SessionType sessionType = getSessionType(sessionId);
        if (!toDeviceRpcPendingMap.isEmpty()) {
            log.debug("[{}] Pushing {} pending RPC messages to new async session [{}]", deviceId, toDeviceRpcPendingMap.size(), sessionId);
            if (sessionType == SessionType.SYNC) {
                log.debug("[{}] Cleanup sync rpc session [{}]", deviceId, sessionId);
                rpcSubscriptions.remove(sessionId);
            }
        } else {
            log.debug("[{}] No pending RPC messages for new async session [{}]", deviceId, sessionId);
        }
        Set<Integer> sentOneWayIds = new HashSet<>();

        if (rpcSequential) {
            getFirstRpc().ifPresent(processPendingRpc(context, sessionId, nodeId, sentOneWayIds));
        } else if (sessionType == SessionType.ASYNC) {
            toDeviceRpcPendingMap.entrySet().forEach(processPendingRpc(context, sessionId, nodeId, sentOneWayIds));
        } else {
            toDeviceRpcPendingMap.entrySet().stream().findFirst().ifPresent(processPendingRpc(context, sessionId, nodeId, sentOneWayIds));
        }

        sentOneWayIds.stream().filter(id -> !toDeviceRpcPendingMap.get(id).getMsg().getMsg().isPersisted()).forEach(toDeviceRpcPendingMap::remove);
    }

    private Optional<Map.Entry<Integer, ToDeviceRpcRequestMetadata>> getFirstRpc() {
        return toDeviceRpcPendingMap.entrySet().stream().filter(e -> !e.getValue().isDelivered()).findFirst();
    }

    private void sendNextPendingRequest(TbActorCtx context) {
        if (rpcSequential) {
            rpcSubscriptions.forEach((id, s) -> sendPendingRequests(context, id, s.getNodeId()));
        }
    }

    private Consumer<Map.Entry<Integer, ToDeviceRpcRequestMetadata>> processPendingRpc(TbActorCtx context, UUID sessionId, String nodeId, Set<Integer> sentOneWayIds) {
        return entry -> {
            ToDeviceRpcRequest request = entry.getValue().getMsg().getMsg();
            ToDeviceRpcRequestBody body = request.getBody();
//            if (request.isOneway() && !rpcSequential) {
//                sentOneWayIds.add(entry.getKey());
//                systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(new FromDeviceRpcResponse(request.getId(), null, null));
//            }
            ToDeviceRpcRequestMsg rpcRequest = ToDeviceRpcRequestMsg.newBuilder()
                    .setRequestId(entry.getKey())
                    .setMethodName(body.getMethod())
                    .setParams(body.getParams())
                    .setExpirationTime(request.getExpirationTime())
                    .setRequestIdMSB(request.getId().getMostSignificantBits())
                    .setRequestIdLSB(request.getId().getLeastSignificantBits())
                    .setOneway(request.isOneway())
                    .setPersisted(request.isPersisted())
                    .build();
            sendToTransport(rpcRequest, sessionId, nodeId);
        };
    }

    void process(TbActorCtx context, TransportToDeviceActorMsgWrapper wrapper) {
        TransportToDeviceActorMsg msg = wrapper.getMsg();
        TbCallback callback = wrapper.getCallback();
        SessionInfoProto sessionInfo = msg.getSessionInfo();

        if (msg.hasSessionEvent()) {
            processSessionStateMsgs(sessionInfo, msg.getSessionEvent());
        }
        if (msg.hasSubscribeToAttributes()) {
            processSubscriptionCommands(context, sessionInfo, msg.getSubscribeToAttributes());
        }
        if (msg.hasSubscribeToRPC()) {
            processSubscriptionCommands(context, sessionInfo, msg.getSubscribeToRPC());
        }
        if (msg.hasSendPendingRPC()) {
            sendPendingRequests(context, getSessionId(sessionInfo), sessionInfo.getNodeId());
        }
        if (msg.hasGetAttributes()) {
            handleGetAttributesRequest(context, sessionInfo, msg.getGetAttributes());
        }
        if (msg.hasToDeviceRPCCallResponse()) {
            processRpcResponses(context, sessionInfo, msg.getToDeviceRPCCallResponse());
        }
        if (msg.hasSubscriptionInfo()) {
            handleSessionActivity(context, sessionInfo, msg.getSubscriptionInfo());
        }
        if (msg.hasClaimDevice()) {
            handleClaimDeviceMsg(context, sessionInfo, msg.getClaimDevice());
        }
        if (msg.hasRpcResponseStatusMsg()) {
            processRpcResponseStatus(context, sessionInfo, msg.getRpcResponseStatusMsg());
        }
        if (msg.hasUplinkNotificationMsg()) {
            processUplinkNotificationMsg(context, sessionInfo, msg.getUplinkNotificationMsg());
        }
        callback.onSuccess();
    }

    private void processUplinkNotificationMsg(TbActorCtx context, SessionInfoProto sessionInfo, TransportProtos.UplinkNotificationMsg uplinkNotificationMsg) {
//        String nodeId = sessionInfo.getNodeId();
//        sessions.entrySet().stream()
//                .filter(kv -> kv.getValue().getSessionInfo().getNodeId().equals(nodeId) && (kv.getValue().isSubscribedToAttributes() || kv.getValue().isSubscribedToRPC()))
//                .forEach(kv -> {
//                    ToTransportMsg msg = ToTransportMsg.newBuilder()
//                            .setSessionIdMSB(kv.getKey().getMostSignificantBits())
//                            .setSessionIdLSB(kv.getKey().getLeastSignificantBits())
//                            .setUplinkNotificationMsg(uplinkNotificationMsg)
//                            .build();
//                    systemContext.getTbCoreToTransportService().process(kv.getValue().getSessionInfo().getNodeId(), msg);
//                });
    }

    private void handleClaimDeviceMsg(TbActorCtx context, SessionInfoProto sessionInfo, ClaimDeviceMsg msg) {
//        DeviceId deviceId = new DeviceId(new UUID(msg.getDeviceIdMSB(), msg.getDeviceIdLSB()));
//        systemContext.getClaimDevicesService().registerClaimingInfo(tenantId, deviceId, msg.getSecretKey(), msg.getDurationMs());
    }

    private void reportSessionOpen() {
        systemContext.getDeviceStateService().onDeviceConnect(tenantId.getId(), deviceId);
    }

    private void reportSessionClose() {
        systemContext.getDeviceStateService().onDeviceDisconnect(tenantId.getId(), deviceId);
    }

    private void handleGetAttributesRequest(TbActorCtx context, SessionInfoProto sessionInfo, GetAttributeRequestMsg request) {
        int requestId = request.getRequestId();
        if (request.getOnlyShared()) {
            Futures.addCallback(findAllAttributesByScope(DataConstants.SHARED_SCOPE), new FutureCallback<>() {
                @Override
                public void onSuccess(@Nullable List<AttributeKv> result) {
                    GetAttributeResponseMsg responseMsg = GetAttributeResponseMsg.newBuilder()
                            .setRequestId(requestId)
                            .setSharedStateMsg(true)
                            .addAllSharedAttributeList(toTsKvProtos(result))
                            .build();
                    sendToTransport(responseMsg, sessionInfo);
                }

                @Override
                public void onFailure(Throwable t) {
                    GetAttributeResponseMsg responseMsg = GetAttributeResponseMsg.newBuilder()
                            .setError(t.getMessage())
                            .setSharedStateMsg(true)
                            .build();
                    sendToTransport(responseMsg, sessionInfo);
                }
            }, MoreExecutors.directExecutor());
        } else {
            Futures.addCallback(getAttributesKvEntries(request), new FutureCallback<>() {
                @Override
                public void onSuccess(@Nullable List<List<AttributeKv>> result) {
                    GetAttributeResponseMsg responseMsg = GetAttributeResponseMsg.newBuilder()
                            .setRequestId(requestId)
                            .addAllClientAttributeList(toTsKvProtos(result.get(0)))
                            .addAllSharedAttributeList(toTsKvProtos(result.get(1)))
                            .build();
                    sendToTransport(responseMsg, sessionInfo);
                }

                @Override
                public void onFailure(Throwable t) {
                    GetAttributeResponseMsg responseMsg = GetAttributeResponseMsg.newBuilder()
                            .setError(t.getMessage())
                            .build();
                    sendToTransport(responseMsg, sessionInfo);
                }
            }, MoreExecutors.directExecutor());
        }
    }

    private ListenableFuture<List<List<AttributeKv>>> getAttributesKvEntries(GetAttributeRequestMsg request) {
        ListenableFuture<List<AttributeKv>> clientAttributesFuture;
        ListenableFuture<List<AttributeKv>> sharedAttributesFuture;
        if (CollectionUtils.isEmpty(request.getClientAttributeNamesList()) && CollectionUtils.isEmpty(request.getSharedAttributeNamesList())) {
            clientAttributesFuture = findAllAttributesByScope(DataConstants.CLIENT_SCOPE);
            sharedAttributesFuture = findAllAttributesByScope(DataConstants.SHARED_SCOPE);
        } else if (!CollectionUtils.isEmpty(request.getClientAttributeNamesList()) && !CollectionUtils.isEmpty(request.getSharedAttributeNamesList())) {
            clientAttributesFuture = findAttributesByScope(toSet(request.getClientAttributeNamesList()), DataConstants.CLIENT_SCOPE);
            sharedAttributesFuture = findAttributesByScope(toSet(request.getSharedAttributeNamesList()), DataConstants.SHARED_SCOPE);
        } else if (CollectionUtils.isEmpty(request.getClientAttributeNamesList()) && !CollectionUtils.isEmpty(request.getSharedAttributeNamesList())) {
            clientAttributesFuture = Futures.immediateFuture(Collections.emptyList());
            sharedAttributesFuture = findAttributesByScope(toSet(request.getSharedAttributeNamesList()), DataConstants.SHARED_SCOPE);
        } else {
            sharedAttributesFuture = Futures.immediateFuture(Collections.emptyList());
            clientAttributesFuture = findAttributesByScope(toSet(request.getClientAttributeNamesList()), DataConstants.CLIENT_SCOPE);
        }
        return Futures.allAsList(Arrays.asList(clientAttributesFuture, sharedAttributesFuture));
    }

    private ListenableFuture<List<AttributeKv>> findAllAttributesByScope(String scope) {
        return systemContext.getAttributesService().findAll(tenantId, deviceId, scope);
    }

    private ListenableFuture<List<AttributeKv>> findAttributesByScope(Set<String> attributesSet, String scope) {
        return systemContext.getAttributesService().find(tenantId.getId(), deviceId, scope, attributesSet);
    }

    private Set<String> toSet(List<String> strings) {
        return new HashSet<>(strings);
    }

    private SessionType getSessionType(UUID sessionId) {
        return sessions.containsKey(sessionId) ? SessionType.ASYNC : SessionType.SYNC;
    }

    void processAttributesUpdate(TbActorCtx context, DeviceAttributesEventNotificationMsg msg) {
        if (attributeSubscriptions.size() > 0) {
            boolean hasNotificationData = false;
            AttributeUpdateNotificationMsg.Builder notification = AttributeUpdateNotificationMsg.newBuilder();
            if (msg.isDeleted()) {
//                List<String> sharedKeys = msg.getDeletedKeys().stream()
//                        .filter(key -> DataConstants.SHARED_SCOPE.equals(key.getScope()))
//                        .map(AttributeKv::getAttributeKey)
//                        .collect(Collectors.toList());
//                if (!sharedKeys.isEmpty()) {
//                    notification.addAllSharedDeleted(sharedKeys);
//                    hasNotificationData = true;
//                }
            } else {
                if (DataConstants.SHARED_SCOPE.equals(msg.getScope())) {
                    List<AttributeKv> attributes = new ArrayList<>(msg.getValues());
                    if (attributes.size() > 0) {
                        List<TsKvProto> sharedUpdated = msg.getValues().stream().map(this::toTsKvProto)
                                .collect(Collectors.toList());
                        if (!sharedUpdated.isEmpty()) {
                            notification.addAllSharedUpdated(sharedUpdated);
                            hasNotificationData = true;
                        }
                    } else {
                        log.debug("[{}] No public shared side attributes changed!", deviceId);
                    }
                }
            }
            if (hasNotificationData) {
                AttributeUpdateNotificationMsg finalNotification = notification.build();
                attributeSubscriptions.forEach((key, value) -> sendToTransport(finalNotification, key, value.getNodeId()));
            }
        } else {
            log.debug("[{}] No registered attributes subscriptions to process!", deviceId);
        }
    }

    private void processRpcResponses(TbActorCtx context, SessionInfoProto sessionInfo, ToDeviceRpcResponseMsg responseMsg) {
//        UUID sessionId = getSessionId(sessionInfo);
//        log.debug("[{}] Processing rpc command response [{}]", deviceId, sessionId);
//        ToDeviceRpcRequestMetadata requestMd = toDeviceRpcPendingMap.remove(responseMsg.getRequestId());
//        boolean success = requestMd != null;
//        if (success) {
//            boolean hasError = StringUtils.isNotEmpty(responseMsg.getError());
//            try {
//                String payload = hasError ? responseMsg.getError() : responseMsg.getPayload();
//                systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(
//                        new FromDeviceRpcResponse(requestMd.getMsg().getMsg().getId(),
//                                payload, null));
//                if (requestMd.getMsg().getMsg().isPersisted()) {
//                    RpcStatus status = hasError ? RpcStatus.FAILED : RpcStatus.SUCCESSFUL;
//                    JsonNode response;
//                    try {
//                        response = JacksonUtil.toJsonNode(payload);
//                    } catch (IllegalArgumentException e) {
//                        response = JacksonUtil.newObjectNode().put("error", payload);
//                    }
//                    systemContext.getTbRpcService().save(tenantId, new RpcId(requestMd.getMsg().getMsg().getId()), status, response);
//                }
//            } finally {
//                if (hasError && !requestMd.isDelivered()) {
//                    sendNextPendingRequest(context);
//                }
//            }
//        } else {
//            log.debug("[{}] Rpc command response [{}] is stale!", deviceId, responseMsg.getRequestId());
//        }
    }

    private void processRpcResponseStatus(TbActorCtx context, SessionInfoProto sessionInfo, ToDeviceRpcResponseStatusMsg responseMsg) {
//        UUID rpcId = new UUID(responseMsg.getRequestIdMSB(), responseMsg.getRequestIdLSB());
//        RpcStatus status = RpcStatus.valueOf(responseMsg.getStatus());
//        ToDeviceRpcRequestMetadata md = toDeviceRpcPendingMap.get(responseMsg.getRequestId());
//
//        if (md != null) {
//            if (status.equals(RpcStatus.DELIVERED)) {
//                if (md.getMsg().getMsg().isOneway()) {
//                    toDeviceRpcPendingMap.remove(responseMsg.getRequestId());
//                    if (rpcSequential) {
//                        systemContext.getTbCoreDeviceRpcService().processRpcResponseFromDeviceActor(new FromDeviceRpcResponse(rpcId, null, null));
//                    }
//                } else {
//                    md.setDelivered(true);
//                }
//            } else if (status.equals(RpcStatus.TIMEOUT)) {
//                Integer maxRpcRetries = md.getMsg().getMsg().getRetries();
//                maxRpcRetries = maxRpcRetries == null ? systemContext.getMaxRpcRetries() : Math.min(maxRpcRetries, systemContext.getMaxRpcRetries());
//                if (maxRpcRetries <= md.getRetries()) {
//                    toDeviceRpcPendingMap.remove(responseMsg.getRequestId());
//                    status = RpcStatus.FAILED;
//                } else {
//                    md.setRetries(md.getRetries() + 1);
//                }
//            }
//
//            if (md.getMsg().getMsg().isPersisted()) {
//                systemContext.getTbRpcService().save(tenantId, new RpcId(rpcId), status, null);
//            }
//            if (status != RpcStatus.SENT) {
//                sendNextPendingRequest(context);
//            }
//        } else {
//            log.info("[{}][{}] Rpc has already removed from pending map.", deviceId, rpcId);
//        }
    }

    private void processSubscriptionCommands(TbActorCtx context, SessionInfoProto sessionInfo, SubscribeToAttributeUpdatesMsg subscribeCmd) {
        UUID sessionId = getSessionId(sessionInfo);
        if (subscribeCmd.getUnsubscribe()) {
            log.debug("[{}] Canceling attributes subscription for session [{}]", deviceId, sessionId);
            attributeSubscriptions.remove(sessionId);
        } else {
            SessionInfoMetaData sessionMD = sessions.get(sessionId);
            if (sessionMD == null) {
                sessionMD = new SessionInfoMetaData(new SessionInfo(subscribeCmd.getSessionType(), sessionInfo.getNodeId()));
            }
            sessionMD.setSubscribedToAttributes(true);
            log.debug("[{}] Registering attributes subscription for session [{}]", deviceId, sessionId);
            attributeSubscriptions.put(sessionId, sessionMD.getSessionInfo());
            dumpSessions();
        }
    }

    private UUID getSessionId(SessionInfoProto sessionInfo) {
        return new UUID(sessionInfo.getSessionIdMSB(), sessionInfo.getSessionIdLSB());
    }

    private void processSubscriptionCommands(TbActorCtx context, SessionInfoProto sessionInfo, SubscribeToRPCMsg subscribeCmd) {
        UUID sessionId = getSessionId(sessionInfo);
        if (subscribeCmd.getUnsubscribe()) {
            log.debug("[{}] Canceling rpc subscription for session [{}]", deviceId, sessionId);
            rpcSubscriptions.remove(sessionId);
        } else {
            SessionInfoMetaData sessionMD = sessions.get(sessionId);
            if (sessionMD == null) {
                sessionMD = new SessionInfoMetaData(new SessionInfo(subscribeCmd.getSessionType(), sessionInfo.getNodeId()));
            }
            sessionMD.setSubscribedToRPC(true);
            log.debug("[{}] Registering rpc subscription for session [{}]", deviceId, sessionId);
            rpcSubscriptions.put(sessionId, sessionMD.getSessionInfo());
            sendPendingRequests(context, sessionId, sessionInfo.getNodeId());
            dumpSessions();
        }
    }

    private void processSessionStateMsgs(SessionInfoProto sessionInfo, SessionEventMsg msg) {
        UUID sessionId = getSessionId(sessionInfo);
        Objects.requireNonNull(sessionId);
        if (msg.getEvent() == SessionEvent.OPEN) {
            if (sessions.containsKey(sessionId)) {
                log.debug("[{}] Received duplicate session open event [{}]", deviceId, sessionId);
                return;
            }
            log.debug("[{}] Processing new session [{}]. Current sessions size {}", deviceId, sessionId, sessions.size());

            sessions.put(sessionId, new SessionInfoMetaData(new SessionInfo(SessionType.ASYNC, sessionInfo.getNodeId())));
            if (sessions.size() == 1) {
                reportSessionOpen();
            }
            systemContext.getDeviceStateService().onDeviceActivity(tenantId.getId(), deviceId, System.currentTimeMillis());
            dumpSessions();
        } else if (msg.getEvent() == SessionEvent.CLOSED) {
            log.debug("[{}] Canceling subscriptions for closed session [{}]", deviceId, sessionId);
            sessions.remove(sessionId);
            attributeSubscriptions.remove(sessionId);
            rpcSubscriptions.remove(sessionId);
            if (sessions.isEmpty()) {
                reportSessionClose();
            }
            dumpSessions();
        }
    }

    private void handleSessionActivity(TbActorCtx context, SessionInfoProto sessionInfoProto, SubscriptionInfoProto subscriptionInfo) {
        UUID sessionId = getSessionId(sessionInfoProto);
        Objects.requireNonNull(sessionId);

        SessionInfoMetaData sessionMD = sessions.get(sessionId);
        if (sessionMD != null) {
            sessionMD.setLastActivityTime(subscriptionInfo.getLastActivityTime());
            sessionMD.setSubscribedToAttributes(subscriptionInfo.getAttributeSubscription());
            sessionMD.setSubscribedToRPC(subscriptionInfo.getRpcSubscription());
            if (subscriptionInfo.getAttributeSubscription()) {
                attributeSubscriptions.putIfAbsent(sessionId, sessionMD.getSessionInfo());
            }
            if (subscriptionInfo.getRpcSubscription()) {
                rpcSubscriptions.putIfAbsent(sessionId, sessionMD.getSessionInfo());
            }
        }
        systemContext.getDeviceStateService().onDeviceActivity(tenantId.getId(), deviceId, subscriptionInfo.getLastActivityTime());
        if (sessionMD != null) {
            dumpSessions();
        }
    }

    void processCredentialsUpdate(TbActorMsg msg) {
//        if (((DeviceCredentialsUpdateNotificationMsg) msg).getDeviceCredentials().getCredentialsType() == DeviceCredentialsType.LWM2M_CREDENTIALS) {
//            sessions.forEach((k, v) -> {
//                notifyTransportAboutDeviceCredentialsUpdate(k, v, ((DeviceCredentialsUpdateNotificationMsg) msg).getDeviceCredentials());
//            });
//        } else {
//            sessions.forEach((sessionId, sessionMd) -> notifyTransportAboutClosedSession(sessionId, sessionMd, "device credentials updated!"));
//            attributeSubscriptions.clear();
//            rpcSubscriptions.clear();
//            dumpSessions();
//
//        }
    }

    private void notifyTransportAboutClosedSessionMaxSessionsLimit(UUID sessionId, SessionInfoMetaData sessionMd) {
        log.debug("remove eldest session (max concurrent sessions limit reached per device) sessionId [{}] sessionMd [{}]", sessionId, sessionMd);
        notifyTransportAboutClosedSession(sessionId, sessionMd, "max concurrent sessions limit reached per device!");
    }

    private void notifyTransportAboutClosedSession(UUID sessionId, SessionInfoMetaData sessionMd, String message) {
        SessionCloseNotificationProto sessionCloseNotificationProto = SessionCloseNotificationProto
                .newBuilder()
                .setMessage(message).build();
        ToTransportMsg msg = ToTransportMsg.newBuilder()
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setSessionCloseNotification(sessionCloseNotificationProto)
                .build();
       /// systemContext.getTbCoreToTransportService().process(sessionMd.getSessionInfo().getNodeId(), msg);
    }

    void notifyTransportAboutDeviceCredentialsUpdate(UUID sessionId, SessionInfoMetaData sessionMd, DeviceCredentials deviceCredentials) {
        ToTransportUpdateCredentialsProto.Builder notification = ToTransportUpdateCredentialsProto.newBuilder();
        notification.addCredentialsId(deviceCredentials.getCredentialsId());
        notification.addCredentialsValue(deviceCredentials.getCredentialsValue());
        ToTransportMsg msg = ToTransportMsg.newBuilder()
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setToTransportUpdateCredentialsNotification(notification).build();
      //  systemContext.getTbCoreToTransportService().process(sessionMd.getSessionInfo().getNodeId(), msg);
    }

    void processNameOrTypeUpdate(DeviceNameOrTypeUpdateMsg msg) {
        this.deviceName = msg.getDeviceName();
        this.deviceType = msg.getDeviceType();
        this.defaultMetaData = new TbMsgMetaData();
        this.defaultMetaData.putValue("deviceName", deviceName);
        this.defaultMetaData.putValue("deviceType", deviceType);
    }

    void processEdgeUpdate(DeviceEdgeUpdateMsg msg) {
        log.trace("[{}] Processing edge update {}", deviceId, msg);
        this.edgeId = msg.getEdgeId();
    }

    private void sendToTransport(GetAttributeResponseMsg responseMsg, SessionInfoProto sessionInfo) {
        ToTransportMsg msg = ToTransportMsg.newBuilder()
                .setSessionIdMSB(sessionInfo.getSessionIdMSB())
                .setSessionIdLSB(sessionInfo.getSessionIdLSB())
                .setGetAttributesResponse(responseMsg).build();
       // systemContext.getTbCoreToTransportService().process(sessionInfo.getNodeId(), msg);
    }

    private void sendToTransport(AttributeUpdateNotificationMsg notificationMsg, UUID sessionId, String nodeId) {
        ToTransportMsg msg = ToTransportMsg.newBuilder()
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setAttributeUpdateNotification(notificationMsg).build();
       // systemContext.getTbCoreToTransportService().process(nodeId, msg);
    }

    private void sendToTransport(ToDeviceRpcRequestMsg rpcMsg, UUID sessionId, String nodeId) {
        ToTransportMsg msg = ToTransportMsg.newBuilder()
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setToDeviceRequest(rpcMsg).build();
        systemContext.getTbCoreToTransportService().process(nodeId, msg);
    }

    private void sendToTransport(ToServerRpcResponseMsg rpcMsg, UUID sessionId, String nodeId) {
        ToTransportMsg msg = ToTransportMsg.newBuilder()
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setToServerResponse(rpcMsg).build();
        systemContext.getTbCoreToTransportService().process(nodeId, msg);
    }

    private void saveRpcRequestToEdgeQueue(ToDeviceRpcRequest msg, Integer requestId) {
        EdgeEvent edgeEvent = new EdgeEvent();
        edgeEvent.setTenantId(tenantId);
        edgeEvent.setAction(EdgeEventActionType.RPC_CALL);
        edgeEvent.setEntityId(deviceId.getId());
        edgeEvent.setType(EdgeEventType.DEVICE);

        ObjectNode body = mapper.createObjectNode();
        body.put("requestId", requestId);
        body.put("requestUUID", msg.getId().toString());
        body.put("oneway", msg.isOneway());
        body.put("expirationTime", msg.getExpirationTime());
        body.put("method", msg.getBody().getMethod());
        body.put("params", msg.getBody().getParams());
        edgeEvent.setBody(body);

//        edgeEvent.setEdgeId(edgeId);
//        systemContext.getEdgeEventService().save(edgeEvent);
//        systemContext.getClusterService().onEdgeEventUpdate(tenantId.getId(), edgeId);
    }

    private List<TsKvProto> toTsKvProtos(@Nullable List<AttributeKv> result) {
        List<TsKvProto> clientAttributes;
        if (result == null || result.isEmpty()) {
            clientAttributes = Collections.emptyList();
        } else {
            clientAttributes = new ArrayList<>(result.size());
            for (AttributeKv attrEntry : result) {
                clientAttributes.add(toTsKvProto(attrEntry));
            }
        }
        return clientAttributes;
    }

    private TsKvProto toTsKvProto(AttributeKv attrEntry) {
        return TsKvProto.newBuilder().setTs(attrEntry.getLastupdatets().getTime())
                .setKv(toKeyValueProto(attrEntry)).build();
    }

    private KeyValueProto toKeyValueProto(AttributeKv kvEntry) {
        KeyValueProto.Builder builder = KeyValueProto.newBuilder();
        builder.setKey(kvEntry.getKey());
        switch (kvEntry.getAttributeType()) {
            case BOOLEAN:
                builder.setType(KeyValueType.BOOLEAN_V);
                builder.setBoolV(kvEntry.getBooleanValue()==1);
                break;
            case DOUBLE:
                builder.setType(KeyValueType.DOUBLE_V);
                builder.setDoubleV(kvEntry.getDoubleValue());
                break;
            case LONG:
                builder.setType(KeyValueType.LONG_V);
                builder.setLongV(kvEntry.getLongValue());
                break;
            case STRING:
                builder.setType(KeyValueType.STRING_V);
                builder.setStringV(kvEntry.getStrValue());
                break;
            case JSON:
                builder.setType(KeyValueType.JSON_V);
                builder.setJsonV(kvEntry.getJsonValue());
                break;
        }
        return builder.build();
    }

    void restoreSessions() {
        log.debug("[{}] Restoring sessions from cache", deviceId);
        DeviceSessionsCacheEntry sessionsDump = null;
        try {
            sessionsDump = DeviceSessionsCacheEntry.parseFrom(systemContext.getDeviceSessionCacheService().get(deviceId));
        } catch (InvalidProtocolBufferException e) {
            log.warn("[{}] Failed to decode device sessions from cache", deviceId);
            return;
        }
        if (sessionsDump.getSessionsCount() == 0) {
            log.debug("[{}] No session information found", deviceId);
            return;
        }
        // TODO: Take latest max allowed sessions size from cache
        for (SessionSubscriptionInfoProto sessionSubscriptionInfoProto : sessionsDump.getSessionsList()) {
            SessionInfoProto sessionInfoProto = sessionSubscriptionInfoProto.getSessionInfo();
            UUID sessionId = getSessionId(sessionInfoProto);
            SessionInfo sessionInfo = new SessionInfo(SessionType.ASYNC, sessionInfoProto.getNodeId());
            SubscriptionInfoProto subInfo = sessionSubscriptionInfoProto.getSubscriptionInfo();
            SessionInfoMetaData sessionMD = new SessionInfoMetaData(sessionInfo, subInfo.getLastActivityTime());
            sessions.put(sessionId, sessionMD);
            if (subInfo.getAttributeSubscription()) {
                attributeSubscriptions.put(sessionId, sessionInfo);
                sessionMD.setSubscribedToAttributes(true);
            }
            if (subInfo.getRpcSubscription()) {
                rpcSubscriptions.put(sessionId, sessionInfo);
                sessionMD.setSubscribedToRPC(true);
            }
            log.debug("[{}] Restored session: {}", deviceId, sessionMD);
        }
        log.debug("[{}] Restored sessions: {}, rpc subscriptions: {}, attribute subscriptions: {}", deviceId, sessions.size(), rpcSubscriptions.size(), attributeSubscriptions.size());
    }

    private void dumpSessions() {
        log.debug("[{}] Dumping sessions: {}, rpc subscriptions: {}, attribute subscriptions: {} to cache", deviceId, sessions.size(), rpcSubscriptions.size(), attributeSubscriptions.size());
        List<SessionSubscriptionInfoProto> sessionsList = new ArrayList<>(sessions.size());
        sessions.forEach((uuid, sessionMD) -> {
            if (sessionMD.getSessionInfo().getType() == SessionType.SYNC) {
                return;
            }
            SessionInfo sessionInfo = sessionMD.getSessionInfo();
            SubscriptionInfoProto subscriptionInfoProto = SubscriptionInfoProto.newBuilder()
                    .setLastActivityTime(sessionMD.getLastActivityTime())
                    .setAttributeSubscription(sessionMD.isSubscribedToAttributes())
                    .setRpcSubscription(sessionMD.isSubscribedToRPC()).build();
            SessionInfoProto sessionInfoProto = SessionInfoProto.newBuilder()
                    .setSessionIdMSB(uuid.getMostSignificantBits())
                    .setSessionIdLSB(uuid.getLeastSignificantBits())
                    .setNodeId(sessionInfo.getNodeId()).build();
            sessionsList.add(SessionSubscriptionInfoProto.newBuilder()
                    .setSessionInfo(sessionInfoProto)
                    .setSubscriptionInfo(subscriptionInfoProto).build());
            log.debug("[{}] Dumping session: {}", deviceId, sessionMD);
        });
        systemContext.getDeviceSessionCacheService()
                .put(deviceId, DeviceSessionsCacheEntry.newBuilder()
                        .addAllSessions(sessionsList).build().toByteArray());
    }

    void init(TbActorCtx ctx) {
//        schedulePeriodicMsgWithDelay(ctx, SessionTimeoutCheckMsg.instance(), systemContext.getSessionReportTimeout(), systemContext.getSessionReportTimeout());
//        PageLink pageLink = new PageLink(1024, 0, null, new SortOrder("createdTime"));
//        PageData<Rpc> pageData;
//        do {
//            pageData = systemContext.getTbRpcService().findAllByDeviceIdAndStatus(tenantId, deviceId, RpcStatus.QUEUED, pageLink);
//            pageData.getData().forEach(rpc -> {
//                ToDeviceRpcRequest msg = JacksonUtil.convertValue(rpc.getRequest(), ToDeviceRpcRequest.class);
//                long timeout = rpc.getExpirationTime() - System.currentTimeMillis();
//                if (timeout <= 0) {
//                    rpc.setStatus(RpcStatus.EXPIRED);
//                    systemContext.getTbRpcService().save(tenantId, rpc);
//                } else {
//                    registerPendingRpcRequest(ctx, new ToDeviceRpcRequestActorMsg(systemContext.getServiceId(), msg), false, creteToDeviceRpcRequestMsg(msg), timeout);
//                }
//            });
//            if (pageData.hasNext()) {
//                pageLink = pageLink.nextPageLink();
//            }
//        } while (pageData.hasNext());
    }

    void checkSessionsTimeout() {
        log.debug("[{}] checkSessionsTimeout started. Size before check {}", deviceId, sessions.size());
        long expTime = System.currentTimeMillis() - systemContext.getSessionInactivityTimeout();
        Map<UUID, SessionInfoMetaData> sessionsToRemove = sessions.entrySet().stream().filter(kv -> kv.getValue().getLastActivityTime() < expTime).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        sessionsToRemove.forEach((sessionId, sessionMD) -> {
            sessions.remove(sessionId);
            rpcSubscriptions.remove(sessionId);
            attributeSubscriptions.remove(sessionId);
            notifyTransportAboutClosedSession(sessionId, sessionMD, "session timeout!");
        });
        if (!sessionsToRemove.isEmpty()) {
            dumpSessions();
        }
        log.debug("[{}] checkSessionsTimeout finished. Size after check {}", deviceId, sessions.size());
    }

}
