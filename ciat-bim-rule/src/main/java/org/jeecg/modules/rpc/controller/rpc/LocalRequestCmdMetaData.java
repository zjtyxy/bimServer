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
package org.jeecg.modules.rpc.controller.rpc;

import com.ciat.bim.server.common.msg.rpc.ToDeviceCmdRequest;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequest;
import com.ciat.bim.server.security.SecurityUser;
import lombok.Data;
import org.jeecg.common.api.vo.Result;
import org.springframework.http.ResponseEntity;

/**
 * Created by ashvayka on 16.04.18.
 */
@Data
public class LocalRequestCmdMetaData {
    private final ToDeviceCmdRequest request;
    private final SecurityUser user;
    private final Result<ResponseEntity> responseWriter;
}
