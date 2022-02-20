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
package com.ciat.bim.rule.engine.rpc;

import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.*;
import com.ciat.bim.rule.util.TbNodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


import java.util.UUID;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "rpc call reply",
        configClazz = TbSendRpcReplyNodeConfiguration.class,
        nodeDescription = "Sends reply to RPC call from device",
        nodeDetails = "Expects messages with any message type. Will forward message body to the device.",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbActionNodeRpcReplyConfig",
        icon = "call_merge"
)
public class TbSendRPCReplyNode implements TbNode {

    private TbSendRpcReplyNodeConfiguration config;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbSendRpcReplyNodeConfiguration.class);
    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        String serviceIdStr = msg.getMetaData().getValue(config.getServiceIdMetaDataAttribute());
        String sessionIdStr = msg.getMetaData().getValue(config.getSessionIdMetaDataAttribute());
        String requestIdStr = msg.getMetaData().getValue(config.getRequestIdMetaDataAttribute());
        if (msg.getOriginator().getEntityType() != EntityType.DEVICE) {
            ctx.tellFailure(msg, new RuntimeException("Message originator is not a device entity!"));
        } else if (StringUtils.isEmpty(requestIdStr)) {
            ctx.tellFailure(msg, new RuntimeException("Request id is not present in the metadata!"));
        } else if (StringUtils.isEmpty(serviceIdStr)) {
            ctx.tellFailure(msg, new RuntimeException("Service id is not present in the metadata!"));
        } else if (StringUtils.isEmpty(sessionIdStr)) {
            ctx.tellFailure(msg, new RuntimeException("Session id is not present in the metadata!"));
        } else if (StringUtils.isEmpty(msg.getData())) {
            ctx.tellFailure(msg, new RuntimeException("Request body is empty!"));
        } else {
            ctx.getRpcService().sendRpcReplyToDevice(serviceIdStr, UUID.fromString(sessionIdStr), Integer.parseInt(requestIdStr), msg.getData());
            ctx.tellSuccess(msg);
        }
    }

    @Override
    public void destroy() {
    }

}
