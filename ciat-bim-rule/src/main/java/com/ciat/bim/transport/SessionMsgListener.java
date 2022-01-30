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



import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by ashvayka on 04.10.18.
 */
public interface SessionMsgListener {

    void onGetAttributesResponse(TransportProtos.GetAttributeResponseMsg getAttributesResponse);

    void onAttributeUpdate(UUID sessionId, AttributeUpdateNotificationMsg attributeUpdateNotification);

    void onRemoteSessionCloseCommand(UUID sessionId, SessionCloseNotificationProto sessionCloseNotification);

    void onToDeviceRpcRequest(UUID sessionId, ToDeviceRpcRequestMsg toDeviceRequest);

    void onToServerRpcResponse(ToServerRpcResponseMsg toServerResponse);

    default void onUplinkNotification(UplinkNotificationMsg notificationMsg){};

    default void onToTransportUpdateCredentials(ToTransportUpdateCredentialsProto toTransportUpdateCredentials){}

    default void onDeviceProfileUpdate(TransportProtos.SessionInfoProto newSessionInfo, DeviceProfile deviceProfile) {}

    default void onDeviceUpdate(TransportProtos.SessionInfoProto sessionInfo, Device device,
                                Optional<DeviceProfile> deviceProfileOpt) {}

    default void onDeviceDeleted(DeviceId deviceId) {}

    default void onResourceUpdate(TransportProtos.ResourceUpdateMsg resourceUpdateMsgOpt) {}

    default void onResourceDelete(TransportProtos.ResourceDeleteMsg resourceUpdateMsgOpt) {}
}
