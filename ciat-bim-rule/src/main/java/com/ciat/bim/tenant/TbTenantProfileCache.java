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
package com.ciat.bim.tenant;


import com.ciat.bim.data.id.TenantProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import org.jeecg.modules.tenant.entity.TenantProfile;


import java.util.function.Consumer;

public interface TbTenantProfileCache {

    TenantProfile get(TenantId tenantId);

    TenantProfile get(String tenantProfileId);

    void put(TenantProfile profile);

    void evict(TenantProfileId id);

    void evict(TenantId id);

    void addListener(TenantId tenantId, EntityId listenerId, Consumer<TenantProfile> profileListener);

    void removeListener(TenantId tenantId, EntityId listenerId);

}
