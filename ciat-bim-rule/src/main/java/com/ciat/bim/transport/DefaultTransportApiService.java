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
package com.ciat.bim.transport;

import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.device.DeviceTransportType;
import com.ciat.bim.data.id.*;
import com.ciat.bim.data.security.DeviceCredentialsType;
import com.ciat.bim.msg.EntityType;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgDataType;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.server.apiusage.TbApiUsageStateService;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.ApiUsageState;
import com.ciat.bim.server.common.data.OtaPackageType;
import com.ciat.bim.server.common.data.ResourceType;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.common.data.relation.EntityRelation;
import com.ciat.bim.server.common.transport.util.DataDecodingEncodingService;
import com.ciat.bim.server.executors.DbCallbackExecutorService;
import com.ciat.bim.server.profile.TbDeviceProfileCache;
import com.ciat.bim.server.queue.common.TbProtoQueueMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.state.DeviceStateService;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import com.ciat.bim.server.utils.JacksonUtil;
import com.ciat.bim.tenant.TbTenantProfileCache;
import com.ciat.bim.transport.auth.BasicMqttCredentials;
import com.ciat.bim.transport.ssl.EncryptionUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.device.entity.Device;
import org.jeecg.modules.device.entity.DeviceCredentials;
import org.jeecg.modules.device.entity.DeviceProfile;
import org.jeecg.modules.device.service.IDeviceCredentialsService;
import org.jeecg.modules.device.service.IDeviceService;
import org.jeecg.modules.resource.entity.TbResource;
import org.jeecg.modules.resource.service.ITbResourceService;
import org.jeecg.modules.tenant.entity.TenantProfile;
import org.springframework.stereotype.Service;


import javax.management.relation.RelationService;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import static com.ciat.bim.transport.BasicCredentialsValidationResult.VALID;
import static com.ciat.bim.transport.BasicCredentialsValidationResult.PASSWORD_MISMATCH;

/**
 * Created by ashvayka on 05.10.18.
 */
@Slf4j
@Service
@TbCoreComponent
@RequiredArgsConstructor
public class DefaultTransportApiService implements TransportApiService {

    private static final ObjectMapper mapper = new ObjectMapper();

    private final TbDeviceProfileCache deviceProfileCache;
    private final TbTenantProfileCache tenantProfileCache;
    private final TbApiUsageStateService apiUsageStateService;
    private final IDeviceService deviceService;
   // private final RelationService relationService;
    private final IDeviceCredentialsService deviceCredentialsService;
    private final DeviceStateService deviceStateService;
    private final DbCallbackExecutorService dbCallbackExecutorService;
    private final TbClusterService tbClusterService;
    private final DataDecodingEncodingService dataDecodingEncodingService;
   // private final DeviceProvisionService deviceProvisionService;
    private final ITbResourceService resourceService;
  //  private final OtaPackageService otaPackageService;
  //  private final OtaPackageDataCache otaPackageDataCache;

    private final ConcurrentMap<String, ReentrantLock> deviceCreationLocks = new ConcurrentHashMap<>();

    @Override
    public ListenableFuture<TbProtoQueueMsg<TransportApiResponseMsg>> handle(TbProtoQueueMsg<TransportApiRequestMsg> tbProtoQueueMsg) {
        TransportApiRequestMsg transportApiRequestMsg = tbProtoQueueMsg.getValue();
        ListenableFuture<TransportApiResponseMsg> result = null;

        if (transportApiRequestMsg.hasValidateTokenRequestMsg()) {
            ValidateDeviceTokenRequestMsg msg = transportApiRequestMsg.getValidateTokenRequestMsg();
            result = validateCredentials(msg.getToken(), DeviceCredentialsType.ACCESSTOKEN);
        }
        else if (transportApiRequestMsg.hasValidateBasicMqttCredRequestMsg()) {
            TransportProtos.ValidateBasicMqttCredRequestMsg msg = transportApiRequestMsg.getValidateBasicMqttCredRequestMsg();
            result = validateCredentials(msg);
        }
//        else if (transportApiRequestMsg.hasValidateX509CertRequestMsg()) {
//            ValidateDeviceX509CertRequestMsg msg = transportApiRequestMsg.getValidateX509CertRequestMsg();
//            result = validateCredentials(msg.getHash(), DeviceCredentialsType.X509_CERTIFICATE);
//        } else if (transportApiRequestMsg.hasGetOrCreateDeviceRequestMsg()) {
//            result = handle(transportApiRequestMsg.getGetOrCreateDeviceRequestMsg());
//        } else if (transportApiRequestMsg.hasEntityProfileRequestMsg()) {
//            result = handle(transportApiRequestMsg.getEntityProfileRequestMsg());
//        } else if (transportApiRequestMsg.hasLwM2MRequestMsg()) {
//            result = handle(transportApiRequestMsg.getLwM2MRequestMsg());
//        } else if (transportApiRequestMsg.hasValidateDeviceLwM2MCredentialsRequestMsg()) {
//            ValidateDeviceLwM2MCredentialsRequestMsg msg = transportApiRequestMsg.getValidateDeviceLwM2MCredentialsRequestMsg();
//            result = validateCredentials(msg.getCredentialsId(), DeviceCredentialsType.LWM2M_CREDENTIALS);
//        } else if (transportApiRequestMsg.hasProvisionDeviceRequestMsg()) {
//            result = handle(transportApiRequestMsg.getProvisionDeviceRequestMsg());
//        } else if (transportApiRequestMsg.hasResourceRequestMsg()) {
//            result = handle(transportApiRequestMsg.getResourceRequestMsg());
//        } else if (transportApiRequestMsg.hasSnmpDevicesRequestMsg()) {
//            result = handle(transportApiRequestMsg.getSnmpDevicesRequestMsg());
//        } else if (transportApiRequestMsg.hasDeviceRequestMsg()) {
//            result = handle(transportApiRequestMsg.getDeviceRequestMsg());
//        }
        else if (transportApiRequestMsg.hasDeviceCredentialsRequestMsg()) {
            result = handle(transportApiRequestMsg.getDeviceCredentialsRequestMsg());
        }
 //       else if (transportApiRequestMsg.hasOtaPackageRequestMsg()) {
//            result = handle(transportApiRequestMsg.getOtaPackageRequestMsg());
//        }

        return Futures.transform(Optional.ofNullable(result).orElseGet(this::getEmptyTransportApiResponseFuture),
                value -> new TbProtoQueueMsg<>(tbProtoQueueMsg.getKey(), value, tbProtoQueueMsg.getHeaders()),
                MoreExecutors.directExecutor());
    }

    private ListenableFuture<TransportApiResponseMsg> validateCredentials(String credentialsId, DeviceCredentialsType credentialsType) {
        //TODO: Make async and enable caching
        DeviceCredentials credentials = deviceCredentialsService.getById(credentialsId);
        if (credentials != null && credentials.getDeviceCredentialsType() == credentialsType) {
            return getDeviceInfo(credentials);
        } else {
            return getEmptyTransportApiResponseFuture();
        }
    }

    /**
     * 验证链接
     * @param mqtt
     * @return
     */
    private ListenableFuture<TransportApiResponseMsg> validateCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg mqtt) {
        DeviceCredentials credentials;
        if (StringUtils.isEmpty(mqtt.getUserName())) {
            credentials = checkMqttCredentials(mqtt, EncryptionUtil.getSha3Hash(mqtt.getClientId()));
            if (credentials != null) {
                return getDeviceInfo(credentials);
            } else {
                return getEmptyTransportApiResponseFuture();
            }
        } else {
//            credentials = deviceCredentialsService.getById(
//                    EncryptionUtil.getSha3Hash("|", mqtt.getClientId(), mqtt.getUserName()));
            credentials = deviceCredentialsService.findDeviceCredentialsByDeviceId(mqtt.getClientId());
            if (checkIsMqttCredentials(credentials)) {
                BasicCredentialsValidationResult validationResult = validateMqttCredentials(mqtt, credentials);
                if (VALID.equals(validationResult)) {
                    return getDeviceInfo(credentials);
                } else if (PASSWORD_MISMATCH.equals(validationResult)) {
                    return getEmptyTransportApiResponseFuture();
                } else {
                    return validateUserNameCredentials(mqtt);
                }
            } else {
                return validateUserNameCredentials(mqtt);
            }
        }
    }

    private ListenableFuture<TransportApiResponseMsg> validateUserNameCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg mqtt) {
        DeviceCredentials credentials = deviceCredentialsService.getById(mqtt.getUserName());
        if (credentials != null) {
            switch (credentials.getDeviceCredentialsType()) {
                case ACCESSTOKEN:
                    return getDeviceInfo(credentials);
                case MQTTBASIC:
                    if (VALID.equals(validateMqttCredentials(mqtt, credentials))) {
                        return getDeviceInfo(credentials);
                    } else {
                        return getEmptyTransportApiResponseFuture();
                    }
            }
        }
        return getEmptyTransportApiResponseFuture();
    }

    private static boolean checkIsMqttCredentials(DeviceCredentials credentials) {
        return credentials != null && DeviceCredentialsType.MQTTBASIC.equals(credentials.getDeviceCredentialsType());
    }

    private DeviceCredentials checkMqttCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg clientCred, String credId) {
        return checkMqttCredentials(clientCred, deviceCredentialsService.getById(credId));
    }

    private DeviceCredentials checkMqttCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg clientCred, DeviceCredentials deviceCredentials) {
        if (deviceCredentials != null && deviceCredentials.getDeviceCredentialsType() == DeviceCredentialsType.MQTTBASIC) {
            if (VALID.equals(validateMqttCredentials(clientCred, deviceCredentials))) {
                return deviceCredentials;
            }
        }
        return null;
    }

    private BasicCredentialsValidationResult validateMqttCredentials(TransportProtos.ValidateBasicMqttCredRequestMsg clientCred, DeviceCredentials deviceCredentials) {
        BasicMqttCredentials dbCred = JacksonUtil.fromString(deviceCredentials.getCredentialsValue(), BasicMqttCredentials.class);
        if (!StringUtils.isEmpty(dbCred.getClientId()) && !dbCred.getClientId().equals(clientCred.getClientId())) {
            return BasicCredentialsValidationResult.HASH_MISMATCH;
        }
        if (!StringUtils.isEmpty(dbCred.getUserName()) && !dbCred.getUserName().equals(clientCred.getUserName())) {
            return BasicCredentialsValidationResult.HASH_MISMATCH;
        }
        if (!StringUtils.isEmpty(dbCred.getPassword())) {
            if (StringUtils.isEmpty(clientCred.getPassword())) {
                return BasicCredentialsValidationResult.PASSWORD_MISMATCH;
            } else {
                return dbCred.getPassword().equals(clientCred.getPassword()) ? VALID : BasicCredentialsValidationResult.PASSWORD_MISMATCH;
            }
        }
        return VALID;
    }

//    private ListenableFuture<TransportApiResponseMsg> handle(GetOrCreateDeviceFromGatewayRequestMsg requestMsg) {
//        DeviceId gatewayId = new DeviceId(new UUID(requestMsg.getGatewayIdMSB(), requestMsg.getGatewayIdLSB()));
//        ListenableFuture<Device> gatewayFuture = deviceService.findDeviceByIdAsync(TenantId.SYS_TENANT_ID, gatewayId);
//        return Futures.transform(gatewayFuture, gateway -> {
//            Lock deviceCreationLock = deviceCreationLocks.computeIfAbsent(requestMsg.getDeviceName(), id -> new ReentrantLock());
//            deviceCreationLock.lock();
//            try {
//                Device device = deviceService.findDeviceByTenantIdAndName(gateway.getTenantId(), requestMsg.getDeviceName());
//                if (device == null) {
//                    TenantId tenantId = gateway.getTenantId();
//                    device = new Device();
//                    device.setTenantId(tenantId);
//                    device.setName(requestMsg.getDeviceName());
//                    device.setType(requestMsg.getDeviceType());
//                    device.setCustomerId(gateway.getCustomerId());
//                    DeviceProfile deviceProfile = deviceProfileCache.findOrCreateDeviceProfile(gateway.getTenantId(), requestMsg.getDeviceType());
//                    device.setDeviceProfileId(deviceProfile.getId());
//                    Device savedDevice = deviceService.saveDevice(device);
//                    tbClusterService.onDeviceUpdated(savedDevice, null);
//                    device = savedDevice;
//
//                    relationService.saveRelationAsync(TenantId.SYS_TENANT_ID, new EntityRelation(gateway.getId(), device.getId(), "Created"));
//
//                    TbMsgMetaData metaData = new TbMsgMetaData();
//                    CustomerId customerId = gateway.getCustomerId();
//                    if (customerId != null && !customerId.isNullUid()) {
//                        metaData.putValue("customerId", customerId.toString());
//                    }
//                    metaData.putValue("gatewayId", gatewayId.toString());
//
//                    DeviceId deviceId = device.getId();
//                    ObjectNode entityNode = mapper.valueToTree(device);
//                    TbMsg tbMsg = TbMsg.newMsg(DataConstants.ENTITY_CREATED, deviceId, customerId, metaData, TbMsgDataType.JSON, mapper.writeValueAsString(entityNode));
//                    tbClusterService.pushMsgToRuleEngine(tenantId, deviceId, tbMsg, null);
//                }
//                GetOrCreateDeviceFromGatewayResponseMsg.Builder builder = GetOrCreateDeviceFromGatewayResponseMsg.newBuilder()
//                        .setDeviceInfo(getDeviceInfoProto(device));
//                DeviceProfile deviceProfile = deviceProfileCache.get(device.getTenantId(), device.getDeviceProfileId());
//                if (deviceProfile != null) {
//                    builder.setProfileBody(ByteString.copyFrom(dataDecodingEncodingService.encode(deviceProfile)));
//                } else {
//                    log.warn("[{}] Failed to find device profile [{}] for device. ", device.getId(), device.getDeviceProfileId());
//                }
//                return TransportApiResponseMsg.newBuilder()
//                        .setGetOrCreateDeviceResponseMsg(builder.build())
//                        .build();
//            } catch (JsonProcessingException e) {
//                log.warn("[{}] Failed to lookup device by gateway id and name: [{}]", gatewayId, requestMsg.getDeviceName(), e);
//                throw new RuntimeException(e);
//            } finally {
//                deviceCreationLock.unlock();
//            }
//        }, dbCallbackExecutorService);
//    }
//
//    private ListenableFuture<TransportApiResponseMsg> handle(ProvisionDeviceRequestMsg requestMsg) {
//        ListenableFuture<ProvisionResponse> provisionResponseFuture = null;
//        try {
//            provisionResponseFuture = Futures.immediateFuture(deviceProvisionService.provisionDevice(
//                    new ProvisionRequest(
//                            requestMsg.getDeviceName(),
//                            requestMsg.getCredentialsType() != null ? DeviceCredentialsType.valueOf(requestMsg.getCredentialsType().name()) : null,
//                            new ProvisionDeviceCredentialsData(requestMsg.getCredentialsDataProto().getValidateDeviceTokenRequestMsg().getToken(),
//                                    requestMsg.getCredentialsDataProto().getValidateBasicMqttCredRequestMsg().getClientId(),
//                                    requestMsg.getCredentialsDataProto().getValidateBasicMqttCredRequestMsg().getUserName(),
//                                    requestMsg.getCredentialsDataProto().getValidateBasicMqttCredRequestMsg().getPassword(),
//                                    requestMsg.getCredentialsDataProto().getValidateDeviceX509CertRequestMsg().getHash()),
//                            new ProvisionDeviceProfileCredentials(
//                                    requestMsg.getProvisionDeviceCredentialsMsg().getProvisionDeviceKey(),
//                                    requestMsg.getProvisionDeviceCredentialsMsg().getProvisionDeviceSecret()))));
//        } catch (ProvisionFailedException e) {
//            return Futures.immediateFuture(getTransportApiResponseMsg(
//                    new DeviceCredentials(),
//                    TransportProtos.ResponseStatus.valueOf(e.getMessage())));
//        }
//        return Futures.transform(provisionResponseFuture, provisionResponse -> getTransportApiResponseMsg(provisionResponse.getDeviceCredentials(), TransportProtos.ResponseStatus.SUCCESS),
//                dbCallbackExecutorService);
//    }
//
//    private TransportApiResponseMsg getTransportApiResponseMsg(DeviceCredentials deviceCredentials, TransportProtos.ResponseStatus status) {
//        if (!status.equals(TransportProtos.ResponseStatus.SUCCESS)) {
//            return TransportApiResponseMsg.newBuilder().setProvisionDeviceResponseMsg(TransportProtos.ProvisionDeviceResponseMsg.newBuilder().setStatus(status).build()).build();
//        }
//        TransportProtos.ProvisionDeviceResponseMsg.Builder provisionResponse = TransportProtos.ProvisionDeviceResponseMsg.newBuilder()
//                .setCredentialsType(TransportProtos.CredentialsType.valueOf(deviceCredentials.getCredentialsType().name()))
//                .setStatus(status);
//        switch (deviceCredentials.getCredentialsType()) {
//            case ACCESS_TOKEN:
//                provisionResponse.setCredentialsValue(deviceCredentials.getCredentialsId());
//                break;
//            case MQTT_BASIC:
//            case X509_CERTIFICATE:
//            case LWM2M_CREDENTIALS:
//                provisionResponse.setCredentialsValue(deviceCredentials.getCredentialsValue());
//                break;
//        }
//
//        return TransportApiResponseMsg.newBuilder()
//                .setProvisionDeviceResponseMsg(provisionResponse.build())
//                .build();
//    }
//
//    private ListenableFuture<TransportApiResponseMsg> handle(GetEntityProfileRequestMsg requestMsg) {
//        EntityType entityType = EntityType.valueOf(requestMsg.getEntityType());
//        UUID entityUuid = new UUID(requestMsg.getEntityIdMSB(), requestMsg.getEntityIdLSB());
//        GetEntityProfileResponseMsg.Builder builder = GetEntityProfileResponseMsg.newBuilder();
//        if (entityType.equals(EntityType.DEVICE_PROFILE)) {
//            DeviceProfileId deviceProfileId = new DeviceProfileId(entityUuid);
//            DeviceProfile deviceProfile = deviceProfileCache.find(deviceProfileId);
//            builder.setData(ByteString.copyFrom(dataDecodingEncodingService.encode(deviceProfile)));
//        } else if (entityType.equals(EntityType.TENANT)) {
//            TenantId tenantId = new TenantId(entityUuid);
//            TenantProfile tenantProfile = tenantProfileCache.get(tenantId);
//            ApiUsageState state = apiUsageStateService.getApiUsageState(tenantId);
//            builder.setData(ByteString.copyFrom(dataDecodingEncodingService.encode(tenantProfile)));
//            builder.setApiState(ByteString.copyFrom(dataDecodingEncodingService.encode(state)));
//        } else {
//            throw new RuntimeException("Invalid entity profile request: " + entityType);
//        }
//        return Futures.immediateFuture(TransportApiResponseMsg.newBuilder().setEntityProfileResponseMsg(builder).build());
//    }
//
//    private ListenableFuture<TransportApiResponseMsg> handle(GetDeviceRequestMsg requestMsg) {
//        DeviceId deviceId = new DeviceId(new UUID(requestMsg.getDeviceIdMSB(), requestMsg.getDeviceIdLSB()));
//        Device device = deviceService.findDeviceById(TenantId.SYS_TENANT_ID, deviceId);
//
//        TransportApiResponseMsg responseMsg;
//        if (device != null) {
//            UUID deviceProfileId = device.getDeviceProfileId().getId();
//            responseMsg = TransportApiResponseMsg.newBuilder()
//                    .setDeviceResponseMsg(TransportProtos.GetDeviceResponseMsg.newBuilder()
//                            .setDeviceProfileIdMSB(deviceProfileId.getMostSignificantBits())
//                            .setDeviceProfileIdLSB(deviceProfileId.getLeastSignificantBits())
//                            .setDeviceTransportConfiguration(ByteString.copyFrom(
//                                    dataDecodingEncodingService.encode(device.getDeviceData().getTransportConfiguration())
//                            )))
//                    .build();
//        } else {
//            responseMsg = TransportApiResponseMsg.getDefaultInstance();
//        }
//
//        return Futures.immediateFuture(responseMsg);
//    }

    private ListenableFuture<TransportApiResponseMsg> handle(GetDeviceCredentialsRequestMsg requestMsg) {
        String deviceId = requestMsg.getDeviceIdMSB()+"";
        DeviceCredentials deviceCredentials = deviceCredentialsService.findDeviceCredentialsByDeviceId(deviceId);

        return Futures.immediateFuture(TransportApiResponseMsg.newBuilder()
                .setDeviceCredentialsResponseMsg(TransportProtos.GetDeviceCredentialsResponseMsg.newBuilder()
                        .setDeviceCredentialsData(ByteString.copyFrom(dataDecodingEncodingService.encode(deviceCredentials))))
                .build());
    }


//    private ListenableFuture<TransportApiResponseMsg> handle(GetResourceRequestMsg requestMsg) {
//        TenantId tenantId = new TenantId(new UUID(requestMsg.getTenantIdMSB(), requestMsg.getTenantIdLSB()));
//        ResourceType resourceType = ResourceType.valueOf(requestMsg.getResourceType());
//        String resourceKey = requestMsg.getResourceKey();
//        TransportProtos.GetResourceResponseMsg.Builder builder = TransportProtos.GetResourceResponseMsg.newBuilder();
//        TbResource resource = resourceService.getResource(tenantId, resourceType, resourceKey);
//
//        if (resource == null && !tenantId.equals(TenantId.SYS_TENANT_ID)) {
//            resource = resourceService.getResource(TenantId.SYS_TENANT_ID, resourceType, resourceKey);
//        }
//
//        if (resource != null) {
//            builder.setResource(ByteString.copyFrom(dataDecodingEncodingService.encode(resource)));
//        }
//
//        return Futures.immediateFuture(TransportApiResponseMsg.newBuilder().setResourceResponseMsg(builder).build());
//    }
//
//    private ListenableFuture<TransportApiResponseMsg> handle(GetSnmpDevicesRequestMsg requestMsg) {
//        PageLink pageLink = new PageLink(requestMsg.getPageSize(), requestMsg.getPage());
//        PageData<UUID> result = deviceService.findDevicesIdsByDeviceProfileTransportType(DeviceTransportType.SNMP, pageLink);
//
//        GetSnmpDevicesResponseMsg responseMsg = GetSnmpDevicesResponseMsg.newBuilder()
//                .addAllIds(result.getData().stream()
//                        .map(UUID::toString)
//                        .collect(Collectors.toList()))
//                .setHasNextPage(result.hasNext())
//                .build();
//
//        return Futures.immediateFuture(TransportApiResponseMsg.newBuilder()
//                .setSnmpDevicesResponseMsg(responseMsg)
//                .build());
//    }
//
    private ListenableFuture<TransportApiResponseMsg> getDeviceInfo(DeviceCredentials credentials) {
        return Futures.transform(deviceService.getByIdFuture(credentials.getDeviceId()), device -> {
            if (device == null) {
                log.trace("[{}] Failed to lookup device by id", credentials.getDeviceId());
                return getEmptyTransportApiResponse();
            }
            try {
                ValidateDeviceCredentialsResponseMsg.Builder builder = ValidateDeviceCredentialsResponseMsg.newBuilder();
                builder.setDeviceInfo(getDeviceInfoProto(device));
                DeviceProfile deviceProfile = deviceProfileCache.get(device.getTenantId(),DeviceProfileId.fromString(device.getDeviceProfileId()));
                if (deviceProfile != null) {
                    builder.setProfileBody(ByteString.copyFrom(dataDecodingEncodingService.encode(deviceProfile)));
                } else {
                    log.warn("[{}] Failed to find device profile [{}] for device. ", device.getId(), device.getDeviceProfileId());
                }
                if (!StringUtils.isEmpty(credentials.getCredentialsValue())) {
                    builder.setCredentialsBody(credentials.getCredentialsValue());
                }
                return TransportApiResponseMsg.newBuilder()
                        .setValidateCredResponseMsg(builder.build()).build();
            } catch (JsonProcessingException e) {
                log.warn("[{}] Failed to lookup device by id", credentials.getDeviceId(), e);
                return getEmptyTransportApiResponse();
            }
        }, MoreExecutors.directExecutor());
    }

    private DeviceInfoProto getDeviceInfoProto(Device device) throws JsonProcessingException {
        DeviceInfoProto.Builder builder = DeviceInfoProto.newBuilder()
                .setTenantIdMSB(Long.parseLong(device.getTenantId()))
                .setTenantIdLSB(Long.parseLong(device.getTenantId()))
                .setCustomerIdMSB(Optional.ofNullable(device.getCustomerId()).map(customerId -> Long.parseLong(customerId)).orElse(0L))
                .setCustomerIdLSB(Optional.ofNullable(device.getCustomerId()).map(customerId -> Long.parseLong(customerId)).orElse(0L))
                .setDeviceIdMSB(Long.parseLong(device.getId()))
                .setDeviceIdLSB(Long.parseLong(device.getId()))
                .setDeviceName(device.getName())
                .setDeviceType(device.getType())
                .setDeviceProfileIdMSB(Long.parseLong(device.getDeviceProfileId()))
                .setDeviceProfileIdLSB(Long.parseLong(device.getDeviceProfileId()))
                .setAdditionalInfo(mapper.writeValueAsString(device.getAdditionalInfo()));

//        PowerSavingConfiguration psmConfiguration = null;
//        switch (device.getDeviceData().getTransportConfiguration().getType()) {
//            case LWM2M:
//                psmConfiguration = (Lwm2mDeviceTransportConfiguration) device.getDeviceData().getTransportConfiguration();
//                break;
//            case COAP:
//                psmConfiguration = (CoapDeviceTransportConfiguration) device.getDeviceData().getTransportConfiguration();
//                break;
//        }

//        if (psmConfiguration != null) {
//            PowerMode powerMode = psmConfiguration.getPowerMode();
//            if (powerMode != null) {
//                builder.setPowerMode(powerMode.name());
//                if (powerMode.equals(PowerMode.PSM)) {
//                    builder.setPsmActivityTimer(checkLong(psmConfiguration.getPsmActivityTimer()));
//                } else if (powerMode.equals(PowerMode.E_DRX)) {
//                    builder.setEdrxCycle(checkLong(psmConfiguration.getEdrxCycle()));
//                    builder.setPagingTransmissionWindow(checkLong(psmConfiguration.getPagingTransmissionWindow()));
//                }
//            }
//        }
        return builder.build();
    }

    private ListenableFuture<TransportApiResponseMsg> getEmptyTransportApiResponseFuture() {
        return Futures.immediateFuture(getEmptyTransportApiResponse());
    }

    private TransportApiResponseMsg getEmptyTransportApiResponse() {
        return TransportApiResponseMsg.newBuilder()
                .setValidateCredResponseMsg(ValidateDeviceCredentialsResponseMsg.getDefaultInstance()).build();
    }

//    private ListenableFuture<TransportApiResponseMsg> handle(TransportProtos.LwM2MRequestMsg requestMsg) {
//        if (requestMsg.hasRegistrationMsg()) {
//            return handleRegistration(requestMsg.getRegistrationMsg());
//        } else {
//            return Futures.immediateFailedFuture(new RuntimeException("Not supported!"));
//        }
//    }
//
//    private ListenableFuture<TransportApiResponseMsg> handle(TransportProtos.GetOtaPackageRequestMsg requestMsg) {
//        TenantId tenantId = new TenantId(new UUID(requestMsg.getTenantIdMSB(), requestMsg.getTenantIdLSB()));
//        DeviceId deviceId = new DeviceId(new UUID(requestMsg.getDeviceIdMSB(), requestMsg.getDeviceIdLSB()));
//        OtaPackageType otaPackageType = OtaPackageType.valueOf(requestMsg.getType());
//        Device device = deviceService.findDeviceById(tenantId, deviceId);
//
//        if (device == null) {
//            return getEmptyTransportApiResponseFuture();
//        }
//
//        OtaPackageId otaPackageId = OtaPackageUtil.getOtaPackageId(device, otaPackageType);
//        if (otaPackageId == null) {
//            DeviceProfile deviceProfile = deviceProfileCache.find(device.getDeviceProfileId());
//            otaPackageId = OtaPackageUtil.getOtaPackageId(deviceProfile, otaPackageType);
//        }
//
//        TransportProtos.GetOtaPackageResponseMsg.Builder builder = TransportProtos.GetOtaPackageResponseMsg.newBuilder();
//
//        if (otaPackageId == null) {
//            builder.setResponseStatus(TransportProtos.ResponseStatus.NOT_FOUND);
//        } else {
//            OtaPackageInfo otaPackageInfo = otaPackageService.findOtaPackageInfoById(tenantId, otaPackageId);
//
//            if (otaPackageInfo == null) {
//                builder.setResponseStatus(TransportProtos.ResponseStatus.NOT_FOUND);
//            } else if (otaPackageInfo.hasUrl()) {
//                builder.setResponseStatus(TransportProtos.ResponseStatus.FAILURE);
//                log.trace("[{}] Can`t send OtaPackage with URL data!", otaPackageInfo.getId());
//            } else {
//                builder.setResponseStatus(TransportProtos.ResponseStatus.SUCCESS);
//                builder.setOtaPackageIdMSB(otaPackageId.getId().getMostSignificantBits());
//                builder.setOtaPackageIdLSB(otaPackageId.getId().getLeastSignificantBits());
//                builder.setType(otaPackageInfo.getType().name());
//                builder.setTitle(otaPackageInfo.getTitle());
//                builder.setVersion(otaPackageInfo.getVersion());
//                builder.setFileName(otaPackageInfo.getFileName());
//                builder.setContentType(otaPackageInfo.getContentType());
//                if (!otaPackageDataCache.has(otaPackageId.toString())) {
//                    OtaPackage otaPackage = otaPackageService.findOtaPackageById(tenantId, otaPackageId);
//                    otaPackageDataCache.put(otaPackageId.toString(), otaPackage.getData().array());
//                }
//            }
//        }
//
//        return Futures.immediateFuture(
//                TransportApiResponseMsg.newBuilder()
//                        .setOtaPackageResponseMsg(builder.build())
//                        .build());
//    }
//
//    private ListenableFuture<TransportApiResponseMsg> handleRegistration(TransportProtos.LwM2MRegistrationRequestMsg msg) {
//        TenantId tenantId = new TenantId(UUID.fromString(msg.getTenantId()));
//        String deviceName = msg.getEndpoint();
//        Lock deviceCreationLock = deviceCreationLocks.computeIfAbsent(deviceName, id -> new ReentrantLock());
//        deviceCreationLock.lock();
//        try {
//            Device device = deviceService.findDeviceByTenantIdAndName(tenantId, deviceName);
//            if (device == null) {
//                device = new Device();
//                device.setTenantId(tenantId);
//                device.setName(deviceName);
//                device.setType("LwM2M");
//                device = deviceService.saveDevice(device);
//                tbClusterService.onDeviceUpdated(device, null);
//            }
//            TransportProtos.LwM2MRegistrationResponseMsg registrationResponseMsg =
//                    TransportProtos.LwM2MRegistrationResponseMsg.newBuilder()
//                            .setDeviceInfo(getDeviceInfoProto(device)).build();
//            TransportProtos.LwM2MResponseMsg responseMsg = TransportProtos.LwM2MResponseMsg.newBuilder().setRegistrationMsg(registrationResponseMsg).build();
//            return Futures.immediateFuture(TransportApiResponseMsg.newBuilder().setLwM2MResponseMsg(responseMsg).build());
//        } catch (JsonProcessingException e) {
//            log.warn("[{}][{}] Failed to lookup device by gateway id and name", tenantId, deviceName, e);
//            throw new RuntimeException(e);
//        } finally {
//            deviceCreationLock.unlock();
//        }
//    }
//
//    private Long checkLong(Long l) {
//        return l != null ? l : 0;
//    }
}
