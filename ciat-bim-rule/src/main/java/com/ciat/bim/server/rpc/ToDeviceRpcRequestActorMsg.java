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
package com.ciat.bim.server.rpc;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.MsgType;
import com.ciat.bim.server.common.msg.ToDeviceActorNotificationMsg;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


/**
 * Created by ashvayka on 16.04.18.
 */
@ToString
@RequiredArgsConstructor
public class ToDeviceRpcRequestActorMsg implements ToDeviceActorNotificationMsg {

    private static final long serialVersionUID = -8592877558138716589L;

    @Getter
    private final String serviceId;
    @Getter
    private final ToDeviceRpcRequest msg;

    @Override
    public DeviceId getDeviceId() {
        return msg.getDeviceId();
    }

    @Override
    public TenantId getTenantId() {
        return msg.getTenantId();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.DEVICE_RPC_REQUEST_TO_DEVICE_ACTOR_MSG;
    }
}
