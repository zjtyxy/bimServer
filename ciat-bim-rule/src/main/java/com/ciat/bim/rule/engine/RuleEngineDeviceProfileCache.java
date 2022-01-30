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
package com.ciat.bim.rule.engine;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.DeviceProfileId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import org.jeecg.modules.device.entity.DeviceProfile;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by ashvayka on 02.04.18.
 */
public interface RuleEngineDeviceProfileCache {

    DeviceProfile get(String tenantId, DeviceProfileId deviceProfileId);

    DeviceProfile get(String tenantId, DeviceId deviceId);

    void addListener(String tenantId, EntityId listenerId, Consumer<DeviceProfile> profileListener, BiConsumer<DeviceId, DeviceProfile> devicelistener);

    void removeListener(String tenantId, EntityId listenerId);

}
