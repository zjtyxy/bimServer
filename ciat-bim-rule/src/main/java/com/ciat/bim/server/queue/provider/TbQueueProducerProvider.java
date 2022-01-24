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
import com.ciat.bim.server.transport.TransportProtos.ToUsageStatsServiceMsg;
import com.ciat.bim.server.transport.TransportProtos.ToCoreNotificationMsg;
import com.ciat.bim.server.transport.TransportProtos.ToCoreMsg;
import com.ciat.bim.server.transport.TransportProtos.ToCoreNotificationMsg;
import com.ciat.bim.server.transport.TransportProtos.ToRuleEngineNotificationMsg;
import com.ciat.bim.server.transport.TransportProtos.ToRuleEngineMsg;
import com.ciat.bim.server.transport.TransportProtos.ToTransportMsg;

/**
 * Responsible for providing various Producers to other services.
 */
public interface TbQueueProducerProvider {

    /**
     * Used to push messages to instances of TB Transport Service
     *
     * @return
     */
    TbQueueProducer<TbProtoQueueMsg<ToTransportMsg>> getTransportNotificationsMsgProducer();

    /**
     * Used to push messages to instances of TB RuleEngine Service
     *
     * @return
     */
    TbQueueProducer<TbProtoQueueMsg<ToRuleEngineMsg>> getRuleEngineMsgProducer();

    /**
     * Used to push notifications to instances of TB RuleEngine Service
     *
     * @return
     */
    TbQueueProducer<TbProtoQueueMsg<ToRuleEngineNotificationMsg>> getRuleEngineNotificationsMsgProducer();

    /**
     * Used to push messages to other instances of TB Core Service
     *
     * @return
     */
    TbQueueProducer<TbProtoQueueMsg<ToCoreMsg>> getTbCoreMsgProducer();

    /**
     * Used to push messages to other instances of TB Core Service
     *
     * @return
     */
    TbQueueProducer<TbProtoQueueMsg<ToCoreNotificationMsg>> getTbCoreNotificationsMsgProducer();

    /**
     * Used to push messages to other instances of TB Core Service
     *
     * @return
     */
    TbQueueProducer<TbProtoQueueMsg<ToUsageStatsServiceMsg>> getTbUsageStatsMsgProducer();
}
