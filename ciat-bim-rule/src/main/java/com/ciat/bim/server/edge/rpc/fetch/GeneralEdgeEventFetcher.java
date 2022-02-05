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


@AllArgsConstructor
public class GeneralEdgeEventFetcher implements EdgeEventFetcher {
//
//    private final Long queueStartTs;
//    private final EdgeEventService edgeEventService;
//
//    @Override
//    public PageLink getPageLink(int pageSize) {
//        return new TimePageLink(
//                pageSize,
//                0,
//                null,
//                new SortOrder("createdTime", SortOrder.Direction.ASC),
//                queueStartTs,
//                null);
//    }
//
//    @Override
//    public PageData<EdgeEvent> fetchEdgeEvents(TenantId tenantId, Edge edge, PageLink pageLink) {
//        return edgeEventService.findEdgeEvents(tenantId, edge.getId(), (TimePageLink) pageLink, true);
//    }
}