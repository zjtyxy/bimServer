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

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.server.edge.gen.AssetUpdateMsg;
import com.ciat.bim.server.edge.gen.UpdateMsgType;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class AssetMsgConstructor {

//    public AssetUpdateMsg constructAssetUpdatedMsg(UpdateMsgType msgType, Asset asset, CustomerId customerId) {
//        AssetUpdateMsg.Builder builder = AssetUpdateMsg.newBuilder()
//                .setMsgType(msgType)
//                .setIdMSB(asset.getId().getId().getMostSignificantBits())
//                .setIdLSB(asset.getId().getId().getLeastSignificantBits())
//                .setName(asset.getName())
//                .setType(asset.getType());
//        if (asset.getLabel() != null) {
//            builder.setLabel(asset.getLabel());
//        }
//        if (customerId != null) {
//            builder.setCustomerIdMSB(customerId.getId().getMostSignificantBits());
//            builder.setCustomerIdLSB(customerId.getId().getLeastSignificantBits());
//        }
//        if (asset.getAdditionalInfo() != null) {
//            builder.setAdditionalInfo(JacksonUtil.toString(asset.getAdditionalInfo()));
//        }
//        return builder.build();
//    }
//
//    public AssetUpdateMsg constructAssetDeleteMsg(AssetId assetId) {
//        return AssetUpdateMsg.newBuilder()
//                .setMsgType(UpdateMsgType.ENTITY_DELETED_RPC_MESSAGE)
//                .setIdMSB(assetId.getId().getMostSignificantBits())
//                .setIdLSB(assetId.getId().getLeastSignificantBits()).build();
//    }
}
