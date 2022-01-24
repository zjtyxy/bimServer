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

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.TbMsgMetaData;
import lombok.Builder;
import lombok.Data;


/**
 * Created by ashvayka on 01.05.18.
 */
@Data
@Builder
class DeviceStateData {

    private final TenantId tenantId;
    private final CustomerId customerId;
    private final DeviceId deviceId;
    private final long deviceCreationTime;
    private TbMsgMetaData metaData;
    private final DeviceState state;

}
