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
import com.ciat.bim.server.telemetry.sub.TelemetrySubscriptionUpdate;
import lombok.Builder;
import lombok.Getter;


import java.util.Map;
import java.util.function.BiConsumer;

public class TbTimeseriesSubscription extends TbSubscription<TelemetrySubscriptionUpdate> {

    @Getter
    private final boolean allKeys;
    @Getter
    private final Map<String, Long> keyStates;
    @Getter
    private final long startTime;
    @Getter
    private final long endTime;
    @Getter
    private final boolean latestValues;

    @Builder
    public TbTimeseriesSubscription(String serviceId, String sessionId, int subscriptionId, String tenantId, EntityId entityId,
                                    BiConsumer<String, TelemetrySubscriptionUpdate> updateConsumer,
                                    boolean allKeys, Map<String, Long> keyStates, long startTime, long endTime, boolean latestValues) {
        super(serviceId, sessionId, subscriptionId, tenantId, entityId, TbSubscriptionType.TIMESERIES, updateConsumer);
        this.allKeys = allKeys;
        this.keyStates = keyStates;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latestValues = latestValues;
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
