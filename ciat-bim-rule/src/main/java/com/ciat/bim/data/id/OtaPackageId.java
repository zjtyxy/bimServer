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
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;


import java.util.UUID;

public class OtaPackageId extends UUIDBased implements EntityId {

    private static final long serialVersionUID = 1L;

    @JsonCreator
    public OtaPackageId(@JsonProperty("id") String id) {
        super(id);
    }

    public static OtaPackageId fromString(String firmwareId) {
        return new OtaPackageId(firmwareId);
    }

    @ApiModelProperty(position = 2, required = true, value = "string", example = "OTA_PACKAGE", allowableValues = "OTA_PACKAGE")
    @Override
    public EntityType getEntityType() {
        return EntityType.OTA_PACKAGE;
    }

}
