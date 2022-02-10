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
package com.ciat.bim.server.common.data.query.filter;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.server.common.data.query.EntityFilter;
import com.ciat.bim.server.common.data.query.EntityFilterType;
import com.ciat.bim.server.common.data.relation.EntitySearchDirection;
import com.ciat.bim.server.common.data.relation.RelationEntityTypeFilter;
import lombok.Data;


import java.util.List;

@Data
public class RelationsQueryFilter implements EntityFilter {

    @Override
    public EntityFilterType getType() {
        return EntityFilterType.RELATIONS_QUERY;
    }

    private EntityId rootEntity;
    private EntitySearchDirection direction;
    private List<RelationEntityTypeFilter> filters;
    private int maxLevel;
    private boolean fetchLastLevelOnly;

}
