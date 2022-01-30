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
package com.ciat.bim.server.event;

import com.ciat.bim.data.Event;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.data.event.EventFilter;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.TimePageLink;
import com.ciat.bim.server.exception.DataValidationException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.google.common.util.concurrent.ListenableFuture;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BaseEventService implements EventService {

    @Value("${event.debug.max-symbols:4096}")
    private int maxDebugEventSymbols;

//    @Autowired
//    public EventDao eventDao;

    @Override
    public Event save(Event event) {
//        eventValidator.validate(event, Event::getTenantId);
//        return eventDao.save(event.getTenantId(), event);
        return  null;
    }

    @Override
    public ListenableFuture<Event> saveAsync(Event event) {
//        eventValidator.validate(event, Event::getTenantId);
//        checkAndTruncateDebugEvent(event);
//        return eventDao.saveAsync(event);
        return  null;
    }

    @Override
    public Optional<Event> saveIfNotExists(Event event) {
//        eventValidator.validate(event, Event::getTenantId);
//        if (StringUtils.isEmpty(event.getUid())) {
//            throw new DataValidationException("Event uid should be specified!.");
//        }
//        checkAndTruncateDebugEvent(event);
//        return eventDao.saveIfNotExists(event);
        return  null;
    }

    private void checkAndTruncateDebugEvent(Event event) {
        if (event.getType().startsWith("DEBUG") && event.getBody() != null && event.getBody().has("data")) {
            String dataStr = event.getBody().get("data").asText();
            int length = dataStr.length();
            if (length > maxDebugEventSymbols) {
                ((ObjectNode) event.getBody()).put("data", dataStr.substring(0, maxDebugEventSymbols) + "...[truncated " + (length - maxDebugEventSymbols) + " symbols]");
                log.trace("[{}] Event was truncated: {}", event.getId(), dataStr);
            }
        }
    }

    @Override
    public Optional<Event> findEvent(TenantId tenantId, EntityId entityId, String eventType, String eventUid) {
        if (tenantId == null) {
            throw new DataValidationException("Tenant id should be specified!.");
        }
        if (entityId == null) {
            throw new DataValidationException("Entity id should be specified!.");
        }
        if (StringUtils.isEmpty(eventType)) {
            throw new DataValidationException("Event type should be specified!.");
        }
        if (StringUtils.isEmpty(eventUid)) {
            throw new DataValidationException("Event uid should be specified!.");
        }
//        Event event = eventDao.findEvent(tenantId.getId(), entityId, eventType, eventUid);
//        return event != null ? Optional.of(event) : Optional.empty();
        return  null;

    }

    @Override
    public PageData<Event> findEvents(TenantId tenantId, EntityId entityId, TimePageLink pageLink) {
       // return eventDao.findEvents(tenantId.getId(), entityId, pageLink);
        return  null;
    }

    @Override
    public PageData<Event> findEvents(TenantId tenantId, EntityId entityId, String eventType, TimePageLink pageLink) {
      //  return eventDao.findEvents(tenantId.getId(), entityId, eventType, pageLink);
        return  null;
    }

    @Override
    public List<Event> findLatestEvents(TenantId tenantId, EntityId entityId, String eventType, int limit) {
       // return eventDao.findLatestEvents(tenantId.getId(), entityId, eventType, limit);
        return  null;
    }

    @Override
    public PageData<Event> findEventsByFilter(TenantId tenantId, EntityId entityId, EventFilter eventFilter, TimePageLink pageLink) {
       // return eventDao.findEventByFilter(tenantId.getId(), entityId, eventFilter, pageLink);
        return  null;
    }

    @Override
    public void removeEvents(TenantId tenantId, EntityId entityId) {
//        PageData<Event> eventPageData;
//        TimePageLink eventPageLink = new TimePageLink(1000);
//        do {
//            eventPageData = findEvents(tenantId, entityId, eventPageLink);
//            for (Event event : eventPageData.getData()) {
//                eventDao.removeById(tenantId, event.getUuidId());
//            }
//            if (eventPageData.hasNext()) {
//                eventPageLink = eventPageLink.nextPageLink();
//            }
//        } while (eventPageData.hasNext());
    }

    @Override
    public void cleanupEvents(long ttl, long debugTtl) {
      //  eventDao.cleanupEvents(ttl, debugTtl);
    }

//    private DataValidator<Event> eventValidator =
//            new DataValidator<Event>() {
//                @Override
//                protected void validateDataImpl(TenantId tenantId, Event event) {
//                    if (event.getEntityId() == null) {
//                        throw new DataValidationException("Entity id should be specified!.");
//                    }
//                    if (StringUtils.isEmpty(event.getType())) {
//                        throw new DataValidationException("Event type should be specified!.");
//                    }
//                    if (event.getBody() == null) {
//                        throw new DataValidationException("Event body should be specified!.");
//                    }
//                }
//            };
}
