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
package com.ciat.bim.rule;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.data.id.UUIDBased;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;


import java.util.UUID;

public class RuleNodeId extends UUIDBased implements EntityId {

    @JsonCreator
    public RuleNodeId(@JsonProperty("id") String id) {
        super(id);
    }
    public static RuleNodeId fromString(String tenantId) {
        return new RuleNodeId(tenantId);
    }
    @ApiModelProperty(position = 2, required = true, value = "string", example = "RULE_NODE", allowableValues = "RULE_NODE")
    @Override
    public EntityType getEntityType() {
        return EntityType.RULE_NODE;
    }
}
