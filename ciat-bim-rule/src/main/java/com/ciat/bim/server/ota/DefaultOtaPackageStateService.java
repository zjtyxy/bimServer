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
package com.ciat.bim.server.ota;

import com.ciat.bim.data.device.profile.DeviceProfile;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.transport.TransportProtos.ToOtaPackageStateServiceMsg;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.device.entity.Device;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DefaultOtaPackageStateService implements OtaPackageStateService {

//    private final TbClusterService tbClusterService;
//    private final OtaPackageService otaPackageService;
//    private final DeviceService deviceService;
//    private final DeviceProfileService deviceProfileService;
//    private final RuleEngineTelemetryService telemetryService;
//    private final TbQueueProducer<TbProtoQueueMsg<ToOtaPackageStateServiceMsg>> otaPackageStateMsgProducer;

//    public DefaultOtaPackageStateService(@Lazy TbClusterService tbClusterService,
//                                         OtaPackageService otaPackageService,
//                                         DeviceService deviceService,
//                                         DeviceProfileService deviceProfileService,
//                                         @Lazy RuleEngineTelemetryService telemetryService,
//                                         Optional<TbCoreQueueFactory> coreQueueFactory,
//                                         Optional<TbRuleEngineQueueFactory> reQueueFactory) {
//        this.tbClusterService = tbClusterService;
//        this.otaPackageService = otaPackageService;
//        this.deviceService = deviceService;
//        this.deviceProfileService = deviceProfileService;
//        this.telemetryService = telemetryService;
//        if (coreQueueFactory.isPresent()) {
//            this.otaPackageStateMsgProducer = coreQueueFactory.get().createToOtaPackageStateServiceMsgProducer();
//        } else {
//            this.otaPackageStateMsgProducer = reQueueFactory.get().createToOtaPackageStateServiceMsgProducer();
//        }
//    }

    @Override
    public void update(Device device, Device oldDevice) {
        //updateFirmware(device, oldDevice);
       // updateSoftware(device, oldDevice);
    }

//    private void updateFirmware(Device device, Device oldDevice) {
//        OtaPackageId newFirmwareId = device.getFirmwareId();
//        if (newFirmwareId == null) {
//            DeviceProfile newDeviceProfile = deviceProfileService.findDeviceProfileById(device.getTenantId(), device.getDeviceProfileId());
//            newFirmwareId = newDeviceProfile.getFirmwareId();
//        }
//        if (oldDevice != null) {
//            if (newFirmwareId != null) {
//                OtaPackageId oldFirmwareId = oldDevice.getFirmwareId();
//                if (oldFirmwareId == null) {
//                    DeviceProfile oldDeviceProfile = deviceProfileService.findDeviceProfileById(oldDevice.getTenantId(), oldDevice.getDeviceProfileId());
//                    oldFirmwareId = oldDeviceProfile.getFirmwareId();
//                }
//                if (!newFirmwareId.equals(oldFirmwareId)) {
//                    // Device was updated and new firmware is different from previous firmware.
//                    send(device.getTenantId(), device.getId(), newFirmwareId, System.currentTimeMillis(), FIRMWARE);
//                }
//            } else {
//                // Device was updated and new firmware is not set.
//                remove(device, FIRMWARE);
//            }
//        } else if (newFirmwareId != null) {
//            // Device was created and firmware is defined.
//            send(device.getTenantId(), device.getId(), newFirmwareId, System.currentTimeMillis(), FIRMWARE);
//        }
//    }
//
//    private void updateSoftware(Device device, Device oldDevice) {
//        OtaPackageId newSoftwareId = device.getSoftwareId();
//        if (newSoftwareId == null) {
//            DeviceProfile newDeviceProfile = deviceProfileService.findDeviceProfileById(device.getTenantId(), device.getDeviceProfileId());
//            newSoftwareId = newDeviceProfile.getSoftwareId();
//        }
//        if (oldDevice != null) {
//            if (newSoftwareId != null) {
//                OtaPackageId oldSoftwareId = oldDevice.getSoftwareId();
//                if (oldSoftwareId == null) {
//                    DeviceProfile oldDeviceProfile = deviceProfileService.findDeviceProfileById(oldDevice.getTenantId(), oldDevice.getDeviceProfileId());
//                    oldSoftwareId = oldDeviceProfile.getSoftwareId();
//                }
//                if (!newSoftwareId.equals(oldSoftwareId)) {
//                    // Device was updated and new firmware is different from previous firmware.
//                    send(device.getTenantId(), device.getId(), newSoftwareId, System.currentTimeMillis(), SOFTWARE);
//                }
//            } else {
//                // Device was updated and new firmware is not set.
//                remove(device, SOFTWARE);
//            }
//        } else if (newSoftwareId != null) {
//            // Device was created and firmware is defined.
//            send(device.getTenantId(), device.getId(), newSoftwareId, System.currentTimeMillis(), SOFTWARE);
//        }
//    }

    @Override
    public void update(DeviceProfile deviceProfile, boolean isFirmwareChanged, boolean isSoftwareChanged) {
        TenantId tenantId = deviceProfile.getTenantId();

//        if (isFirmwareChanged) {
//            update(tenantId, deviceProfile, FIRMWARE);
//        }
//        if (isSoftwareChanged) {
//            update(tenantId, deviceProfile, SOFTWARE);
//        }
    }

//    private void update(TenantId tenantId, DeviceProfile deviceProfile, OtaPackageType otaPackageType) {
//        Consumer<Device> updateConsumer;
//
//        if (deviceProfile.getFirmwareId() != null) {
//            long ts = System.currentTimeMillis();
//            updateConsumer = d -> send(d.getTenantId(), d.getId(), deviceProfile.getFirmwareId(), ts, otaPackageType);
//        } else {
//            updateConsumer = d -> remove(d, otaPackageType);
//        }
//
//        PageLink pageLink = new PageLink(100);
//        PageData<Device> pageData;
//        do {
//            pageData = deviceService.findDevicesByTenantIdAndTypeAndEmptyOtaPackage(tenantId, deviceProfile.getId(), otaPackageType, pageLink);
//            pageData.getData().forEach(updateConsumer);
//
//            if (pageData.hasNext()) {
//                pageLink = pageLink.nextPageLink();
//            }
//        } while (pageData.hasNext());
//    }

    @Override
    public boolean process(ToOtaPackageStateServiceMsg msg) {
        boolean isSuccess = false;
//        OtaPackageId targetOtaPackageId = new OtaPackageId(new UUID(msg.getOtaPackageIdMSB(), msg.getOtaPackageIdLSB()));
//        DeviceId deviceId = new DeviceId(new UUID(msg.getDeviceIdMSB(), msg.getDeviceIdLSB()));
//        TenantId tenantId = new TenantId(new UUID(msg.getTenantIdMSB(), msg.getTenantIdLSB()));
//        OtaPackageType firmwareType = OtaPackageType.valueOf(msg.getType());
//        long ts = msg.getTs();
//
//        Device device = deviceService.findDeviceById(tenantId, deviceId);
//        if (device == null) {
//            log.warn("[{}] [{}] Device was removed during firmware update msg was queued!", tenantId, deviceId);
//        } else {
//            OtaPackageId currentOtaPackageId = OtaPackageUtil.getOtaPackageId(device, firmwareType);
//            if (currentOtaPackageId == null) {
//                DeviceProfile deviceProfile = deviceProfileService.findDeviceProfileById(tenantId, device.getDeviceProfileId());
//                currentOtaPackageId = OtaPackageUtil.getOtaPackageId(deviceProfile, firmwareType);
//            }
//
//            if (targetOtaPackageId.equals(currentOtaPackageId)) {
//                update(device, otaPackageService.findOtaPackageInfoById(device.getTenantId(), targetOtaPackageId), ts);
//                isSuccess = true;
//            } else {
//                log.warn("[{}] [{}] Can`t update firmware for the device, target firmwareId: [{}], current firmwareId: [{}]!", tenantId, deviceId, targetOtaPackageId, currentOtaPackageId);
//            }
//        }
        return isSuccess;
    }

//    private void send(TenantId tenantId, DeviceId deviceId, OtaPackageId firmwareId, long ts, OtaPackageType firmwareType) {
//        ToOtaPackageStateServiceMsg msg = ToOtaPackageStateServiceMsg.newBuilder()
//                .setTenantIdMSB(tenantId.getId().getMostSignificantBits())
//                .setTenantIdLSB(tenantId.getId().getLeastSignificantBits())
//                .setDeviceIdMSB(deviceId.getId().getMostSignificantBits())
//                .setDeviceIdLSB(deviceId.getId().getLeastSignificantBits())
//                .setOtaPackageIdMSB(firmwareId.getId().getMostSignificantBits())
//                .setOtaPackageIdLSB(firmwareId.getId().getLeastSignificantBits())
//                .setType(firmwareType.name())
//                .setTs(ts)
//                .build();
//
//        OtaPackageInfo firmware = otaPackageService.findOtaPackageInfoById(tenantId, firmwareId);
//        if (firmware == null) {
//            log.warn("[{}] Failed to send firmware update because firmware was already deleted", firmwareId);
//            return;
//        }
//
//        TopicPartitionInfo tpi = new TopicPartitionInfo(otaPackageStateMsgProducer.getDefaultTopic(), null, null, false);
//        otaPackageStateMsgProducer.send(tpi, new TbProtoQueueMsg<>(UUID.randomUUID(), msg), null);
//
//        List<TsKvEntry> telemetry = new ArrayList<>();
//        telemetry.add(new BasicTsKvEntry(ts, new StringDataEntry(getTargetTelemetryKey(firmware.getType(), TITLE), firmware.getTitle())));
//        telemetry.add(new BasicTsKvEntry(ts, new StringDataEntry(getTargetTelemetryKey(firmware.getType(), VERSION), firmware.getVersion())));
//
//        if (StringUtils.isNotEmpty(firmware.getTag())) {
//            telemetry.add(new BasicTsKvEntry(ts, new StringDataEntry(getTargetTelemetryKey(firmware.getType(), TAG), firmware.getTag())));
//        }
//
//        telemetry.add(new BasicTsKvEntry(ts, new LongDataEntry(getTargetTelemetryKey(firmware.getType(), TS), ts)));
//        telemetry.add(new BasicTsKvEntry(ts, new StringDataEntry(getTelemetryKey(firmware.getType(), STATE), OtaPackageUpdateStatus.QUEUED.name())));
//
//        telemetryService.saveAndNotify(tenantId, deviceId, telemetry, new FutureCallback<>() {
//            @Override
//            public void onSuccess(@Nullable Void tmp) {
//                log.trace("[{}] Success save firmware status!", deviceId);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("[{}] Failed to save firmware status!", deviceId, t);
//            }
//        });
//    }

//
//    private void update(Device device, OtaPackageInfo otaPackage, long ts) {
//        TenantId tenantId = device.getTenantId();
//        DeviceId deviceId = device.getId();
//        OtaPackageType otaPackageType = otaPackage.getType();
//
//        BasicTsKvEntry status = new BasicTsKvEntry(System.currentTimeMillis(), new StringDataEntry(getTelemetryKey(otaPackageType, STATE), OtaPackageUpdateStatus.INITIATED.name()));
//
//        telemetryService.saveAndNotify(tenantId, deviceId, Collections.singletonList(status), new FutureCallback<>() {
//            @Override
//            public void onSuccess(@Nullable Void tmp) {
//                log.trace("[{}] Success save telemetry with target {} for device!", deviceId, otaPackage);
//                updateAttributes(device, otaPackage, ts, tenantId, deviceId, otaPackageType);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("[{}] Failed to save telemetry with target {} for device!", deviceId, otaPackage, t);
//                updateAttributes(device, otaPackage, ts, tenantId, deviceId, otaPackageType);
//            }
//        });
//    }
//
//    private void updateAttributes(Device device, OtaPackageInfo otaPackage, long ts, TenantId tenantId, DeviceId deviceId, OtaPackageType otaPackageType) {
//        List<AttributeKvEntry> attributes = new ArrayList<>();
//        attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, TITLE), otaPackage.getTitle())));
//        attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, VERSION), otaPackage.getVersion())));
//        if (StringUtils.isNotEmpty(otaPackage.getTag())) {
//            attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, TAG), otaPackage.getTag())));
//        }
//        if (otaPackage.hasUrl()) {
//            attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, URL), otaPackage.getUrl())));
//            List<String> attrToRemove = new ArrayList<>();
//
//            if (otaPackage.getDataSize() == null) {
//                attrToRemove.add(getAttributeKey(otaPackageType, SIZE));
//            } else {
//                attributes.add(new BaseAttributeKvEntry(ts, new LongDataEntry(getAttributeKey(otaPackageType, SIZE), otaPackage.getDataSize())));
//            }
//
//            if (otaPackage.getChecksumAlgorithm() != null) {
//                attrToRemove.add(getAttributeKey(otaPackageType, CHECKSUM_ALGORITHM));
//            } else {
//                attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, CHECKSUM_ALGORITHM), otaPackage.getChecksumAlgorithm().name())));
//            }
//
//            if (StringUtils.isEmpty(otaPackage.getChecksum())) {
//                attrToRemove.add(getAttributeKey(otaPackageType, CHECKSUM));
//            } else {
//                attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, CHECKSUM), otaPackage.getChecksum())));
//            }
//
//            remove(device, otaPackageType, attrToRemove);
//        } else {
//            attributes.add(new BaseAttributeKvEntry(ts, new LongDataEntry(getAttributeKey(otaPackageType, SIZE), otaPackage.getDataSize())));
//            attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, CHECKSUM_ALGORITHM), otaPackage.getChecksumAlgorithm().name())));
//            attributes.add(new BaseAttributeKvEntry(ts, new StringDataEntry(getAttributeKey(otaPackageType, CHECKSUM), otaPackage.getChecksum())));
//            remove(device, otaPackageType, Collections.singletonList(getAttributeKey(otaPackageType, URL)));
//        }
//
//        telemetryService.saveAndNotify(tenantId, deviceId, DataConstants.SHARED_SCOPE, attributes, new FutureCallback<>() {
//            @Override
//            public void onSuccess(@Nullable Void tmp) {
//                log.trace("[{}] Success save attributes with target firmware!", deviceId);
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                log.error("[{}] Failed to save attributes with target firmware!", deviceId, t);
//            }
//        });
//    }
//
//    private void remove(Device device, OtaPackageType otaPackageType) {
//        remove(device, otaPackageType, OtaPackageUtil.getAttributeKeys(otaPackageType));
//    }
//
//    private void remove(Device device, OtaPackageType otaPackageType, List<String> attributesKeys) {
//        telemetryService.deleteAndNotify(device.getTenantId(), device.getId(), DataConstants.SHARED_SCOPE, attributesKeys,
//                new FutureCallback<>() {
//                    @Override
//                    public void onSuccess(@Nullable Void tmp) {
//                        log.trace("[{}] Success remove target {} attributes!", device.getId(), otaPackageType);
//                        Set<AttributeKey> keysToNotify = new HashSet<>();
//                        attributesKeys.forEach(key -> keysToNotify.add(new AttributeKey(DataConstants.SHARED_SCOPE, key)));
//                        tbClusterService.pushMsgToCore(DeviceAttributesEventNotificationMsg.onDelete(device.getTenantId(), device.getId(), keysToNotify), null);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        log.error("[{}] Failed to remove target {} attributes!", device.getId(), otaPackageType, t);
//                    }
//                });
//    }
}
