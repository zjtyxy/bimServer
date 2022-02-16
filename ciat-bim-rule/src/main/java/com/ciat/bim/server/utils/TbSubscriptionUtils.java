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
package com.ciat.bim.server.utils;



import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.data.id.EntityIdFactory;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.subscription.*;
import com.ciat.bim.server.telemetry.sub.AlarmSubscriptionUpdate;
import com.ciat.bim.server.telemetry.sub.SubscriptionErrorCode;
import com.ciat.bim.server.telemetry.sub.TelemetrySubscriptionUpdate;
import com.ciat.bim.server.transport.TransportProtos;
import com.ciat.bim.server.transport.TransportProtos.*;
import org.jeecg.modules.alarm.entity.Alarm;
import org.jeecg.modules.device.entity.AttributeKv;
import org.jeecg.modules.device.entity.KvEntry;
import org.jeecg.modules.device.entity.TsKv;
import org.springframework.data.redis.connection.DataType;

import java.util.*;

public class TbSubscriptionUtils {

    public static ToCoreMsg toNewSubscriptionProto(TbSubscription subscription) {
        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        TbSubscriptionProto subscriptionProto = TbSubscriptionProto.newBuilder()
                .setServiceId(subscription.getServiceId())
                .setSessionId(subscription.getSessionId())
                .setSubscriptionId(subscription.getSubscriptionId())
                .setTenantIdMSB(Long.parseLong(subscription.getTenantId()))
                .setTenantIdLSB(Long.parseLong(subscription.getTenantId()))
                .setEntityType(subscription.getEntityId().getEntityType().name())
                .setEntityIdMSB(Long.parseLong(subscription.getEntityId().getId()))
                .setEntityIdLSB(Long.parseLong(subscription.getEntityId().getId())).build();

        switch (subscription.getType()) {
            case TIMESERIES:
                TbTimeseriesSubscription tSub = (TbTimeseriesSubscription) subscription;
                TbTimeSeriesSubscriptionProto.Builder tSubProto = TbTimeSeriesSubscriptionProto.newBuilder()
                        .setSub(subscriptionProto)
                        .setAllKeys(tSub.isAllKeys());
                tSub.getKeyStates().forEach((key, value) -> tSubProto.addKeyStates(
                        TbSubscriptionKetStateProto.newBuilder().setKey(key).setTs(value).build()));
                tSubProto.setStartTime(tSub.getStartTime());
                tSubProto.setEndTime(tSub.getEndTime());
                tSubProto.setLatestValues(tSub.isLatestValues());
                msgBuilder.setTelemetrySub(tSubProto.build());
                break;
            case ATTRIBUTES:
                TbAttributeSubscription aSub = (TbAttributeSubscription) subscription;
                TbAttributeSubscriptionProto.Builder aSubProto = TbAttributeSubscriptionProto.newBuilder()
                        .setSub(subscriptionProto)
                        .setAllKeys(aSub.isAllKeys())
                        .setScope(aSub.getScope().name());
                aSub.getKeyStates().forEach((key, value) -> aSubProto.addKeyStates(
                        TbSubscriptionKetStateProto.newBuilder().setKey(key).setTs(value).build()));
                msgBuilder.setAttributeSub(aSubProto.build());
                break;
            case ALARMS:
                TbAlarmsSubscription alarmSub = (TbAlarmsSubscription) subscription;
                TransportProtos.TbAlarmSubscriptionProto.Builder alarmSubProto = TransportProtos.TbAlarmSubscriptionProto.newBuilder()
                        .setSub(subscriptionProto)
                        .setTs(alarmSub.getTs());
                msgBuilder.setAlarmSub(alarmSubProto.build());
                break;
        }
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }

    public static ToCoreMsg toCloseSubscriptionProto(TbSubscription subscription) {
        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        TbSubscriptionCloseProto closeProto = TbSubscriptionCloseProto.newBuilder()
                .setSessionId(subscription.getSessionId())
                .setSubscriptionId(subscription.getSubscriptionId()).build();
        msgBuilder.setSubClose(closeProto);
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }

    public static TbSubscription fromProto(TbAttributeSubscriptionProto attributeSub) {
        TbSubscriptionProto subProto = attributeSub.getSub();
        TbAttributeSubscription.TbAttributeSubscriptionBuilder builder = TbAttributeSubscription.builder()
                .serviceId(subProto.getServiceId())
                .sessionId(subProto.getSessionId())
                .subscriptionId(subProto.getSubscriptionId())
                .entityId(EntityIdFactory.getByTypeAndUuid(subProto.getEntityType(), subProto.getEntityIdMSB()+""))
                .tenantId(subProto.getTenantIdMSB()+"");

        builder.scope(TbAttributeSubscriptionScope.valueOf(attributeSub.getScope()));
        builder.allKeys(attributeSub.getAllKeys());
        Map<String, Long> keyStates = new HashMap<>();
        attributeSub.getKeyStatesList().forEach(ksProto -> keyStates.put(ksProto.getKey(), ksProto.getTs()));
        builder.keyStates(keyStates);
        return builder.build();
    }

    public static TbSubscription fromProto(TbTimeSeriesSubscriptionProto telemetrySub) {
        TbSubscriptionProto subProto = telemetrySub.getSub();
        TbTimeseriesSubscription.TbTimeseriesSubscriptionBuilder builder = TbTimeseriesSubscription.builder()
                .serviceId(subProto.getServiceId())
                .sessionId(subProto.getSessionId())
                .subscriptionId(subProto.getSubscriptionId())
                .entityId(EntityIdFactory.getByTypeAndUuid(subProto.getEntityType(), subProto.getEntityIdMSB()+""))
                .tenantId(subProto.getTenantIdMSB()+"");

        builder.allKeys(telemetrySub.getAllKeys());
        Map<String, Long> keyStates = new HashMap<>();
        telemetrySub.getKeyStatesList().forEach(ksProto -> keyStates.put(ksProto.getKey(), ksProto.getTs()));
        builder.startTime(telemetrySub.getStartTime());
        builder.endTime(telemetrySub.getEndTime());
        builder.latestValues(telemetrySub.getLatestValues());
        builder.keyStates(keyStates);
        return builder.build();
    }

    public static TbSubscription fromProto(TransportProtos.TbAlarmSubscriptionProto alarmSub) {
        TbSubscriptionProto subProto = alarmSub.getSub();
        TbAlarmsSubscription.TbAlarmsSubscriptionBuilder builder = TbAlarmsSubscription.builder()
                .serviceId(subProto.getServiceId())
                .sessionId(subProto.getSessionId())
                .subscriptionId(subProto.getSubscriptionId())
                .entityId(EntityIdFactory.getByTypeAndUuid(subProto.getEntityType(), subProto.getEntityIdMSB()+""))
                .tenantId(subProto.getTenantIdMSB()+"");
        builder.ts(alarmSub.getTs());
        return builder.build();
    }

    public static TelemetrySubscriptionUpdate fromProto(TbSubscriptionUpdateProto proto) {
        if (proto.getErrorCode() > 0) {
            return new TelemetrySubscriptionUpdate(proto.getSubscriptionId(), SubscriptionErrorCode.forCode(proto.getErrorCode()), proto.getErrorMsg());
        } else {
            Map<String, List<Object>> data = new TreeMap<>();
            proto.getDataList().forEach(v -> {
                List<Object> values = data.computeIfAbsent(v.getKey(), k -> new ArrayList<>());
                for (int i = 0; i < v.getTsCount(); i++) {
                    Object[] value = new Object[2];
                    value[0] = v.getTs(i);
                    value[1] = v.getValue(i);
                    values.add(value);
                }
            });
            return new TelemetrySubscriptionUpdate(proto.getSubscriptionId(), data);
        }
    }

    public static AlarmSubscriptionUpdate fromProto(TransportProtos.TbAlarmSubscriptionUpdateProto proto) {
        if (proto.getErrorCode() > 0) {
            return new AlarmSubscriptionUpdate(proto.getSubscriptionId(), SubscriptionErrorCode.forCode(proto.getErrorCode()), proto.getErrorMsg());
        } else {
            Alarm alarm = JacksonUtil.fromString(proto.getAlarm(), Alarm.class);
            return new AlarmSubscriptionUpdate(proto.getSubscriptionId(), alarm);
        }
    }


    public static ToCoreMsg toTimeseriesUpdateProto(TenantId tenantId, EntityId entityId, List<TsKv> ts) {
        TbTimeSeriesUpdateProto.Builder builder = TbTimeSeriesUpdateProto.newBuilder();
        builder.setEntityType(entityId.getEntityType().name());
        builder.setEntityIdMSB(Long.parseLong(entityId.getId()));
        builder.setEntityIdLSB(Long.parseLong(entityId.getId()));
        builder.setTenantIdMSB(Long.parseLong(tenantId.getId()));
        builder.setTenantIdLSB(Long.parseLong(tenantId.getId()));
        ts.forEach(v -> builder.addData(toKeyValueProto(v.getCreateTime().getTime(), v).build()));
        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        msgBuilder.setTsUpdate(builder);
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }

    public static ToCoreMsg toAttributesUpdateProto(TenantId tenantId, EntityId entityId, String scope, List<AttributeKv> attributes) {
        TbAttributeUpdateProto.Builder builder = TbAttributeUpdateProto.newBuilder();
        builder.setEntityType(entityId.getEntityType().name());
        builder.setEntityIdMSB(Long.parseLong(entityId.getId()));
        builder.setEntityIdLSB(Long.parseLong(entityId.getId()));
        builder.setTenantIdMSB(Long.parseLong(tenantId.getId()));
        builder.setTenantIdLSB(Long.parseLong(tenantId.getId()));
        builder.setScope(scope);
        attributes.forEach(v -> builder.addData(toKeyValueProto(v.getLastupdatets().getTime(), v).build()));

        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        msgBuilder.setAttrUpdate(builder);
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }

    public static ToCoreMsg toAttributesDeleteProto(TenantId tenantId, EntityId entityId, String scope, List<String> keys) {
        TbAttributeDeleteProto.Builder builder = TbAttributeDeleteProto.newBuilder();
        builder.setEntityType(entityId.getEntityType().name());
        builder.setEntityIdMSB(Long.parseLong(entityId.getId()));
        builder.setEntityIdLSB(Long.parseLong(entityId.getId()));
        builder.setTenantIdMSB(Long.parseLong(tenantId.getId()));
        builder.setTenantIdLSB(Long.parseLong(tenantId.getId()));
        builder.setScope(scope);
        builder.addAllKeys(keys);

        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        msgBuilder.setAttrDelete(builder);
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }


    private static TsKvProto.Builder toKeyValueProto(long ts, KvEntry attr) {
        KeyValueProto.Builder dataBuilder = KeyValueProto.newBuilder();
        dataBuilder.setKey(attr.getKey());
        dataBuilder.setType(KeyValueType.forNumber(attr.getDataType().ordinal()));
        switch (attr.getDataType()) {
            case BOOLEAN:
                dataBuilder.setBoolV(attr.getBooleanValue().equals("Y"));
              //  attr.getBooleanValue().ifPresent(dataBuilder::setBoolV);
                break;
            case LONG:
                dataBuilder.setLongV(attr.getLongValue());
                break;
            case DOUBLE:
                dataBuilder.setDoubleV(attr.getDoubleValue());
                break;
            case JSON:
                dataBuilder.setJsonV(attr.getJsonValue());

                break;
            case STRING:
                dataBuilder.setStringV(attr.getStrValue());

                break;
        }
        return TsKvProto.newBuilder().setTs(ts).setKv(dataBuilder);
    }

    public static EntityId toEntityId(String entityType, long entityIdMSB, long entityIdLSB) {
        return EntityIdFactory.getByTypeAndUuid(entityType, entityIdMSB+"");
    }

    public static List<TsKv> toTsKvEntityList(List<TsKvProto> dataList) {
        List<TsKv> result = new ArrayList<>(dataList.size());
     //   dataList.forEach(proto -> result.add(new AttributeKv(proto.getTs(), getKvEntry(proto.getKv()))));
        return result;
    }

    public static List<AttributeKv> toAttributeKvList(List<TsKvProto> dataList) {
        List<AttributeKv> result = new ArrayList<>(dataList.size());
      //  dataList.forEach(proto -> result.add(new AttributeKv(getKvEntry(proto.getKv()), proto.getTs())));
        return result;
    }

    private static AttributeKv getKvEntry(KeyValueProto proto) {
        AttributeKv entry = null;
        DataType type = DataType.values()[proto.getType().getNumber()];
        switch (type) {
//            case BOOLEAN:
//                entry = new BooleanDataEntry(proto.getKey(), proto.getBoolV());
//                break;
//            case LONG:
//                entry = new LongDataEntry(proto.getKey(), proto.getLongV());
//                break;
//            case DOUBLE:
//                entry = new DoubleDataEntry(proto.getKey(), proto.getDoubleV());
//                break;
//            case STRING:
//                entry = new StringDataEntry(proto.getKey(), proto.getStringV());
//                break;
//            case JSON:
//                entry = new JsonDataEntry(proto.getKey(), proto.getJsonV());
//                break;
        }
        return entry;
    }

    public static ToCoreMsg toAlarmUpdateProto(TenantId tenantId, EntityId entityId, Alarm alarm) {
        TbAlarmUpdateProto.Builder builder = TbAlarmUpdateProto.newBuilder();
        builder.setEntityType(entityId.getEntityType().name());
        builder.setEntityIdMSB(Long.parseLong(entityId.getId()));
        builder.setEntityIdLSB(Long.parseLong(entityId.getId()));
        builder.setTenantIdMSB(Long.parseLong(tenantId.getId()));
        builder.setTenantIdLSB(Long.parseLong(tenantId.getId()));
        builder.setAlarm(JacksonUtil.toString(alarm));
        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        msgBuilder.setAlarmUpdate(builder);
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }

    public static ToCoreMsg toAlarmDeletedProto(TenantId tenantId, EntityId entityId, Alarm alarm) {
        TbAlarmDeleteProto.Builder builder = TbAlarmDeleteProto.newBuilder();
        builder.setEntityType(entityId.getEntityType().name());
        builder.setEntityIdMSB(Long.parseLong(entityId.getId()));
        builder.setEntityIdLSB(Long.parseLong(entityId.getId()));
        builder.setTenantIdMSB(Long.parseLong(tenantId.getId()));
        builder.setTenantIdLSB(Long.parseLong(tenantId.getId()));
        builder.setAlarm(JacksonUtil.toString(alarm));
        SubscriptionMgrMsgProto.Builder msgBuilder = SubscriptionMgrMsgProto.newBuilder();
        msgBuilder.setAlarmDelete(builder);
        return ToCoreMsg.newBuilder().setToSubscriptionMgrMsg(msgBuilder.build()).build();
    }
}
