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
package com.ciat.bim.server.common.msg.queue;

import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleEngineException;

import com.ciat.bim.rule.RuleNodeId;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.rule.entity.RuleNode;


@Slf4j
public class RuleNodeException extends RuleEngineException {

    private static final long serialVersionUID = -1776681087370749776L;

    @Getter
    private final String ruleChainName;
    @Getter
    private  String ruleNodeName;
    @Getter
    private  RuleChainId ruleChainId;
    @Getter
    private  RuleNodeId ruleNodeId;


    public RuleNodeException(String message, String ruleChainName, RuleNode ruleNode) {
        super(message);
        this.ruleChainName = ruleChainName;
        if (ruleNode != null) {
            this.ruleNodeName = ruleNode.getName();
            this.ruleChainId =RuleChainId.fromString( ruleNode.getRuleChainId());
            this.ruleNodeId = RuleNodeId.fromString(ruleNode.getId());
        } else {
            ruleNodeName = "Unknown";
            ruleChainId = new RuleChainId(RuleChainId.NULL_UUID.toString());
            ruleNodeId = new RuleNodeId(RuleNodeId.NULL_UUID.toString());
        }
    }

    public String toJsonString() {
        try {
            return mapper.writeValueAsString(mapper.createObjectNode()
                    .put("ruleNodeId", ruleNodeId.toString())
                    .put("ruleChainId", ruleChainId.toString())
                    .put("ruleNodeName", ruleNodeName)
                    .put("ruleChainName", ruleChainName)
                    .put("message", getMessage()));
        } catch (JsonProcessingException e) {
            log.warn("Failed to serialize exception ", e);
            throw new RuntimeException(e);
        }
    }

}
