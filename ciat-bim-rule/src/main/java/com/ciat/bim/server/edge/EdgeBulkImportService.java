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

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.server.importing.AbstractBulkImportService;
import com.ciat.bim.server.importing.BulkImportColumnType;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.security.SecurityUser;
import com.ciat.bim.server.utils.JacksonUtil;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Map;
import java.util.Optional;

@Service
@TbCoreComponent
@RequiredArgsConstructor
public class EdgeBulkImportService extends AbstractBulkImportService<Edge> {
//    private final EdgeService edgeService;
//
//    @Override
//    protected void setEntityFields(Edge entity, Map<BulkImportColumnType, String> fields) {
//        ObjectNode additionalInfo = (ObjectNode) Optional.ofNullable(entity.getAdditionalInfo()).orElseGet(JacksonUtil::newObjectNode);
//        fields.forEach((columnType, value) -> {
//            switch (columnType) {
//                case NAME:
//                    entity.setName(value);
//                    break;
//                case TYPE:
//                    entity.setType(value);
//                    break;
//                case LABEL:
//                    entity.setLabel(value);
//                    break;
//                case DESCRIPTION:
//                    additionalInfo.set("description", new TextNode(value));
//                    break;
//                case EDGE_LICENSE_KEY:
//                    entity.setEdgeLicenseKey(value);
//                    break;
//                case CLOUD_ENDPOINT:
//                    entity.setCloudEndpoint(value);
//                    break;
//                case ROUTING_KEY:
//                    entity.setRoutingKey(value);
//                    break;
//                case SECRET:
//                    entity.setSecret(value);
//                    break;
//            }
//        });
//        entity.setAdditionalInfo(additionalInfo);
//    }
//
//    @Override
//    protected Edge saveEntity(Edge entity, Map<BulkImportColumnType, String> fields) {
//        return edgeService.saveEdge(entity, true);
//    }
//
//    @Override
//    protected Edge findOrCreateEntity(TenantId tenantId, String name) {
//        return Optional.ofNullable(edgeService.findEdgeByTenantIdAndName(tenantId, name))
//                .orElseGet(Edge::new);
//    }
//
//    @Override
//    protected void setOwners(Edge entity, SecurityUser user) {
//        entity.setTenantId(user.getTenantId());
//        entity.setCustomerId(user.getCustomerId());
//    }
//
//    @Override
//    protected EntityType getEntityType() {
//        return EntityType.EDGE;
//    }

}
