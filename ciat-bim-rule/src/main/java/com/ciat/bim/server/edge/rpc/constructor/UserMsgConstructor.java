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
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.edge.gen.UserUpdateMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class UserMsgConstructor {

//    public UserUpdateMsg constructUserUpdatedMsg(UpdateMsgType msgType, User user, CustomerId customerId) {
//        UserUpdateMsg.Builder builder = UserUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(user.getId().getId().getMostSignificantBits())
//                .setIdLSB(user.getId().getId().getLeastSignificantBits())
//                .setEmail(user.getEmail())
//                .setAuthority(user.getAuthority().name());
//        if (customerId != null) {
//            builder.setCustomerIdMSB(customerId.getId().getMostSignificantBits());
//            builder.setCustomerIdLSB(customerId.getId().getLeastSignificantBits());
//        }
//        if (user.getFirstName() != null) {
//            builder.setFirstName(user.getFirstName());
//        }
//        if (user.getLastName() != null) {
//            builder.setLastName(user.getLastName());
//        }
//        if (user.getAdditionalInfo() != null) {
//            builder.setAdditionalInfo(JacksonUtil.toString(user.getAdditionalInfo()));
//        }
//        return builder.build();
//    }
//
//    public UserUpdateMsg constructUserDeleteMsg(UserId userId) {
//        return UserUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(userId.getId().getMostSignificantBits())
//                .setIdLSB(userId.getId().getLeastSignificantBits()).build();
//    }
//
//    public UserCredentialsUpdateMsg constructUserCredentialsUpdatedMsg(UserCredentials userCredentials) {
//        UserCredentialsUpdateMsg.Builder builder = UserCredentialsUpdateMsg.newBuilder()
//                .setUserIdMSB(userCredentials.getUserId().getId().getMostSignificantBits())
//                .setUserIdLSB(userCredentials.getUserId().getId().getLeastSignificantBits())
//                .setEnabled(userCredentials.isEnabled())
//                .setPassword(userCredentials.getPassword());
//        return builder.build();
//    }
}
