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

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.edge.Edge;
import com.ciat.bim.server.edge.EdgeEvent;
import com.ciat.bim.server.edge.gen.DownlinkMsg;
import com.ciat.bim.server.edge.gen.EntityViewUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.EdgeUtils;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@TbCoreComponent
public class EntityViewEdgeProcessor extends BaseEdgeProcessor {

//    public DownlinkMsg processEntityViewToEdge(Edge edge, EdgeEvent edgeEvent, UpdateMsgType msgType, EdgeEventActionType action) {
//        EntityViewId entityViewId = new EntityViewId(edgeEvent.getEntityId());
//        DownlinkMsg downlinkMsg = null;
//        switch (action) {
//            case ADDED:
//            case UPDATED:
//            case ASSIGNED_TO_EDGE:
//            case ASSIGNED_TO_CUSTOMER:
//            case UNASSIGNED_FROM_CUSTOMER:
//                EntityView entityView = entityViewService.findEntityViewById(edgeEvent.getTenantId(), entityViewId);
//                if (entityView != null) {
//                    CustomerId customerId = getCustomerIdIfEdgeAssignedToCustomer(entityView, edge);
//                    EntityViewUpdateMsg entityViewUpdateMsg =
//                            entityViewMsgConstructor.constructEntityViewUpdatedMsg(msgType, entityView, customerId);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addEntityViewUpdateMsg(entityViewUpdateMsg)
//                            .build();
//                }
//                break;
//            case DELETED:
//            case UNASSIGNED_FROM_EDGE:
//                EntityViewUpdateMsg entityViewUpdateMsg =
//                        entityViewMsgConstructor.constructEntityViewDeleteMsg(entityViewId);
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addEntityViewUpdateMsg(entityViewUpdateMsg)
//                        .build();
//                break;
//        }
//        return downlinkMsg;
//    }
}
