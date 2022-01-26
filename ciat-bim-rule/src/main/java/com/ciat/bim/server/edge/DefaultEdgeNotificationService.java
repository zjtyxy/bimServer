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

import com.ciat.bim.data.id.EdgeId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.common.data.edge.EdgeEventType;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.edge.rpc.processor.*;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@TbCoreComponent
@Slf4j
public class DefaultEdgeNotificationService implements EdgeNotificationService {

//    @Autowired
//    private EdgeService edgeService;
//
//    @Autowired
//    private EdgeEventService edgeEventService;
//
//    @Autowired
//    private TbClusterService clusterService;
//
//    @Autowired
//    private EdgeProcessor edgeProcessor;
//
//    @Autowired
//    private EntityEdgeProcessor entityProcessor;
//
//    @Autowired
//    private AlarmEdgeProcessor alarmProcessor;
//
//    @Autowired
//    private RelationEdgeProcessor relationProcessor;
//
//    @Autowired
//    private CustomerEdgeProcessor customerProcessor;
//
//    private ExecutorService tsCallBackExecutor;
//
//    @PostConstruct
//    public void initExecutor() {
//        tsCallBackExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("edge-notifications"));
//    }
//
//    @PreDestroy
//    public void shutdownExecutor() {
//        if (tsCallBackExecutor != null) {
//            tsCallBackExecutor.shutdownNow();
//        }
//    }
//
//    @Override
//    public Edge setEdgeRootRuleChain(TenantId tenantId, Edge edge, RuleChainId ruleChainId) throws IOException {
//        edge.setRootRuleChainId(ruleChainId);
//        Edge savedEdge = edgeService.saveEdge(edge, true);
//        saveEdgeEvent(tenantId, edge.getId(), EdgeEventType.RULE_CHAIN, EdgeEventActionType.UPDATED, ruleChainId, null);
//        return savedEdge;
//    }
//
//    private void saveEdgeEvent(TenantId tenantId,
//                               EdgeId edgeId,
//                               EdgeEventType type,
//                               EdgeEventActionType action,
//                               EntityId entityId,
//                               JsonNode body) {
//        log.debug("Pushing edge event to edge queue. tenantId [{}], edgeId [{}], type [{}], action[{}], entityId [{}], body [{}]",
//                tenantId, edgeId, type, action, entityId, body);
//
//        EdgeEvent edgeEvent = new EdgeEvent();
//        edgeEvent.setEdgeId(edgeId);
//        edgeEvent.setTenantId(tenantId);
//        edgeEvent.setType(type);
//        edgeEvent.setAction(action);
//        if (entityId != null) {
//            edgeEvent.setEntityId(entityId.getId());
//        }
//        edgeEvent.setBody(body);
//        edgeEventService.save(edgeEvent);
//        clusterService.onEdgeEventUpdate(tenantId, edgeId);
//    }
//
//    @Override
//    public void pushNotificationToEdge(TransportProtos.EdgeNotificationMsgProto edgeNotificationMsg, TbCallback callback) {
//        log.trace("Pushing notification to edge {}", edgeNotificationMsg);
//        try {
//            TenantId tenantId = new TenantId(new UUID(edgeNotificationMsg.getTenantIdMSB(), edgeNotificationMsg.getTenantIdLSB()));
//            EdgeEventType type = EdgeEventType.valueOf(edgeNotificationMsg.getType());
//            switch (type) {
//                case EDGE:
//                    edgeProcessor.processEdgeNotification(tenantId, edgeNotificationMsg);
//                    break;
//                case USER:
//                case ASSET:
//                case DEVICE:
//                case DEVICE_PROFILE:
//                case ENTITY_VIEW:
//                case DASHBOARD:
//                case RULE_CHAIN:
//                    entityProcessor.processEntityNotification(tenantId, edgeNotificationMsg);
//                    break;
//                case CUSTOMER:
//                    customerProcessor.processCustomerNotification(tenantId, edgeNotificationMsg);
//                    break;
//                case WIDGETS_BUNDLE:
//                case WIDGET_TYPE:
//                    entityProcessor.processEntityNotificationForAllEdges(tenantId, edgeNotificationMsg);
//                    break;
//                case ALARM:
//                    alarmProcessor.processAlarmNotification(tenantId, edgeNotificationMsg);
//                    break;
//                case RELATION:
//                    relationProcessor.processRelationNotification(tenantId, edgeNotificationMsg);
//                    break;
//                default:
//                    log.debug("Edge event type [{}] is not designed to be pushed to edge", type);
//            }
//        } catch (Exception e) {
//            callback.onFailure(e);
//            log.error("Can't push to edge updates, edgeNotificationMsg [{}]", edgeNotificationMsg, e);
//        } finally {
//            callback.onSuccess();
//        }
//    }

}


