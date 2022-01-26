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
import com.ciat.bim.server.queue.util.TbCoreComponent;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class EntityViewMsgConstructor {

//    public EntityViewUpdateMsg constructEntityViewUpdatedMsg(UpdateMsgType msgType, EntityView entityView, CustomerId customerId) {
//        EdgeEntityType entityType;
//        switch (entityView.getEntityId().getEntityType()) {
//            case DEVICE:
//                entityType = EdgeEntityType.DEVICE;
//                break;
//            case ASSET:
//                entityType = EdgeEntityType.ASSET;
//                break;
//            default:
//                throw new RuntimeException("Unsupported entity type [" + entityView.getEntityId().getEntityType() + "]");
//        }
//        EntityViewUpdateMsg.Builder builder = EntityViewUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(entityView.getId().getId().getMostSignificantBits())
//                .setIdLSB(entityView.getId().getId().getLeastSignificantBits())
//                .setName(entityView.getName())
//                .setType(entityView.getType())
//                .setEntityIdMSB(entityView.getEntityId().getId().getMostSignificantBits())
//                .setEntityIdLSB(entityView.getEntityId().getId().getLeastSignificantBits())
//                .setEntityType(entityType);
//        if (customerId != null) {
//            builder.setCustomerIdMSB(customerId.getId().getMostSignificantBits());
//            builder.setCustomerIdLSB(customerId.getId().getLeastSignificantBits());
//        }
//        if (entityView.getAdditionalInfo() != null) {
//            builder.setAdditionalInfo(JacksonUtil.toString(entityView.getAdditionalInfo()));
//        }
//        return builder.build();
//    }
//
//    public EntityViewUpdateMsg constructEntityViewDeleteMsg(EntityViewId entityViewId) {
//        return EntityViewUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(entityViewId.getId().getMostSignificantBits())
//                .setIdLSB(entityViewId.getId().getLeastSignificantBits()).build();
//    }
}
