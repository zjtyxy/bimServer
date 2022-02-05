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
package com.ciat.bim.transport;

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.data.id.TenantProfileId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.utils.TbTransportComponent;
import com.ciat.bim.transport.limit.TransportRateLimitService;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
@TbTransportComponent
@Slf4j
public class DefaultTransportTenantProfileCache implements TransportTenantProfileCache {

    private final Lock tenantProfileFetchLock = new ReentrantLock();
    private final ConcurrentMap<TenantProfileId, TenantProfile> profiles = new ConcurrentHashMap<>();
    private final ConcurrentMap<TenantId, TenantProfileId> tenantIds = new ConcurrentHashMap<>();
    private final ConcurrentMap<TenantProfileId, Set<TenantId>> tenantProfileIds = new ConcurrentHashMap<>();
    private final DataDecodingEncodingService dataDecodingEncodingService;

    private TransportRateLimitService rateLimitService;
    private TransportService transportService;

    @Lazy
    @Autowired
    public void setRateLimitService(TransportRateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Lazy
    @Autowired
    public void setTransportService(TransportService transportService) {
        this.transportService = transportService;
    }

    public DefaultTransportTenantProfileCache(DataDecodingEncodingService dataDecodingEncodingService) {
        this.dataDecodingEncodingService = dataDecodingEncodingService;
    }

    @Override
    public TenantProfile get(TenantId tenantId) {
        return getTenantProfile(tenantId);
    }

    @Override
    public TenantProfileUpdateResult put(ByteString profileBody) {
        Optional<TenantProfile> profileOpt = dataDecodingEncodingService.decode(profileBody.toByteArray());
        if (profileOpt.isPresent()) {
            TenantProfile newProfile = profileOpt.get();
            log.trace("[{}] put: {}", newProfile.getId(), newProfile);
            Set<TenantId> affectedTenants = tenantProfileIds.get(newProfile.getId());
            return new TenantProfileUpdateResult(newProfile, affectedTenants != null ? affectedTenants : Collections.emptySet());
        } else {
            log.warn("Failed to decode profile: {}", profileBody.toString());
            return new TenantProfileUpdateResult(null, Collections.emptySet());
        }
    }

    @Override
    public boolean put(TenantId tenantId, TenantProfileId profileId) {
        log.trace("[{}] put: {}", tenantId, profileId);
        TenantProfileId oldProfileId = tenantIds.get(tenantId);
        if (oldProfileId != null && !oldProfileId.equals(profileId)) {
            tenantProfileIds.computeIfAbsent(oldProfileId, id -> ConcurrentHashMap.newKeySet()).remove(tenantId);
            tenantIds.put(tenantId, profileId);
            tenantProfileIds.computeIfAbsent(profileId, id -> ConcurrentHashMap.newKeySet()).add(tenantId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<TenantId> remove(TenantProfileId profileId) {
        Set<TenantId> tenants = tenantProfileIds.remove(profileId);
        if (tenants != null) {
            tenants.forEach(tenantIds::remove);
        }
        profiles.remove(profileId);
        return tenants;
    }

    private TenantProfile getTenantProfile(TenantId tenantId) {
        TenantProfile profile = null;
        TenantProfileId tenantProfileId = tenantIds.get(tenantId);
        if (tenantProfileId != null) {
            profile = profiles.get(tenantProfileId);
        }
        if (profile == null) {
            tenantProfileFetchLock.lock();
            try {
                tenantProfileId = tenantIds.get(tenantId);
                if (tenantProfileId != null) {
                    profile = profiles.get(tenantProfileId);
                }
                if (profile == null) {
                    TransportProtos.GetEntityProfileRequestMsg msg = TransportProtos.GetEntityProfileRequestMsg.newBuilder()
                            .setEntityType(EntityType.TENANT.name())
                            .setEntityIdMSB(Long.parseLong(tenantId.getId()))
                            .setEntityIdLSB(Long.parseLong(tenantId.getId()))
                            .build();
                    TransportProtos.GetEntityProfileResponseMsg entityProfileMsg = transportService.getEntityProfile(msg);
                    Optional<TenantProfile> profileOpt = dataDecodingEncodingService.decode(entityProfileMsg.getData().toByteArray());
                    if (profileOpt.isPresent()) {
                        profile = profileOpt.get();
                        TenantProfile existingProfile = profiles.get(profile.getId());
                        if (existingProfile != null) {
                            profile = existingProfile;
                        } else {
                            profiles.put(TenantProfileId.fromString(profile.getId()), profile);
                        }
                        tenantProfileIds.computeIfAbsent(TenantProfileId.fromString(profile.getId()), id -> ConcurrentHashMap.newKeySet()).add(tenantId);
                        tenantIds.put(tenantId,TenantProfileId.fromString( profile.getId()));
                    } else {
                        log.warn("[{}] Can't decode tenant profile: {}", tenantId, entityProfileMsg.getData());
                        throw new RuntimeException("Can't decode tenant profile!");
                    }
                    Optional<ApiUsageState> apiStateOpt = dataDecodingEncodingService.decode(entityProfileMsg.getApiState().toByteArray());
                    apiStateOpt.ifPresent(apiUsageState -> rateLimitService.update(tenantId, apiUsageState.isTransportEnabled()));
                }
            } finally {
                tenantProfileFetchLock.unlock();
            }
        }
        return profile;
    }


}
