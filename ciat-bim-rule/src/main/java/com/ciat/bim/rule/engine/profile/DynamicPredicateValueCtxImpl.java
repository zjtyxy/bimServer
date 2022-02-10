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
package com.ciat.bim.rule.engine.profile;

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.rule.TbContext;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.Device;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
public class DynamicPredicateValueCtxImpl implements DynamicPredicateValueCtx {
    private final TenantId tenantId;
    private CustomerId customerId;
    private final DeviceId deviceId;
    private final TbContext ctx;

    public DynamicPredicateValueCtxImpl(TenantId tenantId, DeviceId deviceId, TbContext ctx) {
        this.tenantId = tenantId;
        this.deviceId = deviceId;
        this.ctx = ctx;
        resetCustomer();
    }

    @Override
    public EntityKeyValue getTenantValue(String key) {
        return getValue(tenantId, key);
    }

    @Override
    public EntityKeyValue getCustomerValue(String key) {
        return customerId == null || customerId.isNullUid() ? null : getValue(customerId, key);
    }

    @Override
    public void resetCustomer() {
        Device device = ctx.getDeviceService().getById(deviceId);
        if (device != null) {
            this.customerId = new CustomerId(device.getCustomerId());
        }
    }

    private EntityKeyValue getValue(EntityId entityId, String key) {
        try {
            List<AttributeKv> entry = ctx.getAttributesService().find(tenantId.getId(), entityId, DataConstants.SERVER_SCOPE, Collections.singleton(key)).get();
            if (!entry.isEmpty()) {
                return DeviceState.toEntityValue(entry.get(0));
            }
        } catch (InterruptedException | ExecutionException e) {
            log.warn("Failed to get attribute by key: {} for {}: [{}]", key, entityId.getEntityType(), entityId.getId());
        }
        return null;
    }
}
