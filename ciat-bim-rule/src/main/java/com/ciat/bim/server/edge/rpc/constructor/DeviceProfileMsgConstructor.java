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

import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.edge.gen.DeviceProfileUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;

@Component
@TbCoreComponent
public class DeviceProfileMsgConstructor {

//    @Autowired
//    private DataDecodingEncodingService dataDecodingEncodingService;
//
//    public DeviceProfileUpdateMsg constructDeviceProfileUpdatedMsg(UpdateMsgType msgType, DeviceProfile deviceProfile) {
//        DeviceProfileUpdateMsg.Builder builder = DeviceProfileUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(deviceProfile.getId().getId().getMostSignificantBits())
//                .setIdLSB(deviceProfile.getId().getId().getLeastSignificantBits())
//                .setName(deviceProfile.getName())
//                .setDefault(deviceProfile.isDefault())
//                .setType(deviceProfile.getType().name())
//                .setProfileDataBytes(ByteString.copyFrom(dataDecodingEncodingService.encode(deviceProfile.getProfileData())));
//        // TODO: @voba - add possibility to setup edge rule chain as device profile default
////        if (deviceProfile.getDefaultRuleChainId() != null) {
////            builder.setDefaultRuleChainIdMSB(deviceProfile.getDefaultRuleChainId().getId().getMostSignificantBits())
////                    .setDefaultRuleChainIdLSB(deviceProfile.getDefaultRuleChainId().getId().getLeastSignificantBits());
////        }
////        if (deviceProfile.getDefaultQueueName() != null) {
////            builder.setDefaultQueueName(deviceProfile.getDefaultQueueName());
////        }
//        if (deviceProfile.getDescription() != null) {
//            builder.setDescription(deviceProfile.getDescription());
//        }
//        if (deviceProfile.getTransportType() != null) {
//            builder.setTransportType(deviceProfile.getTransportType().name());
//        }
//        if (deviceProfile.getProvisionType() != null) {
//            builder.setProvisionType(deviceProfile.getProvisionType().name());
//        }
//        if (deviceProfile.getProvisionDeviceKey() != null) {
//            builder.setProvisionDeviceKey(deviceProfile.getProvisionDeviceKey());
//        }
//        if (deviceProfile.getImage() != null) {
//            builder.setImage(ByteString.copyFrom(deviceProfile.getImage().getBytes(StandardCharsets.UTF_8)));
//        }
//        return builder.build();
//    }
//
//    public DeviceProfileUpdateMsg constructDeviceProfileDeleteMsg(DeviceProfileId deviceProfileId) {
//        return DeviceProfileUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(deviceProfileId.getId().getMostSignificantBits())
//                .setIdLSB(deviceProfileId.getId().getLeastSignificantBits()).build();
//    }

}
