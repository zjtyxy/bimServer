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
package com.ciat.bim.server.usagerecord;


import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.data.ApiUsageStateId;

public interface ApiUsageStateService {

    ApiUsageState createDefaultApiUsageState(TenantId id, EntityId entityId);
//
//    ApiUsageState update(ApiUsageState apiUsageState);
//
    ApiUsageState findTenantApiUsageState(TenantId tenantId);
//
//    ApiUsageState findApiUsageStateByEntityId(EntityId entityId);
//
//    void deleteApiUsageStateByTenantId(TenantId tenantId);
//
//    void deleteApiUsageStateByEntityId(EntityId entityId);
//
//    ApiUsageState findApiUsageStateById(TenantId tenantId, ApiUsageStateId id);
}
