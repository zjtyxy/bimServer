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
package com.ciat.bim.server.edge.rpc.fetch;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@AllArgsConstructor
@Slf4j
public class DashboardsEdgeEventFetcher
        //extends BasePageableEdgeEventFetcher<DashboardInfo>
{

//    private final DashboardService dashboardService;
//
//    @Override
//    PageData<DashboardInfo> fetchPageData(TenantId tenantId, Edge edge, PageLink pageLink) {
//        return dashboardService.findDashboardsByTenantIdAndEdgeId(tenantId, edge.getId(), pageLink);
//    }
//
//    @Override
//    EdgeEvent constructEdgeEvent(TenantId tenantId, Edge edge, DashboardInfo dashboardInfo) {
//        return EdgeEventUtils.constructEdgeEvent(tenantId, edge.getId(), EdgeEventType.DASHBOARD,
//                EdgeEventActionType.ADDED, dashboardInfo.getId(), null);
//    }
}
