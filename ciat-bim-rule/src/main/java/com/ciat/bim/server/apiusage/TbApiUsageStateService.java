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
package com.ciat.bim.server.apiusage;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.data.id.TenantProfileId;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.ToUsageStatsServiceMsg;
import org.springframework.context.ApplicationListener;


public interface TbApiUsageStateService extends ApplicationListener<PartitionChangeEvent> {

    void process(TbProtoQueueMsg<ToUsageStatsServiceMsg> msg, TbCallback callback);

    ApiUsageState getApiUsageState(TenantId tenantId);

    void onTenantProfileUpdate(TenantProfileId tenantProfileId);

    void onTenantUpdate(TenantId tenantId);

    void onTenantDelete(TenantId tenantId);

    void onCustomerDelete(CustomerId customerId);

    void onApiUsageStateUpdate(TenantId tenantId);
}
