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

import com.ciat.bim.server.edge.gen.RelationUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.utils.JacksonUtil;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class RelationMsgConstructor {

//    public RelationUpdateMsg constructRelationUpdatedMsg(UpdateMsgType msgType, EntityRelation entityRelation) {
//        RelationUpdateMsg.Builder builder = RelationUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setFromIdMSB(entityRelation.getFrom().getId().getMostSignificantBits())
//                .setFromIdLSB(entityRelation.getFrom().getId().getLeastSignificantBits())
//                .setFromEntityType(entityRelation.getFrom().getEntityType().name())
//                .setToIdMSB(entityRelation.getTo().getId().getMostSignificantBits())
//                .setToIdLSB(entityRelation.getTo().getId().getLeastSignificantBits())
//                .setToEntityType(entityRelation.getTo().getEntityType().name())
//                .setType(entityRelation.getType());
//        if (entityRelation.getAdditionalInfo() != null) {
//            builder.setAdditionalInfo(JacksonUtil.toString(entityRelation.getAdditionalInfo()));
//        }
//        if (entityRelation.getTypeGroup() != null) {
//            builder.setTypeGroup(entityRelation.getTypeGroup().name());
//        }
//        return builder.build();
//    }
}
