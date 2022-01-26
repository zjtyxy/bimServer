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

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.edge.gen.WidgetsBundleUpdateMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;

@Component
@TbCoreComponent
public class WidgetsBundleMsgConstructor {

//    public WidgetsBundleUpdateMsg constructWidgetsBundleUpdateMsg(UpdateMsgType msgType, WidgetsBundle widgetsBundle) {
//        WidgetsBundleUpdateMsg.Builder builder = WidgetsBundleUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(widgetsBundle.getId().getId().getMostSignificantBits())
//                .setIdLSB(widgetsBundle.getId().getId().getLeastSignificantBits())
//                .setTitle(widgetsBundle.getTitle())
//                .setAlias(widgetsBundle.getAlias());
//        if (widgetsBundle.getImage() != null) {
//            builder.setImage(ByteString.copyFrom(widgetsBundle.getImage().getBytes(StandardCharsets.UTF_8)));
//        }
//        if (widgetsBundle.getDescription() != null) {
//            builder.setDescription(widgetsBundle.getDescription());
//        }
//        if (widgetsBundle.getTenantId().equals(TenantId.SYS_TENANT_ID)) {
//            builder.setIsSystem(true);
//        }
//        return builder.build();
//    }
//
//    public WidgetsBundleUpdateMsg constructWidgetsBundleDeleteMsg(WidgetsBundleId widgetsBundleId) {
//        return WidgetsBundleUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(widgetsBundleId.getId().getMostSignificantBits())
//                .setIdLSB(widgetsBundleId.getId().getLeastSignificantBits())
//                .build();
//    }
}
