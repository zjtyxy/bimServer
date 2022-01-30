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

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.MsgType;
import com.ciat.bim.msg.TbActorStopReason;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.RuleEngineException;
import com.ciat.bim.server.common.msg.TbRuleEngineActorMsg;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


import java.util.Set;

/**
 * Created by ashvayka on 15.03.18.
 */
@ToString
@EqualsAndHashCode(callSuper = true)
public final class QueueToRuleEngineMsg extends TbRuleEngineActorMsg {

    @Getter
    private final String tenantId;
    @Getter
    private final Set<String> relationTypes;
    @Getter
    private final String failureMessage;

    public QueueToRuleEngineMsg(String tenantId, TbMsg tbMsg, Set<String> relationTypes, String failureMessage) {
        super(tbMsg);
        this.tenantId = tenantId;
        this.relationTypes = relationTypes;
        this.failureMessage = failureMessage;
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.QUEUE_TO_RULE_ENGINE_MSG;
    }

    @Override
    public void onTbActorStopped(TbActorStopReason reason) {
        String message;
        if (msg.getRuleChainId() != null) {
            message = reason == TbActorStopReason.STOPPED ?
                    String.format("Rule chain [%s] stopped", msg.getRuleChainId()) :
                    String.format("Failed to initialize rule chain [%s]!", msg.getRuleChainId());
        } else {
            message = reason == TbActorStopReason.STOPPED ? "Rule chain stopped" : "Failed to initialize rule chain!";
        }
        msg.getCallback().onFailure(new RuleEngineException(message));
    }

    public boolean isTellNext() {
        return relationTypes != null && !relationTypes.isEmpty();
    }

}
