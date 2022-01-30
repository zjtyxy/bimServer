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
package com.ciat.bim.tenant;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.data.id.TenantProfileId;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantProfileService;
import org.jeecg.modules.tenant.service.ITenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;

@Service
@Slf4j
public class DefaultTbTenantProfileCache implements TbTenantProfileCache {

    private final Lock tenantProfileFetchLock = new ReentrantLock();
    private final ITenantProfileService tenantProfileService;
    private final ITenantService tenantService;

    private final ConcurrentMap<String, TenantProfile> tenantProfilesMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<TenantId, TenantProfileId> tenantsMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<TenantId, ConcurrentMap<EntityId, Consumer<TenantProfile>>> profileListeners = new ConcurrentHashMap<>();

    public DefaultTbTenantProfileCache(ITenantProfileService tenantProfileService, ITenantService tenantService) {
        this.tenantProfileService = tenantProfileService;
        this.tenantService = tenantService;
    }

    @Override
    public TenantProfile get(String tenantProfileId) {
        TenantProfile profile = tenantProfilesMap.get(tenantProfileId);
        if (profile == null) {
            tenantProfileFetchLock.lock();
            try {
                profile = tenantProfilesMap.get(tenantProfileId);
                if (profile == null) {
                    profile = tenantProfileService.getById(tenantProfileId);
                    if (profile != null) {
                        tenantProfilesMap.put(tenantProfileId, profile);
                    }
                }
            } finally {
                tenantProfileFetchLock.unlock();
            }
        }
        return profile;
    }

    @Override
    public TenantProfile get(TenantId tenantId) {
        TenantProfileId profileId = tenantsMap.get(tenantId);
        if (profileId == null) {
            Tenant tenant = tenantService.getById(tenantId.getId());
            if (tenant != null) {
                profileId = new TenantProfileId(tenant.getTenantProfileId());
                tenantsMap.put(tenantId, profileId);
            } else {
                return null;
            }
        }
        return get(profileId.getId().toString());
    }

    @Override
    public void put(TenantProfile profile) {
        if (profile.getId() != null) {
            tenantProfilesMap.put(profile.getId(), profile);
            notifyTenantListeners(profile);
        }
    }

    @Override
    public void evict(TenantProfileId profileId) {
        tenantProfilesMap.remove(profileId);
        notifyTenantListeners(get(profileId.getId().toString()));
    }

    public void notifyTenantListeners(TenantProfile tenantProfile) {
        if (tenantProfile != null) {
            tenantsMap.forEach(((tenantId, tenantProfileId) -> {
                if (tenantProfileId.equals(tenantProfile.getId())) {
                    ConcurrentMap<EntityId, Consumer<TenantProfile>> tenantListeners = profileListeners.get(tenantId);
                    if (tenantListeners != null) {
                        tenantListeners.forEach((id, listener) -> listener.accept(tenantProfile));
                    }
                }
            }));
        }
    }

    @Override
    public void evict(TenantId tenantId) {
        tenantsMap.remove(tenantId);
        TenantProfile tenantProfile = get(tenantId);
        if (tenantProfile != null) {
            ConcurrentMap<EntityId, Consumer<TenantProfile>> tenantListeners = profileListeners.get(tenantId);
            if (tenantListeners != null) {
                tenantListeners.forEach((id, listener) -> listener.accept(tenantProfile));
            }
        }
    }

    @Override
    public void addListener(TenantId tenantId, EntityId listenerId, Consumer<TenantProfile> profileListener) {
        //Force cache of the tenant id.
        get(tenantId);
        if (profileListener != null) {
            profileListeners.computeIfAbsent(tenantId, id -> new ConcurrentHashMap<>()).put(listenerId, profileListener);
        }
    }

    @Override
    public void removeListener(TenantId tenantId, EntityId listenerId) {
        ConcurrentMap<EntityId, Consumer<TenantProfile>> tenantListeners = profileListeners.get(tenantId);
        if (tenantListeners != null) {
            tenantListeners.remove(listenerId);
        }
    }

}
