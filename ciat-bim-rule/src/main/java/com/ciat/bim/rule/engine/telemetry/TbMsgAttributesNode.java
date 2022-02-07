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
package com.ciat.bim.rule.engine.telemetry;

import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.*;
import com.ciat.bim.rule.util.TbNodeUtils;
import com.ciat.bim.server.common.msg.SessionMsgType;
import com.ciat.bim.server.common.transport.util.JsonConverter;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.device.entity.AttributeKv;


import java.util.ArrayList;
import java.util.Set;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "save attributes",
        configClazz = TbMsgAttributesNodeConfiguration.class,
        nodeDescription = "Saves attributes data",
        nodeDetails = "Saves entity attributes based on configurable scope parameter. Expects messages with 'POST_ATTRIBUTES_REQUEST' message type",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbActionNodeAttributesConfig",
        icon = "file_upload"
)
public class TbMsgAttributesNode implements TbNode {

    private TbMsgAttributesNodeConfiguration config;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbMsgAttributesNodeConfiguration.class);
        if(this.config ==  null)
        {
            this.config  = new TbMsgAttributesNodeConfiguration().defaultConfiguration();
        }
        if (config.getNotifyDevice() == null) {
            config.setNotifyDevice(true);
        }
    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        if (!msg.getType().equals(SessionMsgType.POST_ATTRIBUTES_REQUEST.name())) {
            ctx.tellFailure(msg, new IllegalArgumentException("Unsupported msg type: " + msg.getType()));
            return;
        }
        String src = msg.getData();
        Set<AttributeKv> attributes = JsonConverter.convertToAttributes(new JsonParser().parse(src));
        String notifyDeviceStr = msg.getMetaData().getValue("notifyDevice");
        ctx.getTelemetryService().saveAndNotify(
                TenantId.fromString(ctx.getTenantId()),
                msg.getOriginator(),
                config.getScope(),
                new ArrayList<>(attributes),
                config.getNotifyDevice() || StringUtils.isEmpty(notifyDeviceStr) || Boolean.parseBoolean(notifyDeviceStr),
                new TelemetryNodeCallback(ctx, msg)
        );
    }

    @Override
    public void destroy() {
    }

}
