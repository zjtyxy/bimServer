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
package com.ciat.bim.server.actors.ruleChain;

import com.ciat.bim.msg.MsgType;
import com.ciat.bim.msg.TbActorStopReason;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleEngineException;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.common.msg.TbRuleEngineActorMsg;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


import java.io.Serializable;
import java.util.Set;

/**
 * Created by ashvayka on 19.03.18.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
class RuleNodeToRuleChainTellNextMsg extends TbRuleEngineActorMsg implements Serializable {

    private static final long serialVersionUID = 4577026446412871820L;
    @Getter
    private final RuleChainId ruleChainId;
    @Getter
    private final String originator;
    @Getter
    private final Set<String> relationTypes;
    @Getter
    private final String failureMessage;

    public RuleNodeToRuleChainTellNextMsg(RuleChainId ruleChainId, String originator, Set<String> relationTypes, TbMsg tbMsg, String failureMessage) {
        super(tbMsg);
        this.ruleChainId = ruleChainId;
        this.originator = originator;
        this.relationTypes = relationTypes;
        this.failureMessage = failureMessage;
    }

    @Override
    public void onTbActorStopped(TbActorStopReason reason) {
        String message = reason == TbActorStopReason.STOPPED ? String.format("Rule chain [%s] stopped", ruleChainId.getId()) : String.format("Failed to initialize rule chain [%s]!", ruleChainId.getId());
        msg.getCallback().onFailure(new RuleEngineException(message));
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.RULE_TO_RULE_CHAIN_TELL_NEXT_MSG;
    }

}
