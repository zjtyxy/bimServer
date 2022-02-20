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
package com.ciat.bim.data.id;

import com.ciat.bim.msg.EntityType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;


public final class TenantId extends UUIDBased implements EntityId {
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @JsonIgnore
    public static final String SYS_TENANT_ID = EntityId.NULL_UUID.toString();

    private static final long serialVersionUID = 1L;
    public static TenantId fromString(String tenantId) {
        return new TenantId(tenantId);
    }
    @JsonCreator
    public TenantId(@JsonProperty("id") String id) {
        super(id);
    }

    @ApiModelProperty(position = 2, required = true, value = "string", example = "TENANT", allowableValues = "TENANT")
    @Override
    public EntityType getEntityType() {
        return EntityType.TENANT;
    }
}
