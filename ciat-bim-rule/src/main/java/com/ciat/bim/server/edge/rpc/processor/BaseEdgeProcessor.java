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

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class BaseEdgeProcessor {

//    protected static final ObjectMapper mapper = new ObjectMapper();
//
//    protected static final int DEFAULT_PAGE_SIZE = 1000;
//
//    @Autowired
//    protected RuleChainService ruleChainService;
//
//    @Autowired
//    protected AlarmService alarmService;
//
//    @Autowired
//    protected DeviceService deviceService;
//
//    @Autowired
//    protected TbDeviceProfileCache deviceProfileCache;
//
//    @Autowired
//    protected DashboardService dashboardService;
//
//    @Autowired
//    protected AssetService assetService;
//
//    @Autowired
//    protected EntityViewService entityViewService;
//
//    @Autowired
//    protected EdgeService edgeService;
//
//    @Autowired
//    protected CustomerService customerService;
//
//    @Autowired
//    protected UserService userService;
//
//    @Autowired
//    protected DeviceProfileService deviceProfileService;
//
//    @Autowired
//    protected RelationService relationService;
//
//    @Autowired
//    protected DeviceCredentialsService deviceCredentialsService;
//
//    @Autowired
//    protected AttributesService attributesService;
//
//    @Autowired
//    protected TbClusterService tbClusterService;
//
//    @Autowired
//    protected DeviceStateService deviceStateService;
//
//    @Autowired
//    protected EdgeEventService edgeEventService;
//
//    @Autowired
//    protected WidgetsBundleService widgetsBundleService;
//
//    @Autowired
//    protected WidgetTypeService widgetTypeService;
//
//    @Autowired
//    protected EntityDataMsgConstructor entityDataMsgConstructor;
//
//    @Autowired
//    protected RuleChainMsgConstructor ruleChainMsgConstructor;
//
//    @Autowired
//    protected AlarmMsgConstructor alarmMsgConstructor;
//
//    @Autowired
//    protected DeviceMsgConstructor deviceMsgConstructor;
//
//    @Autowired
//    protected AssetMsgConstructor assetMsgConstructor;
//
//    @Autowired
//    protected EntityViewMsgConstructor entityViewMsgConstructor;
//
//    @Autowired
//    protected DashboardMsgConstructor dashboardMsgConstructor;
//
//    @Autowired
//    protected RelationMsgConstructor relationMsgConstructor;
//
//    @Autowired
//    protected UserMsgConstructor userMsgConstructor;
//
//    @Autowired
//    protected CustomerMsgConstructor customerMsgConstructor;
//
//    @Autowired
//    protected DeviceProfileMsgConstructor deviceProfileMsgConstructor;
//
//    @Autowired
//    protected WidgetsBundleMsgConstructor widgetsBundleMsgConstructor;
//
//    @Autowired
//    protected WidgetTypeMsgConstructor widgetTypeMsgConstructor;
//
//    @Autowired
//    protected AdminSettingsMsgConstructor adminSettingsMsgConstructor;
//
//    @Autowired
//    protected DbCallbackExecutorService dbCallbackExecutorService;
//
//    protected void saveEdgeEvent(TenantId tenantId,
//                                 EdgeId edgeId,
//                                 EdgeEventType type,
//                                 EdgeEventActionType action,
//                                 EntityId entityId,
//                                 JsonNode body) {
//        log.debug("Pushing event to edge queue. tenantId [{}], edgeId [{}], type[{}], " +
//                        "action [{}], entityId [{}], body [{}]",
//                tenantId, edgeId, type, action, entityId, body);
//
//        EdgeEvent edgeEvent = new EdgeEvent();
//        edgeEvent.setTenantId(tenantId);
//        edgeEvent.setEdgeId(edgeId);
//        edgeEvent.setType(type);
//        edgeEvent.setAction(action);
//        if (entityId != null) {
//            edgeEvent.setEntityId(entityId.getId());
//        }
//        edgeEvent.setBody(body);
//        edgeEventService.save(edgeEvent);
//        tbClusterService.onEdgeEventUpdate(tenantId, edgeId);
//    }
//
//    protected CustomerId getCustomerIdIfEdgeAssignedToCustomer(HasCustomerId hasCustomerIdEntity, Edge edge) {
//        if (!edge.getCustomerId().isNullUid() && edge.getCustomerId().equals(hasCustomerIdEntity.getCustomerId())) {
//            return edge.getCustomerId();
//        } else {
//            return null;
//        }
//    }
//
//    protected void processActionForAllEdges(TenantId tenantId, EdgeEventType type, EdgeEventActionType actionType, EntityId entityId) {
//        PageLink pageLink = new PageLink(DEFAULT_PAGE_SIZE);
//        PageData<Edge> pageData;
//        do {
//            pageData = edgeService.findEdgesByTenantId(tenantId, pageLink);
//            if (pageData != null && pageData.getData() != null && !pageData.getData().isEmpty()) {
//                for (Edge edge : pageData.getData()) {
//                    saveEdgeEvent(tenantId, edge.getId(), type, actionType, entityId, null);
//                }
//                if (pageData.hasNext()) {
//                    pageLink = pageLink.nextPageLink();
//                }
//            }
//        } while (pageData != null && pageData.hasNext());
//    }
}
