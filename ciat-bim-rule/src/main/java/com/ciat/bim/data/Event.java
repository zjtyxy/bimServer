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
package com.ciat.bim.data;


import com.ciat.bim.data.id.EventId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Andrew Shvayka
 */
@Data
@ApiModel
public class Event extends BaseData<EventId> {

    @ApiModelProperty(position = 1, value = "JSON object with Tenant Id.", readOnly = true)
    private String tenantId;
    @ApiModelProperty(position = 2, value = "Event type", example = "STATS")
    private String type;
    @ApiModelProperty(position = 3, value = "string", example = "784f394c-42b6-435a-983c-b7beff2784f9")
    private String uid;
    @ApiModelProperty(position = 4, value = "JSON object with Entity Id for which event is created.", readOnly = true)
    private String entityId;
    @ApiModelProperty(position = 5, value = "Event body.", dataType = "com.fasterxml.jackson.databind.JsonNode")
    private transient JsonNode body;

    public Event() {
        super();
    }

    public Event(EventId id) {
        super(id);
    }

    public Event(Event event) {
        super(event);
    }

    @ApiModelProperty(position = 6, value = "Timestamp of the event creation, in milliseconds", example = "1609459200000", readOnly = true)
    @Override
    public long getCreatedTime() {
        return super.getCreatedTime();
    }
}
