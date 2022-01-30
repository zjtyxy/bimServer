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
package com.ciat.bim.server.usagerecord;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.server.common.data.*;
import com.ciat.bim.server.exception.DataValidationException;
import com.ciat.bim.server.timeseries.TimeseriesService;
import com.ciat.bim.server.utils.DataValidator;
import com.ciat.bim.tenant.TenantProfileConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.tenant.entity.Tenant;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.jeecg.modules.tenant.service.ITenantProfileService;
import org.jeecg.modules.tenant.service.ITenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import static  com.ciat.bim.server.utils.Validator.validateId;


@Service
@Slf4j
public class ApiUsageStateServiceImpl  implements ApiUsageStateService {
    public static final String INCORRECT_TENANT_ID = "Incorrect tenantId ";

//    private final ApiUsageStateDao apiUsageStateDao;
       @Autowired
      private  ITenantProfileService tenantProfileDao;
      @Autowired
      private ITenantService tenantDao;
//    private final TimeseriesService tsService;
//
//    public ApiUsageStateServiceImpl(TenantDao tenantDao, ApiUsageStateDao apiUsageStateDao, TenantProfileDao tenantProfileDao, TimeseriesService tsService) {
//        this.tenantDao = tenantDao;
//        this.apiUsageStateDao = apiUsageStateDao;
//        this.tenantProfileDao = tenantProfileDao;
//        this.tsService = tsService;
//    }
//
//    @Override
//    public void deleteApiUsageStateByTenantId(TenantId tenantId) {
//        log.trace("Executing deleteUsageRecordsByTenantId [{}]", tenantId);
//        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
//        apiUsageStateDao.deleteApiUsageStateByTenantId(tenantId);
//    }
//
//    @Override
//    public void deleteApiUsageStateByEntityId(EntityId entityId) {
//        log.trace("Executing deleteApiUsageStateByEntityId [{}]", entityId);
//        validateId(entityId.getId(), "Invalid entity id");
//        apiUsageStateDao.deleteApiUsageStateByEntityId(entityId);
//    }
//
    @Override
    public ApiUsageState createDefaultApiUsageState(TenantId tenantId, EntityId entityId) {
        entityId = Objects.requireNonNullElse(entityId, tenantId);
        log.trace("Executing createDefaultUsageRecord [{}]", entityId);
        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
        ApiUsageState apiUsageState = new ApiUsageState();
        apiUsageState.setTenantId(tenantId);
        apiUsageState.setEntityId(entityId);
        apiUsageState.setTransportState(ApiUsageStateValue.ENABLED);
        apiUsageState.setReExecState(ApiUsageStateValue.ENABLED);
        apiUsageState.setJsExecState(ApiUsageStateValue.ENABLED);
        apiUsageState.setDbStorageState(ApiUsageStateValue.ENABLED);
        apiUsageState.setSmsExecState(ApiUsageStateValue.ENABLED);
        apiUsageState.setEmailExecState(ApiUsageStateValue.ENABLED);
        apiUsageState.setAlarmExecState(ApiUsageStateValue.ENABLED);
        apiUsageStateValidator.validate(apiUsageState, ApiUsageState::getTenantId);

      //  ApiUsageState saved = apiUsageStateDao.save(apiUsageState.getTenantId(), apiUsageState);

//        List<TsKvEntry> apiUsageStates = new ArrayList<>();
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.TRANSPORT.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.DB.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.RE.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.JS.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.EMAIL.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.SMS.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        apiUsageStates.add(new BasicTsKvEntry(saved.getCreatedTime(),
//                new StringDataEntry(ApiFeature.ALARM.getApiStateKey(), ApiUsageStateValue.ENABLED.name())));
//        tsService.save(tenantId, saved.getId(), apiUsageStates, 0L);

        if (entityId.getEntityType() == EntityType.TENANT && !entityId.equals(TenantId.SYS_TENANT_ID)) {
            tenantId = (TenantId) entityId;
            Tenant tenant = tenantDao.getById(tenantId.getId());
            TenantProfile tenantProfile = tenantProfileDao.getById( tenant.getTenantProfileId());
            TenantProfileConfiguration configuration = tenantProfile.fetchProfileData().getConfiguration();

            List<AttributeKv> profileThresholds = new ArrayList<>();
//            for (ApiUsageRecordKey key : ApiUsageRecordKey.values()) {
//                profileThresholds.add(new AttributeKv(saved.getCreatedTime(), new LongDataEntry(key.getApiLimitKey(), configuration.getProfileThreshold(key))));
//            }
            //tsService.save(tenantId, saved.getId(), profileThresholds, 0L);
        }

        return apiUsageState;
    }

//    @Override
//    public ApiUsageState update(ApiUsageState apiUsageState) {
//        log.trace("Executing save [{}]", apiUsageState.getTenantId());
//        validateId(apiUsageState.getTenantId(), INCORRECT_TENANT_ID + apiUsageState.getTenantId());
//        validateId(apiUsageState.getId(), "Can't save new usage state. Only update is allowed!");
//        return apiUsageStateDao.save(apiUsageState.getTenantId(), apiUsageState);
//    }
//
    @Override
    public ApiUsageState findTenantApiUsageState(TenantId tenantId) {
        log.trace("Executing findTenantUsageRecord, tenantId [{}]", tenantId);
        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
       // return apiUsageStateDao.findTenantApiUsageState(tenantId.getId());
        return this.createDefaultApiUsageState(tenantId,tenantId);
    }
//
//    @Override
//    public ApiUsageState findApiUsageStateByEntityId(EntityId entityId) {
//        validateId(entityId.getId(), "Invalid entity id");
//        return apiUsageStateDao.findApiUsageStateByEntityId(entityId);
//    }
//
//    @Override
//    public ApiUsageState findApiUsageStateById(TenantId tenantId, ApiUsageStateId id) {
//        log.trace("Executing findApiUsageStateById, tenantId [{}], apiUsageStateId [{}]", tenantId, id);
//        validateId(tenantId, INCORRECT_TENANT_ID + tenantId);
//        validateId(id, "Incorrect apiUsageStateId " + id);
//        return apiUsageStateDao.findById(tenantId, id.getId());
//    }
//
    private DataValidator<ApiUsageState> apiUsageStateValidator =
            new DataValidator<ApiUsageState>() {
                @Override
                protected void validateDataImpl(TenantId requestTenantId, ApiUsageState apiUsageState) {
                    if (apiUsageState.getTenantId() == null) {
                        throw new DataValidationException("ApiUsageState should be assigned to tenant!");
                    } else {
                        Tenant tenant = tenantDao.getById(apiUsageState.getTenantId().getId());
                        if (tenant == null && !requestTenantId.equals(TenantId.SYS_TENANT_ID)) {
                            throw new DataValidationException("ApiUsageState is referencing to non-existent tenant!");
                        }
                    }
                    if (apiUsageState.getEntityId() == null) {
                        throw new DataValidationException("UsageRecord should be assigned to entity!");
                    } else if (apiUsageState.getEntityId().getEntityType() != EntityType.TENANT && apiUsageState.getEntityId().getEntityType() != EntityType.CUSTOMER) {
                        throw new DataValidationException("Only Tenant and Customer Usage Records are supported!");
                    }
                }
            };

}
