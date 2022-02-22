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
package com.ciat.bim.rule.engine.mail;

import com.ciat.bim.common.data.rule.ComponentType;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.rule.*;
import com.ciat.bim.rule.util.TbNodeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.ciat.bim.common.data.rule.TbRelationTypes.SUCCESS;
import static com.ciat.bim.rule.engine.mail.TbSendEmailNode.SEND_EMAIL_TYPE;


@Slf4j
@RuleNode(
        type = ComponentType.TRANSFORMATION,
        name = "to email",
        configClazz = TbMsgToEmailNodeConfiguration.class,
        nodeDescription = "Transforms message to email message",
        nodeDetails = "Transforms message to email message by populating email fields using values derived from message metadata. " +
                      "Set 'SEND_EMAIL' output message type.",
        uiResources = {"static/rulenode/rulenode-core-config.js"},
        configDirective = "tbTransformationNodeToEmailConfig",
        icon = "email"
)
public class TbMsgToEmailNode implements TbNode {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String IMAGES = "images";
    private static final String DYNAMIC = "dynamic";

    private TbMsgToEmailNodeConfiguration config;
    private boolean isDynamicHtmlTemplate;

    @Override
    public void init(TbContext ctx, TbNodeConfiguration configuration) throws TbNodeException {
        this.config = TbNodeUtils.convert(configuration, TbMsgToEmailNodeConfiguration.class);
        this.isDynamicHtmlTemplate = DYNAMIC.equals(this.config.getMailBodyType());
     }

    @Override
    public void onMsg(TbContext ctx, TbMsg msg) {
        try {
            TbEmail email = convert(msg);
            TbMsg emailMsg = buildEmailMsg(ctx, msg, email);
            ctx.tellNext(emailMsg, SUCCESS);
        } catch (Exception ex) {
            log.warn("Can not convert message to email " + ex.getMessage());
            ctx.tellFailure(msg, ex);
        }
    }

    private TbMsg buildEmailMsg(TbContext ctx, TbMsg msg, TbEmail email) throws JsonProcessingException {
        String emailJson = MAPPER.writeValueAsString(email);
        return ctx.transformMsg(msg, SEND_EMAIL_TYPE, msg.getOriginator(), msg.getMetaData().copy(), emailJson);
    }

    private TbEmail convert(TbMsg msg) throws IOException {
        TbEmail.TbEmailBuilder builder = TbEmail.builder();
        builder.from(fromTemplate(this.config.getFromTemplate(), msg));
        builder.to(fromTemplate(this.config.getToTemplate(), msg));
        builder.cc(fromTemplate(this.config.getCcTemplate(), msg));
        builder.bcc(fromTemplate(this.config.getBccTemplate(), msg));
        if(isDynamicHtmlTemplate) {
            builder.html(Boolean.parseBoolean(fromTemplate(this.config.getIsHtmlTemplate(), msg)));
        } else {
            builder.html(Boolean.parseBoolean(this.config.getMailBodyType()));
        }
        builder.subject(fromTemplate(this.config.getSubjectTemplate(), msg));
        builder.body(fromTemplate(this.config.getBodyTemplate(), msg));
        String imagesStr = msg.getMetaData().getValue(IMAGES);
        if (!StringUtils.isEmpty(imagesStr)) {
            Map<String, String> imgMap = MAPPER.readValue(imagesStr, new TypeReference<HashMap<String, String>>() {});
            builder.images(imgMap);
        }
        return builder.build();
    }

    private String fromTemplate(String template, TbMsg msg) {
        if (!StringUtils.isEmpty(template)) {
            return TbNodeUtils.processPattern(template, msg);
        } else {
            return null;
        }
    }

    @Override
    public void destroy() {

    }
}
