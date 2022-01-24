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
package com.ciat.bim.server.queue.queue.discovery.event;

import com.ciat.bim.msg.ServiceQueueKey;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TopicPartitionInfo;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@ToString(callSuper = true)
public class PartitionChangeEvent extends TbApplicationEvent {

    private static final long serialVersionUID = -8731788167026510559L;

    @Getter
    private final ServiceQueueKey serviceQueueKey;
    @Getter
    private final Set<TopicPartitionInfo> partitions;

    public PartitionChangeEvent(Object source, ServiceQueueKey serviceQueueKey, Set<TopicPartitionInfo> partitions) {
        super(source);
        this.serviceQueueKey = serviceQueueKey;
        this.partitions = partitions;
    }

    public ServiceType getServiceType() {
        return serviceQueueKey.getServiceQueue().getType();
    }
}
