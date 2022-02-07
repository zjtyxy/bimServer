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
package com.ciat.bim.rule.engine.action;

import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.ServiceQueue;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.rule.*;
import com.ciat.bim.rule.util.TbNodeUtils;
import com.ciat.bim.server.common.msg.SessionMsgType;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import static com.ciat.bim.common.data.rule.TbRelationTypes.SUCCESS;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "message count",
        configClazz = TbMsgCountNodeConfiguration.class,
        nodeDescription = "Count incoming messages",
        nodeDetails = "Count incoming messages for specified interval and produces POST_TELEMETRY_REQUEST msg with messages count",
        icon = "functions",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbActionNodeMsgCountConfig"
)
public class TbMsgCountNode implements TbNode {

    private static final String TB_MSG_COUNT_NODE_MSG = "TbMsgCountNodeMsg";

    private AtomicLong messagesProcessed = new AtomicLong(0);
    private final Gson gson = new Gson();
    private String nextTickId;
    private long delay;
    private String telemetryPrefix;
    private long lastScheduledTs;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        TbMsgCountNodeConfiguration config = TbNodeUtils.convert(configuration, TbMsgCountNodeConfiguration.class);
        if(config == null)
        {
            config =  new TbMsgCountNodeConfiguration().defaultConfiguration();
        }
        this.delay = TimeUnit.SECONDS.toMillis(config.getInterval());
        this.telemetryPrefix = config.getTelemetryPrefix();
        scheduleTickMsg(ctx, null);

    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        if (msg.getType().equals(TB_MSG_COUNT_NODE_MSG) && msg.getId().equals(nextTickId)) {
            JsonObject telemetryJson = new JsonObject();
            telemetryJson.addProperty(this.telemetryPrefix + "_" + ctx.getServiceId(), messagesProcessed.longValue());

            messagesProcessed = new AtomicLong(0);

            TbMsgMetaData metaData = new TbMsgMetaData();
            metaData.putValue("delta", Long.toString(System.currentTimeMillis() - lastScheduledTs + delay));

            TbMsg tbMsg = TbMsg.newMsg(msg.getQueueName(), SessionMsgType.POST_TELEMETRY_REQUEST.name(), TenantId.fromString( ctx.getTenantId()), msg.getCustomerId(), metaData, gson.toJson(telemetryJson));
            ctx.enqueueForTellNext(tbMsg, SUCCESS);
            scheduleTickMsg(ctx, tbMsg);
        } else {
            messagesProcessed.incrementAndGet();
            ctx.ack(msg);
        }
    }

    private void scheduleTickMsg(TbContext ctx, TbMsg msg) {
        long curTs = System.currentTimeMillis();
        if (lastScheduledTs == 0L) {
            lastScheduledTs = curTs;
        }
        lastScheduledTs = lastScheduledTs + delay;
        long curDelay = Math.max(0L, (lastScheduledTs - curTs));
        TbMsg tickMsg = ctx.newMsg(ServiceQueue.MAIN, TB_MSG_COUNT_NODE_MSG, ctx.getSelfId(), msg != null ? msg.getCustomerId() : null, new TbMsgMetaData(), "");
        nextTickId = tickMsg.getId();
        ctx.tellSelf(tickMsg, curDelay);
    }

    @Override
    public void destroy() {
    }
}
