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
package com.ciat.bim.server.queue;

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.queue.discovery.TenantRoutingInfo;
import com.ciat.bim.server.queue.discovery.TenantRoutingInfoService;
import com.ciat.bim.tenant.TbTenantProfileCache;



import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
//@ConditionalOnExpression("'${service.type:null}'=='monolith' || '${service.type:null}'=='tb-core' || '${service.type:null}'=='tb-rule-engine'")
public class DefaultTenantRoutingInfoService implements TenantRoutingInfoService {
    @Autowired
    private  ITenantService tenantService;
    @Autowired
    private  TbTenantProfileCache tenantProfileCache;

    @Override
    public TenantRoutingInfo getRoutingInfo(TenantId tenantId) {
        Tenant tenant = tenantService.getById(tenantId);
        if (tenant != null) {
            TenantProfile tenantProfile = tenantProfileCache.get(tenant.getTenantProfileId());
            return new TenantRoutingInfo(tenantId, tenantProfile.getIsolatedTbCore(), tenantProfile.getIsolatedTbRuleEngine());
        } else {
            throw new RuntimeException("Tenant not found!");
        }
    }
}
