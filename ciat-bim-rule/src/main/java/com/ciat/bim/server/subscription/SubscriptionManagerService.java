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
package com.ciat.bim.server.subscription;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import org.jeecg.modules.device.entity.AttributeKv;
import org.springframework.context.ApplicationListener;


import java.util.List;

public interface SubscriptionManagerService extends ApplicationListener<PartitionChangeEvent> {

    void addSubscription(TbSubscription subscription, TbCallback callback);

    void cancelSubscription(String sessionId, int subscriptionId, TbCallback callback);
//
//    void onTimeSeriesUpdate(TenantId tenantId, EntityId entityId, List<AttributeKv> ts, TbCallback callback);
//
//    void onAttributesUpdate(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, TbCallback callback);
//
//    void onAttributesUpdate(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes, boolean notifyDevice, TbCallback callback);
//
//    void onAttributesDelete(TenantId tenantId, EntityId entityId, String scope, List<String> keys, TbCallback empty);
//
//    void onAlarmUpdate(TenantId tenantId, EntityId entityId, Alarm alarm, TbCallback callback);
//
//    void onAlarmDeleted(TenantId tenantId, EntityId entityId, Alarm alarm, TbCallback callback);


}
