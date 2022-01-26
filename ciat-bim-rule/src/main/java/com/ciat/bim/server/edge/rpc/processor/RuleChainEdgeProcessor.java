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
package com.ciat.bim.server.edge.rpc.processor;

import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.server.common.data.edge.EdgeEventActionType;
import com.ciat.bim.server.edge.Edge;
import com.ciat.bim.server.edge.EdgeEvent;
import com.ciat.bim.server.edge.gen.DownlinkMsg;
import com.ciat.bim.server.edge.gen.RuleChainMetadataUpdateMsg;
import com.ciat.bim.server.edge.gen.RuleChainUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.EdgeUtils;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import lombok.extern.slf4j.Slf4j;
import net.sf.saxon.trans.rules.RuleChain;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@TbCoreComponent
public class RuleChainEdgeProcessor extends BaseEdgeProcessor {
//
//    public DownlinkMsg processRuleChainToEdge(Edge edge, EdgeEvent edgeEvent, UpdateMsgType msgType, EdgeEventActionType action) {
//        RuleChainId ruleChainId = new RuleChainId(edgeEvent.getEntityId());
//        DownlinkMsg downlinkMsg = null;
//        switch (action) {
//            case ADDED:
//            case UPDATED:
//            case ASSIGNED_TO_EDGE:
//                RuleChain ruleChain = ruleChainService.findRuleChainById(edgeEvent.getTenantId(), ruleChainId);
//                if (ruleChain != null) {
//                    RuleChainUpdateMsg ruleChainUpdateMsg =
//                            ruleChainMsgConstructor.constructRuleChainUpdatedMsg(edge.getRootRuleChainId(), msgType, ruleChain);
//                    downlinkMsg = DownlinkMsg.newBuilder()
//                            .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                            .addRuleChainUpdateMsg(ruleChainUpdateMsg)
//                            .build();
//                }
//                break;
//            case DELETED:
//            case UNASSIGNED_FROM_EDGE:
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addRuleChainUpdateMsg(ruleChainMsgConstructor.constructRuleChainDeleteMsg(ruleChainId))
//                        .build();
//                break;
//        }
//        return downlinkMsg;
//    }
//
//    public DownlinkMsg processRuleChainMetadataToEdge(EdgeEvent edgeEvent, UpdateMsgType msgType) {
//        RuleChainId ruleChainId = new RuleChainId(edgeEvent.getEntityId());
//        RuleChain ruleChain = ruleChainService.findRuleChainById(edgeEvent.getTenantId(), ruleChainId);
//        DownlinkMsg downlinkMsg = null;
//        if (ruleChain != null) {
//            RuleChainMetaData ruleChainMetaData = ruleChainService.loadRuleChainMetaData(edgeEvent.getTenantId(), ruleChainId);
//            RuleChainMetadataUpdateMsg ruleChainMetadataUpdateMsg =
//                    ruleChainMsgConstructor.constructRuleChainMetadataUpdatedMsg(msgType, ruleChainMetaData);
//            if (ruleChainMetadataUpdateMsg != null) {
//                downlinkMsg = DownlinkMsg.newBuilder()
//                        .setDownlinkMsgId(EdgeUtils.nextPositiveInt())
//                        .addRuleChainMetadataUpdateMsg(ruleChainMetadataUpdateMsg)
//                        .build();
//            }
//        }
//        return downlinkMsg;
//    }
}
