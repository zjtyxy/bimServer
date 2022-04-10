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
package com.ciat.bim.server.queue.provider;

import com.ciat.bim.server.queue.TbQueueAdmin;
import com.ciat.bim.server.queue.TbQueueConsumer;
import com.ciat.bim.server.queue.TbQueueRequestTemplate;
import com.ciat.bim.server.queue.common.DefaultTbQueueRequestTemplate;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.TbServiceInfoProvider;
import com.ciat.bim.server.queue.memory.InMemoryTbQueueConsumer;
import com.ciat.bim.server.queue.memory.InMemoryTbQueueProducer;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.queue.setting.TbQueueCoreSettings;
import com.ciat.bim.server.queue.setting.TbQueueTransportApiSettings;
import com.ciat.bim.server.queue.setting.TbQueueTransportNotificationSettings;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;


@Component
@ConditionalOnExpression("'${queue.type:null}'=='in-memory' && (('${service.type:null}'=='monolith' && '${transport.api_enabled:true}'=='true') || '${service.type:null}'=='tb-transport')")
@Slf4j
public class InMemoryTbTransportQueueFactory implements TbTransportQueueFactory {
    private final TbQueueTransportApiSettings transportApiSettings;
    private final TbQueueTransportNotificationSettings transportNotificationSettings;
    private final TbServiceInfoProvider serviceInfoProvider;
    private final TbQueueCoreSettings coreSettings;

    public InMemoryTbTransportQueueFactory(TbQueueTransportApiSettings transportApiSettings,
                                           TbQueueTransportNotificationSettings transportNotificationSettings,
                                           TbServiceInfoProvider serviceInfoProvider,
                                           TbQueueCoreSettings coreSettings) {
        this.transportApiSettings = transportApiSettings;
        this.transportNotificationSettings = transportNotificationSettings;
        this.serviceInfoProvider = serviceInfoProvider;
        this.coreSettings = coreSettings;
    }

    @Override
    public TbQueueRequestTemplate<TbProtoQueueMsg<TransportApiRequestMsg>, TbProtoQueueMsg<TransportApiResponseMsg>> createTransportApiRequestTemplate() {
        InMemoryTbQueueProducer<TbProtoQueueMsg<TransportApiRequestMsg>> producerTemplate =
                new InMemoryTbQueueProducer<>(transportApiSettings.getRequestsTopic());

        InMemoryTbQueueConsumer<TbProtoQueueMsg<TransportApiResponseMsg>> consumerTemplate =
                new InMemoryTbQueueConsumer<>(transportApiSettings.getResponsesTopic() + "." + serviceInfoProvider.getServiceId());

        DefaultTbQueueRequestTemplate.DefaultTbQueueRequestTemplateBuilder
                <TbProtoQueueMsg<TransportApiRequestMsg>, TbProtoQueueMsg<TransportApiResponseMsg>> templateBuilder = DefaultTbQueueRequestTemplate.builder();

        templateBuilder.queueAdmin(new TbQueueAdmin() {
            @Override
            public void createTopicIfNotExists(String topic) {}

            @Override
            public void destroy() {}
        });

        templateBuilder.requestTemplate(producerTemplate);
        templateBuilder.responseTemplate(consumerTemplate);
        templateBuilder.maxPendingRequests(transportApiSettings.getMaxPendingRequests());
        templateBuilder.maxRequestTimeout(transportApiSettings.getMaxRequestsTimeout());
        templateBuilder.pollInterval(transportApiSettings.getResponsePollInterval());
        return templateBuilder.build();
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToRuleEngineMsg>> createRuleEngineMsgProducer() {
        return new InMemoryTbQueueProducer<>(transportApiSettings.getRequestsTopic());
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToCoreMsg>> createTbCoreMsgProducer() {
        return new InMemoryTbQueueProducer<>(coreSettings.getTopic());
    }

    @Override
    public TbQueueConsumer<TbProtoQueueMsg<ToTransportMsg>> createTransportNotificationsConsumer() {
        return new InMemoryTbQueueConsumer<>(transportNotificationSettings.getNotificationsTopic() + "." + serviceInfoProvider.getServiceId());
    }
    @Override
    public TbQueueConsumer<CmdQueueMsg> createCmdTransportNotificationsConsumer() {
        return new InMemoryTbQueueConsumer<>(transportNotificationSettings.getNotificationsTopic() + "." + serviceInfoProvider.getServiceId());
    }
    @Override
    public TbQueueProducer<TbProtoQueueMsg<TransportProtos.ToUsageStatsServiceMsg>> createToUsageStatsServiceMsgProducer() {
        return new InMemoryTbQueueProducer<>(coreSettings.getUsageStatsTopic());
    }

}
