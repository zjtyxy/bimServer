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
package com.ciat.bim.server.queue.discovery.event;

import com.ciat.bim.msg.ServiceQueueKey;
import lombok.Getter;


import java.util.Set;


public class ClusterTopologyChangeEvent extends TbApplicationEvent {

    private static final long serialVersionUID = -2441739930040282254L;

    @Getter
    private final Set<ServiceQueueKey> serviceQueueKeys;

    public ClusterTopologyChangeEvent(Object source, Set<ServiceQueueKey> serviceQueueKeys) {
        super(source);
        this.serviceQueueKeys = serviceQueueKeys;
    }
}
