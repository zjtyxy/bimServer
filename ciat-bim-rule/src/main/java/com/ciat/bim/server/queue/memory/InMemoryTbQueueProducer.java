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
package com.ciat.bim.server.queue.memory;

import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.queue.TbQueueMsg;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import lombok.Data;


@Data
public class InMemoryTbQueueProducer<T extends TbQueueMsg> implements TbQueueProducer<T> {

    private final InMemoryStorage storage = InMemoryStorage.getInstance();

    private final String defaultTopic;

    public InMemoryTbQueueProducer(String defaultTopic) {
        this.defaultTopic = defaultTopic;
    }

    @Override
    public void init() {

    }

    @Override
    public void send(TopicPartitionInfo tpi, T msg, TbQueueCallback callback) {
        boolean result = storage.put(tpi.getFullTopicName(), msg);
        if (result) {
            if (callback != null) {
                callback.onSuccess(null);
            }
        } else {
            if (callback != null) {
                callback.onFailure(new RuntimeException("Failure add msg to InMemoryQueue"));
            }
        }
    }

    @Override
    public void stop() {

    }
}
