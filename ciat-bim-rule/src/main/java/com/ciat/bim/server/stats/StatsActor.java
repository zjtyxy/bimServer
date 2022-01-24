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
package com.ciat.bim.server.stats;


import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.Event;
import com.ciat.bim.data.id.TbStringActorId;
import com.ciat.bim.msg.MsgType;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.server.ContextAwareActor;
import com.ciat.bim.server.ContextBasedCreator;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.actors.TbActor;
import com.ciat.bim.server.actors.TbActorId;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StatsActor extends ContextAwareActor {

    private final ObjectMapper mapper = new ObjectMapper();

    public StatsActor(ActorSystemContext context) {
        super(context);
    }

    @Override
    protected boolean doProcess(TbActorMsg msg) {
        log.debug("Received message: {}", msg);
        if (msg.getMsgType().equals(MsgType.STATS_PERSIST_MSG)) {
            onStatsPersistMsg((StatsPersistMsg) msg);
            return true;
        } else {
            return false;
        }
    }

    public void onStatsPersistMsg(StatsPersistMsg msg) {
        if (msg.isEmpty()) {
            return;
        }
        Event event = new Event();
        event.setEntityId(msg.getEntityId());
        event.setTenantId(msg.getTenantId());
        event.setType(DataConstants.STATS);
//        event.setBody(toBodyJson(systemContext.getServiceInfoProvider().getServiceId(), msg.getMessagesProcessed(), msg.getErrorsOccurred()));
//        systemContext.getEventService().save(event);
    }

    private JsonNode toBodyJson(String serviceId, long messagesProcessed, long errorsOccurred) {
        return mapper.createObjectNode().put("server", serviceId).put("messagesProcessed", messagesProcessed).put("errorsOccurred", errorsOccurred);
    }

    public static class ActorCreator extends ContextBasedCreator {
        private final String actorId;

        public ActorCreator(ActorSystemContext context, String actorId) {
            super(context);
            this.actorId = actorId;
        }

        @Override
        public TbActorId createActorId() {
            return new TbStringActorId(actorId);
        }

        @Override
        public TbActor createActor() {
            return new StatsActor(context);
        }
    }
}
