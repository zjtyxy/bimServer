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
package com.ciat.bim.server.edge.rpc.processor;

import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.edge.EdgeEvent;
import com.ciat.bim.server.edge.gen.DownlinkMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.edge.gen.WidgetTypeUpdateMsg;
import com.ciat.bim.server.queue.util.EdgeUtils;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@TbCoreComponent
public class WidgetTypeEdgeProcessor extends BaseEdgeProcessor {

//    public DownlinkMsg processWidgetTypeToEdge(EdgeEvent edgeEvent, UpdateMsgType msgType, EdgeEventActionType edgeEdgeEventActionType) {
//        WidgetTypeId widgetTypeId = new WidgetTypeId(edgeEvent.getEntityId());
//        DownlinkMsg downlinkMsg = null;
//        switch (edgeEdgeEventActionType) {
//            case ADDED:
//            case UPDATED:
//                WidgetType widgetType = widgetTypeService.findWidgetTypeById(edgeEvent.getTenantId(), widgetTypeId);
//                if (widgetType != null) {
//                    WidgetTypeUpdateMsg widgetTypeUpdateMsg =
//                            widgetTypeMsgConstructor.constructWidgetTypeUpdateMsg(msgType, widgetType);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addWidgetTypeUpdateMsg(widgetTypeUpdateMsg)
//                            .build();
//                }
//                break;
//            case DELETED:
//                WidgetTypeUpdateMsg widgetTypeUpdateMsg =
//                        widgetTypeMsgConstructor.constructWidgetTypeDeleteMsg(widgetTypeId);
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addWidgetTypeUpdateMsg(widgetTypeUpdateMsg)
//                        .build();
//                break;
//        }
//        return downlinkMsg;
//    }

}
