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
package com.ciat.bim.server.queue.queue;

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.queue.discovery.TenantRoutingInfo;
import com.ciat.bim.server.queue.discovery.TenantRoutingInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@ConditionalOnExpression("'${service.type:null}'=='tb-transport'")
public class TransportTenantRoutingInfoService implements TenantRoutingInfoService {

//    private TransportTenantProfileCache tenantProfileCache;
//
//    public TransportTenantRoutingInfoService(TransportTenantProfileCache tenantProfileCache) {
//        this.tenantProfileCache = tenantProfileCache;
//    }

    @Override
    public TenantRoutingInfo getRoutingInfo(String tenantId) {
        return  null;
//        TenantProfile profile = tenantProfileCache.get(tenantId);
//        return new TenantRoutingInfo(tenantId, profile.isIsolatedTbCore(), profile.isIsolatedTbRuleEngine());
    }

}