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
package com.ciat.bim.server.rpc;

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgDataType;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.msg.rpc.FromDeviceRpcResponse;
import com.ciat.bim.server.common.msg.rpc.RpcError;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequest;
import com.ciat.bim.server.queue.discovery.TbServiceInfoProvider;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.security.SecurityUser;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.service.IDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * Created by ashvayka on 27.03.18.
 */
@Service
@Slf4j
@TbCoreComponent
public class DefaultTbCoreDeviceRpcService implements TbCoreDeviceRpcService {

    private static final ObjectMapper json = new ObjectMapper();

    private final IDeviceService deviceService;
    private final TbClusterService clusterService;
    private final TbServiceInfoProvider serviceInfoProvider;
    private final ActorSystemContext actorContext;

    private final ConcurrentMap<UUID, Consumer<FromDeviceRpcResponse>> localToRuleEngineRpcRequests = new ConcurrentHashMap<>();
    private final ConcurrentMap<UUID, ToDeviceRpcRequestActorMsg> localToDeviceRpcRequests = new ConcurrentHashMap<>();

    private Optional<TbRuleEngineDeviceRpcService> tbRuleEngineRpcService;
    private ScheduledExecutorService scheduler;
    private String serviceId;

    public DefaultTbCoreDeviceRpcService(IDeviceService deviceService, TbClusterService clusterService, TbServiceInfoProvider serviceInfoProvider,
                                         ActorSystemContext actorContext) {
        this.deviceService = deviceService;
        this.clusterService = clusterService;
        this.serviceInfoProvider = serviceInfoProvider;
        this.actorContext = actorContext;
    }

    @Autowired(required = false)
    public void setTbRuleEngineRpcService(Optional<TbRuleEngineDeviceRpcService> tbRuleEngineRpcService) {
        this.tbRuleEngineRpcService = tbRuleEngineRpcService;
    }

    @PostConstruct
    public void initExecutor() {
        scheduler = Executors.newSingleThreadScheduledExecutor(ThingsBoardThreadFactory.forName("tb-core-rpc-scheduler"));
        serviceId = serviceInfoProvider.getServiceId();
    }

    @PreDestroy
    public void shutdownExecutor() {
        if (scheduler != null) {
            scheduler.shutdownNow();
        }
    }

    @Override
    public void processRestApiRpcRequest(ToDeviceRpcRequest request, Consumer<FromDeviceRpcResponse> responseConsumer, SecurityUser currentUser) {
        log.trace("[{}][{}] Processing REST API call to rule engine [{}]", request.getTenantId(), request.getId(), request.getDeviceId());
        UUID requestId = request.getId();
        localToRuleEngineRpcRequests.put(requestId, responseConsumer);
        sendRpcRequestToRuleEngine(request, currentUser);
        scheduleToRuleEngineTimeout(request, requestId);
    }

    @Override
    public void processRpcResponseFromRuleEngine(FromDeviceRpcResponse response) {
        log.trace("[{}] Received response to server-side RPC request from rule engine: [{}]", response.getId(), response);
        String requestId = response.getId();
        Consumer<FromDeviceRpcResponse> consumer = localToRuleEngineRpcRequests.remove(requestId);
        if (consumer != null) {
            consumer.accept(response);
        } else {
            log.trace("[{}] Unknown or stale rpc response received [{}]", requestId, response);
        }
    }

    @Override
    public void forwardRpcRequestToDeviceActor(ToDeviceRpcRequestActorMsg rpcMsg) {
        ToDeviceRpcRequest request = rpcMsg.getMsg();
        log.trace("[{}][{}] Processing local rpc call to device actor [{}]", request.getTenantId(), request.getId(), request.getDeviceId());
        UUID requestId = request.getId();
        localToDeviceRpcRequests.put(requestId, rpcMsg);
        actorContext.tellWithHighPriority(rpcMsg);
        scheduleToDeviceTimeout(request, requestId);
    }

    @Override
    public void processRpcResponseFromDeviceActor(FromDeviceRpcResponse response) {
        log.trace("[{}] Received response to server-side RPC request from device actor.", response.getId());
        String requestId = response.getId();
        ToDeviceRpcRequestActorMsg request = localToDeviceRpcRequests.remove(requestId);
        if (request != null) {
            sendRpcResponseToTbRuleEngine(request.getServiceId(), response);
        } else {
            log.trace("[{}] Unknown or stale rpc response received [{}]", requestId, response);
        }
    }

    @Override
    public void processRemoveRpc(RemoveRpcActorMsg removeRpcMsg) {
        log.trace("[{}][{}] Processing remove RPC [{}]", removeRpcMsg.getTenantId(), removeRpcMsg.getRequestId(), removeRpcMsg.getDeviceId());
        actorContext.tellWithHighPriority(removeRpcMsg);
    }

    private void sendRpcResponseToTbRuleEngine(String originServiceId, FromDeviceRpcResponse response) {
        if (serviceId.equals(originServiceId)) {
            if (tbRuleEngineRpcService.isPresent()) {
                tbRuleEngineRpcService.get().processRpcResponseFromDevice(response);
            } else {
                log.warn("Failed to find tbCoreRpcService for local service. Possible duplication of serviceIds.");
            }
        } else {
            clusterService.pushNotificationToRuleEngine(originServiceId, response, null);
        }
    }

    private void sendRpcRequestToRuleEngine(ToDeviceRpcRequest msg, SecurityUser currentUser) {
        ObjectNode entityNode = json.createObjectNode();
        TbMsgMetaData metaData = new TbMsgMetaData();
        metaData.putValue("requestUUID", msg.getId().toString());
        metaData.putValue("originServiceId", serviceId);
        metaData.putValue("expirationTime", Long.toString(msg.getExpirationTime()));
        metaData.putValue("oneway", Boolean.toString(msg.isOneway()));
        metaData.putValue(DataConstants.PERSISTENT, Boolean.toString(msg.isPersisted()));

        if (msg.getRetries() != null) {
            metaData.putValue(DataConstants.RETRIES, msg.getRetries().toString());
        }


        Device device = deviceService.getById(msg.getDeviceId().getId());
        if (device != null) {
            metaData.putValue("deviceName", device.getName());
            metaData.putValue("deviceType", device.getType());
        }

        entityNode.put("method", msg.getBody().getMethod());
        entityNode.put("params", msg.getBody().getParams());

        entityNode.put(DataConstants.ADDITIONAL_INFO, msg.getAdditionalInfo());

        try {
            TbMsg tbMsg = TbMsg.newMsg(DataConstants.RPC_CALL_FROM_SERVER_TO_DEVICE, msg.getDeviceId(), currentUser.getCustomerId(), metaData, TbMsgDataType.JSON, json.writeValueAsString(entityNode));
            clusterService.pushMsgToRuleEngine(msg.getTenantId().getId(), msg.getDeviceId(), tbMsg, null);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void scheduleToRuleEngineTimeout(ToDeviceRpcRequest request, UUID requestId) {
        long timeout = Math.max(0, request.getExpirationTime() - System.currentTimeMillis()) + TimeUnit.SECONDS.toMillis(1);
        log.trace("[{}] processing to rule engine request.", requestId);
        scheduler.schedule(() -> {
            log.trace("[{}] timeout for processing to rule engine request.", requestId);
            Consumer<FromDeviceRpcResponse> consumer = localToRuleEngineRpcRequests.remove(requestId);
            if (consumer != null) {
                consumer.accept(new FromDeviceRpcResponse(requestId.toString(), null, RpcError.TIMEOUT));
            }
        }, timeout, TimeUnit.MILLISECONDS);
    }

    private void scheduleToDeviceTimeout(ToDeviceRpcRequest request, UUID requestId) {
        long timeout = Math.max(0, request.getExpirationTime() - System.currentTimeMillis()) + TimeUnit.SECONDS.toMillis(1);
        log.trace("[{}] processing to device request.", requestId);
        scheduler.schedule(() -> {
            log.trace("[{}] timeout for to device request.", requestId);
            localToDeviceRpcRequests.remove(requestId);
        }, timeout, TimeUnit.MILLISECONDS);
    }

}
