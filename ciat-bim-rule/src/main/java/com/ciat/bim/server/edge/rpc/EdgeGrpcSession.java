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
package com.ciat.bim.server.edge.rpc;

import com.ciat.bim.data.id.EdgeId;

import com.ciat.bim.server.edge.EdgeContextComponent;
import com.ciat.bim.server.edge.gen.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.stub.StreamObserver;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.edge.entity.Edge;


import java.io.Closeable;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
@Data
public final class EdgeGrpcSession implements Closeable {

    private static final ReentrantLock downlinkMsgLock = new ReentrantLock();

    private static final String QUEUE_START_TS_ATTR_KEY = "queueStartTs";

    private final UUID sessionId;
    private final BiConsumer<EdgeId, EdgeGrpcSession> sessionOpenListener;
    private final Consumer<EdgeId> sessionCloseListener;
    private final ObjectMapper mapper;

    private final EdgeSessionState sessionState = new EdgeSessionState();

    private EdgeContextComponent ctx;
    private Edge edge;
    private StreamObserver<RequestMsg> inputStream;
    private StreamObserver<ResponseMsg> outputStream;
    private boolean connected;
    private boolean syncCompleted;

    private ScheduledExecutorService sendDownlinkExecutorService;

//    EdgeGrpcSession(EdgeContextComponent ctx, StreamObserver<ResponseMsg> outputStream, BiConsumer<EdgeId, EdgeGrpcSession> sessionOpenListener,
//                    Consumer<EdgeId> sessionCloseListener, ObjectMapper mapper, ScheduledExecutorService sendDownlinkExecutorService) {
//        this.sessionId = UUID.randomUUID();
//        this.ctx = ctx;
//        this.outputStream = outputStream;
//        this.sessionOpenListener = sessionOpenListener;
//        this.sessionCloseListener = sessionCloseListener;
//        this.mapper = mapper;
//        this.sendDownlinkExecutorService = sendDownlinkExecutorService;
//        initInputStream();
//    }

//    private void initInputStream() {
//        this.inputStream = new StreamObserver<>() {
//            @Override
//            public void onNext(RequestMsg requestMsg) {
//                if (!connected && requestMsg.getMsgType().equals(RequestMsgType.CONNECT_RPC_MESSAGE)) {
//                    ConnectResponseMsg responseMsg = processConnect(requestMsg.getConnectRequestMsg());
//                    outputStream.onNext(ResponseMsg.newBuilder()
//                            .setConnectResponseMsg(responseMsg)
//                            .build());
//                    if (ConnectResponseCode.ACCEPTED != responseMsg.getResponseCode()) {
//                        outputStream.onError(new RuntimeException(responseMsg.getErrorMsg()));
//                    } else {
//                        connected = true;
//                    }
//                }
//                if (connected) {
//                    if (requestMsg.getMsgType().equals(RequestMsgType.SYNC_REQUEST_RPC_MESSAGE)) {
//                        if (requestMsg.hasSyncRequestMsg() && requestMsg.getSyncRequestMsg().getSyncRequired()) {
//                            startSyncProcess(edge.getTenantId(), edge.getId());
//                        } else {
//                            syncCompleted = true;
//                        }
//                    }
//                    if (requestMsg.getMsgType().equals(RequestMsgType.UPLINK_RPC_MESSAGE)) {
//                        if (requestMsg.hasUplinkMsg()) {
//                            onUplinkMsg(requestMsg.getUplinkMsg());
//                        }
//                        if (requestMsg.hasDownlinkResponseMsg()) {
//                            onDownlinkResponse(requestMsg.getDownlinkResponseMsg());
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                log.error("Failed to deliver message from client!", t);
//                closeSession();
//            }
//
//            @Override
//            public void onCompleted() {
//                closeSession();
//            }
//
//            private void closeSession() {
//                connected = false;
//                if (edge != null) {
//                    try {
//                        sessionCloseListener.accept(edge.getId());
//                    } catch (Exception ignored) {
//                    }
//                }
//                try {
//                    outputStream.onCompleted();
//                } catch (Exception ignored) {
//                }
//            }
//        };
//    }
//
//    public void startSyncProcess(TenantId tenantId, EdgeId edgeId) {
//        log.trace("[{}][{}] Staring edge sync process", tenantId, edgeId);
//        syncCompleted = false;
//        doSync(new EdgeSyncCursor(ctx, edge));
//    }
//
//    private void doSync(EdgeSyncCursor cursor) {
//        if (cursor.hasNext()) {
//            log.info("[{}][{}] starting sync process, cursor current idx = {}", edge.getTenantId(), edge.getId(), cursor.getCurrentIdx());
//            ListenableFuture<UUID> uuidListenableFuture = startProcessingEdgeEvents(cursor.getNext());
//            Futures.addCallback(uuidListenableFuture, new FutureCallback<>() {
//                @Override
//                public void onSuccess(@Nullable UUID result) {
//                    doSync(cursor);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error("[{}][{}] Exception during sync process", edge.getTenantId(), edge.getId(), t);
//                }
//            }, ctx.getGrpcCallbackExecutorService());
//        } else {
//            DownlinkMsg syncCompleteDownlinkMsg = DownlinkMsg.newBuilder()
//                    .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                    .setSyncCompletedMsg(SyncCompletedMsg.newBuilder().build())
//                    .build();
//            Futures.addCallback(sendDownlinkMsgsPack(Collections.singletonList(syncCompleteDownlinkMsg)), new FutureCallback<Void>() {
//                @Override
//                public void onSuccess(Void result) {
//                    syncCompleted = true;
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error("[{}][{}] Exception during sending sync complete", edge.getTenantId(), edge.getId(), t);
//                }
//            }, ctx.getGrpcCallbackExecutorService());
//        }
//    }
//
//    private void onUplinkMsg(UplinkMsg uplinkMsg) {
//        ListenableFuture<List<Void>> future = processUplinkMsg(uplinkMsg);
//        Futures.addCallback(future, new FutureCallback<>() {
//            @Override
//            public void onSuccess(@Nullable List<Void> result) {
//                UplinkResponseMsg uplinkResponseMsg = UplinkResponseMsg.newBuilder()
//                        .setUplinkMsgId(uplinkMsg.getUplinkMsgId())
//                        .setSuccess(true).build();
//                sendDownlinkMsg(ResponseMsg.newBuilder()
//                        .setUplinkResponseMsg(uplinkResponseMsg)
//                        .build());
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                String errorMsg = t.getMessage() != null ? t.getMessage() : "";
//                UplinkResponseMsg uplinkResponseMsg = UplinkResponseMsg.newBuilder()
//                        .setUplinkMsgId(uplinkMsg.getUplinkMsgId())
//                        .setSuccess(false).setErrorMsg(errorMsg).build();
//                sendDownlinkMsg(ResponseMsg.newBuilder()
//                        .setUplinkResponseMsg(uplinkResponseMsg)
//                        .build());
//            }
//        }, ctx.getGrpcCallbackExecutorService());
//    }
//
//    private void onDownlinkResponse(DownlinkResponseMsg msg) {
//        try {
//            if (msg.getSuccess()) {
//                sessionState.getPendingMsgsMap().remove(msg.getDownlinkMsgId());
//                log.debug("[{}] Msg has been processed successfully! {}", edge.getRoutingKey(), msg);
//            } else {
//                log.error("[{}] Msg processing failed! Error msg: {}", edge.getRoutingKey(), msg.getErrorMsg());
//            }
//            if (sessionState.getPendingMsgsMap().isEmpty()) {
//                log.debug("[{}] Pending msgs map is empty. Stopping current iteration", edge.getRoutingKey());
//                if (sessionState.getScheduledSendDownlinkTask() != null) {
//                    sessionState.getScheduledSendDownlinkTask().cancel(false);
//                }
//                sessionState.getSendDownlinkMsgsFuture().set(null);
//            }
//        } catch (Exception e) {
//            log.error("[{}] Can't process downlink response message [{}]", this.sessionId, msg, e);
//        }
//    }
//
//    private void sendDownlinkMsg(ResponseMsg downlinkMsg) {
//        log.trace("[{}] Sending downlink msg [{}]", this.sessionId, downlinkMsg);
//        if (isConnected()) {
//            downlinkMsgLock.lock();
//            try {
//                outputStream.onNext(downlinkMsg);
//            } catch (Exception e) {
//                log.error("[{}] Failed to send downlink message [{}]", this.sessionId, downlinkMsg, e);
//                connected = false;
//                sessionCloseListener.accept(edge.getId());
//            } finally {
//                downlinkMsgLock.unlock();
//            }
//            log.trace("[{}] Response msg successfully sent [{}]", this.sessionId, downlinkMsg);
//        }
//    }
//
//    void onConfigurationUpdate(Edge edge) {
//        log.debug("[{}] onConfigurationUpdate [{}]", this.sessionId, edge);
//        this.edge = edge;
//        EdgeUpdateMsg edgeConfig = EdgeUpdateMsg.newBuilder()
//                .setConfiguration(constructEdgeConfigProto(edge)).build();
//        ResponseMsg edgeConfigMsg = ResponseMsg.newBuilder()
//                .setEdgeUpdateMsg(edgeConfig)
//                .build();
//        sendDownlinkMsg(edgeConfigMsg);
//    }
//
//    ListenableFuture<Void> processEdgeEvents() throws Exception {
//        SettableFuture<Void> result = SettableFuture.create();
//        log.trace("[{}] starting processing edge events", this.sessionId);
//        if (isConnected() && isSyncCompleted()) {
//            Long queueStartTs = getQueueStartTs().get();
//            GeneralEdgeEventFetcher fetcher = new GeneralEdgeEventFetcher(
//                    queueStartTs,
//                    ctx.getEdgeEventService());
//            ListenableFuture<UUID> ifOffsetFuture = startProcessingEdgeEvents(fetcher);
//            Futures.addCallback(ifOffsetFuture, new FutureCallback<>() {
//                @Override
//                public void onSuccess(@Nullable UUID ifOffset) {
//                    if (ifOffset != null) {
//                        Long newStartTs = Uuids.unixTimestamp(ifOffset);
//                        ListenableFuture<List<Void>> updateFuture = updateQueueStartTs(newStartTs);
//                        Futures.addCallback(updateFuture, new FutureCallback<>() {
//                            @Override
//                            public void onSuccess(@Nullable List<Void> list) {
//                                log.debug("[{}] queue offset was updated [{}][{}]", sessionId, ifOffset, newStartTs);
//                                result.set(null);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t) {
//                                log.error("[{}] Failed to update queue offset [{}]", sessionId, ifOffset, t);
//                                result.setException(t);
//                            }
//                        }, ctx.getGrpcCallbackExecutorService());
//                    } else {
//                        log.trace("[{}] ifOffset is null. Skipping iteration without db update", sessionId);
//                        result.set(null);
//                    }
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    log.error("[{}] Failed to process events", sessionId, t);
//                    result.setException(t);
//                }
//            }, ctx.getGrpcCallbackExecutorService());
//        } else {
//            log.trace("[{}] edge is not connected or sync is not completed. Skipping iteration", sessionId);
//            result.set(null);
//        }
//        return result;
//    }
//
//    private ListenableFuture<UUID> startProcessingEdgeEvents(EdgeEventFetcher fetcher) {
//        SettableFuture<UUID> result = SettableFuture.create();
//        PageLink pageLink = fetcher.getPageLink(ctx.getEdgeEventStorageSettings().getMaxReadRecordsCount());
//        processEdgeEvents(fetcher, pageLink, result);
//        return result;
//    }
//
//    private void processEdgeEvents(EdgeEventFetcher fetcher, PageLink pageLink, SettableFuture<UUID> result) {
//        try {
//            PageData<EdgeEvent> pageData = fetcher.fetchEdgeEvents(edge.getTenantId(), edge, pageLink);
//            if (isConnected() && !pageData.getData().isEmpty()) {
//                log.trace("[{}] [{}] event(s) are going to be processed.", this.sessionId, pageData.getData().size());
//                List<DownlinkMsg> downlinkMsgsPack = convertToDownlinkMsgsPack(pageData.getData());
//                Futures.addCallback(sendDownlinkMsgsPack(downlinkMsgsPack), new FutureCallback<Void>() {
//                    @Override
//                    public void onSuccess(@Nullable Void tmp) {
//                        if (isConnected() && pageData.hasNext()) {
//                            processEdgeEvents(fetcher, pageLink.nextPageLink(), result);
//                        } else {
//                            UUID ifOffset = pageData.getData().get(pageData.getData().size() - 1).getUuidId();
//                            result.set(ifOffset);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        log.error("[{}] Failed to send downlink msgs pack", sessionId, t);
//                        result.setException(t);
//                    }
//                }, ctx.getGrpcCallbackExecutorService());
//            } else {
//                log.trace("[{}] no event(s) found. Stop processing edge events", this.sessionId);
//                result.set(null);
//            }
//        } catch (Exception e) {
//            log.error("[{}] Failed to fetch edge events", this.sessionId, e);
//            result.setException(e);
//        }
//    }
//
//    private ListenableFuture<Void> sendDownlinkMsgsPack(List<DownlinkMsg> downlinkMsgsPack) {
//        if (sessionState.getSendDownlinkMsgsFuture() != null && !sessionState.getSendDownlinkMsgsFuture().isDone()) {
//            String erroMsg = "[" + this.sessionId + "] Previous send downdlink future was not properly completed, stopping it now";
//            log.error(erroMsg);
//            sessionState.getSendDownlinkMsgsFuture().setException(new RuntimeException(erroMsg));
//        }
//        sessionState.setSendDownlinkMsgsFuture(SettableFuture.create());
//        sessionState.getPendingMsgsMap().clear();
//        downlinkMsgsPack.forEach(msg -> sessionState.getPendingMsgsMap().put(msg.getDownlinkMsgId(), msg));
//        scheduleDownlinkMsgsPackSend(true);
//        return sessionState.getSendDownlinkMsgsFuture();
//    }
//
//    private void scheduleDownlinkMsgsPackSend(boolean firstRun) {
//        Runnable sendDownlinkMsgsTask = () -> {
//            try {
//                if (isConnected() && sessionState.getPendingMsgsMap().values().size() > 0) {
//                    if (!firstRun) {
//                        log.warn("[{}] Failed to deliver the batch: {}", this.sessionId, sessionState.getPendingMsgsMap().values());
//                    }
//                    log.trace("[{}] [{}] downlink msg(s) are going to be send.", this.sessionId, sessionState.getPendingMsgsMap().values().size());
//                    List<DownlinkMsg> copy = new ArrayList<>(sessionState.getPendingMsgsMap().values());
//                    for (DownlinkMsg downlinkMsg : copy) {
//                        sendDownlinkMsg(ResponseMsg.newBuilder()
//                                .setDownlinkMsg(downlinkMsg)
//                                .build());
//                    }
//                    scheduleDownlinkMsgsPackSend(false);
//                } else {
//                    sessionState.getSendDownlinkMsgsFuture().set(null);
//                }
//            } catch (Exception e) {
//                sessionState.getSendDownlinkMsgsFuture().setException(e);
//            }
//        };
//
//        if (firstRun) {
//            sendDownlinkExecutorService.submit(sendDownlinkMsgsTask);
//        } else {
//            sessionState.setScheduledSendDownlinkTask(
//                    sendDownlinkExecutorService.schedule(
//                            sendDownlinkMsgsTask,
//                            ctx.getEdgeEventStorageSettings().getSleepIntervalBetweenBatches(),
//                            TimeUnit.MILLISECONDS)
//            );
//        }
//
//    }
//
//    private DownlinkMsg convertToDownlinkMsg(EdgeEvent edgeEvent) {
//        log.trace("[{}][{}] converting edge event to downlink msg [{}]", edge.getTenantId(), this.sessionId, edgeEvent);
//        DownlinkMsg downlinkMsg = null;
//        try {
//            switch (edgeEvent.getAction()) {
//                case UPDATED:
//                case ADDED:
//                case DELETED:
//                case ASSIGNED_TO_EDGE:
//                case UNASSIGNED_FROM_EDGE:
//                case ALARM_ACK:
//                case ALARM_CLEAR:
//                case CREDENTIALS_UPDATED:
//                case RELATION_ADD_OR_UPDATE:
//                case RELATION_DELETED:
//                case ASSIGNED_TO_CUSTOMER:
//                case UNASSIGNED_FROM_CUSTOMER:
//                    downlinkMsg = processEntityMessage(edgeEvent, edgeEvent.getAction());
//                    log.trace("[{}][{}] entity message processed [{}]", edgeEvent.getTenantId(), this.sessionId, downlinkMsg);
//                    break;
//                case ATTRIBUTES_UPDATED:
//                case POST_ATTRIBUTES:
//                case ATTRIBUTES_DELETED:
//                case TIMESERIES_UPDATED:
//                    downlinkMsg = ctx.getTelemetryProcessor().processTelemetryMessageToEdge(edgeEvent);
//                    break;
//                case CREDENTIALS_REQUEST:
//                    downlinkMsg = ctx.getEntityProcessor().processCredentialsRequestMessageToEdge(edgeEvent);
//                    break;
//                case ENTITY_MERGE_REQUEST:
//                    downlinkMsg = ctx.getEntityProcessor().processEntityMergeRequestMessageToEdge(edge, edgeEvent);
//                    break;
//                case RPC_CALL:
//                    downlinkMsg = ctx.getDeviceProcessor().processRpcCallMsgToEdge(edgeEvent);
//                    break;
//                default:
//                    log.warn("[{}][{}] Unsupported action type [{}]", edge.getTenantId(), this.sessionId, edgeEvent.getAction());
//            }
//        } catch (Exception e) {
//            log.error("[{}][{}] Exception during converting edge event to downlink msg", edge.getTenantId(), this.sessionId, e);
//        }
//        return downlinkMsg;
//    }
//
//    private List<DownlinkMsg> convertToDownlinkMsgsPack(List<EdgeEvent> edgeEvents) {
//        return edgeEvents
//                .stream()
//                .map(this::convertToDownlinkMsg)
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//    }
//
//    private ListenableFuture<Long> getQueueStartTs() {
//        ListenableFuture<Optional<AttributeKvEntry>> future =
//                ctx.getAttributesService().find(edge.getTenantId(), edge.getId(), DataConstants.SERVER_SCOPE, QUEUE_START_TS_ATTR_KEY);
//        return Futures.transform(future, attributeKvEntryOpt -> {
//            if (attributeKvEntryOpt != null && attributeKvEntryOpt.isPresent()) {
//                AttributeKvEntry attributeKvEntry = attributeKvEntryOpt.get();
//                return attributeKvEntry.getLongValue().isPresent() ? attributeKvEntry.getLongValue().get() : 0L;
//            } else {
//                return 0L;
//            }
//        }, ctx.getGrpcCallbackExecutorService());
//    }
//
//    private ListenableFuture<List<Void>> updateQueueStartTs(Long newStartTs) {
//        log.trace("[{}] updating QueueStartTs [{}][{}]", this.sessionId, edge.getId(), newStartTs);
//        newStartTs = ++newStartTs; // increments ts by 1 - next edge event search starts from current offset + 1
//        List<AttributeKvEntry> attributes = Collections.singletonList(new BaseAttributeKvEntry(new LongDataEntry(QUEUE_START_TS_ATTR_KEY, newStartTs), System.currentTimeMillis()));
//        return ctx.getAttributesService().save(edge.getTenantId(), edge.getId(), DataConstants.SERVER_SCOPE, attributes);
//    }
//
//    private DownlinkMsg processEntityMessage(EdgeEvent edgeEvent, EdgeEventActionType action) {
//        UpdateMsgType msgType = getResponseMsgType(edgeEvent.getAction());
//        log.trace("Executing processEntityMessage, edgeEvent [{}], action [{}], msgType [{}]", edgeEvent, action, msgType);
//        switch (edgeEvent.getType()) {
//            case DEVICE:
//                return ctx.getDeviceProcessor().processDeviceToEdge(edge, edgeEvent, msgType, action);
//            case DEVICE_PROFILE:
//                return ctx.getDeviceProfileProcessor().processDeviceProfileToEdge(edgeEvent, msgType, action);
//            case ASSET:
//                return ctx.getAssetProcessor().processAssetToEdge(edge, edgeEvent, msgType, action);
//            case ENTITY_VIEW:
//                return ctx.getEntityViewProcessor().processEntityViewToEdge(edge, edgeEvent, msgType, action);
//            case DASHBOARD:
//                return ctx.getDashboardProcessor().processDashboardToEdge(edge, edgeEvent, msgType, action);
//            case CUSTOMER:
//                return ctx.getCustomerProcessor().processCustomerToEdge(edgeEvent, msgType, action);
//            case RULE_CHAIN:
//                return ctx.getRuleChainProcessor().processRuleChainToEdge(edge, edgeEvent, msgType, action);
//            case RULE_CHAIN_METADATA:
//                return ctx.getRuleChainProcessor().processRuleChainMetadataToEdge(edgeEvent, msgType);
//            case ALARM:
//                return ctx.getAlarmProcessor().processAlarmToEdge(edge, edgeEvent, msgType, action);
//            case USER:
//                return ctx.getUserProcessor().processUserToEdge(edge, edgeEvent, msgType, action);
//            case RELATION:
//                return ctx.getRelationProcessor().processRelationToEdge(edgeEvent, msgType);
//            case WIDGETS_BUNDLE:
//                return ctx.getWidgetBundleProcessor().processWidgetsBundleToEdge(edgeEvent, msgType, action);
//            case WIDGET_TYPE:
//                return ctx.getWidgetTypeProcessor().processWidgetTypeToEdge(edgeEvent, msgType, action);
//            case ADMIN_SETTINGS:
//                return ctx.getAdminSettingsProcessor().processAdminSettingsToEdge(edgeEvent);
//            default:
//                log.warn("Unsupported edge event type [{}]", edgeEvent);
//                return null;
//        }
//    }
//
//    private UpdateMsgType getResponseMsgType(EdgeEventActionType actionType) {
//        switch (actionType) {
//            case UPDATED:
//            case CREDENTIALS_UPDATED:
//            case ASSIGNED_TO_CUSTOMER:
//            case UNASSIGNED_FROM_CUSTOMER:
//                return UpdateMsgType.ENTITY_UPDATED_RPC_MESSAGE;
//            case ADDED:
//            case ASSIGNED_TO_EDGE:
//            case RELATION_ADD_OR_UPDATE:
//                return UpdateMsgType.ENTITY_CREATED_RPC_MESSAGE;
//            case DELETED:
//            case UNASSIGNED_FROM_EDGE:
//            case RELATION_DELETED:
//                return UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE;
//            case ALARM_ACK:
//                return UpdateMsgType.ALARM_ACK_RPC_MESSAGE;
//            case ALARM_CLEAR:
//                return UpdateMsgType.ALARM_CLEAR_RPC_MESSAGE;
//            default:
//                throw new RuntimeException("Unsupported actionType [" + actionType + "]");
//        }
//    }
//
//    private ListenableFuture<List<Void>> processUplinkMsg(UplinkMsg uplinkMsg) {
//        List<ListenableFuture<Void>> result = new ArrayList<>();
//        try {
//            if (uplinkMsg.getEntityDataCount() > 0) {
//                for (EntityDataProto entityData : uplinkMsg.getEntityDataList()) {
//                    result.addAll(ctx.getTelemetryProcessor().processTelemetryFromEdge(edge.getTenantId(), edge.getCustomerId(), entityData));
//                }
//            }
//            if (uplinkMsg.getDeviceUpdateMsgCount() > 0) {
//                for (DeviceUpdateMsg deviceUpdateMsg : uplinkMsg.getDeviceUpdateMsgList()) {
//                    result.add(ctx.getDeviceProcessor().processDeviceFromEdge(edge.getTenantId(), edge, deviceUpdateMsg));
//                }
//            }
//            if (uplinkMsg.getDeviceCredentialsUpdateMsgCount() > 0) {
//                for (DeviceCredentialsUpdateMsg deviceCredentialsUpdateMsg : uplinkMsg.getDeviceCredentialsUpdateMsgList()) {
//                    result.add(ctx.getDeviceProcessor().processDeviceCredentialsFromEdge(edge.getTenantId(), deviceCredentialsUpdateMsg));
//                }
//            }
//            if (uplinkMsg.getAlarmUpdateMsgCount() > 0) {
//                for (AlarmUpdateMsg alarmUpdateMsg : uplinkMsg.getAlarmUpdateMsgList()) {
//                    result.add(ctx.getAlarmProcessor().processAlarmFromEdge(edge.getTenantId(), alarmUpdateMsg));
//                }
//            }
//            if (uplinkMsg.getRelationUpdateMsgCount() > 0) {
//                for (RelationUpdateMsg relationUpdateMsg : uplinkMsg.getRelationUpdateMsgList()) {
//                    result.add(ctx.getRelationProcessor().processRelationFromEdge(edge.getTenantId(), relationUpdateMsg));
//                }
//            }
//            if (uplinkMsg.getRuleChainMetadataRequestMsgCount() > 0) {
//                for (RuleChainMetadataRequestMsg ruleChainMetadataRequestMsg : uplinkMsg.getRuleChainMetadataRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processRuleChainMetadataRequestMsg(edge.getTenantId(), edge, ruleChainMetadataRequestMsg));
//                }
//            }
//            if (uplinkMsg.getAttributesRequestMsgCount() > 0) {
//                for (AttributesRequestMsg attributesRequestMsg : uplinkMsg.getAttributesRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processAttributesRequestMsg(edge.getTenantId(), edge, attributesRequestMsg));
//                }
//            }
//            if (uplinkMsg.getRelationRequestMsgCount() > 0) {
//                for (RelationRequestMsg relationRequestMsg : uplinkMsg.getRelationRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processRelationRequestMsg(edge.getTenantId(), edge, relationRequestMsg));
//                }
//            }
//            if (uplinkMsg.getUserCredentialsRequestMsgCount() > 0) {
//                for (UserCredentialsRequestMsg userCredentialsRequestMsg : uplinkMsg.getUserCredentialsRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processUserCredentialsRequestMsg(edge.getTenantId(), edge, userCredentialsRequestMsg));
//                }
//            }
//            if (uplinkMsg.getDeviceCredentialsRequestMsgCount() > 0) {
//                for (DeviceCredentialsRequestMsg deviceCredentialsRequestMsg : uplinkMsg.getDeviceCredentialsRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processDeviceCredentialsRequestMsg(edge.getTenantId(), edge, deviceCredentialsRequestMsg));
//                }
//            }
//            if (uplinkMsg.getDeviceRpcCallMsgCount() > 0) {
//                for (DeviceRpcCallMsg deviceRpcCallMsg : uplinkMsg.getDeviceRpcCallMsgList()) {
//                    result.add(ctx.getDeviceProcessor().processDeviceRpcCallResponseFromEdge(edge.getTenantId(), deviceRpcCallMsg));
//                }
//            }
//            if (uplinkMsg.getDeviceProfileDevicesRequestMsgCount() > 0) {
//                for (DeviceProfileDevicesRequestMsg deviceProfileDevicesRequestMsg : uplinkMsg.getDeviceProfileDevicesRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processDeviceProfileDevicesRequestMsg(edge.getTenantId(), edge, deviceProfileDevicesRequestMsg));
//                }
//            }
//            if (uplinkMsg.getWidgetBundleTypesRequestMsgCount() > 0) {
//                for (WidgetBundleTypesRequestMsg widgetBundleTypesRequestMsg : uplinkMsg.getWidgetBundleTypesRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processWidgetBundleTypesRequestMsg(edge.getTenantId(), edge, widgetBundleTypesRequestMsg));
//                }
//            }
//            if (uplinkMsg.getEntityViewsRequestMsgCount() > 0) {
//                for (EntityViewsRequestMsg entityViewRequestMsg : uplinkMsg.getEntityViewsRequestMsgList()) {
//                    result.add(ctx.getEdgeRequestsService().processEntityViewsRequestMsg(edge.getTenantId(), edge, entityViewRequestMsg));
//                }
//            }
//        } catch (Exception e) {
//            log.error("[{}] Can't process uplink msg [{}]", this.sessionId, uplinkMsg, e);
//        }
//        return Futures.allAsList(result);
//    }
//
//    private ConnectResponseMsg processConnect(ConnectRequestMsg request) {
//        log.trace("[{}] processConnect [{}]", this.sessionId, request);
//        Optional<Edge> optional = ctx.getEdgeService().findEdgeByRoutingKey(TenantId.SYS_TENANT_ID, request.getEdgeRoutingKey());
//        if (optional.isPresent()) {
//            edge = optional.get();
//            try {
//                if (edge.getSecret().equals(request.getEdgeSecret())) {
//                    sessionOpenListener.accept(edge.getId(), this);
//                    return ConnectResponseMsg.newBuilder()
//                            .setResponseCode(ConnectResponseCode.ACCEPTED)
//                            .setErrorMsg("")
//                            .setConfiguration(constructEdgeConfigProto(edge)).build();
//                }
//                return ConnectResponseMsg.newBuilder()
//                        .setResponseCode(ConnectResponseCode.BAD_CREDENTIALS)
//                        .setErrorMsg("Failed to validate the edge!")
//                        .setConfiguration(EdgeConfiguration.getDefaultInstance()).build();
//            } catch (Exception e) {
//                log.error("[{}] Failed to process edge connection!", request.getEdgeRoutingKey(), e);
//                return ConnectResponseMsg.newBuilder()
//                        .setResponseCode(ConnectResponseCode.SERVER_UNAVAILABLE)
//                        .setErrorMsg("Failed to process edge connection!")
//                        .setConfiguration(EdgeConfiguration.getDefaultInstance()).build();
//            }
//        }
//        return ConnectResponseMsg.newBuilder()
//                .setResponseCode(ConnectResponseCode.BAD_CREDENTIALS)
//                .setErrorMsg("Failed to find the edge! Routing key: " + request.getEdgeRoutingKey())
//                .setConfiguration(EdgeConfiguration.getDefaultInstance()).build();
//    }
//
//    private EdgeConfiguration constructEdgeConfigProto(Edge edge) {
//        EdgeConfiguration.Builder builder = EdgeConfiguration.newBuilder()
//                .setEdgeIdMSB(edge.getId().getId().getMostSignificantBits())
//                .setEdgeIdLSB(edge.getId().getId().getLeastSignificantBits())
//                .setTenantIdMSB(edge.getTenantId().getId().getMostSignificantBits())
//                .setTenantIdLSB(edge.getTenantId().getId().getLeastSignificantBits())
//                .setName(edge.getName())
//                .setType(edge.getType())
//                .setRoutingKey(edge.getRoutingKey())
//                .setSecret(edge.getSecret())
//                .setEdgeLicenseKey(edge.getEdgeLicenseKey())
//                .setCloudEndpoint(edge.getCloudEndpoint())
//                .setAdditionalInfo(JacksonUtil.toString(edge.getAdditionalInfo()))
//                .setCloudType("CE");
//        if (edge.getCustomerId() != null) {
//            builder.setCustomerIdMSB(edge.getCustomerId().getId().getMostSignificantBits())
//                    .setCustomerIdLSB(edge.getCustomerId().getId().getLeastSignificantBits());
//        }
//        return builder
//                .build();
//    }
//
//    @Override
    public void close() {
        log.debug("[{}] Closing session", sessionId);
        connected = false;
        try {
            outputStream.onCompleted();
        } catch (Exception e) {
            log.debug("[{}] Failed to close output stream: {}", sessionId, e.getMessage());
        }
    }
}
