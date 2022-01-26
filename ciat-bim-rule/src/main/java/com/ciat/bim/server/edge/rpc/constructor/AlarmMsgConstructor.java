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
package com.ciat.bim.server.edge.rpc.constructor;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.edge.gen.AlarmUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.utils.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class AlarmMsgConstructor {

//    @Autowired
//    private DeviceService deviceService;
//
//    @Autowired
//    private AssetService assetService;
//
//    @Autowired
//    private EntityViewService entityViewService;
//
//    public AlarmUpdateMsg constructAlarmUpdatedMsg(TenantId tenantId, UpdateMsgType msgType, Alarm alarm) {
//        String entityName = null;
//        switch (alarm.getOriginator().getEntityType()) {
//            case DEVICE:
//                entityName = deviceService.findDeviceById(tenantId, new DeviceId(alarm.getOriginator().getId())).getName();
//                break;
//            case ASSET:
//                entityName = assetService.findAssetById(tenantId, new AssetId(alarm.getOriginator().getId())).getName();
//                break;
//            case ENTITY_VIEW:
//                entityName = entityViewService.findEntityViewById(tenantId, new EntityViewId(alarm.getOriginator().getId())).getName();
//                break;
//        }
//        AlarmUpdateMsg.Builder builder = AlarmUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(alarm.getId().getId().getMostSignificantBits())
//                .setIdLSB(alarm.getId().getId().getLeastSignificantBits())
//                .setName(alarm.getName())
//                .setType(alarm.getType())
//                .setOriginatorName(entityName)
//                .setOriginatorType(alarm.getOriginator().getEntityType().name())
//                .setSeverity(alarm.getSeverity().name())
//                .setStatus(alarm.getStatus().name())
//                .setStartTs(alarm.getStartTs())
//                .setEndTs(alarm.getEndTs())
//                .setAckTs(alarm.getAckTs())
//                .setClearTs(alarm.getClearTs())
//                .setDetails(JacksonUtil.toString(alarm.getDetails()))
//                .setPropagate(alarm.isPropagate());
//        return builder.build();
//    }

}
