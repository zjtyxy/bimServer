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
import com.ciat.bim.tenant.entity.DefaultTenantProfileConfiguration;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.springframework.util.StringUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@RuleNode(
        type = ComponentType.ACTION,
        name = "save timeseries",
        configClazz = TbMsgTimeseriesNodeConfiguration.class,
        nodeDescription = "Saves timeseries data",
        nodeDetails = "Saves timeseries telemetry data based on configurable TTL parameter. Expects messages with 'POST_TELEMETRY_REQUEST' message type",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbActionNodeTimeseriesConfig",
        icon = "file_upload"
)
public class TbMsgTimeseriesNode implements TbNode {

    private TbMsgTimeseriesNodeConfiguration config;
    private TbContext ctx;
    private long tenantProfileDefaultStorageTtl;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbMsgTimeseriesNodeConfiguration.class);
        this.ctx = ctx;
        ctx.addTenantProfileListener(this::onTenantProfileUpdate);
        onTenantProfileUpdate(ctx.getTenantProfile());
    }

    void onTenantProfileUpdate(TenantProfile tenantProfile) {
        DefaultTenantProfileConfiguration configuration = (DefaultTenantProfileConfiguration) tenantProfile.fetchProfileData().getConfiguration();
        tenantProfileDefaultStorageTtl = TimeUnit.DAYS.toSeconds(configuration.getDefaultStorageTtlDays());
    }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        if (!msg.getType().equals(SessionMsgType.POST_TELEMETRY_REQUEST.name())) {
            ctx.tellFailure(msg, new IllegalArgumentException("Unsupported msg type: " + msg.getType()));
            return;
        }
        long ts = getTs(msg);
        String src = msg.getData();
        Map<Long, List<AttributeKv>> tsKvMap = JsonConverter.convertToTelemetry(new JsonParser().parse(src), ts);
        if (tsKvMap.isEmpty()) {
            ctx.tellFailure(msg, new IllegalArgumentException("Msg body is empty: " + src));
            return;
        }
        List<AttributeKv> tsKvEntryList = new ArrayList<>();
        for (Map.Entry<Long, List<AttributeKv>> tsKvEntry : tsKvMap.entrySet()) {
            for (AttributeKv kvEntry : tsKvEntry.getValue()) {
                tsKvEntryList.add(new AttributeKv( kvEntry,tsKvEntry.getKey()));
            }
        }
        String ttlValue = msg.getMetaData().getValue("TTL");
        long ttl = !StringUtils.isEmpty(ttlValue) ? Long.parseLong(ttlValue) : config.getDefaultTTL();
        if (ttl == 0L) {
            ttl = tenantProfileDefaultStorageTtl;
        }
        ctx.getTelemetryService().saveAndNotify(TenantId.fromString(ctx.getTenantId()), msg.getCustomerId(), msg.getOriginator(), tsKvEntryList, ttl, new TelemetryNodeCallback(ctx, msg));
    }

    public static long getTs(TbMsg msg) {
        long ts = -1;
        String tsStr = msg.getMetaData().getValue("ts");
        if (!StringUtils.isEmpty(tsStr)) {
            try {
                ts = Long.parseLong(tsStr);
            } catch (NumberFormatException e) {
            }
        } else {
            ts = msg.getTs();
        }
        return ts;
    }

    @Override
    public void destroy() {
        ctx.removeListeners();
    }

}
