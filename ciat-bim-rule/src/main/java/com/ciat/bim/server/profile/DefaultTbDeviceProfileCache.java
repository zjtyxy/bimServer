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
package com.ciat.bim.server.profile;


import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.device.service.IDeviceProfileService;
import org.jeecg.modules.device.service.IDeviceService;
import org.springframework.stereotype.Service;


import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Service
@Slf4j
public class DefaultTbDeviceProfileCache implements TbDeviceProfileCache {

    private final Lock deviceProfileFetchLock = new ReentrantLock();
    private final IDeviceProfileService deviceProfileService;
    private final IDeviceService deviceService;

    private final ConcurrentMap<DeviceProfileId, DeviceProfile> deviceProfilesMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<DeviceId, DeviceProfileId> devicesMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ConcurrentMap<EntityId, Consumer<DeviceProfile>>> profileListeners = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, ConcurrentMap<EntityId, BiConsumer<DeviceId, DeviceProfile>>> deviceProfileListeners = new ConcurrentHashMap<>();

    public DefaultTbDeviceProfileCache(IDeviceProfileService deviceProfileService, IDeviceService deviceService) {
        this.deviceProfileService = deviceProfileService;
        this.deviceService = deviceService;
    }

    @Override
    public DeviceProfile get(String tenantId, DeviceProfileId deviceProfileId) {
        DeviceProfile profile = deviceProfilesMap.get(deviceProfileId);
        if (profile == null) {
            deviceProfileFetchLock.lock();
            try {
                profile = deviceProfilesMap.get(deviceProfileId);
                if (profile == null) {
                    profile = deviceProfileService.findOrCreateDeviceProfile(deviceProfileId.getId());
                    if (profile != null) {
                        deviceProfilesMap.put(deviceProfileId, profile);
                        log.debug("[{}] Fetch device profile into cache: {}", profile.getId(), profile);
                    }
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                deviceProfileFetchLock.unlock();
            }
        }
        log.trace("[{}] Found device profile in cache: {}", deviceProfileId, profile);
        return profile;
    }

    @Override
    public DeviceProfile get(String tenantId, DeviceId deviceId) {
        DeviceProfileId profileId = devicesMap.get(deviceId);
        if (profileId == null) {
            Device device = deviceService.getById(deviceId.getId());
            if (device != null) {
                profileId = DeviceProfileId.fromString(device.getDeviceProfileId());
                devicesMap.put(deviceId, profileId);
            } else {
                return null;
            }
        }
        return get(tenantId, profileId);
    }


    public void evict(String tenantId, DeviceProfileId profileId) {
        DeviceProfile oldProfile = deviceProfilesMap.remove(profileId);
        log.debug("[{}] evict device profile from cache: {}", profileId, oldProfile);
        DeviceProfile newProfile = get(tenantId, profileId);
        if (newProfile != null) {
            notifyProfileListeners(newProfile);
        }
    }


    public void evict(String tenantId, DeviceId deviceId) {
        DeviceProfileId old = devicesMap.remove(deviceId);
        if (old != null) {
            DeviceProfile newProfile = get(tenantId, deviceId);
            if (newProfile == null || !old.equals(newProfile.getId())) {
                notifyDeviceListeners(tenantId, deviceId, newProfile);
            }
        }
    }

    @Override
    public void addListener(String tenantId, EntityId listenerId,
                            Consumer<DeviceProfile> profileListener,
                            BiConsumer<DeviceId, DeviceProfile> deviceListener) {
        if (profileListener != null) {
            profileListeners.computeIfAbsent(tenantId, id -> new ConcurrentHashMap<>()).put(listenerId, profileListener);
        }
        if (deviceListener != null) {
            deviceProfileListeners.computeIfAbsent(tenantId, id -> new ConcurrentHashMap<>()).put(listenerId, deviceListener);
        }
    }

    @Override
    public DeviceProfile find(DeviceProfileId deviceProfileId) {

        return deviceProfileService.findOrCreateDeviceProfile(deviceProfileId.getId());
    }

    @Override
    public DeviceProfile findOrCreateDeviceProfile(TenantId tenantId, String profileName) {
        return deviceProfileService.findOrCreateDeviceProfile(tenantId, profileName);
    }

    @Override
    public void removeListener(String tenantId, EntityId listenerId) {
        ConcurrentMap<EntityId, Consumer<DeviceProfile>> tenantListeners = profileListeners.get(tenantId);
        if (tenantListeners != null) {
            tenantListeners.remove(listenerId);
        }
        ConcurrentMap<EntityId, BiConsumer<DeviceId, DeviceProfile>> deviceListeners = deviceProfileListeners.get(tenantId);
        if (deviceListeners != null) {
            deviceListeners.remove(listenerId);
        }
    }

    private void notifyProfileListeners(DeviceProfile profile) {
        ConcurrentMap<EntityId, Consumer<DeviceProfile>> tenantListeners = profileListeners.get(profile.getTenantId());
        if (tenantListeners != null) {
            tenantListeners.forEach((id, listener) -> listener.accept(profile));
        }
    }

    private void notifyDeviceListeners(String tenantId, DeviceId deviceId, DeviceProfile profile) {
        if (profile != null) {
            ConcurrentMap<EntityId, BiConsumer<DeviceId, DeviceProfile>> tenantListeners = deviceProfileListeners.get(tenantId);
            if (tenantListeners != null) {
                tenantListeners.forEach((id, listener) -> listener.accept(deviceId, profile));
            }
        }
    }

}
