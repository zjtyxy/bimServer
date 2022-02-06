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

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.*;
import com.ciat.bim.rule.RuleEngineException;
import com.ciat.bim.rule.RuleNodeInfo;
import com.ciat.bim.server.actors.ActorSystemContext;
import com.ciat.bim.server.apiusage.TbApiUsageStateService;
import com.ciat.bim.server.common.msg.queue.QueueToRuleEngineMsg;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.common.msg.rpc.FromDeviceRpcResponse;
import com.ciat.bim.server.common.msg.rpc.RpcError;
import com.ciat.bim.server.common.stats.StatsFactory;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.profile.TbDeviceProfileCache;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.queue.processing.*;
import com.ciat.bim.server.queue.provider.TbRuleEngineQueueFactory;
import com.ciat.bim.server.queue.setting.TbQueueRuleEngineSettings;
import com.ciat.bim.server.queue.setting.TbRuleEngineQueueConfiguration;
import com.ciat.bim.server.queue.util.TbRuleEngineComponent;
import com.ciat.bim.server.rpc.TbRuleEngineDeviceRpcService;
import com.ciat.bim.server.stats.RuleEngineStatisticsService;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.ciat.bim.tenant.TbTenantProfileCache;
import com.google.protobuf.ProtocolStringList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;

@Service
@TbRuleEngineComponent
@Slf4j
public class DefaultTbRuleEngineConsumerService extends AbstractConsumerService<ToRuleEngineNotificationMsg> implements TbRuleEngineConsumerService {

    public static final String SUCCESSFUL_STATUS = "successful";
    public static final String FAILED_STATUS = "failed";
    public static final String THREAD_TOPIC_SEPARATOR = " | ";
    @Value("${queue.rule-engine.poll-interval}")
    private long pollDuration;
    @Value("${queue.rule-engine.pack-processing-timeout}")
    private long packProcessingTimeout;
    @Value("${queue.rule-engine.stats.enabled:true}")
    private boolean statsEnabled;
    @Value("${queue.rule-engine.prometheus-stats.enabled:false}")
    boolean prometheusStatsEnabled;

    private final StatsFactory statsFactory;
    private final TbRuleEngineSubmitStrategyFactory submitStrategyFactory;
    private final TbRuleEngineProcessingStrategyFactory processingStrategyFactory;
    private final TbRuleEngineQueueFactory tbRuleEngineQueueFactory;
    private final TbQueueRuleEngineSettings ruleEngineSettings;
    private final RuleEngineStatisticsService statisticsService;
    private final TbRuleEngineDeviceRpcService tbDeviceRpcService;
    private final ConcurrentMap<String, TbQueueConsumer<TbProtoQueueMsg<ToRuleEngineMsg>>> consumers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TbRuleEngineQueueConfiguration> consumerConfigurations = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TbRuleEngineConsumerStats> consumerStats = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, TbTopicWithConsumerPerPartition> topicsConsumerPerPartition = new ConcurrentHashMap<>();
    final ExecutorService submitExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("tb-rule-engine-consumer-submit"));
    final ScheduledExecutorService repartitionExecutor = Executors.newScheduledThreadPool(1, ThingsBoardThreadFactory.forName("tb-rule-engine-consumer-repartition"));

    public DefaultTbRuleEngineConsumerService(TbRuleEngineProcessingStrategyFactory processingStrategyFactory,
                                              TbRuleEngineSubmitStrategyFactory submitStrategyFactory,
                                              TbQueueRuleEngineSettings ruleEngineSettings,
                                              TbRuleEngineQueueFactory tbRuleEngineQueueFactory,
                                              RuleEngineStatisticsService statisticsService,
                                              ActorSystemContext actorContext,
                                              DataDecodingEncodingService encodingService,
                                              TbRuleEngineDeviceRpcService tbDeviceRpcService,
                                              StatsFactory statsFactory,
                                              TbDeviceProfileCache deviceProfileCache,
                                              TbTenantProfileCache tenantProfileCache,
                                              TbApiUsageStateService apiUsageStateService) {
        super(actorContext, encodingService, tenantProfileCache, deviceProfileCache, apiUsageStateService, tbRuleEngineQueueFactory.createToRuleEngineNotificationsMsgConsumer());
        this.statisticsService = statisticsService;
        this.ruleEngineSettings = ruleEngineSettings;
        this.tbRuleEngineQueueFactory = tbRuleEngineQueueFactory;
        this.submitStrategyFactory = submitStrategyFactory;
        this.processingStrategyFactory = processingStrategyFactory;
        this.tbDeviceRpcService = tbDeviceRpcService;
        this.statsFactory = statsFactory;
    }

    @PostConstruct
    public void init() {
        super.init("tb-rule-engine-consumer", "tb-rule-engine-notifications-consumer");
        for (TbRuleEngineQueueConfiguration configuration : ruleEngineSettings.getQueues()) {
            consumerConfigurations.putIfAbsent(configuration.getName(), configuration);
            consumerStats.put(configuration.getName(), new TbRuleEngineConsumerStats(configuration.getName(), statsFactory));
            if (!configuration.isConsumerPerPartition()) {
                consumers.computeIfAbsent(configuration.getName(), queueName -> tbRuleEngineQueueFactory.createToRuleEngineMsgConsumer(configuration));
            } else {
                topicsConsumerPerPartition.computeIfAbsent(configuration.getName(), TbTopicWithConsumerPerPartition::new);
            }
        }
    }

    @PreDestroy
    public void stop() {
        super.destroy();
        submitExecutor.shutdownNow();
        repartitionExecutor.shutdownNow();
        ruleEngineSettings.getQueues().forEach(config -> consumerConfigurations.put(config.getName(), config));
    }

    @Override
    protected void onTbApplicationEvent(PartitionChangeEvent event) {
        if (event.getServiceType().equals(getServiceType())) {
            ServiceQueue serviceQueue = event.getServiceQueueKey().getServiceQueue();
            log.info("[{}] Subscribing to partitions: {}", serviceQueue.getQueue(), event.getPartitions());
            if (!consumerConfigurations.get(serviceQueue.getQueue()).isConsumerPerPartition()) {
                consumers.get(serviceQueue.getQueue()).subscribe(event.getPartitions());
            } else {
                log.info("[{}] Subscribing consumer per partition: {}", serviceQueue.getQueue(), event.getPartitions());
                subscribeConsumerPerPartition(serviceQueue.getQueue(), event.getPartitions());
            }
        }
    }

    void subscribeConsumerPerPartition(String queue, Set<TopicPartitionInfo> partitions) {
        topicsConsumerPerPartition.get(queue).getSubscribeQueue().add(partitions);
        scheduleTopicRepartition(queue);
    }

    private void scheduleTopicRepartition(String queue) {
        repartitionExecutor.schedule(() -> repartitionTopicWithConsumerPerPartition(queue), 1, TimeUnit.SECONDS);
    }

    void repartitionTopicWithConsumerPerPartition(final String queueName) {
        if (stopped) {
            return;
        }
        TbTopicWithConsumerPerPartition tbTopicWithConsumerPerPartition = topicsConsumerPerPartition.get(queueName);
        Queue<Set<TopicPartitionInfo>> subscribeQueue = tbTopicWithConsumerPerPartition.getSubscribeQueue();
        if (subscribeQueue.isEmpty()) {
            return;
        }
        if (tbTopicWithConsumerPerPartition.getLock().tryLock()) {
            try {
                Set<TopicPartitionInfo> partitions = null;
                while (!subscribeQueue.isEmpty()) {
                    partitions = subscribeQueue.poll();
                }
                if (partitions == null) {
                    return;
                }

                Set<TopicPartitionInfo> addedPartitions = new HashSet<>(partitions);
                ConcurrentMap<TopicPartitionInfo, TbQueueConsumer<TbProtoQueueMsg<ToRuleEngineMsg>>> consumers = tbTopicWithConsumerPerPartition.getConsumers();
                addedPartitions.removeAll(consumers.keySet());
                log.info("calculated addedPartitions {}", addedPartitions);

                Set<TopicPartitionInfo> removedPartitions = new HashSet<>(consumers.keySet());
                removedPartitions.removeAll(partitions);
                log.info("calculated removedPartitions {}", removedPartitions);

                removedPartitions.forEach((tpi) -> {
                    removeConsumerForTopicByTpi(queueName, consumers, tpi);
                });

                addedPartitions.forEach((tpi) -> {
                    log.info("[{}] Adding consumer for topic: {}", queueName, tpi);
                    TbRuleEngineQueueConfiguration configuration = consumerConfigurations.get(queueName);
                    TbQueueConsumer<TbProtoQueueMsg<ToRuleEngineMsg>> consumer = tbRuleEngineQueueFactory.createToRuleEngineMsgConsumer(configuration);
                    consumers.put(tpi, consumer);
                    launchConsumer(consumer, consumerConfigurations.get(queueName), consumerStats.get(queueName), "" + queueName + "-" + tpi.getPartition().orElse(-999999));
                    consumer.subscribe(Collections.singleton(tpi));
                });

            } finally {
                tbTopicWithConsumerPerPartition.getLock().unlock();
            }
        } else {
            scheduleTopicRepartition(queueName); //reschedule later
        }

    }

    void removeConsumerForTopicByTpi(String queue, ConcurrentMap<TopicPartitionInfo, TbQueueConsumer<TbProtoQueueMsg<ToRuleEngineMsg>>> consumers, TopicPartitionInfo tpi) {
        log.info("[{}] Removing consumer for topic: {}", queue, tpi);
        consumers.get(tpi).unsubscribe();
        consumers.remove(tpi);
    }

    @Override
    protected void launchMainConsumers() {
        consumers.forEach((queue, consumer) -> launchConsumer(consumer, consumerConfigurations.get(queue), consumerStats.get(queue), queue));
    }

    @Override
    protected void stopMainConsumers() {
        consumers.values().forEach(TbQueueConsumer::unsubscribe);
        topicsConsumerPerPartition.values().forEach(tbTopicWithConsumerPerPartition -> tbTopicWithConsumerPerPartition.getConsumers().keySet()
                .forEach((tpi) -> removeConsumerForTopicByTpi(tbTopicWithConsumerPerPartition.getTopic(), tbTopicWithConsumerPerPartition.getConsumers(), tpi)));
    }

    void launchConsumer(TbQueueConsumer<TbProtoQueueMsg<ToRuleEngineMsg>> consumer, TbRuleEngineQueueConfiguration configuration, TbRuleEngineConsumerStats stats, String threadSuffix) {
        consumersExecutor.execute(() -> consumerLoop(consumer, configuration, stats, threadSuffix));
    }

    void consumerLoop(TbQueueConsumer<TbProtoQueueMsg<ToRuleEngineMsg>> consumer, TbRuleEngineQueueConfiguration configuration, TbRuleEngineConsumerStats stats, String threadSuffix) {
        updateCurrentThreadName(threadSuffix);
        while (!stopped && !consumer.isStopped()) {
            try {
                List<TbProtoQueueMsg<ToRuleEngineMsg>> msgs = consumer.poll(pollDuration);
                if (msgs.isEmpty()) {
                    continue;
                }
                final TbRuleEngineSubmitStrategy submitStrategy = getSubmitStrategy(configuration);
                final TbRuleEngineProcessingStrategy ackStrategy = getAckStrategy(configuration);
                submitStrategy.init(msgs);
                while (!stopped) {
                    TbMsgPackProcessingContext ctx = new TbMsgPackProcessingContext(configuration.getName(), submitStrategy);
                    submitStrategy.submitAttempt((id, msg) -> submitExecutor.submit(() -> submitMessage(configuration, stats, ctx, id, msg)));

                    final boolean timeout = !ctx.await(configuration.getPackProcessingTimeout(), TimeUnit.MILLISECONDS);

                    TbRuleEngineProcessingResult result = new TbRuleEngineProcessingResult(configuration.getName(), timeout, ctx);
                    if (timeout) {
                        printFirstOrAll(configuration, ctx, ctx.getPendingMap(), "Timeout");
                    }
                    if (!ctx.getFailedMap().isEmpty()) {
                        printFirstOrAll(configuration, ctx, ctx.getFailedMap(), "Failed");
                    }
                    ctx.printProfilerStats();

                    TbRuleEngineProcessingDecision decision = ackStrategy.analyze(result);
                    if (statsEnabled) {
                        stats.log(result, decision.isCommit());
                    }

                    ctx.cleanup();

                    if (decision.isCommit()) {
                        submitStrategy.stop();
                        break;
                    } else {
                        submitStrategy.update(decision.getReprocessMap());
                    }
                }
                consumer.commit();
            } catch (Exception e) {
                if (!stopped) {
                    log.warn("Failed to process messages from queue.", e);
                    try {
                        Thread.sleep(pollDuration);
                    } catch (InterruptedException e2) {
                        log.trace("Failed to wait until the server has capacity to handle new requests", e2);
                    }
                }
            }
        }
        log.info("TB Rule Engine Consumer stopped.");
    }

    void updateCurrentThreadName(String threadSuffix) {
        String name = Thread.currentThread().getName();
        int spliteratorIndex = name.indexOf(THREAD_TOPIC_SEPARATOR);
        if (spliteratorIndex > 0) {
            name = name.substring(0, spliteratorIndex);
        }
        name = name + THREAD_TOPIC_SEPARATOR + threadSuffix;
        Thread.currentThread().setName(name);
    }

    TbRuleEngineProcessingStrategy getAckStrategy(TbRuleEngineQueueConfiguration configuration) {
        return processingStrategyFactory.newInstance(configuration.getName(), configuration.getProcessingStrategy());
    }

    TbRuleEngineSubmitStrategy getSubmitStrategy(TbRuleEngineQueueConfiguration configuration) {
        return submitStrategyFactory.newInstance(configuration.getName(), configuration.getSubmitStrategy());
    }

    void submitMessage(TbRuleEngineQueueConfiguration configuration, TbRuleEngineConsumerStats stats, TbMsgPackProcessingContext ctx, UUID id, TbProtoQueueMsg<ToRuleEngineMsg> msg) {
        log.trace("[{}] Creating callback for topic {} message: {}", id, configuration.getName(), msg.getValue());
        ToRuleEngineMsg toRuleEngineMsg = msg.getValue();
        TenantId tenantId = new TenantId(toRuleEngineMsg.getTenantIdMSB()+"");
        TbMsgCallback callback = prometheusStatsEnabled ?
                new TbMsgPackCallback(id, tenantId, ctx, stats.getTimer(tenantId, SUCCESSFUL_STATUS), stats.getTimer(tenantId, FAILED_STATUS)) :
                new TbMsgPackCallback(id, tenantId, ctx);
        try {
            if (toRuleEngineMsg.getTbMsg() != null && !toRuleEngineMsg.getTbMsg().isEmpty()) {
                forwardToRuleEngineActor(configuration.getName(), tenantId, toRuleEngineMsg, callback);
            } else {
                callback.onSuccess();
            }
        } catch (Exception e) {
            callback.onFailure(new RuleEngineException(e.getMessage()));
        }
    }

    private void printFirstOrAll(TbRuleEngineQueueConfiguration configuration, TbMsgPackProcessingContext ctx, Map<UUID, TbProtoQueueMsg<ToRuleEngineMsg>> map, String prefix) {
        boolean printAll = log.isTraceEnabled();
        log.info("{} to process [{}] messages", prefix, map.size());
        for (Map.Entry<UUID, TbProtoQueueMsg<ToRuleEngineMsg>> pending : map.entrySet()) {
            ToRuleEngineMsg tmp = pending.getValue().getValue();
            TbMsg tmpMsg = TbMsg.fromBytes(configuration.getName(), tmp.getTbMsg().toByteArray(), TbMsgCallback.EMPTY);
            RuleNodeInfo ruleNodeInfo = ctx.getLastVisitedRuleNode(pending.getKey());
            if (printAll) {
                log.trace("[{}] {} to process message: {}, Last Rule Node: {}", new TenantId(tmp.getTenantIdMSB()+""), prefix, tmpMsg, ruleNodeInfo);
            } else {
                log.info("[{}] {} to process message: {}, Last Rule Node: {}", new TenantId(tmp.getTenantIdMSB()+""), prefix, tmpMsg, ruleNodeInfo);
                break;
            }
        }
    }

    @Override
    protected ServiceType getServiceType() {
        return ServiceType.TB_RULE_ENGINE;
    }

    @Override
    protected long getNotificationPollDuration() {
        return pollDuration;
    }

    @Override
    protected long getNotificationPackProcessingTimeout() {
        return packProcessingTimeout;
    }

    @Override
    protected void handleNotification(UUID id, TbProtoQueueMsg<ToRuleEngineNotificationMsg> msg, TbCallback callback) throws Exception {
        ToRuleEngineNotificationMsg nfMsg = msg.getValue();
        if (nfMsg.getComponentLifecycleMsg() != null && !nfMsg.getComponentLifecycleMsg().isEmpty()) {
            handleComponentLifecycleMsg(id, nfMsg.getComponentLifecycleMsg());
            callback.onSuccess();
        } else if (nfMsg.hasFromDeviceRpcResponse()) {
            TransportProtos.FromDeviceRPCResponseProto proto = nfMsg.getFromDeviceRpcResponse();
            RpcError error = proto.getError() > 0 ? RpcError.values()[proto.getError()] : null;
            FromDeviceRpcResponse response = new FromDeviceRpcResponse(proto.getRequestIdMSB()+""
                    , proto.getResponse(), error);
            tbDeviceRpcService.processRpcResponseFromDevice(response);
            callback.onSuccess();
        } else {
            log.trace("Received notification with missing handler");
            callback.onSuccess();
        }
    }

    private void forwardToRuleEngineActor(String queueName, TenantId tenantId, ToRuleEngineMsg toRuleEngineMsg, TbMsgCallback callback) {
        TbMsg tbMsg = TbMsg.fromBytes(queueName, toRuleEngineMsg.getTbMsg().toByteArray(), callback);
        QueueToRuleEngineMsg msg;
        ProtocolStringList relationTypesList = toRuleEngineMsg.getRelationTypesList();
        Set<String> relationTypes = null;
        if (relationTypesList != null) {
            if (relationTypesList.size() == 1) {
                relationTypes = Collections.singleton(relationTypesList.get(0));
            } else {
                relationTypes = new HashSet<>(relationTypesList);
            }
        }
        msg = new QueueToRuleEngineMsg(tenantId.getId(), tbMsg, relationTypes, toRuleEngineMsg.getFailureMessage());
        actorContext.tell(msg);
    }

    @Scheduled(fixedDelayString = "${queue.rule-engine.stats.print-interval-ms}")
    public void printStats() {
        if (statsEnabled) {
            long ts = System.currentTimeMillis();
            consumerStats.forEach((queue, stats) -> {
                stats.printStats();
                statisticsService.reportQueueStats(ts, stats);
                stats.reset();
            });
        }
    }

}
