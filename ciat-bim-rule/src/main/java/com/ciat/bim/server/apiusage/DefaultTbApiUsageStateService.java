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
package com.ciat.bim.server.apiusage;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.data.id.TenantProfileId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.ServiceType;
import com.ciat.bim.server.common.data.*;
import com.ciat.bim.server.common.msg.queue.TbCallback;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.discovery.PartitionService;
import com.ciat.bim.server.queue.discovery.TbApplicationEventListener;
import com.ciat.bim.server.queue.discovery.event.PartitionChangeEvent;
import com.ciat.bim.server.queue.scheduler.SchedulerComponent;
import com.ciat.bim.server.transport.TransportProtos.UsageStatsKVProto;
import com.ciat.bim.server.transport.TransportProtos.ToUsageStatsServiceMsg;
import com.ciat.bim.server.utils.SchedulerUtils;
import com.ciat.bim.server.utils.ThingsBoardExecutors;
import com.ciat.bim.server.utils.ThingsBoardThreadFactory;
import com.ciat.bim.tenant.TbTenantProfileCache;
import com.ciat.bim.tenant.TenantProfileConfiguration;
import com.ciat.bim.tenant.entity.TenantProfileData;
import com.google.common.util.concurrent.FutureCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultTbApiUsageStateService extends TbApplicationEventListener<PartitionChangeEvent> implements TbApiUsageStateService {

    public static final String HOURLY = "Hourly";
    public static final FutureCallback<Integer> VOID_CALLBACK = new FutureCallback<Integer>() {
        @Override
        public void onSuccess(@Nullable Integer result) {
        }

        @Override
        public void onFailure(Throwable t) {
        }
    };
    private  PartitionService partitionService;
    private  ITenantService tenantService;
    private  TbTenantProfileCache tenantProfileCache;
//    private final TbClusterService clusterService;
//    private final CustomerService customerService;
//    private final TimeseriesService tsService;
//    private final ApiUsageStateService apiUsageStateService;
    @Autowired
    private SchedulerComponent scheduler;
//    private final MailService mailService;

//    @Lazy
//    @Autowired
//    private InternalTelemetryService tsWsService;
   private  ExecutorService mailExecutor;
    // Entities that should be processed on this server
    private final Map<EntityId, BaseApiUsageState> myUsageStates = new ConcurrentHashMap<>();
    // Entities that should be processed on other servers
    private final Map<EntityId, ApiUsageState> otherUsageStates = new ConcurrentHashMap<>();

    private final Set<EntityId> deletedEntities = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Value("${usage.stats.report.enabled:true}")
    private boolean enabled;

    @Value("${usage.stats.check.cycle:60000}")
    private long nextCycleCheckInterval;

    private final Lock updateLock = new ReentrantLock();



//    public DefaultTbApiUsageStateService(TbClusterService clusterService,
//                                         PartitionService partitionService,
//                                         ITenantService tenantService,
//                                         CustomerService customerService,
//                                         TimeseriesService tsService,
//                                         ApiUsageStateService apiUsageStateService,
//                                         SchedulerComponent scheduler,
//                                         TbTenantProfileCache tenantProfileCache,
//                                         MailService mailService) {
//        this.clusterService = clusterService;
//        this.partitionService = partitionService;
//        this.tenantService = tenantService;
//        this.customerService = customerService;
//        this.tsService = tsService;
//        this.apiUsageStateService = apiUsageStateService;
//        this.scheduler = scheduler;
//        this.tenantProfileCache = tenantProfileCache;
//        this.mailService = mailService;
//        this.mailExecutor = Executors.newSingleThreadExecutor(ThingsBoardThreadFactory.forName("api-usage-svc-mail"));
//    }

    @PostConstruct
    public void init() {
        if (enabled) {
            log.info("Starting api usage service.");
          //  scheduler.scheduleAtFixedRate(this::checkStartOfNextCycle, nextCycleCheckInterval, nextCycleCheckInterval, TimeUnit.MILLISECONDS);
            log.info("Started api usage service.");
        }
    }

    @Override
    public void process(TbProtoQueueMsg<ToUsageStatsServiceMsg> msg, TbCallback callback) {
        ToUsageStatsServiceMsg statsMsg = msg.getValue();

        TenantId tenantId = new TenantId(new UUID(statsMsg.getTenantIdMSB(), statsMsg.getTenantIdLSB()));
        EntityId entityId;
        if (statsMsg.getCustomerIdMSB() != 0 && statsMsg.getCustomerIdLSB() != 0) {
            entityId = new CustomerId(new UUID(statsMsg.getCustomerIdMSB(), statsMsg.getCustomerIdLSB()));
        } else {
            entityId = tenantId;
        }

      //  processEntityUsageStats(tenantId, entityId, statsMsg.getValuesList());
        callback.onSuccess();
    }
//
//    private void processEntityUsageStats(TenantId tenantId, EntityId entityId, List<UsageStatsKVProto> values) {
//        if (deletedEntities.contains(entityId)) return;
//
//        BaseApiUsageState usageState;
//        List<AttributeKv> updatedEntries;
//        Map<ApiFeature, ApiUsageStateValue> result;
//
//        updateLock.lock();
//        try {
//            usageState = getOrFetchState(tenantId, entityId);
//            long ts = usageState.getCurrentCycleTs();
//            long hourTs = usageState.getCurrentHourTs();
//            long newHourTs = SchedulerUtils.getStartOfCurrentHour();
//            if (newHourTs != hourTs) {
//                usageState.setHour(newHourTs);
//            }
//            updatedEntries = new ArrayList<>(ApiUsageRecordKey.values().length);
//            Set<ApiFeature> apiFeatures = new HashSet<>();
//            for (UsageStatsKVProto kvProto : values) {
//                ApiUsageRecordKey recordKey = ApiUsageRecordKey.valueOf(kvProto.getKey());
//                long newValue = usageState.add(recordKey, kvProto.getValue());
//                updatedEntries.add(new AttributeKv(ts, new LongDataEntry(recordKey.getApiCountKey(), newValue)));
//                long newHourlyValue = usageState.addToHourly(recordKey, kvProto.getValue());
//                updatedEntries.add(new AttributeKv(newHourTs, new LongDataEntry(recordKey.getApiCountKey() + HOURLY, newHourlyValue)));
//                apiFeatures.add(recordKey.getApiFeature());
//            }
//            if (usageState.getEntityType() == EntityType.TENANT && !usageState.getEntityId().equals(TenantId.SYS_TENANT_ID)) {
//                result = ((TenantApiUsageState) usageState).checkStateUpdatedDueToThreshold(apiFeatures);
//            } else {
//                result = Collections.emptyMap();
//            }
//        } finally {
//            updateLock.unlock();
//        }
//        tsWsService.saveAndNotifyInternal(tenantId, usageState.getApiUsageState().getId(), updatedEntries, VOID_CALLBACK);
//        if (!result.isEmpty()) {
//            persistAndNotify(usageState, result);
//        }
//    }

    @Override
    protected void onTbApplicationEvent(PartitionChangeEvent partitionChangeEvent) {
        if (partitionChangeEvent.getServiceType().equals(ServiceType.TB_CORE)) {
            myUsageStates.entrySet().removeIf(entry -> {
                return !partitionService.resolve(ServiceType.TB_CORE, entry.getValue().getTenantId(), entry.getKey()).isMyPartition();
            });
            otherUsageStates.entrySet().removeIf(entry -> {
                return partitionService.resolve(ServiceType.TB_CORE, entry.getValue().getTenantId(), entry.getKey()).isMyPartition();
            });
            //initStatesFromDataBase();
        }
    }

    @Override
    public ApiUsageState getApiUsageState(TenantId tenantId) {
//        TenantApiUsageState tenantState = (TenantApiUsageState) myUsageStates.get(tenantId);
//        if (tenantState != null) {
//            return tenantState.getApiUsageState();
//        } else {
//            ApiUsageState state = otherUsageStates.get(tenantId);
//            if (state != null) {
//                return state;
//            } else {
//                if (partitionService.resolve(ServiceType.TB_CORE, tenantId, tenantId).isMyPartition()) {
//                    return getOrFetchState(tenantId, tenantId).getApiUsageState();
//                } else {
//                    updateLock.lock();
//                    try {
//                        state = otherUsageStates.get(tenantId);
//                        if (state == null) {
//                            state = apiUsageStateService.findTenantApiUsageState(tenantId);
//                            if (state != null) {
//                                otherUsageStates.put(tenantId, state);
//                            }
//                        }
//                    } finally {
//                        updateLock.unlock();
//                    }
//                    return state;
//                }
//            }
//        }
        return  null;
    }

    @Override
    public void onApiUsageStateUpdate(TenantId tenantId) {
        otherUsageStates.remove(tenantId);
    }

    @Override
    public void onTenantProfileUpdate(TenantProfileId tenantProfileId) {
//        log.info("[{}] On Tenant Profile Update", tenantProfileId);
//        TenantProfile tenantProfile = tenantProfileCache.get(tenantProfileId);
//        updateLock.lock();
//        try {
//            myUsageStates.values().stream()
//                    .filter(state -> state.getEntityType() == EntityType.TENANT)
//                    .map(state -> (TenantApiUsageState) state)
//                    .forEach(state -> {
//                        if (tenantProfile.getId().equals(state.getTenantProfileId())) {
//                            updateTenantState(state, tenantProfile);
//                        }
//                    });
//        } finally {
//            updateLock.unlock();
//        }
    }

    @Override
    public void onTenantUpdate(TenantId tenantId) {
//        log.info("[{}] On Tenant Update.", tenantId);
//        TenantProfile tenantProfile = tenantProfileCache.get(tenantId);
//        updateLock.lock();
//        try {
//            TenantApiUsageState state = (TenantApiUsageState) myUsageStates.get(tenantId);
//            if (state != null && !state.getTenantProfileId().equals(tenantProfile.getId())) {
//                updateTenantState(state, tenantProfile);
//            }
//        } finally {
//            updateLock.unlock();
//        }
    }

//    private void updateTenantState(TenantApiUsageState state, TenantProfile profile) {
//        TenantProfileData oldProfileData = state.getTenantProfileData();
//        state.setTenantProfileId(profile.getId());
//        state.setTenantProfileData(profile.getProfileData());
//        Map<ApiFeature, ApiUsageStateValue> result = state.checkStateUpdatedDueToThresholds();
//        if (!result.isEmpty()) {
//            persistAndNotify(state, result);
//        }
//        updateProfileThresholds(state.getTenantId(), state.getApiUsageState().getId(),
//                oldProfileData.getConfiguration(), profile.getProfileData().getConfiguration());
//    }
//
//    private void updateProfileThresholds(TenantId tenantId, ApiUsageStateId id,
//                                         TenantProfileConfiguration oldData, TenantProfileConfiguration newData) {
//        long ts = System.currentTimeMillis();
//        List<TsKvEntry> profileThresholds = new ArrayList<>();
//        for (ApiUsageRecordKey key : ApiUsageRecordKey.values()) {
//            long newProfileThreshold = newData.getProfileThreshold(key);
//            if (oldData == null || oldData.getProfileThreshold(key) != newProfileThreshold) {
//                log.info("[{}] Updating profile threshold [{}]:[{}]", tenantId, key, newProfileThreshold);
//                profileThresholds.add(new BasicTsKvEntry(ts, new LongDataEntry(key.getApiLimitKey(), newProfileThreshold)));
//            }
//        }
//        if (!profileThresholds.isEmpty()) {
//            tsWsService.saveAndNotifyInternal(tenantId, id, profileThresholds, VOID_CALLBACK);
//        }
//    }

    public void onTenantDelete(TenantId tenantId) {
        deletedEntities.add(tenantId);
        myUsageStates.remove(tenantId);
        otherUsageStates.remove(tenantId);
    }

    @Override
    public void onCustomerDelete(CustomerId customerId) {
        deletedEntities.add(customerId);
        myUsageStates.remove(customerId);
    }

//    private void persistAndNotify(BaseApiUsageState state, Map<ApiFeature, ApiUsageStateValue> result) {
//        log.info("[{}] Detected update of the API state for {}: {}", state.getEntityId(), state.getEntityType(), result);
//        apiUsageStateService.update(state.getApiUsageState());
//        clusterService.onApiStateChange(state.getApiUsageState(), null);
//        long ts = System.currentTimeMillis();
//        List<TsKvEntry> stateTelemetry = new ArrayList<>();
//        result.forEach((apiFeature, aState) -> stateTelemetry.add(new BasicTsKvEntry(ts, new StringDataEntry(apiFeature.getApiStateKey(), aState.name()))));
//        tsWsService.saveAndNotifyInternal(state.getTenantId(), state.getApiUsageState().getId(), stateTelemetry, VOID_CALLBACK);
//
//        if (state.getEntityType() == EntityType.TENANT && !state.getEntityId().equals(TenantId.SYS_TENANT_ID)) {
//            String email = tenantService.findTenantById(state.getTenantId()).getEmail();
//            if (StringUtils.isNotEmpty(email)) {
//                result.forEach((apiFeature, stateValue) -> {
//                    mailExecutor.submit(() -> {
//                        try {
//                            mailService.sendApiFeatureStateEmail(apiFeature, stateValue, email, createStateMailMessage((TenantApiUsageState) state, apiFeature, stateValue));
//                        } catch (ThingsboardException e) {
//                            log.warn("[{}] Can't send update of the API state to tenant with provided email [{}]", state.getTenantId(), email, e);
//                        }
//                    });
//                });
//            } else {
//                log.warn("[{}] Can't send update of the API state to tenant with empty email!", state.getTenantId());
//            }
//        }
//    }
//
//    private ApiUsageStateMailMessage createStateMailMessage(TenantApiUsageState state, ApiFeature apiFeature, ApiUsageStateValue stateValue) {
//        StateChecker checker = getStateChecker(stateValue);
//        for (ApiUsageRecordKey apiUsageRecordKey : ApiUsageRecordKey.getKeys(apiFeature)) {
//            long threshold = state.getProfileThreshold(apiUsageRecordKey);
//            long warnThreshold = state.getProfileWarnThreshold(apiUsageRecordKey);
//            long value = state.get(apiUsageRecordKey);
//            if (checker.check(threshold, warnThreshold, value)) {
//                return new ApiUsageStateMailMessage(apiUsageRecordKey, threshold, value);
//            }
//        }
//        return null;
//    }
//
//    private StateChecker getStateChecker(ApiUsageStateValue stateValue) {
//        if (ApiUsageStateValue.ENABLED.equals(stateValue)) {
//            return (t, wt, v) -> true;
//        } else if (ApiUsageStateValue.WARNING.equals(stateValue)) {
//            return (t, wt, v) -> v < t && v >= wt;
//        } else {
//            return (t, wt, v) -> v >= t;
//        }
//    }
//
//    private interface StateChecker {
//        boolean check(long threshold, long warnThreshold, long value);
//    }
//
//    private void checkStartOfNextCycle() {
//        updateLock.lock();
//        try {
//            long now = System.currentTimeMillis();
//            myUsageStates.values().forEach(state -> {
//                if ((state.getNextCycleTs() < now) && (now - state.getNextCycleTs() < TimeUnit.HOURS.toMillis(1))) {
//                    state.setCycles(state.getNextCycleTs(), SchedulerUtils.getStartOfNextNextMonth());
//                    saveNewCounts(state, Arrays.asList(ApiUsageRecordKey.values()));
//                    if (state.getEntityType() == EntityType.TENANT && !state.getEntityId().equals(TenantId.SYS_TENANT_ID)) {
//                        TenantId tenantId = state.getTenantId();
//                        updateTenantState((TenantApiUsageState) state, tenantProfileCache.get(tenantId));
//                    }
//                }
//            });
//        } finally {
//            updateLock.unlock();
//        }
//    }
//
//    private void saveNewCounts(BaseApiUsageState state, List<ApiUsageRecordKey> keys) {
//        List<TsKvEntry> counts = keys.stream()
//                .map(key -> new BasicTsKvEntry(state.getCurrentCycleTs(), new LongDataEntry(key.getApiCountKey(), 0L)))
//                .collect(Collectors.toList());
//
//        tsWsService.saveAndNotifyInternal(state.getTenantId(), state.getApiUsageState().getId(), counts, VOID_CALLBACK);
//    }
//
//    private BaseApiUsageState getOrFetchState(TenantId tenantId, EntityId entityId) {
//        if (entityId == null || entityId.isNullUid()) {
//            entityId = tenantId;
//        }
//        BaseApiUsageState state = myUsageStates.get(entityId);
//        if (state != null) {
//            return state;
//        }
//
//        ApiUsageState storedState = apiUsageStateService.findApiUsageStateByEntityId(entityId);
//        if (storedState == null) {
//            try {
//                storedState = apiUsageStateService.createDefaultApiUsageState(tenantId, entityId);
//            } catch (Exception e) {
//                storedState = apiUsageStateService.findApiUsageStateByEntityId(entityId);
//            }
//        }
//        if (entityId.getEntityType() == EntityType.TENANT) {
//            if (!entityId.equals(TenantId.SYS_TENANT_ID)) {
//                state = new TenantApiUsageState(tenantProfileCache.get((TenantId) entityId), storedState);
//            } else {
//                state = new TenantApiUsageState(storedState);
//            }
//        } else {
//            state = new CustomerApiUsageState(storedState);
//        }
//
//        List<ApiUsageRecordKey> newCounts = new ArrayList<>();
//        try {
//            List<TsKvEntry> dbValues = tsService.findAllLatest(tenantId, storedState.getId()).get();
//            for (ApiUsageRecordKey key : ApiUsageRecordKey.values()) {
//                boolean cycleEntryFound = false;
//                boolean hourlyEntryFound = false;
//                for (TsKvEntry tsKvEntry : dbValues) {
//                    if (tsKvEntry.getKey().equals(key.getApiCountKey())) {
//                        cycleEntryFound = true;
//
//                        boolean oldCount = tsKvEntry.getTs() == state.getCurrentCycleTs();
//                        state.put(key, oldCount ? tsKvEntry.getLongValue().get() : 0L);
//
//                        if (!oldCount) {
//                            newCounts.add(key);
//                        }
//                    } else if (tsKvEntry.getKey().equals(key.getApiCountKey() + HOURLY)) {
//                        hourlyEntryFound = true;
//                        state.putHourly(key, tsKvEntry.getTs() == state.getCurrentHourTs() ? tsKvEntry.getLongValue().get() : 0L);
//                    }
//                    if (cycleEntryFound && hourlyEntryFound) {
//                        break;
//                    }
//                }
//            }
//            log.debug("[{}] Initialized state: {}", entityId, storedState);
//            myUsageStates.put(entityId, state);
//            saveNewCounts(state, newCounts);
//        } catch (InterruptedException | ExecutionException e) {
//            log.warn("[{}] Failed to fetch api usage state from db.", tenantId, e);
//        }
//
//        return state;
//    }
//
//    private void initStatesFromDataBase() {
//        try {
//            log.info("Initializing tenant states.");
//            updateLock.lock();
//            try {
//                ExecutorService tmpInitExecutor = ThingsBoardExecutors.newWorkStealingPool(20, "init-tenant-states-from-db");
//                try {
//                    PageDataIterable<Tenant> tenantIterator = new PageDataIterable<>(tenantService::findTenants, 1024);
//                    List<Future<?>> futures = new ArrayList<>();
//                    for (Tenant tenant : tenantIterator) {
//                        if (!myUsageStates.containsKey(tenant.getId()) && partitionService.resolve(ServiceType.TB_CORE, tenant.getId(), tenant.getId()).isMyPartition()) {
//                            log.debug("[{}] Initializing tenant state.", tenant.getId());
//                            futures.add(tmpInitExecutor.submit(() -> {
//                                try {
//                                    updateTenantState((TenantApiUsageState) getOrFetchState(tenant.getId(), tenant.getId()), tenantProfileCache.get(tenant.getTenantProfileId()));
//                                    log.debug("[{}] Initialized tenant state.", tenant.getId());
//                                } catch (Exception e) {
//                                    log.warn("[{}] Failed to initialize tenant API state", tenant.getId(), e);
//                                }
//                            }));
//                        }
//                    }
//                    for (Future<?> future : futures) {
//                        future.get();
//                    }
//                } finally {
//                    tmpInitExecutor.shutdownNow();
//                }
//            } finally {
//                updateLock.unlock();
//            }
//            log.info("Initialized tenant states.");
//        } catch (Exception e) {
//            log.warn("Unknown failure", e);
//        }
//    }

    @PreDestroy
    private void destroy() {
        if (mailExecutor != null) {
            mailExecutor.shutdownNow();
        }
    }
}
