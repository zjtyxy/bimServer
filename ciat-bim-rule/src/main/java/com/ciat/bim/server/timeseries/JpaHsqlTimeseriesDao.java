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
import com.ciat.bim.server.dao.HsqlDao;
import com.ciat.bim.server.dao.SqlTsDao;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.TsKv;
import org.springframework.stereotype.Component;



@Component
@Slf4j
@SqlTsDao
@HsqlDao
public class JpaHsqlTimeseriesDao extends AbstractChunkedAggregationTimeseriesDao implements TimeseriesDao {

    @Override
    public ListenableFuture<Integer> save(TenantId tenantId, EntityId entityId, TsKv tsKvEntry, long ttl) {
        int dataPointDays = getDataPointDays(tsKvEntry, computeTtl(ttl));
        String strKey = tsKvEntry.getKey();
        Integer keyId = getOrSaveKeyId(strKey);
        TsKv entity = new TsKv();
        entity.setEntityId(entityId.getId());
        entity.setTs(tsKvEntry.getTs());
        entity.setEntityKey(tsKvEntry.getEntityKey());
        entity.setStrValue(tsKvEntry.getStrValue());
        entity.setDoubleValue(tsKvEntry.getDoubleValue());
        entity.setLongValue(tsKvEntry.getLongValue());
        entity.setBooleanValue(tsKvEntry.getBooleanValue());
        entity.setJsonValue(tsKvEntry.getJsonValue());
        log.trace("Saving entity: {}", entity);
        return Futures.transform(tsQueue.add(entity), v -> dataPointDays, MoreExecutors.directExecutor());
    }

    @Override
    public void cleanup(long systemTtl) {

    }

}
