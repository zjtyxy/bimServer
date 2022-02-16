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
package com.ciat.bim.server.telemetry;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.rule.engine.api.RuleEngineTelemetryService;
import com.google.common.util.concurrent.FutureCallback;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.TsKv;
import org.jeecg.modules.device.entity.TsKvLatest;


import java.util.List;

/**
 * Created by ashvayka on 27.03.18.
 */
public interface InternalTelemetryService extends RuleEngineTelemetryService {

    void saveAndNotifyInternal(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Integer> callback);

    void saveAndNotifyInternal(TenantId tenantId, EntityId entityId, List<TsKv> ts, long ttl, FutureCallback<Integer> callback);

    void saveAndNotifyInternal(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice, FutureCallback<Void> callback);

    void saveLatestAndNotifyInternal(TenantId tenantId, EntityId entityId, List<TsKv> ts, FutureCallback<Void> callback);

    void deleteAndNotifyInternal(TenantId tenantId, EntityId entityId, String scope, List<String> keys, FutureCallback<Void> callback);

    void deleteLatestInternal(TenantId tenantId, EntityId entityId, List<String> keys, FutureCallback<Void> callback);



}
