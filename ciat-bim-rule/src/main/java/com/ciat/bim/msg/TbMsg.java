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
package com.ciat.bim.msg;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.common.msg.gen.MsgProtos;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.protobuf.ByteString;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ashvayka on 13.01.18.
 */
@Data
@Slf4j
public final class TbMsg implements Serializable {

    private final String queueName;
    private final String id;
    private final long ts;
    private final String type;
    private final EntityId originator;
    private final CustomerId customerId;
    private final TbMsgMetaData metaData;
    private final TbMsgDataType dataType;
    private final String data;
    private final String ruleChainId;
    private final String ruleNodeId;
    @Getter(value = AccessLevel.NONE)
    private final AtomicInteger ruleNodeExecCounter;


    public int getAndIncrementRuleNodeCounter() {
        return ruleNodeExecCounter.getAndIncrement();
    }

    //This field is not serialized because we use queues and there is no need to do it
    @JsonIgnore
    transient private final TbMsgCallback callback;

    public static TbMsg newMsg(String queueName, String type, EntityId originator, TbMsgMetaData metaData, String data, String ruleChainId, String ruleNodeId) {
        return newMsg(queueName, type, originator, null, metaData, data, ruleChainId, ruleNodeId);
    }

    public static TbMsg newMsg(String queueName, String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, String data, String ruleChainId, String ruleNodeId) {
        return new TbMsg(queueName, UUID.randomUUID().toString(), System.currentTimeMillis(), type, originator, customerId,
                metaData.copy(), TbMsgDataType.JSON, data, ruleChainId, ruleNodeId, 0, TbMsgCallback.EMPTY);
    }

    public static TbMsg newMsg(String type, EntityId originator, TbMsgMetaData metaData, String data) {
        return newMsg(type, originator, null, metaData, data);
    }

    public static TbMsg newMsg(String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, String data) {
        return new TbMsg(ServiceQueue.MAIN, UUID.randomUUID().toString(), System.currentTimeMillis(), type, originator, customerId, metaData.copy(), TbMsgDataType.JSON, data, null, null, 0, TbMsgCallback.EMPTY);
    }

    // REALLY NEW MSG

    public static TbMsg newMsg(String queueName, String type, EntityId originator, TbMsgMetaData metaData, String data) {
        return newMsg(queueName, type, originator, null, metaData, data);
    }

    public static TbMsg newMsg(String queueName, String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, String data) {
        return new TbMsg(queueName, UUID.randomUUID().toString(), System.currentTimeMillis(), type, originator, customerId, metaData.copy(), TbMsgDataType.JSON, data, null, null, 0, TbMsgCallback.EMPTY);
    }

    public static TbMsg newMsg(String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, TbMsgDataType dataType, String data) {
        return new TbMsg(ServiceQueue.MAIN, UUID.randomUUID().toString(), System.currentTimeMillis(), type, originator, customerId, metaData.copy(), dataType, data, null, null, 0, TbMsgCallback.EMPTY);
    }

    public static TbMsg newMsg(String type, EntityId originator, TbMsgMetaData metaData, TbMsgDataType dataType, String data) {
        return newMsg(type, originator, null, metaData, dataType, data);
    }

    // For Tests only

    public static TbMsg newMsg(String type, EntityId originator, TbMsgMetaData metaData, TbMsgDataType dataType, String data, String ruleChainId, String ruleNodeId) {
        return new TbMsg(ServiceQueue.MAIN, UUID.randomUUID().toString(), System.currentTimeMillis(), type, originator, null, metaData.copy(), dataType, data, ruleChainId, ruleNodeId, 0, TbMsgCallback.EMPTY);
    }

    public static TbMsg newMsg(String type, EntityId originator, TbMsgMetaData metaData, String data, TbMsgCallback callback) {
        return new TbMsg(ServiceQueue.MAIN, UUID.randomUUID().toString(), System.currentTimeMillis(), type, originator, null, metaData.copy(), TbMsgDataType.JSON, data, null, null, 0, callback);
    }

    public static TbMsg transformMsg(TbMsg tbMsg, String type, EntityId originator, TbMsgMetaData metaData, String data) {
        return new TbMsg(tbMsg.queueName, tbMsg.id, tbMsg.ts, type, originator, tbMsg.customerId, metaData.copy(), tbMsg.dataType,
                data, tbMsg.ruleChainId, tbMsg.ruleNodeId, tbMsg.ruleNodeExecCounter.get(), tbMsg.callback);
    }

    public static TbMsg transformMsg(TbMsg tbMsg, CustomerId customerId) {
        return new TbMsg(tbMsg.queueName, tbMsg.id, tbMsg.ts, tbMsg.type, tbMsg.originator, customerId, tbMsg.metaData, tbMsg.dataType,
                tbMsg.data, tbMsg.ruleChainId, tbMsg.ruleNodeId, tbMsg.ruleNodeExecCounter.get(), tbMsg.getCallback());
    }

    public static TbMsg transformMsg(TbMsg tbMsg, RuleChainId ruleChainId) {//????????????????
        return new TbMsg(tbMsg.queueName, tbMsg.id, tbMsg.ts, tbMsg.type, tbMsg.originator, tbMsg.customerId, tbMsg.metaData, tbMsg.dataType,
                tbMsg.data, ruleChainId.getId(), null, tbMsg.ruleNodeExecCounter.get(), tbMsg.getCallback());
    }

    public static TbMsg transformMsg(TbMsg tbMsg, String queueName) {
        return new TbMsg(queueName, tbMsg.id, tbMsg.ts, tbMsg.type, tbMsg.originator, tbMsg.customerId, tbMsg.metaData, tbMsg.dataType,
                tbMsg.data, tbMsg.getRuleChainId(), null, tbMsg.ruleNodeExecCounter.get(), tbMsg.getCallback());
    }

    public static TbMsg transformMsg(TbMsg tbMsg, String ruleChainId, String queueName) {
        return new TbMsg(queueName, tbMsg.id, tbMsg.ts, tbMsg.type, tbMsg.originator, tbMsg.customerId, tbMsg.metaData, tbMsg.dataType,
                tbMsg.data, ruleChainId, null, tbMsg.ruleNodeExecCounter.get(), tbMsg.getCallback());
    }

    //used for enqueueForTellNext
    public static TbMsg newMsg(TbMsg tbMsg, String queueName, String ruleChainId, String ruleNodeId) {
        return new TbMsg(queueName, UUID.randomUUID().toString(), tbMsg.getTs(), tbMsg.getType(), tbMsg.getOriginator(), tbMsg.customerId, tbMsg.getMetaData().copy(),
                tbMsg.getDataType(), tbMsg.getData(), ruleChainId, ruleNodeId, tbMsg.ruleNodeExecCounter.get(), TbMsgCallback.EMPTY);
    }

    private TbMsg(String queueName, String id, long ts, String type, EntityId originator, CustomerId customerId, TbMsgMetaData metaData, TbMsgDataType dataType, String data,
                  String ruleChainId, String ruleNodeId, int ruleNodeExecCounter, TbMsgCallback callback) {
        this.id = id;
        this.queueName = queueName != null ? queueName : ServiceQueue.MAIN;
        if (ts > 0) {
            this.ts = ts;
        } else {
            this.ts = System.currentTimeMillis();
        }
        this.type = type;
        this.originator = originator;
        if (customerId == null || customerId.isNullUid()) {
            if (originator != null && originator.getEntityType() == EntityType.CUSTOMER) {
                this.customerId = (CustomerId) originator;
            } else {
                this.customerId = null;
            }
        } else {
            this.customerId = customerId;
        }
        this.metaData = metaData;
        this.dataType = dataType;
        this.data = data;
        this.ruleChainId = ruleChainId;
        this.ruleNodeId = ruleNodeId;
        this.ruleNodeExecCounter = new AtomicInteger(ruleNodeExecCounter);
        if (callback != null) {
            this.callback = callback;
        } else {
            this.callback = TbMsgCallback.EMPTY;
        }
    }

    public static ByteString toByteString(TbMsg msg) {
        return ByteString.copyFrom(toByteArray(msg));
    }

    public static byte[] toByteArray(TbMsg msg) {
        MsgProtos.TbMsgProto.Builder builder = MsgProtos.TbMsgProto.newBuilder();
        builder.setId(msg.getId().toString());
        builder.setTs(msg.getTs());
        builder.setType(msg.getType());
        builder.setEntityType(msg.getOriginator().getEntityType().name());
        builder.setEntityIdMSB(Long.parseLong( msg.getOriginator().getId()));
        builder.setEntityIdLSB(Long.parseLong(msg.getOriginator().getId()));

        if (msg.getCustomerId() != null) {
            builder.setCustomerIdMSB(Long.parseLong(msg.getCustomerId().getId()));
            builder.setCustomerIdLSB(Long.parseLong(msg.getCustomerId().getId()));
        }

        if (msg.getRuleChainId() != null) {
            builder.setRuleChainIdMSB(Long.parseLong(msg.getRuleChainId()));
            builder.setRuleChainIdLSB(Long.parseLong(msg.getRuleChainId()));
        }

        if (msg.getRuleNodeId() != null) {
            builder.setRuleNodeIdMSB(Long.parseLong(msg.getRuleNodeId()));
            builder.setRuleNodeIdLSB(Long.parseLong(msg.getRuleNodeId()));
        }

        if (msg.getMetaData() != null) {
            builder.setMetaData(MsgProtos.TbMsgMetaDataProto.newBuilder().putAllData(msg.getMetaData().getData()).build());
        }

        builder.setDataType(msg.getDataType().ordinal());
        builder.setData(msg.getData());
        builder.setRuleNodeExecCounter(msg.ruleNodeExecCounter.get());
        return builder.build().toByteArray();
    }
//
//    public static TbMsg fromBytes(String queueName, byte[] data, TbMsgCallback callback) {
//        try {
//            MsgProtos.TbMsgProto proto = MsgProtos.TbMsgProto.parseFrom(data);
//            TbMsgMetaData metaData = new TbMsgMetaData(proto.getMetaData().getDataMap());
//            EntityId entityId = EntityIdFactory.getByTypeAndUuid(proto.getEntityType(), new UUID(proto.getEntityIdMSB(), proto.getEntityIdLSB()));
//            CustomerId customerId = null;
//            RuleChainId ruleChainId = null;
//            RuleNodeId ruleNodeId = null;
//            if (proto.getCustomerIdMSB() != 0L && proto.getCustomerIdLSB() != 0L) {
//                customerId = new CustomerId(new UUID(proto.getCustomerIdMSB(), proto.getCustomerIdLSB()));
//            }
//            if (proto.getRuleChainIdMSB() != 0L && proto.getRuleChainIdLSB() != 0L) {
//                ruleChainId = new RuleChainId(new UUID(proto.getRuleChainIdMSB(), proto.getRuleChainIdLSB()));
//            }
//            if (proto.getRuleNodeIdMSB() != 0L && proto.getRuleNodeIdLSB() != 0L) {
//                ruleNodeId = new RuleNodeId(new UUID(proto.getRuleNodeIdMSB(), proto.getRuleNodeIdLSB()));
//            }
//
//            TbMsgDataType dataType = TbMsgDataType.values()[proto.getDataType()];
//            return new TbMsg(queueName, UUID.fromString(proto.getId()), proto.getTs(), proto.getType(), entityId, customerId, metaData, dataType, proto.getData(), ruleChainId, ruleNodeId, proto.getRuleNodeExecCounter(), callback);
//        } catch (InvalidProtocolBufferException e) {
//            throw new IllegalStateException("Could not parse protobuf for TbMsg", e);
//        }
//    }

    public TbMsg copyWithRuleChainId(String ruleChainId) {
        return copyWithRuleChainId(ruleChainId, this.id);
    }

    public TbMsg copyWithRuleChainId(String ruleChainId, String msgId) {
        return new TbMsg(this.queueName, msgId, this.ts, this.type, this.originator, this.customerId, this.metaData, this.dataType, this.data, ruleChainId, null, this.ruleNodeExecCounter.get(), callback);
    }

    public TbMsg copyWithRuleNodeId(String ruleChainId, String ruleNodeId, String msgId) {
        return new TbMsg(this.queueName, msgId, this.ts, this.type, this.originator, this.customerId, this.metaData, this.dataType, this.data, ruleChainId, ruleNodeId, this.ruleNodeExecCounter.get(), callback);
    }

    public TbMsgCallback getCallback() {
        //May be null in case of deserialization;
        if (callback != null) {
            return callback;
        } else {
            return TbMsgCallback.EMPTY;
        }
    }

    public String getQueueName() {
        return queueName != null ? queueName : ServiceQueue.MAIN;
    }
}
