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
import com.ciat.bim.server.edge.gen.CustomerUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.utils.JacksonUtil;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class CustomerMsgConstructor {

//    public CustomerUpdateMsg constructCustomerUpdatedMsg(UpdateMsgType msgType, Customer customer) {
//        CustomerUpdateMsg.Builder builder = CustomerUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(customer.getId().getId().getMostSignificantBits())
//                .setIdLSB(customer.getId().getId().getLeastSignificantBits())
//                .setTitle(customer.getTitle());
//        if (customer.getCountry() != null) {
//            builder.setCountry(customer.getCountry());
//        }
//        if (customer.getState() != null) {
//            builder.setState(customer.getState());
//        }
//        if (customer.getCity() != null) {
//            builder.setCity(customer.getCity());
//        }
//        if (customer.getAddress() != null) {
//            builder.setAddress(customer.getAddress());
//        }
//        if (customer.getAddress2() != null) {
//            builder.setAddress2(customer.getAddress2());
//        }
//        if (customer.getZip() != null) {
//            builder.setZip(customer.getZip());
//        }
//        if (customer.getPhone() != null) {
//            builder.setPhone(customer.getPhone());
//        }
//        if (customer.getEmail() != null) {
//            builder.setEmail(customer.getEmail());
//        }
//        if (customer.getAdditionalInfo() != null) {
//            builder.setAdditionalInfo(JacksonUtil.toString(customer.getAdditionalInfo()));
//        }
//        return builder.build();
//    }
//
//    public CustomerUpdateMsg constructCustomerDeleteMsg(CustomerId customerId) {
//        return CustomerUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(customerId.getId().getMostSignificantBits())
//                .setIdLSB(customerId.getId().getLeastSignificantBits()).build();
//    }
}
