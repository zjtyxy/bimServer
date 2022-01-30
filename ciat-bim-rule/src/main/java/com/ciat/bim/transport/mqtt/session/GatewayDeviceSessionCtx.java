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
package com.ciat.bim.transport.mqtt.session;

import com.ciat.bim.server.rpc.RpcStatus;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.transport.TransportService;
import com.ciat.bim.transport.TransportServiceCallback;
import com.ciat.bim.transport.auth.TransportDeviceInfo;
import com.ciat.bim.transport.session.SessionMsgListener;

import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.mqtt.MqttMessage;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.DeviceProfile;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ashvayka on 19.01.17.
 */
@Slf4j
public class GatewayDeviceSessionCtx extends MqttDeviceAwareSessionContext implements SessionMsgListener {

    private final GatewaySessionHandler parent;
    private final TransportService transportService;

    public GatewayDeviceSessionCtx(GatewaySessionHandler parent, TransportDeviceInfo deviceInfo,
                                   DeviceProfile deviceProfile, ConcurrentMap<MqttTopicMatcher, Integer> mqttQoSMap,
                                   TransportService transportService) {
        super(UUID.randomUUID(), mqttQoSMap);
        this.parent = parent;
        setSessionInfo(TransportProtos.SessionInfoProto.newBuilder()
                .setNodeId(parent.getNodeId())
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setDeviceIdMSB(Long.parseLong(deviceInfo.getDeviceId().getId()))
                .setDeviceIdLSB(Long.parseLong(deviceInfo.getDeviceId().getId()))
                .setTenantIdMSB(Long.parseLong(deviceInfo.getTenantId().getId()))
                .setTenantIdLSB(Long.parseLong(deviceInfo.getTenantId().getId()))
                .setCustomerIdMSB(Long.parseLong(deviceInfo.getCustomerId().getId()))
                .setCustomerIdLSB(Long.parseLong(deviceInfo.getCustomerId().getId()))
                .setDeviceName(deviceInfo.getDeviceName())
                .setDeviceType(deviceInfo.getDeviceType())
                .setGwSessionIdMSB(parent.getSessionId().getMostSignificantBits())
                .setGwSessionIdLSB(parent.getSessionId().getLeastSignificantBits())
                .setDeviceProfileIdMSB(Long.parseLong(deviceInfo.getDeviceProfileId().getId()))
                .setDeviceProfileIdLSB(Long.parseLong(deviceInfo.getDeviceProfileId().getId()))
                .build());
        setDeviceInfo(deviceInfo);
        setConnected(true);
        setDeviceProfile(deviceProfile);
        this.transportService = transportService;
    }

    @Override
    public UUID getSessionId() {
        return sessionId;
    }

    @Override
    public int nextMsgId() {
        return parent.nextMsgId();
    }

    @Override
    public void onGetAttributesResponse(TransportProtos.GetAttributeResponseMsg response) {
        try {
            parent.getPayloadAdaptor().convertToGatewayPublish(this, getDeviceInfo().getDeviceName(), response).ifPresent(parent::writeAndFlush);
        } catch (Exception e) {
            log.trace("[{}] Failed to convert device attributes response to MQTT msg", sessionId, e);
        }
    }

    @Override
    public void onAttributeUpdate(UUID sessionId, TransportProtos.AttributeUpdateNotificationMsg notification) {
        log.trace("[{}] Received attributes update notification to device", sessionId);
        try {
            parent.getPayloadAdaptor().convertToGatewayPublish(this, getDeviceInfo().getDeviceName(), notification).ifPresent(parent::writeAndFlush);
        } catch (Exception e) {
            log.trace("[{}] Failed to convert device attributes response to MQTT msg", sessionId, e);
        }
    }

    @Override
    public void onToDeviceRpcRequest(UUID sessionId, TransportProtos.ToDeviceRpcRequestMsg request) {
        log.trace("[{}] Received RPC command to device", sessionId);
        try {
            parent.getPayloadAdaptor().convertToGatewayPublish(this, getDeviceInfo().getDeviceName(), request).ifPresent(
                    payload -> {
                        ChannelFuture channelFuture = parent.writeAndFlush(payload);
                        if (request.getPersisted()) {
                            channelFuture.addListener(result -> {
                                if (result.cause() == null) {
                                    if (!isAckExpected(payload)) {
                                        transportService.process(getSessionInfo(), request, RpcStatus.DELIVERED, TransportServiceCallback.EMPTY);
                                    } else if (request.getPersisted()) {
                                        transportService.process(getSessionInfo(), request, RpcStatus.SENT, TransportServiceCallback.EMPTY);

                                    }
                                }
                            });
                        }
                    }
            );
        } catch (Exception e) {
            transportService.process(getSessionInfo(),
                    TransportProtos.ToDeviceRpcResponseMsg.newBuilder()
                            .setRequestId(request.getRequestId()).setError("Failed to convert device RPC command to MQTT msg").build(), TransportServiceCallback.EMPTY);
            log.trace("[{}] Failed to convert device attributes response to MQTT msg", sessionId, e);
        }
    }

    @Override
    public void onRemoteSessionCloseCommand(UUID sessionId, TransportProtos.SessionCloseNotificationProto sessionCloseNotification) {
        log.trace("[{}] Received the remote command to close the session: {}", sessionId, sessionCloseNotification.getMessage());
        parent.deregisterSession(getDeviceInfo().getDeviceName());
    }

    @Override
    public void onToServerRpcResponse(TransportProtos.ToServerRpcResponseMsg toServerResponse) {
        // This feature is not supported in the TB IoT Gateway yet.
    }

    private boolean isAckExpected(MqttMessage message) {
        return message.fixedHeader().qosLevel().value() > 0;
    }

}
