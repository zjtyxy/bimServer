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
package com.ciat.bim.server.queue.discovery;



import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.transport.TransportProtos;

import java.util.List;
import java.util.Set;
import java.util.UUID;


public interface PartitionService {

    TopicPartitionInfo resolve(ServiceType serviceType, String tenantId, String entityId);

    TopicPartitionInfo resolve(ServiceType serviceType, String queueName, String tenantId, String entityId);

    /**
     * Received from the Discovery service when network topology is changed.

     */
    void recalculatePartitions(TransportProtos.ServiceInfo currentService, List<TransportProtos.ServiceInfo> otherServices);

    /**
     * Get all active service ids by service type
     * @param serviceType to filter the list of services
     * @return list of all active services
     */
    Set<String> getAllServiceIds(ServiceType serviceType);

    /**
     * Each Service should start a consumer for messages that target individual service instance based on serviceId.
     * This topic is likely to have single partition, and is always assigned to the service.
     * @param serviceType
     * @param serviceId
     * @return
     */
    TopicPartitionInfo getNotificationsTopic(ServiceType serviceType, String serviceId);

    int resolvePartitionIndex(UUID entityId, int partitions);

    int countTransportsByType(String type);
}
