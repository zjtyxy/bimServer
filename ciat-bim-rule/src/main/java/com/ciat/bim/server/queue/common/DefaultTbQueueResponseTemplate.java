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
package com.ciat.bim.server.queue.common;

import com.ciat.bim.msg.TopicPartitionInfo;
import com.ciat.bim.server.common.stats.MessagesStats;
import com.ciat.bim.server.queue.TbQueueConsumer;
import com.ciat.bim.server.queue.TbQueueHandler;
import com.ciat.bim.server.queue.TbQueueMsg;
import com.ciat.bim.server.queue.TbQueueResponseTemplate;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.ciat.bim.transport.AsyncCallbackTemplate;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DefaultTbQueueResponseTemplate<Request extends TbQueueMsg, Response extends TbQueueMsg> extends AbstractTbQueueTemplate
        implements TbQueueResponseTemplate<Request, Response> {

    private final TbQueueConsumer<Request> requestTemplate;
    private final TbQueueProducer<Response> responseTemplate;
    private final ConcurrentMap<UUID, String> pendingRequests;
    private final ExecutorService loopExecutor;
    private final ScheduledExecutorService timeoutExecutor;
    private final ExecutorService callbackExecutor;
    private final MessagesStats stats;
    private final int maxPendingRequests;
    private final long requestTimeout;

    private final long pollInterval;
    private volatile boolean stopped = false;
    private final AtomicInteger pendingRequestCount = new AtomicInteger();

    @Builder
    public DefaultTbQueueResponseTemplate(TbQueueConsumer<Request> requestTemplate,
                                          TbQueueProducer<Response> responseTemplate,
                                          TbQueueHandler<Request, Response> handler,
                                          long pollInterval,
                                          long requestTimeout,
                                          int maxPendingRequests,
                                          ExecutorService executor,
                                          MessagesStats stats) {
        this.requestTemplate = requestTemplate;
        this.responseTemplate = responseTemplate;
        this.pendingRequests = new ConcurrentHashMap<>();
        this.maxPendingRequests = maxPendingRequests;
        this.pollInterval = pollInterval;
        this.requestTimeout = requestTimeout;
        this.callbackExecutor = executor;
        this.stats = stats;
        this.timeoutExecutor = Executors.newSingleThreadScheduledExecutor(ThingsBoardThreadFactory.forName("tb-queue-response-template-timeout-" + requestTemplate.getTopic()));
        this.loopExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("tb-queue-response-template-loop-" + requestTemplate.getTopic()));
    }

    @Override
    public void init(TbQueueHandler<Request, Response> handler) {
        this.responseTemplate.init();
        requestTemplate.subscribe();
        loopExecutor.submit(() -> {
            while (!stopped) {
                try {
                    while (pendingRequestCount.get() >= maxPendingRequests) {
                        try {
                            Thread.sleep(pollInterval);
                        } catch (InterruptedException e) {
                            log.trace("Failed to wait until the server has capacity to handle new requests", e);
                        }
                    }
                    List<Request> requests = requestTemplate.poll(pollInterval);

                    if (requests.isEmpty()) {
                        continue;
                    }

                    requests.forEach(request -> {
                        long currentTime = System.currentTimeMillis();
                        long requestTime = bytesToLong(request.getHeaders().get(REQUEST_TIME));
                        if (requestTime + requestTimeout >= currentTime) {
                            byte[] requestIdHeader = request.getHeaders().get(REQUEST_ID_HEADER);
                            if (requestIdHeader == null) {
                                log.error("[{}] Missing requestId in header", request);
                                return;
                            }
                            byte[] responseTopicHeader = request.getHeaders().get(RESPONSE_TOPIC_HEADER);
                            if (responseTopicHeader == null) {
                                log.error("[{}] Missing response topic in header", request);
                                return;
                            }
                            UUID requestId = bytesToUuid(requestIdHeader);
                            String responseTopic = bytesToString(responseTopicHeader);
                            try {
                                pendingRequestCount.getAndIncrement();
                                stats.incrementTotal();
                                AsyncCallbackTemplate.withCallbackAndTimeout(handler.handle(request),
                                        response -> {
                                            pendingRequestCount.decrementAndGet();
                                            response.getHeaders().put(REQUEST_ID_HEADER, uuidToBytes(requestId));
                                            responseTemplate.send(TopicPartitionInfo.builder().topic(responseTopic).build(), response, null);
                                            stats.incrementSuccessful();
                                        },
                                        e -> {
                                            pendingRequestCount.decrementAndGet();
                                            if (e.getCause() != null && e.getCause() instanceof TimeoutException) {
                                                log.warn("[{}] Timeout to process the request: {}", requestId, request, e);
                                            } else {
                                                log.trace("[{}] Failed to process the request: {}", requestId, request, e);
                                            }
                                            stats.incrementFailed();
                                        },
                                        requestTimeout,
                                        timeoutExecutor,
                                        callbackExecutor);
                            } catch (Throwable e) {
                                pendingRequestCount.decrementAndGet();
                                log.warn("[{}] Failed to process the request: {}", requestId, request, e);
                                stats.incrementFailed();
                            }
                        }
                    });
                    requestTemplate.commit();
                } catch (Throwable e) {
                    log.warn("Failed to obtain messages from queue.", e);
                    try {
                        Thread.sleep(pollInterval);
                    } catch (InterruptedException e2) {
                        log.trace("Failed to wait until the server has capacity to handle new requests", e2);
                    }
                }
            }
        });
    }

    public void stop() {
        stopped = true;
        if (requestTemplate != null) {
            requestTemplate.unsubscribe();
        }
        if (responseTemplate != null) {
            responseTemplate.stop();
        }
        if (timeoutExecutor != null) {
            timeoutExecutor.shutdownNow();
        }
        if (loopExecutor != null) {
            loopExecutor.shutdownNow();
        }
    }

}
