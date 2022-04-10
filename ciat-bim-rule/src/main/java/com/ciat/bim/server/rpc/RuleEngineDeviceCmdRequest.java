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
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Created by ashvayka on 02.04.18.
 */
@Data
@Builder
public final class RuleEngineDeviceCmdRequest {

    private final TenantId tenantId;
    private final DeviceId deviceId;
    private final int requestId;
    private final UUID requestUUID;
    private final String originServiceId;
    private final boolean oneway;
    private final boolean persisted;
    private final String type;
    private final String body;
    private final long expirationTime;
    private final boolean restApiCall;
    private final String additionalInfo;
    private final Integer retries;
}
