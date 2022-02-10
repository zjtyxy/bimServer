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
package org.jeecg.modules.alarm.entity;

import com.ciat.bim.data.id.EntityId;
import lombok.Data;


import java.util.Collections;
import java.util.List;

@Data
public class AlarmOperationResult {
    private final Alarm alarm;
    private final boolean successful;
    private final boolean created;
    private final List<EntityId> propagatedEntitiesList;

    public AlarmOperationResult(Alarm alarm, boolean successful) {
        this(alarm, successful, Collections.emptyList());
    }

    public AlarmOperationResult(Alarm alarm, boolean successful, List<EntityId> propagatedEntitiesList) {
        this(alarm, successful, false, propagatedEntitiesList);
    }

    public AlarmOperationResult(Alarm alarm, boolean successful, boolean created, List<EntityId> propagatedEntitiesList) {
        this.alarm = alarm;
        this.successful = successful;
        this.created = created;
        this.propagatedEntitiesList = propagatedEntitiesList;
    }
}
