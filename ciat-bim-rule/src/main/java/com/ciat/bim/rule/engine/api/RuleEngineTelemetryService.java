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
package com.ciat.bim.rule.engine.api;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.google.common.util.concurrent.FutureCallback;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.TsKv;
import org.jeecg.modules.device.entity.TsKvLatest;


import java.util.Collection;
import java.util.List;

/**
 * Created by ashvayka on 02.04.18.
 */
public interface RuleEngineTelemetryService {

    void saveAndNotify(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Void> callback);

    void saveAndNotify(TenantId tenantId, CustomerId id, EntityId entityId, List<TsKv> ts, long ttl, FutureCallback<Void> callback);

    void saveAndNotify(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, FutureCallback<Void> callback);

    void saveAndNotify(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice, FutureCallback<Void> callback);

    void saveLatestAndNotify(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Void> callback);

    void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, long value, FutureCallback<Void> callback);

    void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, String value, FutureCallback<Void> callback);

    void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, double value, FutureCallback<Void> callback);

    void saveAttrAndNotify(TenantId tenantId, EntityId entityId, String scope, String key, boolean value, FutureCallback<Void> callback);

    void deleteAndNotify(TenantId tenantId, EntityId entityId, String scope, List<String> keys, FutureCallback<Void> callback);

    void deleteLatest(TenantId tenantId, EntityId entityId, List<String> keys, FutureCallback<Void> callback);

    void deleteAllLatest(TenantId tenantId, EntityId entityId, FutureCallback<Collection<String>> callback);


}
