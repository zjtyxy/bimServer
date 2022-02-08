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
package com.ciat.bim.server.dao.attributes;

import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.google.common.util.concurrent.ListenableFuture;
import org.jeecg.modules.device.entity.AttributeKv;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author Andrew Shvayka
 */
public interface AttributesDao {

    ListenableFuture<Optional<AttributeKv>> find(TenantId tenantId, EntityId entityId, String attributeType, String attributeKey);

    ListenableFuture<List<AttributeKv>> find(TenantId tenantId, EntityId entityId, String attributeType, Collection<String> attributeKey);

    ListenableFuture<List<AttributeKv>> findAll(TenantId tenantId, EntityId entityId, String attributeType);

    ListenableFuture<Void> save(TenantId tenantId, EntityId entityId, String attributeType, AttributeKv attribute);

    ListenableFuture<List<Void>> removeAll(TenantId tenantId, EntityId entityId, String attributeType, List<String> keys);

    List<String> findAllKeysByDeviceProfileId(TenantId tenantId, DeviceProfileId deviceProfileId);

    List<String> findAllKeysByEntityIds(TenantId tenantId, EntityType entityType, List<EntityId> entityIds);
}
