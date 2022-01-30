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
package com.ciat.bim.data.id;

import com.ciat.bim.msg.EntityType;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleNodeId;


import java.util.UUID;

/**
 * Created by ashvayka on 25.04.17.
 */
public class EntityIdFactory {

    public static EntityId getByTypeAndId(String type, String uuid) {
        return getByTypeAndUuid2(EntityType.valueOf(type), uuid);
    }

    public static EntityId getByTypeAndUuid(String type, String uuid) {
        return getByTypeAndUuid2(EntityType.valueOf(type), uuid);
    }

    public static EntityId getByTypeAndUuid(EntityType type, String uuid) {
        return getByTypeAndUuid2(type, uuid);
    }

    public static EntityId getByTypeAndUuid2(EntityType type, String uuid) {
        switch (type) {
            case TENANT:
                return new TenantId(uuid);
            case CUSTOMER:
                return new CustomerId(uuid);
//            case USER:
//                return new UserId(uuid);
//            case DASHBOARD:
//                return new DashboardId(uuid);
//            case DEVICE:
//                return new DeviceId(uuid);
//            case ASSET:
//                return new AssetId(uuid);
//            case ALARM:
//                return new AlarmId(uuid);
//            case RULE_CHAIN:
//                return new RuleChainId(uuid);
//            case RULE_NODE:
//                return new RuleNodeId(uuid);
//            case ENTITY_VIEW:
//                return new EntityViewId(uuid);
//            case WIDGETS_BUNDLE:
//                return new WidgetsBundleId(uuid);
//            case WIDGET_TYPE:
//                return new WidgetTypeId(uuid);
//            case DEVICE_PROFILE:
//                return new DeviceProfileId(uuid);
//            case TENANT_PROFILE:
//                return new TenantProfileId(uuid);
//            case API_USAGE_STATE:
//                return new ApiUsageStateId(uuid);
//            case TB_RESOURCE:
//                return new TbResourceId(uuid);
//            case OTA_PACKAGE:
//                return new OtaPackageId(uuid);
//            case EDGE:
//                return new EdgeId(uuid);
//            case RPC:
//                return new RpcId(uuid);
        }
        throw new IllegalArgumentException("EntityType " + type + " is not supported!");
    }

//    public static EntityId getByEdgeEventTypeAndUuid(EdgeEventType edgeEventType, UUID uuid) {
//        switch (edgeEventType) {
//            case CUSTOMER:
//                return new CustomerId(uuid);
//            case USER:
//                return new UserId(uuid);
//            case DASHBOARD:
//                return new DashboardId(uuid);
//            case DEVICE:
//                return new DeviceId(uuid);
//            case DEVICE_PROFILE:
//                return new DeviceProfileId(uuid);
//            case ASSET:
//                return new AssetId(uuid);
//            case ALARM:
//                return new AlarmId(uuid);
//            case RULE_CHAIN:
//                return new RuleChainId(uuid);
//            case ENTITY_VIEW:
//                return new EntityViewId(uuid);
//            case WIDGETS_BUNDLE:
//                return new WidgetsBundleId(uuid);
//            case WIDGET_TYPE:
//                return new WidgetTypeId(uuid);
//            case EDGE:
//                return new EdgeId(uuid);
//        }
//        throw new IllegalArgumentException("EdgeEventType " + edgeEventType + " is not supported!");
//    }

}
