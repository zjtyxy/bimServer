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

import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.transport.TransportProtos.*;
import org.springframework.stereotype.Service;



import javax.annotation.PostConstruct;

@Service
@TbCoreComponent
public class TbCoreQueueProducerProvider implements TbQueueProducerProvider {

    private final TbCoreQueueFactory tbQueueProvider;
    private TbQueueProducer<TbProtoQueueMsg<ToTransportMsg>> toTransport;
    private TbQueueProducer<TbProtoQueueMsg<ToRuleEngineMsg>> toRuleEngine;
    private TbQueueProducer<TbProtoQueueMsg<ToCoreMsg>> toTbCore;
    private TbQueueProducer<TbProtoQueueMsg<ToRuleEngineNotificationMsg>> toRuleEngineNotifications;
    private TbQueueProducer<TbProtoQueueMsg<ToCoreNotificationMsg>> toTbCoreNotifications;
    private TbQueueProducer<TbProtoQueueMsg<ToUsageStatsServiceMsg>> toUsageStats;

    public TbCoreQueueProducerProvider(TbCoreQueueFactory tbQueueProvider) {
        this.tbQueueProvider = tbQueueProvider;
    }

    @PostConstruct
    public void init() {
        this.toTbCore = tbQueueProvider.createTbCoreMsgProducer();
        this.toTransport = tbQueueProvider.createTransportNotificationsMsgProducer();
        this.toRuleEngine = tbQueueProvider.createRuleEngineMsgProducer();
        this.toRuleEngineNotifications = tbQueueProvider.createRuleEngineNotificationsMsgProducer();
        this.toTbCoreNotifications = tbQueueProvider.createTbCoreNotificationsMsgProducer();
        this.toUsageStats = tbQueueProvider.createToUsageStatsServiceMsgProducer();
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToTransportMsg>> getTransportNotificationsMsgProducer() {
        return toTransport;
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToRuleEngineMsg>> getRuleEngineMsgProducer() {
        return toRuleEngine;
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToRuleEngineNotificationMsg>> getRuleEngineNotificationsMsgProducer() {
        return toRuleEngineNotifications;
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToCoreMsg>> getTbCoreMsgProducer() {
        return toTbCore;
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToCoreNotificationMsg>> getTbCoreNotificationsMsgProducer() {
        return toTbCoreNotifications;
    }

    @Override
    public TbQueueProducer<TbProtoQueueMsg<ToUsageStatsServiceMsg>> getTbUsageStatsMsgProducer() {
        return toUsageStats;
    }
}
