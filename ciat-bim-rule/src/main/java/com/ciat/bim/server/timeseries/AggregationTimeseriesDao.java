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
package com.ciat.bim.server.timeseries;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.kv.ReadTsKvQuery;
import com.google.common.util.concurrent.ListenableFuture;
import org.jeecg.modules.device.entity.TsKv;


import java.util.List;

public interface AggregationTimeseriesDao {

    ListenableFuture<List<TsKv>> findAllAsync(TenantId tenantId, EntityId entityId, ReadTsKvQuery query);
}
