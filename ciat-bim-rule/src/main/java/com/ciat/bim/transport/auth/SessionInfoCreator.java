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
package com.ciat.bim.transport.auth;

import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.transport.TransportContext;
import lombok.extern.slf4j.Slf4j;


import java.util.UUID;

@Slf4j
public class SessionInfoCreator {

    public static TransportProtos.SessionInfoProto create(ValidateDeviceCredentialsResponse msg, TransportContext context, UUID sessionId) {
        return getSessionInfoProto(msg, context.getNodeId(), sessionId);
    }

    public static TransportProtos.SessionInfoProto create(ValidateDeviceCredentialsResponse msg, String nodeId, UUID sessionId) {
        return getSessionInfoProto(msg, nodeId, sessionId);
    }

    private static TransportProtos.SessionInfoProto getSessionInfoProto(ValidateDeviceCredentialsResponse msg, String nodeId, UUID sessionId) {
        return TransportProtos.SessionInfoProto.newBuilder().setNodeId(nodeId)
                .setSessionIdMSB(sessionId.getMostSignificantBits())
                .setSessionIdLSB(sessionId.getLeastSignificantBits())
                .setDeviceIdMSB(Long.parseLong(msg.getDeviceInfo().getDeviceId().getId()))
                .setDeviceIdLSB(Long.parseLong(msg.getDeviceInfo().getDeviceId().getId()))
                .setTenantIdMSB(Long.parseLong(msg.getDeviceInfo().getTenantId().getId()))
                .setTenantIdLSB(Long.parseLong(msg.getDeviceInfo().getTenantId().getId()))
                .setCustomerIdMSB(Long.parseLong(msg.getDeviceInfo().getCustomerId().getId()))
                .setCustomerIdLSB(Long.parseLong(msg.getDeviceInfo().getCustomerId().getId()))
                .setDeviceName(msg.getDeviceInfo().getDeviceName())
                .setDeviceType(msg.getDeviceInfo().getDeviceType())
                .setDeviceProfileIdMSB(Long.parseLong(msg.getDeviceInfo().getDeviceProfileId().getId()))
                .setDeviceProfileIdLSB(Long.parseLong(msg.getDeviceInfo().getDeviceProfileId().getId()))
                .build();
    }

}
