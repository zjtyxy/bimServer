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
import com.ciat.bim.server.telemetry.sub.AlarmSubscriptionUpdate;
import lombok.Builder;
import lombok.Getter;


import java.util.function.BiConsumer;

public class TbAlarmsSubscription extends TbSubscription<AlarmSubscriptionUpdate> {

    @Getter
    private final long ts;

    @Builder
    public TbAlarmsSubscription(String serviceId, String sessionId, int subscriptionId, String tenantId, EntityId entityId,
                                BiConsumer<String, AlarmSubscriptionUpdate> updateConsumer, long ts) {
        super(serviceId, sessionId, subscriptionId, tenantId, entityId, TbSubscriptionType.ALARMS, updateConsumer);
        this.ts = ts;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
