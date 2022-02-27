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
package com.ciat.bim.transport.service;

import com.ciat.bim.data.device.DeviceTransportType;
import com.ciat.bim.data.id.*;
import com.ciat.bim.msg.*;
import com.ciat.bim.server.common.data.ApiUsageRecordKey;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.data.ResourceType;
import com.ciat.bim.server.common.msg.SessionMsgType;
import com.ciat.bim.server.common.msg.TbRateLimitsException;
import com.ciat.bim.server.common.stats.MessagesStats;
import com.ciat.bim.server.common.stats.StatsFactory;
import com.ciat.bim.server.common.stats.StatsType;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.queue.TbQueueConsumer;
import com.ciat.bim.server.queue.TbQueueRequestTemplate;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.PartitionService;
import com.ciat.bim.server.queue.discovery.TbServiceInfoProvider;
import com.ciat.bim.server.queue.provider.TbQueueProducerProvider;
import com.ciat.bim.server.queue.provider.TbTransportQueueFactory;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.queue.TbQueueMsgMetadata;
import com.ciat.bim.server.queue.queue.TbQueueProducer;
import com.ciat.bim.server.queue.scheduler.SchedulerComponent;
import com.ciat.bim.server.queue.usagestats.TbApiUsageClient;
import com.ciat.bim.server.rpc.RpcStatus;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.server.utils.TbTransportComponent;
import com.ciat.bim.server.utils.ThingsBoardExecutors;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.ciat.bim.transport.*;
import com.ciat.bim.transport.auth.GetOrCreateDeviceFromGatewayResponse;
import com.ciat.bim.transport.auth.TransportDeviceInfo;
import com.ciat.bim.transport.auth.ValidateDeviceCredentialsResponse;
import com.ciat.bim.transport.limit.TransportRateLimitService;
import com.ciat.bim.transport.session.SessionMetaData;
import com.ciat.bim.transport.session.SessionMsgListener;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.ByteString;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.tenant.entity.Tenant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by ashvayka on 17.10.18.
 */
@Slf4j
@Service
@TbTransportComponent
public class DefaultTransportService implements TransportService {

    public static final String OVERWRITE_ACTIVITY_TIME = "overwriteActivityTime";

    private final AtomicInteger atomicTs = new AtomicInteger(0);

    @Value("${transport.log.enabled:true}")
    private boolean logEnabled;
    @Value("${transport.log.max_length:1024}")
    private int logMaxLength;
    @Value("${transport.sessions.inactivity_timeout}")
    private long sessionInactivityTimeout;
    @Value("${transport.sessions.report_timeout}")
    private long sessionReportTimeout;
    @Value("${transport.client_side_rpc.timeout:60000}")
    private long clientSideRpcTimeout;
    @Value("${queue.transport.poll_interval}")
    private int notificationsPollDuration;
    @Value("${transport.stats.enabled:false}")
    private boolean statsEnabled;

    private final Map<String, Number> statsMap = new LinkedHashMap<>();

    private final Gson gson = new Gson();
    private final TbTransportQueueFactory queueProvider;
    private final TbQueueProducerProvider producerProvider;
    private final PartitionService partitionService;
    private final TbServiceInfoProvider serviceInfoProvider;
    private final StatsFactory statsFactory;
    private final TransportDeviceProfileCache deviceProfileCache;
    private final TransportTenantProfileCache tenantProfileCache;
    private final TbApiUsageClient apiUsageClient;
    private final TransportRateLimitService rateLimitService;
    private final DataDecodingEncodingService dataDecodingEncodingService;
    private final SchedulerComponent scheduler;
    private final ApplicationEventPublisher eventPublisher;
    private final TransportResourceCache transportResourceCache;

    protected TbQueueRequestTemplate<TbProtoQueueMsg<TransportApiRequestMsg>, TbProtoQueueMsg<TransportApiResponseMsg>> transportApiRequestTemplate;
    protected TbQueueProducer<TbProtoQueueMsg<ToRuleEngineMsg>> ruleEngineMsgProducer;
    protected TbQueueProducer<TbProtoQueueMsg<ToCoreMsg>> tbCoreMsgProducer;
    protected TbQueueConsumer<TbProtoQueueMsg<ToTransportMsg>> transportNotificationsConsumer;

    protected MessagesStats ruleEngineProducerStats;
    protected MessagesStats tbCoreProducerStats;
    protected MessagesStats transportApiStats;

    protected ExecutorService transportCallbackExecutor;
    private ExecutorService mainConsumerExecutor;

    private final ConcurrentMap<UUID, SessionMetaData> sessions = new ConcurrentHashMap<>();
    private final ConcurrentMap<UUID, SessionActivityData> sessionsActivity = new ConcurrentHashMap<>();
    private final Map<String, RpcRequestMetadata> toServerRpcPendingMap = new ConcurrentHashMap<>();

    private volatile boolean stopped = false;

    public DefaultTransportService(TbServiceInfoProvider serviceInfoProvider,
                                   TbTransportQueueFactory queueProvider,
                                   TbQueueProducerProvider producerProvider,
                                   PartitionService partitionService,
                                   StatsFactory statsFactory,
                                   TransportDeviceProfileCache deviceProfileCache,
                                   TransportTenantProfileCache tenantProfileCache,
                                   TbApiUsageClient apiUsageClient, TransportRateLimitService rateLimitService,
                                   DataDecodingEncodingService dataDecodingEncodingService, SchedulerComponent scheduler, TransportResourceCache transportResourceCache,
                                   ApplicationEventPublisher eventPublisher) {
        this.serviceInfoProvider = serviceInfoProvider;
        this.queueProvider = queueProvider;
        this.producerProvider = producerProvider;
        this.partitionService = partitionService;
        this.statsFactory = statsFactory;
        this.deviceProfileCache = deviceProfileCache;
        this.tenantProfileCache = tenantProfileCache;
        this.apiUsageClient = apiUsageClient;
        this.rateLimitService = rateLimitService;
        this.dataDecodingEncodingService = dataDecodingEncodingService;
        this.scheduler = scheduler;
        this.transportResourceCache = transportResourceCache;
        this.eventPublisher = eventPublisher;
    }

    @PostConstruct
    public void init() {
        this.ruleEngineProducerStats = statsFactory.createMessagesStats(StatsType.RULE_ENGINE.getName() + ".producer");
        this.tbCoreProducerStats = statsFactory.createMessagesStats(StatsType.CORE.getName() + ".producer");
        this.transportApiStats = statsFactory.createMessagesStats(StatsType.TRANSPORT.getName() + ".producer");
        this.transportCallbackExecutor = ThingsBoardExecutors.newWorkStealingPool(20, getClass());
        this.scheduler.scheduleAtFixedRate(this::checkInactivityAndReportActivity, new Random().nextInt((int) sessionReportTimeout), sessionReportTimeout, TimeUnit.MILLISECONDS);
        transportApiRequestTemplate = queueProvider.createTransportApiRequestTemplate();
        transportApiRequestTemplate.setMessagesStats(transportApiStats);
        ruleEngineMsgProducer = producerProvider.getRuleEngineMsgProducer();
        tbCoreMsgProducer = producerProvider.getTbCoreMsgProducer();
        transportNotificationsConsumer = queueProvider.createTransportNotificationsConsumer();
        TopicPartitionInfo tpi = partitionService.getNotificationsTopic(ServiceType.TB_TRANSPORT, serviceInfoProvider.getServiceId());
        transportNotificationsConsumer.subscribe(Collections.singleton(tpi));
        transportApiRequestTemplate.init();
        mainConsumerExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("transport-consumer"));
        mainConsumerExecutor.execute(() -> {
            while (!stopped) {
                try {
                    List<TbProtoQueueMsg<ToTransportMsg>> records = transportNotificationsConsumer.poll(notificationsPollDuration);
                    if (records.size() == 0) {
                        continue;
                    }
                    records.forEach(record -> {
                        try {
                            processToTransportMsg(record.getValue());
                        } catch (Throwable e) {
                            log.warn("Failed to process the notification.", e);
                        }
                    });
                    transportNotificationsConsumer.commit();
                } catch (Exception e) {
                    if (!stopped) {
                        log.warn("Failed to obtain messages from queue.", e);
                        try {
                            Thread.sleep(notificationsPollDuration);
                        } catch (InterruptedException e2) {
                            log.trace("Failed to wait until the server has capacity to handle new requests", e2);
                        }
                    }
                }
            }
        });
    }

    @PreDestroy
    public void destroy() {
        stopped = true;

        if (transportNotificationsConsumer != null) {
            transportNotificationsConsumer.unsubscribe();
        }
        if (transportCallbackExecutor != null) {
            transportCallbackExecutor.shutdownNow();
        }
        if (mainConsumerExecutor != null) {
            mainConsumerExecutor.shutdownNow();
        }
        if (transportApiRequestTemplate != null) {
            transportApiRequestTemplate.stop();
        }
    }

    @Override
    public SessionMetaData registerAsyncSession(TransportProtos.SessionInfoProto sessionInfo, SessionMsgListener listener) {
        SessionMetaData newValue = new SessionMetaData(sessionInfo, TransportProtos.SessionType.ASYNC, listener);
        SessionMetaData oldValue = sessions.putIfAbsent(toSessionId(sessionInfo), newValue);
        return oldValue != null ? oldValue : newValue;
    }

    @Override
    public TransportProtos.GetEntityProfileResponseMsg getEntityProfile(TransportProtos.GetEntityProfileRequestMsg msg) {
        TbProtoQueueMsg<TransportProtos.TransportApiRequestMsg> protoMsg =
                new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportProtos.TransportApiRequestMsg.newBuilder().setEntityProfileRequestMsg(msg).build());
        try {
            TbProtoQueueMsg<TransportApiResponseMsg> response = transportApiRequestTemplate.send(protoMsg).get();
            return response.getValue().getEntityProfileResponseMsg();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TransportProtos.GetResourceResponseMsg getResource(TransportProtos.GetResourceRequestMsg msg) {
        TbProtoQueueMsg<TransportProtos.TransportApiRequestMsg> protoMsg =
                new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportProtos.TransportApiRequestMsg.newBuilder().setResourceRequestMsg(msg).build());
        try {
            TbProtoQueueMsg<TransportApiResponseMsg> response = transportApiRequestTemplate.send(protoMsg).get();
            return response.getValue().getResourceResponseMsg();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TransportProtos.GetSnmpDevicesResponseMsg getSnmpDevicesIds(TransportProtos.GetSnmpDevicesRequestMsg requestMsg) {
        TbProtoQueueMsg<TransportProtos.TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(
                UUID.randomUUID().toString(), TransportProtos.TransportApiRequestMsg.newBuilder()
                .setSnmpDevicesRequestMsg(requestMsg)
                .build()
        );

        try {
            TbProtoQueueMsg<TransportApiResponseMsg> response = transportApiRequestTemplate.send(protoMsg).get();
            return response.getValue().getSnmpDevicesResponseMsg();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TransportProtos.GetDeviceResponseMsg getDevice(TransportProtos.GetDeviceRequestMsg requestMsg) {
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(
                UUID.randomUUID().toString(), TransportProtos.TransportApiRequestMsg.newBuilder()
                .setDeviceRequestMsg(requestMsg)
                .build()
        );

        try {
            TransportApiResponseMsg response = transportApiRequestTemplate.send(protoMsg).get().getValue();
            if (response.hasDeviceResponseMsg()) {
                return response.getDeviceResponseMsg();
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public TransportProtos.GetDeviceCredentialsResponseMsg getDeviceCredentials(TransportProtos.GetDeviceCredentialsRequestMsg requestMsg) {
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(
                UUID.randomUUID().toString(), TransportProtos.TransportApiRequestMsg.newBuilder()
                .setDeviceCredentialsRequestMsg(requestMsg)
                .build()
        );

        try {
            TbProtoQueueMsg<TransportApiResponseMsg> response = transportApiRequestTemplate.send(protoMsg).get();
            return response.getValue().getDeviceCredentialsResponseMsg();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //@Override
    public void process(DeviceTransportType transportType, TransportProtos.ValidateDeviceTokenRequestMsg msg,
                        TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        log.trace("Processing msg: {}", msg);
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(),
                TransportApiRequestMsg.newBuilder().setValidateTokenRequestMsg(msg).build());
        doProcess(transportType, protoMsg, callback);
    }

    @Override
    public void process(DeviceTransportType transportType, TransportProtos.ValidateBasicMqttCredRequestMsg msg,
                        TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        log.trace("Processing msg: {}", msg);
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(),
                TransportApiRequestMsg.newBuilder().setValidateBasicMqttCredRequestMsg(msg).build());
        doProcess(transportType, protoMsg, callback);
    }

    @Override
    public void process(TransportProtos.ValidateDeviceLwM2MCredentialsRequestMsg requestMsg, TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        log.trace("Processing msg: {}", requestMsg);
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportApiRequestMsg.newBuilder().setValidateDeviceLwM2MCredentialsRequestMsg(requestMsg).build());
        ListenableFuture<ValidateDeviceCredentialsResponse> response = Futures.transform(transportApiRequestTemplate.send(protoMsg), tmp -> {
            TransportProtos.ValidateDeviceCredentialsResponseMsg msg = tmp.getValue().getValidateCredResponseMsg();
            ValidateDeviceCredentialsResponse.ValidateDeviceCredentialsResponseBuilder result = ValidateDeviceCredentialsResponse.builder();
            if (msg.hasDeviceInfo()) {
                result.credentials(msg.getCredentialsBody());
                TransportDeviceInfo tdi = getTransportDeviceInfo(msg.getDeviceInfo());
                result.deviceInfo(tdi);
                ByteString profileBody = msg.getProfileBody();
                if (!profileBody.isEmpty()) {
                    DeviceProfile profile = deviceProfileCache.getOrCreate(tdi.getDeviceProfileId(), profileBody);
                    result.deviceProfile(profile);
                }
            }
            return result.build();
        }, MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(response, callback::onSuccess, callback::onError, transportCallbackExecutor);
    }

    @Override
    public void process(DeviceTransportType transportType, TransportProtos.ValidateDeviceX509CertRequestMsg msg, TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        log.trace("Processing msg: {}", msg);
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportApiRequestMsg.newBuilder().setValidateX509CertRequestMsg(msg).build());
        doProcess(transportType, protoMsg, callback);
    }

    private void doProcess(DeviceTransportType transportType, TbProtoQueueMsg<TransportApiRequestMsg> protoMsg,
                           TransportServiceCallback<ValidateDeviceCredentialsResponse> callback) {
        ListenableFuture<ValidateDeviceCredentialsResponse> response = Futures.transform(transportApiRequestTemplate.send(protoMsg), tmp -> {
            TransportProtos.ValidateDeviceCredentialsResponseMsg msg = tmp.getValue().getValidateCredResponseMsg();
            ValidateDeviceCredentialsResponse.ValidateDeviceCredentialsResponseBuilder result = ValidateDeviceCredentialsResponse.builder();
            if (msg.hasDeviceInfo()) {
                result.credentials(msg.getCredentialsBody());
                TransportDeviceInfo tdi = getTransportDeviceInfo(msg.getDeviceInfo());
                result.deviceInfo(tdi);
                ByteString profileBody = msg.getProfileBody();
                if (!profileBody.isEmpty()) {
                    DeviceProfile profile = deviceProfileCache.getOrCreate(tdi.getDeviceProfileId(), profileBody);
                    if (transportType != DeviceTransportType.DEFAULT
                            && profile != null && profile.getTransportType() != DeviceTransportType.DEFAULT && profile.getTransportType() != transportType) {
                        log.debug("[{}] Device profile [{}] has different transport type: {}, expected: {}", tdi.getDeviceId(), tdi.getDeviceProfileId(), profile.getTransportType(), transportType);
                        throw new IllegalStateException("Device profile has different transport type: " + profile.getTransportType() + ". Expected: " + transportType);
                    }
                    result.deviceProfile(profile);
                }
            }
            return result.build();
        }, MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(response, callback::onSuccess, callback::onError, transportCallbackExecutor);
    }

    @Override
    public void process(TransportProtos.GetOrCreateDeviceFromGatewayRequestMsg requestMsg, TransportServiceCallback<GetOrCreateDeviceFromGatewayResponse> callback) {
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportApiRequestMsg.newBuilder().setGetOrCreateDeviceRequestMsg(requestMsg).build());
        log.trace("Processing msg: {}", requestMsg);
        ListenableFuture<GetOrCreateDeviceFromGatewayResponse> response = Futures.transform(transportApiRequestTemplate.send(protoMsg), tmp -> {
            TransportProtos.GetOrCreateDeviceFromGatewayResponseMsg msg = tmp.getValue().getGetOrCreateDeviceResponseMsg();
            GetOrCreateDeviceFromGatewayResponse.GetOrCreateDeviceFromGatewayResponseBuilder result = GetOrCreateDeviceFromGatewayResponse.builder();
            if (msg.hasDeviceInfo()) {
                TransportDeviceInfo tdi = getTransportDeviceInfo(msg.getDeviceInfo());
                result.deviceInfo(tdi);
                ByteString profileBody = msg.getProfileBody();
                if (profileBody != null && !profileBody.isEmpty()) {
                    result.deviceProfile(deviceProfileCache.getOrCreate(tdi.getDeviceProfileId(), profileBody));
                }
            }
            return result.build();
        }, MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(response, callback::onSuccess, callback::onError, transportCallbackExecutor);
    }

    @Override
    public void process(TransportProtos.LwM2MRequestMsg msg, TransportServiceCallback<TransportProtos.LwM2MResponseMsg> callback) {
        log.trace("Processing msg: {}", msg);
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(),
                TransportApiRequestMsg.newBuilder().setLwM2MRequestMsg(msg).build());
        AsyncCallbackTemplate.withCallback(transportApiRequestTemplate.send(protoMsg),
                response -> callback.onSuccess(response.getValue().getLwM2MResponseMsg()), callback::onError, transportCallbackExecutor);
    }

    private TransportDeviceInfo getTransportDeviceInfo(TransportProtos.DeviceInfoProto di) {
        TransportDeviceInfo tdi = new TransportDeviceInfo();
        tdi.setTenantId(new TenantId(di.getTenantIdMSB()+""));
        tdi.setCustomerId(new CustomerId(di.getCustomerIdMSB()+""));
        tdi.setDeviceId(new DeviceId(di.getDeviceIdMSB()+""));
        tdi.setDeviceProfileId(new DeviceProfileId(di.getDeviceProfileIdMSB()+""));
        tdi.setAdditionalInfo(di.getAdditionalInfo());
        tdi.setDeviceName(di.getDeviceName());
        tdi.setDeviceType(di.getDeviceType());
        if (StringUtils.isNotEmpty(di.getPowerMode())) {
            tdi.setPowerMode(PowerMode.valueOf(di.getPowerMode()));
            tdi.setEdrxCycle(di.getEdrxCycle());
            tdi.setPsmActivityTimer(di.getPsmActivityTimer());
            tdi.setPagingTransmissionWindow(di.getPagingTransmissionWindow());
        }
        return tdi;
    }

    @Override
    public void process(ProvisionDeviceRequestMsg requestMsg, TransportServiceCallback<ProvisionDeviceResponseMsg> callback) {
        log.trace("Processing msg: {}", requestMsg);
        TbProtoQueueMsg<TransportApiRequestMsg> protoMsg = new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportApiRequestMsg.newBuilder().setProvisionDeviceRequestMsg(requestMsg).build());
        ListenableFuture<ProvisionDeviceResponseMsg> response = Futures.transform(transportApiRequestTemplate.send(protoMsg), tmp ->
                        tmp.getValue().getProvisionDeviceResponseMsg()
                , MoreExecutors.directExecutor());
        AsyncCallbackTemplate.withCallback(response, callback::onSuccess, callback::onError, transportCallbackExecutor);
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.SubscriptionInfoProto msg, TransportServiceCallback<Void> callback) {
        if (log.isTraceEnabled()) {
            log.trace("[{}] Processing msg: {}", toSessionId(sessionInfo), msg);
        }
        sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo)
                .setSubscriptionInfo(msg).build(), callback);
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.SessionEventMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo)
                    .setSessionEvent(msg).build(), callback);
        }
    }

    @Override
    public void process(TransportToDeviceActorMsg msg, TransportServiceCallback<Void> callback) {
        TransportProtos.SessionInfoProto sessionInfo = msg.getSessionInfo();
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, msg, callback);
        }
    }
    /*
       处理设备信息上报
     */
    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.PostTelemetryMsg msg, TransportServiceCallback<Void> callback) {
        int dataPoints = 0;
        for (TransportProtos.TsKvListProto tsKv : msg.getTsKvListList()) {
            dataPoints += tsKv.getKvCount();
        }
        if (checkLimits(sessionInfo, msg, callback, dataPoints)) {
            reportActivityInternal(sessionInfo);
            TenantId tenantId = getTenantId(sessionInfo);
            DeviceId deviceId = new DeviceId(sessionInfo.getDeviceIdMSB()+"");
            CustomerId customerId = getCustomerId(sessionInfo);
            MsgPackCallback packCallback = new MsgPackCallback(msg.getTsKvListCount(), new ApiStatsProxyCallback<>(tenantId, customerId, dataPoints, callback));
            for (TransportProtos.TsKvListProto tsKv : msg.getTsKvListList()) {
                TbMsgMetaData metaData = new TbMsgMetaData();
                metaData.putValue("deviceName", sessionInfo.getDeviceName());
                metaData.putValue("deviceType", sessionInfo.getDeviceType());
                metaData.putValue("ts", tsKv.getTs() + "");
                JsonObject json = JsonUtils.getJsonObject(tsKv.getKvList());
                sendToRuleEngine(tenantId, deviceId, customerId, sessionInfo, json, metaData, SessionMsgType.POST_TELEMETRY_REQUEST, packCallback);
            }
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.PostAttributeMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback, msg.getKvCount())) {
            reportActivityInternal(sessionInfo);
            TenantId tenantId = getTenantId(sessionInfo);
            DeviceId deviceId = new DeviceId(sessionInfo.getDeviceIdMSB()+"");
            JsonObject json = JsonUtils.getJsonObject(msg.getKvList());
            TbMsgMetaData metaData = new TbMsgMetaData();
            metaData.putValue("deviceName", sessionInfo.getDeviceName());
            metaData.putValue("deviceType", sessionInfo.getDeviceType());
            metaData.putValue("notifyDevice", "false");
            CustomerId customerId = getCustomerId(sessionInfo);
            sendToRuleEngine(tenantId, deviceId, customerId, sessionInfo, json, metaData, SessionMsgType.POST_ATTRIBUTES_REQUEST,
                    new TransportTbQueueCallback(new ApiStatsProxyCallback<>(tenantId, customerId, msg.getKvList().size(), callback)));
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.GetAttributeRequestMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo)
                    .setGetAttributes(msg).build(), new ApiStatsProxyCallback<>(getTenantId(sessionInfo), getCustomerId(sessionInfo), 1, callback));
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.SubscribeToAttributeUpdatesMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            SessionMetaData sessionMetaData = sessions.get(toSessionId(sessionInfo));
            if (sessionMetaData != null) {
                sessionMetaData.setSubscribedToAttributes(!msg.getUnsubscribe());
            }
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo).setSubscribeToAttributes(msg).build(),
                    new ApiStatsProxyCallback<>(getTenantId(sessionInfo), getCustomerId(sessionInfo), 1, callback));
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.SubscribeToRPCMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            SessionMetaData sessionMetaData = sessions.get(toSessionId(sessionInfo));
            if (sessionMetaData != null) {
                sessionMetaData.setSubscribedToRPC(!msg.getUnsubscribe());
            }
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo).setSubscribeToRPC(msg).build(),
                    new ApiStatsProxyCallback<>(getTenantId(sessionInfo), getCustomerId(sessionInfo), 1, callback));
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.ToDeviceRpcResponseMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo).setToDeviceRPCCallResponse(msg).build(),
                    new ApiStatsProxyCallback<>(getTenantId(sessionInfo), getCustomerId(sessionInfo), 1, callback));
        }
    }

    @Override
    public void notifyAboutUplink(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.UplinkNotificationMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo).setUplinkNotificationMsg(msg).build(), callback);
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.ToDeviceRpcRequestMsg msg, RpcStatus rpcStatus, TransportServiceCallback<Void> callback) {
        TransportProtos.ToDeviceRpcResponseStatusMsg responseMsg = TransportProtos.ToDeviceRpcResponseStatusMsg.newBuilder()
                .setRequestId(msg.getRequestId())
                .setRequestIdLSB(msg.getRequestIdLSB())
                .setRequestIdMSB(msg.getRequestIdMSB())
                .setStatus(rpcStatus.name())
                .build();

        if (checkLimits(sessionInfo, responseMsg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo).setRpcResponseStatusMsg(responseMsg).build(),
                    new ApiStatsProxyCallback<>(getTenantId(sessionInfo), getCustomerId(sessionInfo), 1, TransportServiceCallback.EMPTY));
        }
    }

    private void processTimeout(String requestId) {
        RpcRequestMetadata data = toServerRpcPendingMap.remove(requestId);
        if (data != null) {
            SessionMetaData md = sessions.get(data.getSessionId());
            if (md != null) {
                SessionMsgListener listener = md.getListener();
                transportCallbackExecutor.submit(() -> {
                    TransportProtos.ToServerRpcResponseMsg responseMsg =
                            TransportProtos.ToServerRpcResponseMsg.newBuilder()
                                    .setRequestId(data.getRequestId())
                                    .setError("timeout").build();
                    listener.onToServerRpcResponse(responseMsg);
                });
                if (md.getSessionType() == TransportProtos.SessionType.SYNC) {
                    deregisterSession(md.getSessionInfo());
                }
            } else {
                log.debug("[{}] Missing session.", data.getSessionId());
            }
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.ToServerRpcRequestMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            UUID sessionId = toSessionId(sessionInfo);
            TenantId tenantId = getTenantId(sessionInfo);
            DeviceId deviceId = getDeviceId(sessionInfo);
            JsonObject json = new JsonObject();
            json.addProperty("method", msg.getMethodName());
            json.add("params", JsonUtils.parse(msg.getParams()));

            TbMsgMetaData metaData = new TbMsgMetaData();
            metaData.putValue("deviceName", sessionInfo.getDeviceName());
            metaData.putValue("deviceType", sessionInfo.getDeviceType());
            metaData.putValue("requestId", Integer.toString(msg.getRequestId()));
            metaData.putValue("serviceId", serviceInfoProvider.getServiceId());
            metaData.putValue("sessionId", sessionId.toString());
            sendToRuleEngine(tenantId, deviceId, getCustomerId(sessionInfo), sessionInfo, json, metaData,
                    SessionMsgType.TO_SERVER_RPC_REQUEST, new TransportTbQueueCallback(callback));
            String requestId = sessionId + "-" + msg.getRequestId();
            toServerRpcPendingMap.put(requestId, new RpcRequestMetadata(sessionId, msg.getRequestId()));
            scheduler.schedule(() -> processTimeout(requestId), clientSideRpcTimeout, TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.ClaimDeviceMsg msg, TransportServiceCallback<Void> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            reportActivityInternal(sessionInfo);
            sendToDeviceActor(sessionInfo, TransportToDeviceActorMsg.newBuilder().setSessionInfo(sessionInfo)
                    .setClaimDevice(msg).build(), callback);
        }
    }

    @Override
    public void process(TransportProtos.SessionInfoProto sessionInfo, TransportProtos.GetOtaPackageRequestMsg msg, TransportServiceCallback<TransportProtos.GetOtaPackageResponseMsg> callback) {
        if (checkLimits(sessionInfo, msg, callback)) {
            TbProtoQueueMsg<TransportProtos.TransportApiRequestMsg> protoMsg =
                    new TbProtoQueueMsg<>(UUID.randomUUID().toString(), TransportProtos.TransportApiRequestMsg.newBuilder().setOtaPackageRequestMsg(msg).build());

            AsyncCallbackTemplate.withCallback(transportApiRequestTemplate.send(protoMsg), response -> {
                callback.onSuccess(response.getValue().getOtaPackageResponseMsg());
            }, callback::onError, transportCallbackExecutor);
        }
    }

    @Override
    public void reportActivity(TransportProtos.SessionInfoProto sessionInfo) {
        reportActivityInternal(sessionInfo);
    }

    private void reportActivityInternal(TransportProtos.SessionInfoProto sessionInfo) {
        UUID sessionId = toSessionId(sessionInfo);
        SessionActivityData sessionMetaData = sessionsActivity.computeIfAbsent(sessionId, id -> new SessionActivityData(sessionInfo));
        sessionMetaData.updateLastActivityTime();
    }

    private void checkInactivityAndReportActivity() {
        long expTime = System.currentTimeMillis() - sessionInactivityTimeout;
        Set<UUID> sessionsToRemove = new HashSet<>();
        sessionsActivity.forEach((uuid, sessionAD) -> {
            long lastActivityTime = sessionAD.getLastActivityTime();
            SessionMetaData sessionMD = sessions.get(uuid);
            if (sessionMD != null) {
                sessionAD.setSessionInfo(sessionMD.getSessionInfo());
            } else {
                sessionsToRemove.add(uuid);
            }
            TransportProtos.SessionInfoProto sessionInfo = sessionAD.getSessionInfo();

            if (sessionInfo.getGwSessionIdMSB() != 0 && sessionInfo.getGwSessionIdLSB() != 0) {
                String gwSessionId = sessionInfo.getGwSessionIdMSB()+"";
                SessionMetaData gwMetaData = sessions.get(gwSessionId);
                SessionActivityData gwActivityData = sessionsActivity.get(gwSessionId);
                if (gwMetaData != null && gwMetaData.isOverwriteActivityTime()) {
                    lastActivityTime = Math.max(gwActivityData.getLastActivityTime(), lastActivityTime);
                }
            }
            if (lastActivityTime < expTime) {
                if (sessionMD != null) {
                    if (log.isDebugEnabled()) {
                        log.debug("[{}] Session has expired due to last activity time: {}", toSessionId(sessionInfo), lastActivityTime);
                    }
                    sessions.remove(uuid);
                    sessionsToRemove.add(uuid);
                    process(sessionInfo, getSessionEventMsg(TransportProtos.SessionEvent.CLOSED), null);
                    TransportProtos.SessionCloseNotificationProto sessionCloseNotificationProto = TransportProtos.SessionCloseNotificationProto
                            .newBuilder()
                            .setMessage("Session has expired due to last activity time!")
                            .build();
                    sessionMD.getListener().onRemoteSessionCloseCommand(uuid, sessionCloseNotificationProto);
                }
            } else {
                if (lastActivityTime > sessionAD.getLastReportedActivityTime()) {
                    final long lastActivityTimeFinal = lastActivityTime;
                    process(sessionInfo, TransportProtos.SubscriptionInfoProto.newBuilder()
                            .setAttributeSubscription(sessionMD != null && sessionMD.isSubscribedToAttributes())
                            .setRpcSubscription(sessionMD != null && sessionMD.isSubscribedToRPC())
                            .setLastActivityTime(lastActivityTime).build(), new TransportServiceCallback<Void>() {
                        @Override
                        public void onSuccess(Void msg) {
                            sessionAD.setLastReportedActivityTime(lastActivityTimeFinal);
                        }

                        @Override
                        public void onError(Throwable e) {
                            log.warn("[{}] Failed to report last activity time", uuid, e);
                        }
                    });
                }
            }
        });
        // Removes all closed or short-lived sessions.
        sessionsToRemove.forEach(sessionsActivity::remove);
    }

    @Override
    public SessionMetaData registerSyncSession(TransportProtos.SessionInfoProto sessionInfo, SessionMsgListener listener, long timeout) {
        SessionMetaData currentSession = new SessionMetaData(sessionInfo, TransportProtos.SessionType.SYNC, listener);
        UUID sessionId = toSessionId(sessionInfo);
        sessions.putIfAbsent(sessionId, currentSession);

        TransportProtos.SessionCloseNotificationProto notification = TransportProtos.SessionCloseNotificationProto.newBuilder().setMessage("session timeout!").build();

        ScheduledFuture executorFuture = scheduler.schedule(() -> {
            listener.onRemoteSessionCloseCommand(sessionId, notification);
            deregisterSession(sessionInfo);
        }, timeout, TimeUnit.MILLISECONDS);

        currentSession.setScheduledFuture(executorFuture);
        return currentSession;
    }

    @Override
    public void deregisterSession(TransportProtos.SessionInfoProto sessionInfo) {
        SessionMetaData currentSession = sessions.get(toSessionId(sessionInfo));
        if (currentSession != null && currentSession.hasScheduledFuture()) {
            log.debug("Stopping scheduler to avoid resending response if request has been ack.");
            currentSession.getScheduledFuture().cancel(false);
        }
        sessions.remove(toSessionId(sessionInfo));
    }

    @Override
    public void log(TransportProtos.SessionInfoProto sessionInfo, String msg) {
        if (!logEnabled || sessionInfo == null || StringUtils.isEmpty(msg)) {
            return;
        }
        if (msg.length() > logMaxLength) {
            msg = msg.substring(0, logMaxLength);
        }
        TransportProtos.PostTelemetryMsg.Builder request = TransportProtos.PostTelemetryMsg.newBuilder();
        TransportProtos.TsKvListProto.Builder builder = TransportProtos.TsKvListProto.newBuilder();
        builder.setTs(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) * 1000L + (atomicTs.getAndIncrement() % 1000));
        builder.addKv(TransportProtos.KeyValueProto.newBuilder()
                .setKey("transportLog")
                .setType(TransportProtos.KeyValueType.STRING_V)
                .setStringV(msg).build());
        request.addTsKvList(builder.build());
        TransportProtos.PostTelemetryMsg postTelemetryMsg = request.build();
        process(sessionInfo, postTelemetryMsg, TransportServiceCallback.EMPTY);
    }

    private boolean checkLimits(TransportProtos.SessionInfoProto sessionInfo, Object msg, TransportServiceCallback<?> callback) {
        return checkLimits(sessionInfo, msg, callback, 0);
    }

    private boolean checkLimits(TransportProtos.SessionInfoProto sessionInfo, Object msg, TransportServiceCallback<?> callback, int dataPoints) {
        if (log.isTraceEnabled()) {
            log.trace("[{}] Processing msg: {}", toSessionId(sessionInfo), msg);
        }
        TenantId tenantId = new TenantId(sessionInfo.getTenantIdMSB()+"");
        DeviceId deviceId = new DeviceId(sessionInfo.getDeviceIdMSB()+"");

     //   EntityType rateLimitedEntityType = rateLimitService.checkLimits(tenantId, deviceId, dataPoints);
        EntityType rateLimitedEntityType = null;
        if (rateLimitedEntityType == null) {
            return true;
        } else {
            if (callback != null) {
                callback.onError(new TbRateLimitsException(rateLimitedEntityType));
            }
            return false;
        }
    }

    protected void processToTransportMsg(TransportProtos.ToTransportMsg toSessionMsg) {
        UUID sessionId = new UUID(toSessionMsg.getSessionIdMSB(), toSessionMsg.getSessionIdLSB());
        SessionMetaData md = sessions.get(sessionId);
        if (md != null) {
            log.trace("[{}] Processing notification: {}", sessionId, toSessionMsg);
            SessionMsgListener listener = md.getListener();
            transportCallbackExecutor.submit(() -> {
                if (toSessionMsg.hasGetAttributesResponse()) {
                    listener.onGetAttributesResponse(toSessionMsg.getGetAttributesResponse());
                }
                if (toSessionMsg.hasAttributeUpdateNotification()) {
                    listener.onAttributeUpdate(sessionId, toSessionMsg.getAttributeUpdateNotification());
                }
                if (toSessionMsg.hasSessionCloseNotification()) {
                    listener.onRemoteSessionCloseCommand(sessionId, toSessionMsg.getSessionCloseNotification());
                }
                if (toSessionMsg.hasToTransportUpdateCredentialsNotification()) {
                    listener.onToTransportUpdateCredentials(toSessionMsg.getToTransportUpdateCredentialsNotification());
                }
                if (toSessionMsg.hasToDeviceRequest()) {
                    listener.onToDeviceRpcRequest(sessionId, toSessionMsg.getToDeviceRequest());
                }
                if (toSessionMsg.hasToServerResponse()) {
                    String requestId = sessionId + "-" + toSessionMsg.getToServerResponse().getRequestId();
                    toServerRpcPendingMap.remove(requestId);
                    listener.onToServerRpcResponse(toSessionMsg.getToServerResponse());
                }
            });
            if (md.getSessionType() == TransportProtos.SessionType.SYNC) {
                deregisterSession(md.getSessionInfo());
            }
        } else {
            log.trace("Processing broadcast notification: {}", toSessionMsg);
            if (toSessionMsg.hasEntityUpdateMsg()) {
                TransportProtos.EntityUpdateMsg msg = toSessionMsg.getEntityUpdateMsg();
                EntityType entityType = EntityType.valueOf(msg.getEntityType());
                if (EntityType.DEVICE_PROFILE.equals(entityType)) {
                    DeviceProfile deviceProfile = deviceProfileCache.put(msg.getData());
                    if (deviceProfile != null) {
                        log.info("On device profile update: {}", deviceProfile);
                        onProfileUpdate(deviceProfile);
                    }
                } else if (EntityType.TENANT_PROFILE.equals(entityType)) {
                    rateLimitService.update(tenantProfileCache.put(msg.getData()));
                } else if (EntityType.TENANT.equals(entityType)) {
                    Optional<Tenant> profileOpt = dataDecodingEncodingService.decode(msg.getData().toByteArray());
                    if (profileOpt.isPresent()) {
                        Tenant tenant = profileOpt.get();
                        boolean updated = tenantProfileCache.put(TenantId.fromString(tenant.getId()), TenantProfileId.fromString(tenant.getTenantProfileId()));
                        if (updated) {
                            rateLimitService.update(TenantId.fromString(tenant.getId()));
                        }
                    }
                } else if (EntityType.API_USAGE_STATE.equals(entityType)) {
                    Optional<ApiUsageState> stateOpt = dataDecodingEncodingService.decode(msg.getData().toByteArray());
                    if (stateOpt.isPresent()) {
                        ApiUsageState apiUsageState = stateOpt.get();
                        rateLimitService.update(apiUsageState.getTenantId(), apiUsageState.isTransportEnabled());
                        //TODO: if transport is disabled, we should close all sessions and not to check credentials.
                    }
                } else if (EntityType.DEVICE.equals(entityType)) {
                    Optional<Device> deviceOpt = dataDecodingEncodingService.decode(msg.getData().toByteArray());
                    deviceOpt.ifPresent(device -> {
                        onDeviceUpdate(device);
                        eventPublisher.publishEvent(new DeviceUpdatedEvent(device));
                    });
                }
            } else if (toSessionMsg.hasEntityDeleteMsg()) {
                TransportProtos.EntityDeleteMsg msg = toSessionMsg.getEntityDeleteMsg();
                EntityType entityType = EntityType.valueOf(msg.getEntityType());
                String entityUuid = msg.getEntityIdMSB()+"";
                if (EntityType.DEVICE_PROFILE.equals(entityType)) {
                    deviceProfileCache.evict(new DeviceProfileId(msg.getEntityIdMSB()+""));
                } else if (EntityType.TENANT_PROFILE.equals(entityType)) {
                    tenantProfileCache.remove(new TenantProfileId(entityUuid));
                } else if (EntityType.TENANT.equals(entityType)) {
                    rateLimitService.remove(new TenantId(entityUuid));
                } else if (EntityType.DEVICE.equals(entityType)) {
                    rateLimitService.remove(new DeviceId(entityUuid));
                    onDeviceDeleted(new DeviceId(entityUuid));
                }
            } else if (toSessionMsg.hasResourceUpdateMsg()) {
                TransportProtos.ResourceUpdateMsg msg = toSessionMsg.getResourceUpdateMsg();
                TenantId tenantId = new TenantId(msg.getTenantIdMSB()+"");
                ResourceType resourceType = ResourceType.valueOf(msg.getResourceType());
                String resourceId = msg.getResourceKey();
                transportResourceCache.update(tenantId, resourceType, resourceId);
                sessions.forEach((id, mdRez) -> {
                    log.warn("ResourceUpdate - [{}] [{}]", id, mdRez);
                    transportCallbackExecutor.submit(() -> mdRez.getListener().onResourceUpdate(msg));
                });

            } else if (toSessionMsg.hasResourceDeleteMsg()) {
                TransportProtos.ResourceDeleteMsg msg = toSessionMsg.getResourceDeleteMsg();
                TenantId tenantId = new TenantId(msg.getTenantIdMSB()+"");
                ResourceType resourceType = ResourceType.valueOf(msg.getResourceType());
                String resourceId = msg.getResourceKey();
                transportResourceCache.evict(tenantId, resourceType, resourceId);
                sessions.forEach((id, mdRez) -> {
                    log.warn("ResourceDelete - [{}] [{}]", id, mdRez);
                    transportCallbackExecutor.submit(() -> mdRez.getListener().onResourceDelete(msg));
                });
            } else {
                //TODO: should we notify the device actor about missed session?
                log.debug("[{}] Missing session.", sessionId);
            }
        }
    }


    public void onProfileUpdate(DeviceProfile deviceProfile) {
        long deviceProfileIdMSB = Long.parseLong(deviceProfile.getId());
        long deviceProfileIdLSB = Long.parseLong(deviceProfile.getId());
        sessions.forEach((id, md) -> {
            //TODO: if transport types are different - we should close the session.
            if (md.getSessionInfo().getDeviceProfileIdMSB() == deviceProfileIdMSB
                    && md.getSessionInfo().getDeviceProfileIdLSB() == deviceProfileIdLSB) {
                TransportProtos.SessionInfoProto newSessionInfo = TransportProtos.SessionInfoProto.newBuilder()
                        .mergeFrom(md.getSessionInfo())
                        .setDeviceProfileIdMSB(deviceProfileIdMSB)
                        .setDeviceProfileIdLSB(deviceProfileIdLSB)
                        .setDeviceType(deviceProfile.getName())
                        .build();
                md.setSessionInfo(newSessionInfo);
                transportCallbackExecutor.submit(() -> md.getListener().onDeviceProfileUpdate(newSessionInfo, deviceProfile));
            }
        });
    }

    private void onDeviceUpdate(Device device) {
        long deviceIdMSB = Long.parseLong(device.getId());
        long deviceIdLSB = Long.parseLong(device.getId());
        long deviceProfileIdMSB = Long.parseLong(device.getDeviceProfileId());
        long deviceProfileIdLSB = Long.parseLong(device.getDeviceProfileId());
        sessions.forEach((id, md) -> {
            if ((md.getSessionInfo().getDeviceIdMSB() == deviceIdMSB && md.getSessionInfo().getDeviceIdLSB() == deviceIdLSB)) {
                DeviceProfile newDeviceProfile;
                if (md.getSessionInfo().getDeviceProfileIdMSB() != deviceProfileIdMSB
                        && md.getSessionInfo().getDeviceProfileIdLSB() != deviceProfileIdLSB) {
                    //TODO: if transport types are different - we should close the session.
                    newDeviceProfile = deviceProfileCache.get(new DeviceProfileId(deviceProfileIdMSB+""));
                } else {
                    newDeviceProfile = null;
                }
                TransportProtos.SessionInfoProto newSessionInfo = TransportProtos.SessionInfoProto.newBuilder()
                        .mergeFrom(md.getSessionInfo())
                        .setDeviceProfileIdMSB(deviceProfileIdMSB)
                        .setDeviceProfileIdLSB(deviceProfileIdLSB)
                        .setDeviceName(device.getName())
                        .setDeviceType(device.getType()).build();
                if (device.fetchAdditionalInfo().has("gateway")
                        && device.fetchAdditionalInfo().get("gateway").asBoolean()
                        && device.fetchAdditionalInfo().has(OVERWRITE_ACTIVITY_TIME)
                        && device.fetchAdditionalInfo().get(OVERWRITE_ACTIVITY_TIME).isBoolean()) {
                    md.setOverwriteActivityTime(device.fetchAdditionalInfo().get(OVERWRITE_ACTIVITY_TIME).asBoolean());
                }
                md.setSessionInfo(newSessionInfo);
                transportCallbackExecutor.submit(() -> md.getListener().onDeviceUpdate(newSessionInfo, device, Optional.ofNullable(newDeviceProfile)));
            }
        });
    }

    private void onDeviceDeleted(DeviceId deviceId) {
        sessions.forEach((id, md) -> {
            DeviceId sessionDeviceId = new DeviceId(md.getSessionInfo().getDeviceIdMSB()+"");
            if (sessionDeviceId.equals(deviceId)) {
                transportCallbackExecutor.submit(() -> {
                    md.getListener().onDeviceDeleted(deviceId);
                });
            }
        });
    }

    protected UUID toSessionId(TransportProtos.SessionInfoProto sessionInfo) {
        return new UUID(sessionInfo.getSessionIdMSB(), sessionInfo.getSessionIdLSB());
    }

    protected UUID getRoutingKey(TransportProtos.SessionInfoProto sessionInfo) {
        return new UUID(sessionInfo.getDeviceIdMSB(), sessionInfo.getDeviceIdLSB());
    }

    protected TenantId getTenantId(TransportProtos.SessionInfoProto sessionInfo) {
        return new TenantId(sessionInfo.getTenantIdMSB()+"");
    }

    protected CustomerId getCustomerId(TransportProtos.SessionInfoProto sessionInfo) {
        long msb = sessionInfo.getCustomerIdMSB();
        long lsb = sessionInfo.getCustomerIdLSB();
        if (msb != 0 && lsb != 0) {
            return new CustomerId(msb+"");
        } else {
            return new CustomerId(EntityId.NULL_UUID);
        }
    }

    protected DeviceId getDeviceId(TransportProtos.SessionInfoProto sessionInfo) {
        return new DeviceId(sessionInfo.getDeviceIdMSB()+"");
    }

    public static TransportProtos.SessionEventMsg getSessionEventMsg(TransportProtos.SessionEvent event) {
        return TransportProtos.SessionEventMsg.newBuilder()
                .setSessionType(TransportProtos.SessionType.ASYNC)
                .setEvent(event).build();
    }

    protected void sendToDeviceActor(TransportProtos.SessionInfoProto sessionInfo, TransportToDeviceActorMsg toDeviceActorMsg, TransportServiceCallback<Void> callback) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_CORE, getTenantId(sessionInfo).getId(), getDeviceId(sessionInfo).getId());
        if (log.isTraceEnabled()) {
            log.trace("[{}][{}] Pushing to topic {} message {}", getTenantId(sessionInfo), getDeviceId(sessionInfo), tpi.getFullTopicName(), toDeviceActorMsg);
        }
        TransportTbQueueCallback transportTbQueueCallback = callback != null ?
                new TransportTbQueueCallback(callback) : null;
        tbCoreProducerStats.incrementTotal();
        StatsCallback wrappedCallback = new StatsCallback(transportTbQueueCallback, tbCoreProducerStats);
        tbCoreMsgProducer.send(tpi,
                new TbProtoQueueMsg<>(getRoutingKey(sessionInfo).toString(),
                        ToCoreMsg.newBuilder().setToDeviceActorMsg(toDeviceActorMsg).build()),
                wrappedCallback);
    }

    private void sendToRuleEngine(TenantId tenantId, TbMsg tbMsg, TbQueueCallback callback) {
        TopicPartitionInfo tpi = partitionService.resolve(ServiceType.TB_RULE_ENGINE, tbMsg.getQueueName(), tenantId.getId(), tbMsg.getOriginator().getId());
        if (log.isTraceEnabled()) {
            log.trace("[{}][{}] Pushing to topic {} message {}", tenantId, tbMsg.getOriginator(), tpi.getFullTopicName(), tbMsg);
        }
        ToRuleEngineMsg msg = ToRuleEngineMsg.newBuilder().setTbMsg(TbMsg.toByteString(tbMsg))
                .setTenantIdMSB(Long.parseLong(tenantId.getId()))
                .setTenantIdLSB(Long.parseLong(tenantId.getId())).build();
        ruleEngineProducerStats.incrementTotal();
        StatsCallback wrappedCallback = new StatsCallback(callback, ruleEngineProducerStats);
        ruleEngineMsgProducer.send(tpi, new TbProtoQueueMsg<>(tbMsg.getId(), msg), wrappedCallback);
    }

    private void sendToRuleEngine(TenantId tenantId, DeviceId deviceId, CustomerId customerId, TransportProtos.SessionInfoProto sessionInfo, JsonObject json,
                                  TbMsgMetaData metaData, SessionMsgType sessionMsgType, TbQueueCallback callback) {
        DeviceProfileId deviceProfileId = new DeviceProfileId(sessionInfo.getDeviceProfileIdMSB()+"");
        DeviceProfile deviceProfile = deviceProfileCache.get(deviceProfileId);
        String ruleChainId;
        String queueName;

        if (deviceProfile == null) {
            log.warn("[{}] Device profile is null!", deviceProfileId);
            ruleChainId = null;
            queueName = ServiceQueue.MAIN;
        } else {
            ruleChainId = deviceProfile.getDefaultRuleChainId();
            String defaultQueueName = deviceProfile.getDefaultQueueName();
            queueName = StringUtils.isNotEmpty(defaultQueueName) ? defaultQueueName : ServiceQueue.MAIN;
        }

        TbMsg tbMsg = TbMsg.newMsg(queueName, sessionMsgType.name(), deviceId, customerId, metaData, gson.toJson(json), ruleChainId, null);
        sendToRuleEngine(tenantId, tbMsg, callback);
    }

    private class TransportTbQueueCallback implements TbQueueCallback {
        private final TransportServiceCallback<Void> callback;

        private TransportTbQueueCallback(TransportServiceCallback<Void> callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(TbQueueMsgMetadata metadata) {
            DefaultTransportService.this.transportCallbackExecutor.submit(() -> callback.onSuccess(null));
        }

        @Override
        public void onFailure(Throwable t) {
            DefaultTransportService.this.transportCallbackExecutor.submit(() -> callback.onError(t));
        }
    }

    private static class StatsCallback implements TbQueueCallback {
        private final TbQueueCallback callback;
        private final MessagesStats stats;

        private StatsCallback(TbQueueCallback callback, MessagesStats stats) {
            this.callback = callback;
            this.stats = stats;
        }

        @Override
        public void onSuccess(TbQueueMsgMetadata metadata) {
            stats.incrementSuccessful();
            if (callback != null)
                callback.onSuccess(metadata);
        }

        @Override
        public void onFailure(Throwable t) {
            stats.incrementFailed();
            if (callback != null)
                callback.onFailure(t);
        }
    }

    private class MsgPackCallback implements TbQueueCallback {
        private final AtomicInteger msgCount;
        private final TransportServiceCallback<Void> callback;

        public MsgPackCallback(Integer msgCount, TransportServiceCallback<Void> callback) {
            this.msgCount = new AtomicInteger(msgCount);
            this.callback = callback;
        }

        @Override
        public void onSuccess(TbQueueMsgMetadata metadata) {
            if (msgCount.decrementAndGet() <= 0) {
                DefaultTransportService.this.transportCallbackExecutor.submit(() -> callback.onSuccess(null));
            }
        }

        @Override
        public void onFailure(Throwable t) {
            DefaultTransportService.this.transportCallbackExecutor.submit(() -> callback.onError(t));
        }
    }

    private class ApiStatsProxyCallback<T> implements TransportServiceCallback<T> {
        private final TenantId tenantId;
        private final CustomerId customerId;
        private final int dataPoints;
        private final TransportServiceCallback<T> callback;

        public ApiStatsProxyCallback(TenantId tenantId, CustomerId customerId, int dataPoints, TransportServiceCallback<T> callback) {
            this.tenantId = tenantId;
            this.customerId = customerId;
            this.dataPoints = dataPoints;
            this.callback = callback;
        }

        @Override
        public void onSuccess(T msg) {
            try {
                apiUsageClient.report(tenantId.getId(), customerId, ApiUsageRecordKey.TRANSPORT_MSG_COUNT, 1);
                apiUsageClient.report(tenantId.getId(), customerId, ApiUsageRecordKey.TRANSPORT_DP_COUNT, dataPoints);
            } finally {
                callback.onSuccess(msg);
            }
        }

        @Override
        public void onError(Throwable e) {
            callback.onError(e);
        }
    }

    @Override
    public ExecutorService getCallbackExecutor() {
        return transportCallbackExecutor;
    }

    @Override
    public boolean hasSession(TransportProtos.SessionInfoProto sessionInfo) {
        return sessions.containsKey(toSessionId(sessionInfo));
    }

    @Override
    public void createGaugeStats(String statsName, AtomicInteger number) {
        statsFactory.createGauge(StatsType.TRANSPORT + "." + statsName, number);
        statsMap.put(statsName, number);
    }

    @Scheduled(fixedDelayString = "${transport.stats.print-interval-ms:60000}")
    public void printStats() {
        if (statsEnabled) {
            String values = statsMap.entrySet().stream()
                    .map(kv -> kv.getKey() + " [" + kv.getValue() + "]").collect(Collectors.joining(", "));
            log.info("Transport Stats: {}", values);
        }
    }
}
