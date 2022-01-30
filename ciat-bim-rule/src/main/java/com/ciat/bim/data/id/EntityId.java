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

import com.ciat.bim.data.id.HasUUID;
import com.ciat.bim.msg.EntityIdDeserializer;
import com.ciat.bim.msg.EntityIdSerializer;
import com.ciat.bim.msg.EntityType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Andrew Shvayka
 */

@JsonDeserialize(using = EntityIdDeserializer.class)
@JsonSerialize(using = EntityIdSerializer.class)
@ApiModel
public interface EntityId extends HasUUID, Serializable { //NOSONAR, the constant is closely related to EntityId

    String NULL_UUID = "123456789123456789";

    @ApiModelProperty(position = 1, required = true, value = "ID of the entity, time-based UUID v1", example = "784f394c-42b6-435a-983c-b7beff2784f9")
    String getId();

    @ApiModelProperty(position = 2, required = true, example = "DEVICE")
    EntityType getEntityType();

    @JsonIgnore
    default boolean isNullUid() {
        return NULL_UUID.equals(getId());
    }

}
