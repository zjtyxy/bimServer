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
package com.ciat.bim.server.transport;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.MsgType;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.server.common.msg.aware.DeviceAwareMsg;
import com.ciat.bim.server.common.msg.aware.TenantAwareMsg;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import lombok.Data;
import com.ciat.bim.server.transport.TransportProtos.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by ashvayka on 09.10.18.
 */
@Data
public class TransportToDeviceActorMsgWrapper implements TbActorMsg, DeviceAwareMsg, TenantAwareMsg, Serializable {

    private static final long serialVersionUID = 7191333353202935941L;

    private final String tenantId;
    private final DeviceId deviceId;
    private final TransportToDeviceActorMsg msg;
    private final TbCallback callback;

    public TransportToDeviceActorMsgWrapper(TransportToDeviceActorMsg msg, TbCallback callback) {
        this.msg = msg;
        this.callback = callback;
        this.tenantId = msg.getSessionInfo().getTenantIdMSB()+"";
        this.deviceId = new DeviceId(msg.getSessionInfo().getDeviceIdMSB()+"");
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.TRANSPORT_TO_DEVICE_ACTOR_MSG;
    }
}
