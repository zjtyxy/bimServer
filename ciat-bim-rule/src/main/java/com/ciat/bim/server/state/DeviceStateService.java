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
package com.ciat.bim.server.state;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.transport.TransportProtos;
import org.springframework.context.ApplicationListener;


/**
 * Created by ashvayka on 01.05.18.
 */
public interface DeviceStateService extends ApplicationListener<PartitionChangeEvent> {

    void onDeviceConnect(String tenantId, DeviceId deviceId);

    void onDeviceActivity(String tenantId, DeviceId deviceId, long lastReportedActivityTime);

    void onDeviceDisconnect(String tenantId, DeviceId deviceId);

    void onDeviceInactivityTimeoutUpdate(String tenantId, DeviceId deviceId, long inactivityTimeout);

    void onQueueMsg(TransportProtos.DeviceStateServiceMsgProto proto, TbCallback bytes);

}
