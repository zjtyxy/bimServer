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
package com.ciat.bim.transport.service;

import com.ciat.bim.server.transport.TransportProtos;
import lombok.Data;


/**
 * Created by ashvayka on 15.10.18.
 */
@Data
public class SessionActivityData {

    private volatile TransportProtos.SessionInfoProto sessionInfo;
    private volatile long lastActivityTime;
    private volatile long lastReportedActivityTime;

    SessionActivityData(TransportProtos.SessionInfoProto sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    void updateLastActivityTime() {
        this.lastActivityTime = System.currentTimeMillis();
    }

}
