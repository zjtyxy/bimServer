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

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.id.EdgeId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.edge.Edge;
import com.ciat.bim.server.edge.EdgeContextComponent;
import com.ciat.bim.server.edge.gen.EdgeRpcServiceGrpc;
import com.ciat.bim.server.edge.gen.RequestMsg;
import com.ciat.bim.server.edge.gen.ResponseMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.state.DefaultDeviceStateService;
import com.ciat.bim.server.utils.ResourceUtils;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import io.grpc.Server;

import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
@ConditionalOnProperty(prefix = "edges", value = "enabled", havingValue = "true")
@TbCoreComponent
public class EdgeGrpcService extends EdgeRpcServiceGrpc.EdgeRpcServiceImplBase implements EdgeRpcService {

    private final ConcurrentMap<EdgeId, EdgeGrpcSession> sessions = new ConcurrentHashMap<>();
    private final ConcurrentMap<EdgeId, Lock> sessionNewEventsLocks = new ConcurrentHashMap<>();
    private final Map<EdgeId, Boolean> sessionNewEvents = new HashMap<>();
    private final ConcurrentMap<EdgeId, ScheduledFuture<?>> sessionEdgeEventChecks = new ConcurrentHashMap<>();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Value("${edges.rpc.port}")
    private int rpcPort;
    @Value("${edges.rpc.ssl.enabled}")
    private boolean sslEnabled;
    @Value("${edges.rpc.ssl.cert}")
    private String certFileResource;
    @Value("${edges.rpc.ssl.private_key}")
    private String privateKeyResource;
    @Value("${edges.state.persistToTelemetry:false}")
    private boolean persistToTelemetry;
    @Value("${edges.rpc.client_max_keep_alive_time_sec}")
    private int clientMaxKeepAliveTimeSec;
    @Value("${edges.rpc.max_inbound_message_size:4194304}")
    private int maxInboundMessageSize;

    @Value("${edges.scheduler_pool_size}")
    private int schedulerPoolSize;

    @Value("${edges.send_scheduler_pool_size}")
    private int sendSchedulerPoolSize;

    @Autowired
    private EdgeContextComponent ctx;

//    @Autowired
//    private TelemetrySubscriptionService tsSubService;

    private Server server;

    private ScheduledExecutorService edgeEventProcessingExecutorService;

    private ScheduledExecutorService sendDownlinkExecutorService;

    @PostConstruct
    public void init() {
        log.info("Initializing Edge RPC service!");
        NettyServerBuilder builder = NettyServerBuilder.forPort(rpcPort)
                .permitKeepAliveTime(clientMaxKeepAliveTimeSec, TimeUnit.SECONDS)
                .maxInboundMessageSize(maxInboundMessageSize)
                .addService(this);
        if (sslEnabled) {
            try {
                InputStream certFileIs = ResourceUtils.getInputStream(this, certFileResource);
                InputStream privateKeyFileIs = ResourceUtils.getInputStream(this, privateKeyResource);
                builder.useTransportSecurity(certFileIs, privateKeyFileIs);
            } catch (Exception e) {
                log.error("Unable to set up SSL context. Reason: " + e.getMessage(), e);
                throw new RuntimeException("Unable to set up SSL context!", e);
            }
        }
        server = builder.build();
        log.info("Going to start Edge RPC server using port: {}", rpcPort);
        try {
            server.start();
        } catch (IOException e) {
            log.error("Failed to start Edge RPC server!", e);
            throw new RuntimeException("Failed to start Edge RPC server!");
        }
        this.edgeEventProcessingExecutorService = Executors.newScheduledThreadPool(schedulerPoolSize, ThingsBoardThreadFactory.forName("edge-scheduler"));
        this.sendDownlinkExecutorService = Executors.newScheduledThreadPool(sendSchedulerPoolSize, ThingsBoardThreadFactory.forName("edge-send-scheduler"));
        log.info("Edge RPC service initialized!");
    }

    @PreDestroy
    public void destroy() {
        if (server != null) {
            server.shutdownNow();
        }
        for (Map.Entry<EdgeId, ScheduledFuture<?>> entry : sessionEdgeEventChecks.entrySet()) {
            EdgeId edgeId = entry.getKey();
            ScheduledFuture<?> sessionEdgeEventCheck = entry.getValue();
            if (sessionEdgeEventCheck != null && !sessionEdgeEventCheck.isCancelled() && !sessionEdgeEventCheck.isDone()) {
                sessionEdgeEventCheck.cancel(true);
                sessionEdgeEventChecks.remove(edgeId);
            }
        }
        if (edgeEventProcessingExecutorService != null) {
            edgeEventProcessingExecutorService.shutdownNow();
        }
        if (sendDownlinkExecutorService != null) {
            sendDownlinkExecutorService.shutdownNow();
        }
    }

//    @Override
//    public StreamObserver<RequestMsg> handleMsgs(StreamObserver<ResponseMsg> outputStream) {
//        return new EdgeGrpcSession(ctx, outputStream, this::onEdgeConnect, this::onEdgeDisconnect, mapper, sendDownlinkExecutorService).getInputStream();
//    }

    @Override
    public void updateEdge(TenantId tenantId, Edge edge) {
        EdgeGrpcSession session = sessions.get(edge.getId());
        if (session != null && session.isConnected()) {
            log.debug("[{}] Updating configuration for edge [{}] [{}]", tenantId, edge.getName(), edge.getId());
           // session.onConfigurationUpdate(edge);
        } else {
            log.debug("[{}] Session doesn't exist for edge [{}] [{}]", tenantId, edge.getName(), edge.getId());
        }
    }

    @Override
    public void deleteEdge(TenantId tenantId, EdgeId edgeId) {
        EdgeGrpcSession session = sessions.get(edgeId);
        if (session != null && session.isConnected()) {
            log.info("[{}] Closing and removing session for edge [{}]", tenantId, edgeId);
            session.close();
            sessions.remove(edgeId);
            final Lock newEventLock = sessionNewEventsLocks.computeIfAbsent(edgeId, id -> new ReentrantLock());
            newEventLock.lock();
            try {
                sessionNewEvents.remove(edgeId);
            } finally {
                newEventLock.unlock();
            }
            cancelScheduleEdgeEventsCheck(edgeId);
        }
    }

    @Override
    public void onEdgeEvent(TenantId tenantId, EdgeId edgeId) {
        log.trace("[{}] onEdgeEvent [{}]", tenantId, edgeId.getId());
        final Lock newEventLock = sessionNewEventsLocks.computeIfAbsent(edgeId, id -> new ReentrantLock());
        newEventLock.lock();
        try {
            if (Boolean.FALSE.equals(sessionNewEvents.get(edgeId))) {
                log.trace("[{}] set session new events flag to true [{}]", tenantId, edgeId.getId());
                sessionNewEvents.put(edgeId, true);
            }
        } finally {
            newEventLock.unlock();
        }
    }

    private void onEdgeConnect(EdgeId edgeId, EdgeGrpcSession edgeGrpcSession) {
        log.info("[{}] edge [{}] connected successfully.", edgeGrpcSession.getSessionId(), edgeId);
        sessions.put(edgeId, edgeGrpcSession);
        final Lock newEventLock = sessionNewEventsLocks.computeIfAbsent(edgeId, id -> new ReentrantLock());
        newEventLock.lock();
        try {
            sessionNewEvents.put(edgeId, true);
        } finally {
            newEventLock.unlock();
        }
        save(edgeId, DefaultDeviceStateService.ACTIVITY_STATE, true);
        save(edgeId, DefaultDeviceStateService.LAST_CONNECT_TIME, System.currentTimeMillis());
        cancelScheduleEdgeEventsCheck(edgeId);
        scheduleEdgeEventsCheck(edgeGrpcSession);
    }

    @Override
    public void startSyncProcess(TenantId tenantId, EdgeId edgeId) {
        EdgeGrpcSession session = sessions.get(edgeId);
        if (session != null && session.isConnected()) {
            //session.startSyncProcess(tenantId, edgeId);
        } else {
            log.error("[{}] Edge is not connected [{}]", tenantId, edgeId);
            throw new RuntimeException("Edge is not connected");
        }
    }

    private void scheduleEdgeEventsCheck(EdgeGrpcSession session) {
//        EdgeId edgeId = session.getEdge().getId();
//        UUID tenantId = session.getEdge().getTenantId().getId();
//        if (sessions.containsKey(edgeId)) {
//            ScheduledFuture<?> edgeEventCheckTask = edgeEventProcessingExecutorService.schedule(() -> {
//                try {
//                    final Lock newEventLock = sessionNewEventsLocks.computeIfAbsent(edgeId, id -> new ReentrantLock());
//                    newEventLock.lock();
//                    try {
//                        if (Boolean.TRUE.equals(sessionNewEvents.get(edgeId))) {
//                            log.trace("[{}] Set session new events flag to false", edgeId.getId());
//                            sessionNewEvents.put(edgeId, false);
//                            Futures.addCallback(session.processEdgeEvents(), new FutureCallback<>() {
//                                @Override
//                                public void onSuccess(Void result) {
//                                    scheduleEdgeEventsCheck(session);
//                                }
//
//                                @Override
//                                public void onFailure(Throwable t) {
//                                    log.warn("[{}] Failed to process edge events for edge [{}]!", tenantId, session.getEdge().getId().getId(), t);
//                                    scheduleEdgeEventsCheck(session);
//                                }
//                            }, ctx.getGrpcCallbackExecutorService());
//                        } else {
//                            scheduleEdgeEventsCheck(session);
//                        }
//                    } finally {
//                        newEventLock.unlock();
//                    }
//                } catch (Exception e) {
//                    log.warn("[{}] Failed to process edge events for edge [{}]!", tenantId, session.getEdge().getId().getId(), e);
//                }
//            }, ctx.getEdgeEventStorageSettings().getNoRecordsSleepInterval(), TimeUnit.MILLISECONDS);
//            sessionEdgeEventChecks.put(edgeId, edgeEventCheckTask);
//            log.trace("[{}] Check edge event scheduled for edge [{}]", tenantId, edgeId.getId());
//        } else {
//            log.debug("[{}] Session was removed and edge event check schedule must not be started [{}]",
//                    tenantId, edgeId.getId());
//        }
    }

    private void cancelScheduleEdgeEventsCheck(EdgeId edgeId) {
        log.trace("[{}] cancelling edge event check for edge", edgeId);
        if (sessionEdgeEventChecks.containsKey(edgeId)) {
            ScheduledFuture<?> sessionEdgeEventCheck = sessionEdgeEventChecks.get(edgeId);
            if (sessionEdgeEventCheck != null && !sessionEdgeEventCheck.isCancelled() && !sessionEdgeEventCheck.isDone()) {
                sessionEdgeEventCheck.cancel(true);
                sessionEdgeEventChecks.remove(edgeId);
            }
        }
    }

    private void onEdgeDisconnect(EdgeId edgeId) {
        log.info("[{}] edge disconnected!", edgeId);
        sessions.remove(edgeId);
        final Lock newEventLock = sessionNewEventsLocks.computeIfAbsent(edgeId, id -> new ReentrantLock());
        newEventLock.lock();
        try {
            sessionNewEvents.remove(edgeId);
        } finally {
            newEventLock.unlock();
        }
        save(edgeId, DefaultDeviceStateService.ACTIVITY_STATE, false);
        save(edgeId, DefaultDeviceStateService.LAST_DISCONNECT_TIME, System.currentTimeMillis());
        cancelScheduleEdgeEventsCheck(edgeId);
    }

    private void save(EdgeId edgeId, String key, long value) {
//        log.debug("[{}] Updating long edge telemetry [{}] [{}]", edgeId, key, value);
//        if (persistToTelemetry) {
//            tsSubService.saveAndNotify(
//                    TenantId.SYS_TENANT_ID, edgeId,
//                    Collections.singletonList(new BasicTsKvEntry(System.currentTimeMillis(), new LongDataEntry(key, value))),
//                    new AttributeSaveCallback(edgeId, key, value));
//        } else {
//            tsSubService.saveAttrAndNotify(TenantId.SYS_TENANT_ID, edgeId, DataConstants.SERVER_SCOPE, key, value, new AttributeSaveCallback(edgeId, key, value));
//        }
    }

    private void save(EdgeId edgeId, String key, boolean value) {
//        log.debug("[{}] Updating boolean edge telemetry [{}] [{}]", edgeId, key, value);
//        if (persistToTelemetry) {
//            tsSubService.saveAndNotify(
//                    TenantId.SYS_TENANT_ID, edgeId,
//                    Collections.singletonList(new BasicTsKvEntry(System.currentTimeMillis(), new BooleanDataEntry(key, value))),
//                    new AttributeSaveCallback(edgeId, key, value));
//        } else {
//            tsSubService.saveAttrAndNotify(TenantId.SYS_TENANT_ID, edgeId, DataConstants.SERVER_SCOPE, key, value, new AttributeSaveCallback(edgeId, key, value));
//        }
    }

    private static class AttributeSaveCallback implements FutureCallback<Void> {
        private final EdgeId edgeId;
        private final String key;
        private final Object value;

        AttributeSaveCallback(EdgeId edgeId, String key, Object value) {
            this.edgeId = edgeId;
            this.key = key;
            this.value = value;
        }

        @Override
        public void onSuccess(@Nullable Void result) {
            log.trace("[{}] Successfully updated attribute [{}] with value [{}]", edgeId, key, value);
        }

        @Override
        public void onFailure(Throwable t) {
            log.warn("[{}] Failed to update attribute [{}] with value [{}]", edgeId, key, value, t);
        }
    }
}
