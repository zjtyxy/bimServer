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
import com.ciat.bim.data.id.DashboardId;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.edge.EdgeEvent;
import com.ciat.bim.server.edge.gen.DashboardUpdateMsg;
import com.ciat.bim.server.edge.gen.DownlinkMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.EdgeUtils;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@TbCoreComponent
public class DashboardEdgeProcessor extends BaseEdgeProcessor {
//
//    public DownlinkMsg processDashboardToEdge(Edge edge, EdgeEvent edgeEvent, UpdateMsgType msgType, EdgeEventActionType action) {
//        DashboardId dashboardId = new DashboardId(edgeEvent.getEntityId());
//        DownlinkMsg downlinkMsg = null;
//        switch (action) {
//            case ADDED:
//            case UPDATED:
//            case ASSIGNED_TO_EDGE:
//            case ASSIGNED_TO_CUSTOMER:
//            case UNASSIGNED_FROM_CUSTOMER:
//                Dashboard dashboard = dashboardService.findDashboardById(edgeEvent.getTenantId(), dashboardId);
//                if (dashboard != null) {
//                    CustomerId customerId = null;
//                    if (!edge.getCustomerId().isNullUid() && dashboard.isAssignedToCustomer(edge.getCustomerId())) {
//                        customerId = edge.getCustomerId();
//                    }
//                    DashboardUpdateMsg dashboardUpdateMsg =
//                            dashboardMsgConstructor.constructDashboardUpdatedMsg(msgType, dashboard, customerId);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addDashboardUpdateMsg(dashboardUpdateMsg)
//                            .build();
//                }
//                break;
//            case DELETED:
//            case UNASSIGNED_FROM_EDGE:
//                DashboardUpdateMsg dashboardUpdateMsg =
//                        dashboardMsgConstructor.constructDashboardDeleteMsg(dashboardId);
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addDashboardUpdateMsg(dashboardUpdateMsg)
//                        .build();
//                break;
//        }
//        return downlinkMsg;
//    }
}
