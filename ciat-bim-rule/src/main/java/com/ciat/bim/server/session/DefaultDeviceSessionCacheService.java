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
package com.ciat.bim.server.session;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.transport.TransportProtos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.Collections;

import static com.ciat.bim.server.common.data.CacheConstants.SESSIONS_CACHE;

/**
 * Created by ashvayka on 29.10.18.
 */
@Service
@TbCoreComponent
@Slf4j
public class DefaultDeviceSessionCacheService implements DeviceSessionCacheService {

    @Override
    //@Cacheable(cacheNames = SESSIONS_CACHE, key = "#p0.toString()")
    public byte[] get(DeviceId deviceId) {
        log.debug("[{}] Fetching session data from cache", deviceId);
        return TransportProtos.DeviceSessionsCacheEntry.newBuilder().addAllSessions(Collections.emptyList()).build().toByteArray();
    }

    @Override
    @CachePut(cacheNames = SESSIONS_CACHE, key = "#p0.toString()")
    public byte[] put(DeviceId deviceId, byte[] sessions) {
        log.debug("[{}] Pushing session data to cache: {}", deviceId, sessions);
        return sessions;
    }
}
