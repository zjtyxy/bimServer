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
package com.ciat.bim.server.common.data.relation;

import com.ciat.bim.msg.EntityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.List;

/**
 * Created by ashvayka on 02.05.17.
 */
@Data
@AllArgsConstructor
@ApiModel
public class RelationEntityTypeFilter {

    @ApiModelProperty(position = 1, value = "Type of the relation between root entity and other entity (e.g. 'Contains' or 'Manages').", example = "Contains")
    private String relationType;

    @ApiModelProperty(position = 2, value = "Array of entity types to filter the related entities (e.g. 'DEVICE', 'ASSET').")
    private List<EntityType> entityTypes;
}
