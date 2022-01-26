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

import com.ciat.bim.server.queue.util.TbCoreComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@TbCoreComponent
public class CustomerEdgeProcessor extends BaseEdgeProcessor {

//    public DownlinkMsg processCustomerToEdge(EdgeEvent edgeEvent, UpdateMsgType msgType, EdgeEventActionType action) {
//        CustomerId customerId = new CustomerId(edgeEvent.getEntityId());
//        DownlinkMsg downlinkMsg = null;
//        switch (action) {
//            case ADDED:
//            case UPDATED:
//                Customer customer = customerService.findCustomerById(edgeEvent.getTenantId(), customerId);
//                if (customer != null) {
//                    CustomerUpdateMsg customerUpdateMsg =
//                            customerMsgConstructor.constructCustomerUpdatedMsg(msgType, customer);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addCustomerUpdateMsg(customerUpdateMsg)
//                            .build();
//                }
//                break;
//            case DELETED:
//                CustomerUpdateMsg customerUpdateMsg =
//                        customerMsgConstructor.constructCustomerDeleteMsg(customerId);
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addCustomerUpdateMsg(customerUpdateMsg)
//                        .build();
//                break;
//        }
//        return downlinkMsg;
//    }
//
//    public void processCustomerNotification(TenantId tenantId, TransportProtos.EdgeNotificationMsgProto edgeNotificationMsg) {
//        EdgeEventActionType actionType = EdgeEventActionType.valueOf(edgeNotificationMsg.getAction());
//        EdgeEventType type = EdgeEventType.valueOf(edgeNotificationMsg.getType());
//        UUID uuid = new UUID(edgeNotificationMsg.getEntityIdMSB(), edgeNotificationMsg.getEntityIdLSB());
//        CustomerId customerId = new CustomerId(EntityIdFactory.getByEdgeEventTypeAndUuid(type, uuid).getId());
//        switch (actionType) {
//            case UPDATED:
//                PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//                PageData<Edge> pageData;
//                do {
//                    pageData = edgeService.findEdgesByTenantIdAndCustomerId(tenantId, customerId, pageLink);
//                    if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                        for (Edge edge : pageData.getData()) {
//                            saveEdgeEvent(tenantId, edge.getId(), type, actionType, customerId, null);
//                        }
//                        if (pageData.hasNext()) {
//                            pageLink = pageLink.nextPageLink();
//                        }
//                    }
//                } while (pageData != null && pageData.hasNext());
//                break;
//            case DELETED:
//                EdgeId edgeId = new EdgeId(new UUID(edgeNotificationMsg.getEdgeIdMSB(), edgeNotificationMsg.getEdgeIdLSB()));
//                saveEdgeEvent(tenantId, edgeId, type, actionType, customerId, null);
//                break;
//        }
//    }

}
