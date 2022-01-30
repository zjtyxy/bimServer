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
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Objects;
import java.util.function.BiConsumer;

@Data
@AllArgsConstructor
public abstract class TbSubscription<T> {

    private final String serviceId;
    private final String sessionId;
    private final int subscriptionId;
    private final String tenantId;
    private final EntityId entityId;
    private final TbSubscriptionType type;
    private final BiConsumer<String, T> updateConsumer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbSubscription that = (TbSubscription) o;
        return subscriptionId == that.subscriptionId &&
                sessionId.equals(that.sessionId) &&
                tenantId.equals(that.tenantId) &&
                entityId.equals(that.entityId) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, subscriptionId, tenantId, entityId, type);
    }
}
