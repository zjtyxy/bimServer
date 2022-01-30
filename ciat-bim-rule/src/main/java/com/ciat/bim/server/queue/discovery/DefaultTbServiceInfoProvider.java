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

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.server.TbTransportService;
import com.ciat.bim.server.queue.setting.TbQueueRuleEngineSettings;
import com.ciat.bim.server.queue.setting.TbRuleEngineQueueConfiguration;
import com.ciat.bim.server.queue.util.AfterContextReady;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.ServiceInfo;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DefaultTbServiceInfoProvider implements TbServiceInfoProvider {

    @Getter
    @Value("${service.id:#{null}}")
    private String serviceId;

    @Getter
    @Value("${service.type:monolith}")
    private String serviceType;

    @Getter
    @Value("${service.tenant_id:}")
    private String tenantIdStr;

    @Autowired(required = false)
    private TbQueueRuleEngineSettings ruleEngineSettings;
    @Autowired
    private ApplicationContext applicationContext;

    private List<ServiceType> serviceTypes;
    private TransportProtos.ServiceInfo serviceInfo;
    private TenantId isolatedTenant;

    @PostConstruct
    public void init() {
        if (StringUtils.isEmpty(serviceId)) {
            try {
                serviceId = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                serviceId = org.apache.commons.lang3.RandomStringUtils.randomAlphabetic(10);
            }
        }
        log.info("Current Service ID: {}", serviceId);
        if (serviceType.equalsIgnoreCase("monolith")) {
            serviceTypes = Collections.unmodifiableList(Arrays.asList(ServiceType.values()));
        } else {
            serviceTypes = Collections.singletonList(ServiceType.of(serviceType));
        }
        TransportProtos.ServiceInfo.Builder builder = ServiceInfo.newBuilder()
                .setServiceId(serviceId)
                .addAllServiceTypes(serviceTypes.stream().map(ServiceType::name).collect(Collectors.toList()));
        String tenantId;
        if (!StringUtils.isEmpty(tenantIdStr)) {
            tenantId = tenantIdStr;
            isolatedTenant = new TenantId(tenantIdStr);
        } else {
            tenantId = TenantId.NULL_UUID.toString();
        }
        builder.setTenantIdMSB(Long.parseLong(tenantId));
        builder.setTenantIdLSB(Long.parseLong(tenantId));

        if (serviceTypes.contains(ServiceType.TB_RULE_ENGINE) && ruleEngineSettings != null) {
            for (TbRuleEngineQueueConfiguration queue : ruleEngineSettings.getQueues()) {
                TransportProtos.QueueInfo queueInfo = TransportProtos.QueueInfo.newBuilder()
                        .setName(queue.getName())
                        .setTopic(queue.getTopic())
                        .setPartitions(queue.getPartitions()).build();
                builder.addRuleEngineQueues(queueInfo);
            }
        }

        serviceInfo = builder.build();
    }

    @AfterContextReady
    public void setTransports() {
        serviceInfo = ServiceInfo.newBuilder(serviceInfo)
                .addAllTransports(getTransportServices().stream()
                        .map(TbTransportService::getName)
                        .collect(Collectors.toSet()))
                .build();
    }

    private Collection<TbTransportService> getTransportServices() {
        return applicationContext.getBeansOfType(TbTransportService.class).values();
    }

    @Override
    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    @Override
    public boolean isService(ServiceType serviceType) {
        return serviceTypes.contains(serviceType);
    }

    @Override
    public Optional<TenantId> getIsolatedTenant() {
        return Optional.ofNullable(isolatedTenant);
    }
}
