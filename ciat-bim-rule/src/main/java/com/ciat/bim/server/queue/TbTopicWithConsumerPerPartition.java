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
package com.ciat.bim.server.queue;

import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.transport.TransportProtos;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
@Data
public class TbTopicWithConsumerPerPartition {
    private final String topic;
    @Getter
    private final ReentrantLock lock = new ReentrantLock(); //NonfairSync
    private volatile Set<TopicPartitionInfo> partitions = Collections.emptySet();
    private final ConcurrentMap<TopicPartitionInfo, TbQueueConsumer<TbProtoQueueMsg<TransportProtos.ToRuleEngineMsg>>> consumers = new ConcurrentHashMap<>();
    private final Queue<Set<TopicPartitionInfo>> subscribeQueue = new ConcurrentLinkedQueue<>();
}
