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
package com.ciat.bim.server.edge;

import com.ciat.bim.data.BaseData;
import com.ciat.bim.data.id.EdgeId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.common.data.edge.EdgeEventType;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;


import java.util.UUID;

@Data
public class EdgeEvent extends BaseData<EdgeEventId> {

    private TenantId tenantId;
    private EdgeId edgeId;
    private EdgeEventActionType action;
    private UUID entityId;
    private String uid;
    private EdgeEventType type;
    private transient JsonNode body;

    public EdgeEvent() {
        super();
    }

    public EdgeEvent(EdgeEventId id) {
        super(id);
    }

    public EdgeEvent(EdgeEvent event) {
        super(event);
    }

}
