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
import com.ciat.bim.msg.ServiceQueue;
import com.ciat.bim.msg.ServiceQueueKey;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.queue.QueueService;
import com.ciat.bim.server.queue.discovery.event.ClusterTopologyChangeEvent;
import com.ciat.bim.server.queue.discovery.event.ServiceListChangedEvent;
import com.ciat.bim.server.queue.setting.TbQueueRuleEngineSettings;

import com.ciat.bim.server.transport.TransportProtos.ServiceInfo;
import com.ciat.bim.server.transport.TransportProtos;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HashPartitionService implements PartitionService {

    @Value("${queue.core.topic}")
    private String coreTopic;
    @Value("${queue.core.partitions:100}")
    private Integer corePartitions;
    @Value("${queue.partitions.hash_function_name:murmur3_128}")
    private String hashFunctionName;

    private final ApplicationEventPublisher applicationEventPublisher;
    private final TbServiceInfoProvider serviceInfoProvider;
    private final TenantRoutingInfoService tenantRoutingInfoService;
    private final TbQueueRuleEngineSettings tbQueueRuleEngineSettings;
    private final QueueService queueService;
    private final ConcurrentMap<ServiceQueue, String> partitionTopics = new ConcurrentHashMap<>();
    private final ConcurrentMap<ServiceQueue, Integer> partitionSizes = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TenantRoutingInfo> tenantRoutingInfoMap = new ConcurrentHashMap<>();

    private ConcurrentMap<ServiceQueueKey, List<Integer>> myPartitions = new ConcurrentHashMap<>();
    private ConcurrentMap<TopicPartitionInfoKey, TopicPartitionInfo> tpiCache = new ConcurrentHashMap<>();

    private Map<String, TopicPartitionInfo> tbCoreNotificationTopics = new HashMap<>();
    private Map<String, TopicPartitionInfo> tbRuleEngineNotificationTopics = new HashMap<>();
    private Map<String, List<TransportProtos.ServiceInfo>> tbTransportServicesByType = new HashMap<>();
    private List<TransportProtos.ServiceInfo> currentOtherServices;

    private HashFunction hashFunction;

    public HashPartitionService(TbServiceInfoProvider serviceInfoProvider,
                                TenantRoutingInfoService tenantRoutingInfoService,
                                ApplicationEventPublisher applicationEventPublisher,
                                TbQueueRuleEngineSettings tbQueueRuleEngineSettings,
                                QueueService queueService) {
        this.serviceInfoProvider = serviceInfoProvider;
        this.tenantRoutingInfoService = tenantRoutingInfoService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.tbQueueRuleEngineSettings = tbQueueRuleEngineSettings;
        this.queueService = queueService;
    }

    @PostConstruct
    public void init() {
        this.hashFunction = forName(hashFunctionName);
        partitionSizes.put(new ServiceQueue(ServiceType.TB_CORE), corePartitions);
        partitionTopics.put(new ServiceQueue(ServiceType.TB_CORE), coreTopic);
        tbQueueRuleEngineSettings.getQueues().forEach(queueConfiguration -> {
            partitionTopics.put(new ServiceQueue(ServiceType.TB_RULE_ENGINE, queueConfiguration.getName()), queueConfiguration.getTopic());
            partitionSizes.put(new ServiceQueue(ServiceType.TB_RULE_ENGINE, queueConfiguration.getName()), queueConfiguration.getPartitions());
        });
    }

    @Override
    public TopicPartitionInfo resolve(ServiceType serviceType, String tenantId, String entityId) {
        return resolve(new ServiceQueue(serviceType), tenantId, entityId);
    }

    @Override
    public TopicPartitionInfo resolve(ServiceType serviceType, String queueName, String tenantId, String entityId) {
        queueName = queueService.resolve(serviceType, queueName);
        return resolve(new ServiceQueue(serviceType, queueName), tenantId, entityId);
    }

    private TopicPartitionInfo resolve(ServiceQueue serviceQueue, String tenantId, String entityId) {
        int hash = hashFunction.newHasher()
                .putLong(Long.parseLong(entityId))
                .putLong(Long.parseLong(entityId)).hash().asInt();
        Integer partitionSize = partitionSizes.get(serviceQueue);
        int partition;
        if (partitionSize != null) {
            partition = Math.abs(hash % partitionSize);
        } else {
            //TODO: In 2.6/3.1 this should not happen because all Rule Engine Queues will be in the DB and we always know their partition sizes.
            partition = 0;
        }
        String isolatedTenant = isIsolated(serviceQueue, tenantId);
        TopicPartitionInfoKey cacheKey = new TopicPartitionInfoKey(serviceQueue, isolatedTenant.equals("Y") ? tenantId : null, partition);
        return tpiCache.computeIfAbsent(cacheKey, key -> buildTopicPartitionInfo(serviceQueue, tenantId, partition));
    }

    @Override
    public synchronized void recalculatePartitions(TransportProtos.ServiceInfo currentService, List<TransportProtos.ServiceInfo> otherServices) {
        tbTransportServicesByType.clear();
        logServiceInfo(currentService);
        otherServices.forEach(this::logServiceInfo);
        Map<ServiceQueueKey, List<TransportProtos.ServiceInfo>> queueServicesMap = new HashMap<>();
        addNode(queueServicesMap, currentService);
        for (TransportProtos.ServiceInfo other : otherServices) {
            addNode(queueServicesMap, other);
        }
        queueServicesMap.values().forEach(list -> list.sort(Comparator.comparing(ServiceInfo::getServiceId)));

        ConcurrentMap<ServiceQueueKey, List<Integer>> oldPartitions = myPartitions;
        TenantId myIsolatedOrSystemTenantId = getSystemOrIsolatedTenantId(currentService);
        myPartitions = new ConcurrentHashMap<>();
        partitionSizes.forEach((serviceQueue, size) -> {
            ServiceQueueKey myServiceQueueKey = new ServiceQueueKey(serviceQueue, myIsolatedOrSystemTenantId.getId());
            for (int i = 0; i < size; i++) {
                ServiceInfo serviceInfo = resolveByPartitionIdx(queueServicesMap.get(myServiceQueueKey), i);
                if (currentService.equals(serviceInfo)) {
                    ServiceQueueKey serviceQueueKey = new ServiceQueueKey(serviceQueue, getSystemOrIsolatedTenantId(serviceInfo).getId());
                    myPartitions.computeIfAbsent(serviceQueueKey, key -> new ArrayList<>()).add(i);
                }
            }
        });

        oldPartitions.forEach((serviceQueueKey, partitions) -> {
            if (!myPartitions.containsKey(serviceQueueKey)) {
                log.info("[{}] NO MORE PARTITIONS FOR CURRENT KEY", serviceQueueKey);
                applicationEventPublisher.publishEvent(new PartitionChangeEvent(this, serviceQueueKey, Collections.emptySet()));
            }
        });

        myPartitions.forEach((serviceQueueKey, partitions) -> {
            if (!partitions.equals(oldPartitions.get(serviceQueueKey))) {
                log.info("[{}] NEW PARTITIONS: {}", serviceQueueKey, partitions);
                Set<TopicPartitionInfo> tpiList = partitions.stream()
                        .map(partition -> buildTopicPartitionInfo(serviceQueueKey, partition))
                        .collect(Collectors.toSet());
                applicationEventPublisher.publishEvent(new PartitionChangeEvent(this, serviceQueueKey, tpiList));
            }
        });
        tpiCache.clear();

        if (currentOtherServices == null) {
            currentOtherServices = new ArrayList<>(otherServices);
        } else {
            Set<ServiceQueueKey> changes = new HashSet<>();
            Map<ServiceQueueKey, List<ServiceInfo>> currentMap = getServiceKeyListMap(currentOtherServices);
            Map<ServiceQueueKey, List<ServiceInfo>> newMap = getServiceKeyListMap(otherServices);
            currentOtherServices = otherServices;
            currentMap.forEach((key, list) -> {
                if (!list.equals(newMap.get(key))) {
                    changes.add(key);
                }
            });
            currentMap.keySet().forEach(newMap::remove);
            changes.addAll(newMap.keySet());
            if (!changes.isEmpty()) {
                applicationEventPublisher.publishEvent(new ClusterTopologyChangeEvent(this, changes));
            }
        }

        applicationEventPublisher.publishEvent(new ServiceListChangedEvent(otherServices, currentService));
    }

    @Override
    public Set<String> getAllServiceIds(ServiceType serviceType) {
        Set<String> result = new HashSet<>();
        ServiceInfo current = serviceInfoProvider.getServiceInfo();
        if (current.getServiceTypesList().contains(serviceType.name())) {
            result.add(current.getServiceId());
        }
        if (currentOtherServices != null) {
            for (ServiceInfo serviceInfo : currentOtherServices) {
                if (serviceInfo.getServiceTypesList().contains(serviceType.name())) {
                    result.add(serviceInfo.getServiceId());
                }
            }
        }
        return result;
    }

    @Override
    public TopicPartitionInfo getNotificationsTopic(ServiceType serviceType, String serviceId) {
        switch (serviceType) {
            case TB_CORE:
                return tbCoreNotificationTopics.computeIfAbsent(serviceId,
                        id -> buildNotificationsTopicPartitionInfo(serviceType, serviceId));
            case TB_RULE_ENGINE:
                return tbRuleEngineNotificationTopics.computeIfAbsent(serviceId,
                        id -> buildNotificationsTopicPartitionInfo(serviceType, serviceId));
            default:
                return buildNotificationsTopicPartitionInfo(serviceType, serviceId);
        }
    }

    @Override
    public int resolvePartitionIndex(UUID entityId, int partitions) {
        int hash = hashFunction.newHasher()
                .putLong(entityId.getMostSignificantBits())
                .putLong(entityId.getLeastSignificantBits()).hash().asInt();
        return Math.abs(hash % partitions);
    }

    @Override
    public int countTransportsByType(String type) {
        List<ServiceInfo> list = tbTransportServicesByType.get(type);
        return list == null ? 0 : list.size();
    }

    private Map<ServiceQueueKey, List<ServiceInfo>> getServiceKeyListMap(List<ServiceInfo> services) {
        final Map<ServiceQueueKey, List<ServiceInfo>> currentMap = new HashMap<>();
        services.forEach(serviceInfo -> {
            for (String serviceTypeStr : serviceInfo.getServiceTypesList()) {
                ServiceType serviceType = ServiceType.valueOf(serviceTypeStr.toUpperCase());
                if (ServiceType.TB_RULE_ENGINE.equals(serviceType)) {
                    for (TransportProtos.QueueInfo queue : serviceInfo.getRuleEngineQueuesList()) {
                        ServiceQueueKey serviceQueueKey = new ServiceQueueKey(new ServiceQueue(serviceType, queue.getName()), getSystemOrIsolatedTenantId(serviceInfo).getId());
                        currentMap.computeIfAbsent(serviceQueueKey, key -> new ArrayList<>()).add(serviceInfo);
                    }
                } else {
                    ServiceQueueKey serviceQueueKey = new ServiceQueueKey(new ServiceQueue(serviceType), getSystemOrIsolatedTenantId(serviceInfo).getId());
                    currentMap.computeIfAbsent(serviceQueueKey, key -> new ArrayList<>()).add(serviceInfo);
                }
            }
        });
        return currentMap;
    }

    private TopicPartitionInfo buildNotificationsTopicPartitionInfo(ServiceType serviceType, String serviceId) {
        return new TopicPartitionInfo(serviceType.name().toLowerCase() + ".notifications." + serviceId, null, null, false);
    }

    private TopicPartitionInfo buildTopicPartitionInfo(ServiceQueueKey serviceQueueKey, int partition) {
        return buildTopicPartitionInfo(serviceQueueKey.getServiceQueue(), serviceQueueKey.getTenantId(), partition);
    }

    private TopicPartitionInfo buildTopicPartitionInfo(ServiceQueue serviceQueue, String tenantId, int partition) {
        TopicPartitionInfo.TopicPartitionInfoBuilder tpi = TopicPartitionInfo.builder();
        tpi.topic(partitionTopics.get(serviceQueue));
        tpi.partition(partition);
        ServiceQueueKey myPartitionsSearchKey;
        if (isIsolated(serviceQueue, tenantId).equals("Y")) {
            tpi.tenantId(tenantId);
            myPartitionsSearchKey = new ServiceQueueKey(serviceQueue, tenantId);
        } else {
            myPartitionsSearchKey = new ServiceQueueKey(serviceQueue,TenantId.NULL_UUID.toString());
        }
        List<Integer> partitions = myPartitions.get(myPartitionsSearchKey);
        if (partitions != null) {
            tpi.myPartition(partitions.contains(partition));
        } else {
            tpi.myPartition(false);
        }
        return tpi.build();
    }

    private String isIsolated(ServiceQueue serviceQueue, String tenantId) {
        if (TenantId.SYS_TENANT_ID.equals(tenantId)) {
            return "N";
        }
        TenantRoutingInfo routingInfo = tenantRoutingInfoMap.get(tenantId);
        if (routingInfo == null) {
            synchronized (tenantRoutingInfoMap) {
                routingInfo = tenantRoutingInfoMap.get(tenantId);
                if (routingInfo == null) {
                    routingInfo = tenantRoutingInfoService.getRoutingInfo(tenantId);
                    tenantRoutingInfoMap.put(tenantId, routingInfo);
                }
            }
        }
        if (routingInfo == null) {
            throw new RuntimeException("Tenant not found!");
        }
        switch (serviceQueue.getType()) {
            case TB_CORE:
                return routingInfo.getIsolatedTbCore();
            case TB_RULE_ENGINE:
                return routingInfo.getIsolatedTbRuleEngine();
            default:
                return "N";
        }
    }

    private void logServiceInfo(TransportProtos.ServiceInfo server) {
        TenantId tenantId = getSystemOrIsolatedTenantId(server);
        if (tenantId.isNullUid()) {
            log.info("[{}] Found common server: [{}]", server.getServiceId(), server.getServiceTypesList());
        } else {
            log.info("[{}][{}] Found specific server: [{}]", server.getServiceId(), tenantId, server.getServiceTypesList());
        }
    }

    private TenantId getSystemOrIsolatedTenantId(TransportProtos.ServiceInfo serviceInfo) {
        return new TenantId(serviceInfo.getTenantIdMSB()+"");
    }

    private void addNode(Map<ServiceQueueKey, List<TransportProtos.ServiceInfo>> queueServiceList, TransportProtos.ServiceInfo instance) {
        TenantId tenantId = getSystemOrIsolatedTenantId(instance);
        for (String serviceTypeStr : instance.getServiceTypesList()) {
            ServiceType serviceType = ServiceType.valueOf(serviceTypeStr.toUpperCase());
            if (ServiceType.TB_RULE_ENGINE.equals(serviceType)) {
                for (TransportProtos.QueueInfo queue : instance.getRuleEngineQueuesList()) {
                    ServiceQueueKey serviceQueueKey = new ServiceQueueKey(new ServiceQueue(serviceType, queue.getName()), tenantId.getId());
                    partitionSizes.put(new ServiceQueue(ServiceType.TB_RULE_ENGINE, queue.getName()), queue.getPartitions());
                    partitionTopics.put(new ServiceQueue(ServiceType.TB_RULE_ENGINE, queue.getName()), queue.getTopic());
                    queueServiceList.computeIfAbsent(serviceQueueKey, key -> new ArrayList<>()).add(instance);
                }
            } else {
                ServiceQueueKey serviceQueueKey = new ServiceQueueKey(new ServiceQueue(serviceType), tenantId.getId());
                queueServiceList.computeIfAbsent(serviceQueueKey, key -> new ArrayList<>()).add(instance);
            }
        }
        for (String transportType : instance.getTransportsList()) {
            tbTransportServicesByType.computeIfAbsent(transportType, t -> new ArrayList<>()).add(instance);
        }
    }

    private TransportProtos.ServiceInfo resolveByPartitionIdx(List<TransportProtos.ServiceInfo> servers, Integer partitionIdx) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        return servers.get(partitionIdx % servers.size());
    }

    public static HashFunction forName(String name) {
        switch (name) {
            case "murmur3_32":
                return Hashing.murmur3_32();
            case "murmur3_128":
                return Hashing.murmur3_128();
            case "sha256":
                return Hashing.sha256();
            default:
                throw new IllegalArgumentException("Can't find hash function with name " + name);
        }
    }

}
