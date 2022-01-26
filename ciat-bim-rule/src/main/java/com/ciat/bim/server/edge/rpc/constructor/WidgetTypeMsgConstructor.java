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
import com.ciat.bim.server.edge.gen.WidgetTypeUpdateMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.utils.JacksonUtil;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class WidgetTypeMsgConstructor {

//    public WidgetTypeUpdateMsg constructWidgetTypeUpdateMsg(UpdateMsgType msgType, WidgetType widgetType) {
//        WidgetTypeUpdateMsg.Builder builder = WidgetTypeUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(widgetType.getId().getId().getMostSignificantBits())
//                .setIdLSB(widgetType.getId().getId().getLeastSignificantBits());
//        if (widgetType.getBundleAlias() != null) {
//            builder.setBundleAlias(widgetType.getBundleAlias());
//        }
//        if (widgetType.getAlias() != null) {
//            builder.setAlias(widgetType.getAlias());
//        }
//        if (widgetType.getName() != null) {
//            builder.setName(widgetType.getName());
//        }
//        if (widgetType.getDescriptor() != null) {
//            builder.setDescriptorJson(JacksonUtil.toString(widgetType.getDescriptor()));
//        }
//        if (widgetType.getTenantId().equals(TenantId.SYS_TENANT_ID)) {
//            builder.setIsSystem(true);
//        }
//        return builder.build();
//    }
//
//    public WidgetTypeUpdateMsg constructWidgetTypeDeleteMsg(WidgetTypeId widgetTypeId) {
//        return WidgetTypeUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(widgetTypeId.getId().getMostSignificantBits())
//                .setIdLSB(widgetTypeId.getId().getLeastSignificantBits())
//                .build();
//    }
}
