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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BasePageableEdgeEventFetcher<T> implements EdgeEventFetcher {
//
//    @Override
//    public PageLink getPageLink(int pageSize) {
//        return new PageLink(pageSize);
//    }
//
//    @Override
//    public PageData<EdgeEvent> fetchEdgeEvents(TenantId tenantId, Edge edge, PageLink pageLink) {
//        log.trace("[{}] start fetching edge events [{}]", tenantId, edge.getId());
//        PageData<T> pageData = fetchPageData(tenantId, edge, pageLink);
//        List<EdgeEvent> result = new ArrayList<>();
//        if (!pageData.getData().isEmpty()) {
//            for (T entity : pageData.getData()) {
//                result.add(constructEdgeEvent(tenantId, edge, entity));
//            }
//        }
//        return new PageData<>(result, pageData.getTotalPages(), pageData.getTotalElements(), pageData.hasNext());
//    }
//
//    abstract PageData<T> fetchPageData(TenantId tenantId, Edge edge, PageLink pageLink);
//
//    abstract EdgeEvent constructEdgeEvent(TenantId tenantId, Edge edge, T entity);
}
