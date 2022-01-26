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
package com.ciat.bim.server.edge.rpc.constructor;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.server.edge.gen.DeviceRpcCallMsg;
import com.ciat.bim.server.edge.gen.RpcRequestMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeecg.modules.device.entity.Device;
import org.springframework.stereotype.Component;


import java.util.UUID;

@Component
@TbCoreComponent
public class DeviceMsgConstructor {

//    protected static final ObjectMapper mapper = new ObjectMapper();
//
//    public DeviceUpdateMsg constructDeviceUpdatedMsg(UpdateMsgType msgType, Device device, CustomerId customerId, String conflictName) {
//        DeviceUpdateMsg.Builder builder = DeviceUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(device.getId().getId().getMostSignificantBits())
//                .setIdLSB(device.getId().getId().getLeastSignificantBits())
//                .setName(device.getName())
//                .setType(device.getType());
//        if (device.getLabel() != null) {
//            builder.setLabel(device.getLabel());
//        }
//        if (customerId != null) {
//            builder.setCustomerIdMSB(customerId.getId().getMostSignificantBits());
//            builder.setCustomerIdLSB(customerId.getId().getLeastSignificantBits());
//        }
//        if (device.getDeviceProfileId() != null) {
//            builder.setDeviceProfileIdMSB(device.getDeviceProfileId().getId().getMostSignificantBits());
//            builder.setDeviceProfileIdLSB(device.getDeviceProfileId().getId().getLeastSignificantBits());
//        }
//        if (device.getAdditionalInfo() != null) {
//            builder.setAdditionalInfo(JacksonUtil.toString(device.getAdditionalInfo()));
//        }
//        if (conflictName != null) {
//            builder.setConflictName(conflictName);
//        }
//        return builder.build();
//    }
//
//    public DeviceCredentialsUpdateMsg constructDeviceCredentialsUpdatedMsg(DeviceCredentials deviceCredentials) {
//        DeviceCredentialsUpdateMsg.Builder builder = DeviceCredentialsUpdateMsg.newBuilder()
//                .setDeviceIdMSB(deviceCredentials.getDeviceId().getId().getMostSignificantBits())
//                .setDeviceIdLSB(deviceCredentials.getDeviceId().getId().getLeastSignificantBits());
//        if (deviceCredentials.getCredentialsType() != null) {
//            builder.setCredentialsType(deviceCredentials.getCredentialsType().name())
//                    .setCredentialsId(deviceCredentials.getCredentialsId());
//        }
//        if (deviceCredentials.getCredentialsValue() != null) {
//            builder.setCredentialsValue(deviceCredentials.getCredentialsValue());
//        }
//        return builder.build();
//    }
//
//    public DeviceUpdateMsg constructDeviceDeleteMsg(DeviceId deviceId) {
//        return DeviceUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(deviceId.getId().getMostSignificantBits())
//                .setIdLSB(deviceId.getId().getLeastSignificantBits()).build();
//    }
//
//    public DeviceRpcCallMsg constructDeviceRpcCallMsg(UUID deviceId, JsonNode body) {
//        int requestId = body.get("requestId").asInt();
//        boolean oneway = body.get("oneway").asBoolean();
//        UUID requestUUID = UUID.fromString(body.get("requestUUID").asText());
//        long expirationTime = body.get("expirationTime").asLong();
//        String method = body.get("method").asText();
//        String params = body.get("params").asText();
//
//        RpcRequestMsg.Builder requestBuilder = RpcRequestMsg.newBuilder();
//        requestBuilder.setMethod(method);
//        requestBuilder.setParams(params);
//        DeviceRpcCallMsg.Builder builder = DeviceRpcCallMsg.newBuilder()
//                .setDeviceIdMSB(deviceId.getMostSignificantBits())
//                .setDeviceIdLSB(deviceId.getLeastSignificantBits())
//                .setRequestUuidMSB(requestUUID.getMostSignificantBits())
//                .setRequestUuidLSB(requestUUID.getLeastSignificantBits())
//                .setRequestId(requestId)
//                .setExpirationTime(expirationTime)
//                .setOneway(oneway)
//                .setRequestMsg(requestBuilder.build());
//        return builder.build();
//    }
}
