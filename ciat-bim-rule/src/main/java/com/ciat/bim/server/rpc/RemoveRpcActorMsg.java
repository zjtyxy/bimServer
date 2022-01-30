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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


import java.util.UUID;

@ToString
@RequiredArgsConstructor
public class RemoveRpcActorMsg implements ToDeviceActorNotificationMsg {

    @Getter
    private final String tenantId;
    @Getter
    private final DeviceId deviceId;

    @Getter
    private final UUID requestId;

    @Override
    public MsgType getMsgType() {
        return MsgType.REMOVE_RPC_TO_DEVICE_ACTOR_MSG;
    }
}
