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
import com.ciat.bim.server.common.msg.TbRuleEngineActorMsg;
import com.ciat.bim.server.common.msg.aware.RuleChainAwareMsg;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


/**
 * Created by ashvayka on 19.03.18.
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public final class RuleChainToRuleChainMsg extends TbRuleEngineActorMsg implements RuleChainAwareMsg {

    @Getter
    private final String target;
    @Getter
    private final String source;
    @Getter
    private final String fromRelationType;

    public RuleChainToRuleChainMsg(String target, String source, TbMsg tbMsg, String fromRelationType) {
        super(tbMsg);
        this.target = target;
        this.source = source;
        this.fromRelationType = fromRelationType;
    }

    @Override
    public void onTbActorStopped(TbActorStopReason reason) {
        String message = reason == TbActorStopReason.STOPPED ? String.format("Rule chain [%s] stopped", target) : String.format("Failed to initialize rule chain [%s]!", target);
        msg.getCallback().onFailure(new RuleEngineException(message));
    }

    @Override
    public String getRuleChainId() {
        return target;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.RULE_CHAIN_TO_RULE_CHAIN_MSG;
    }
}
