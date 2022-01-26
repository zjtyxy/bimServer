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
package com.ciat.bim.server.edge.rpc;



import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.server.edge.Edge;
import com.ciat.bim.server.edge.EdgeContextComponent;
import com.ciat.bim.server.edge.rpc.fetch.*;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class EdgeSyncCursor {

    List<EdgeEventFetcher> fetchers = new LinkedList<>();

    int currentIdx = 0;
//
//    public EdgeSyncCursor(EdgeContextComponent ctx, Edge edge) {
//        fetchers.add(new SystemWidgetsBundlesEdgeEventFetcher(ctx.getWidgetsBundleService()));
//        fetchers.add(new TenantWidgetsBundlesEdgeEventFetcher(ctx.getWidgetsBundleService()));
//        fetchers.add(new DeviceProfilesEdgeEventFetcher(ctx.getDeviceProfileService()));
//        fetchers.add(new RuleChainsEdgeEventFetcher(ctx.getRuleChainService()));
//        fetchers.add(new TenantAdminUsersEdgeEventFetcher(ctx.getUserService()));
//        if (edge.getCustomerId() != null && !EntityId.NULL_UUID.equals(edge.getCustomerId().getId())) {
//            fetchers.add(new CustomerEdgeEventFetcher());
//            fetchers.add(new CustomerUsersEdgeEventFetcher(ctx.getUserService(), edge.getCustomerId()));
//        }
//        fetchers.add(new AdminSettingsEdgeEventFetcher(ctx.getAdminSettingsService(), ctx.getFreemarkerConfig()));
//        fetchers.add(new AssetsEdgeEventFetcher(ctx.getAssetService()));
//        fetchers.add(new DashboardsEdgeEventFetcher(ctx.getDashboardService()));
//    }
//
//    public boolean hasNext() {
//        return fetchers.size() > currentIdx;
//    }
//
//    public EdgeEventFetcher getNext() {
//        if (!hasNext()) {
//            throw new NoSuchElementException();
//        }
//        EdgeEventFetcher edgeEventFetcher = fetchers.get(currentIdx);
//        currentIdx++;
//        return edgeEventFetcher;
//    }

    public int getCurrentIdx() {
        return currentIdx;
    }
}
