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
package com.ciat.bim.transport.session;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.transport.auth.TransportDeviceInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;


import java.util.Optional;
import java.util.UUID;

/**
 * @author Andrew Shvayka
 */
@Data
public abstract class DeviceAwareSessionContext implements SessionContext {

    @Getter
    protected final UUID sessionId;
    @Getter
    private volatile DeviceId deviceId;
    @Getter
    protected volatile TransportDeviceInfo deviceInfo;
    @Getter
    @Setter
    protected volatile DeviceProfile deviceProfile;
    @Getter
    @Setter
    private volatile TransportProtos.SessionInfoProto sessionInfo;

    @Setter
    private volatile boolean connected;

    public DeviceId getDeviceId() {
        return deviceId;
    }

    public void setDeviceInfo(TransportDeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
        this.deviceId = deviceInfo.getDeviceId();
    }

    @Override
    public void onDeviceProfileUpdate(TransportProtos.SessionInfoProto sessionInfo, DeviceProfile deviceProfile) {
        this.sessionInfo = sessionInfo;
        this.deviceProfile = deviceProfile;
        this.deviceInfo.setDeviceType(deviceProfile.getName());

    }

    @Override
    public void onDeviceUpdate(TransportProtos.SessionInfoProto sessionInfo, Device device, Optional<DeviceProfile> deviceProfileOpt) {
        this.sessionInfo = sessionInfo;
        this.deviceInfo.setDeviceProfileId(DeviceProfileId.fromString(device.getDeviceProfileId()));
        this.deviceInfo.setDeviceType(device.getType());
        deviceProfileOpt.ifPresent(profile -> this.deviceProfile = profile);
    }

    public boolean isConnected() {
        return connected;
    }

    public void setDisconnected() {
        this.connected = false;
    }
}
