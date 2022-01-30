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
package com.ciat.bim.transport;



import com.ciat.bim.data.device.DeviceTransportType;
import com.ciat.bim.server.rpc.RpcStatus;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.transport.auth.GetOrCreateDeviceFromGatewayResponse;
import com.ciat.bim.transport.auth.ValidateDeviceCredentialsResponse;
import org.jeecg.modules.device.entity.DeviceProfile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ashvayka on 04.10.18.
 */
public interface TransportService {

    GetEntityProfileResponseMsg getEntityProfile(GetEntityProfileRequestMsg msg);

    GetResourceResponseMsg getResource(GetResourceRequestMsg msg);

    GetSnmpDevicesResponseMsg getSnmpDevicesIds(GetSnmpDevicesRequestMsg requestMsg);

    GetDeviceResponseMsg getDevice(GetDeviceRequestMsg requestMsg);

    GetDeviceCredentialsResponseMsg getDeviceCredentials(GetDeviceCredentialsRequestMsg requestMsg);

    void process(DeviceTransportType transportType, ValidateDeviceTokenRequestMsg msg,
                 TransportServiceCallback<ValidateDeviceCredentialsResponse> callback);

    void process(DeviceTransportType transportType, ValidateBasicMqttCredRequestMsg msg,
                 TransportServiceCallback<ValidateDeviceCredentialsResponse> callback);

    void process(DeviceTransportType transportType, ValidateDeviceX509CertRequestMsg msg,
                 TransportServiceCallback<ValidateDeviceCredentialsResponse> callback);

    void process(ValidateDeviceLwM2MCredentialsRequestMsg msg,
                 TransportServiceCallback<ValidateDeviceCredentialsResponse> callback);

    void process(GetOrCreateDeviceFromGatewayRequestMsg msg,
                 TransportServiceCallback<GetOrCreateDeviceFromGatewayResponse> callback);

    void process(ProvisionDeviceRequestMsg msg,
                 TransportServiceCallback<ProvisionDeviceResponseMsg> callback);

    void onProfileUpdate(DeviceProfile deviceProfile);

    void process(LwM2MRequestMsg msg,
                 TransportServiceCallback<LwM2MResponseMsg> callback);

    void process(SessionInfoProto sessionInfo, SessionEventMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, PostTelemetryMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, PostAttributeMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, GetAttributeRequestMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, SubscribeToAttributeUpdatesMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, SubscribeToRPCMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, ToDeviceRpcResponseMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, ToServerRpcRequestMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, ToDeviceRpcRequestMsg msg, RpcStatus rpcStatus, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, SubscriptionInfoProto msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfo, ClaimDeviceMsg msg, TransportServiceCallback<Void> callback);

    void process(TransportToDeviceActorMsg msg, TransportServiceCallback<Void> callback);

    void process(SessionInfoProto sessionInfoProto, GetOtaPackageRequestMsg msg, TransportServiceCallback<GetOtaPackageResponseMsg> callback);

    SessionMetaData registerAsyncSession(SessionInfoProto sessionInfo, SessionMsgListener listener);

    SessionMetaData registerSyncSession(SessionInfoProto sessionInfo, SessionMsgListener listener, long timeout);

    void reportActivity(SessionInfoProto sessionInfo);

    void deregisterSession(SessionInfoProto sessionInfo);

    void log(SessionInfoProto sessionInfo, String msg);

    void notifyAboutUplink(SessionInfoProto sessionInfo, TransportProtos.UplinkNotificationMsg build, TransportServiceCallback<Void> empty);

    ExecutorService getCallbackExecutor();

    boolean hasSession(SessionInfoProto sessionInfo);

    void createGaugeStats(String openConnections, AtomicInteger connectionsCounter);
}
