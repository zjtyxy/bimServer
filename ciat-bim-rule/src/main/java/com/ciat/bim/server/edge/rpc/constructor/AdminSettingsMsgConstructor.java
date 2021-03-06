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

import com.ciat.bim.server.edge.gen.AdminSettingsUpdateMsg;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.utils.JacksonUtil;
import org.springframework.stereotype.Component;


@Component
@TbCoreComponent
public class AdminSettingsMsgConstructor {

//    public AdminSettingsUpdateMsg constructAdminSettingsUpdateMsg(AdminSettings adminSettings) {
//        AdminSettingsUpdateMsg.Builder builder = AdminSettingsUpdateMsg.newBuilder()
//                .setKey(adminSettings.getKey())
//                .setJsonValue(JacksonUtil.toString(adminSettings.getJsonValue()));
//        if (adminSettings.getId() != null) {
//            builder.setIsSystem(true);
//        }
//        return builder.build();
//    }

}
