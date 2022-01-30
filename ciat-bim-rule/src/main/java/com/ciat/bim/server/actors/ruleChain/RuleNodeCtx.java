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
package com.ciat.bim.server.actors.ruleChain;

import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.server.actors.TbActorRef;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jeecg.modules.rule.entity.RuleNode;


/**
 * Created by ashvayka on 19.03.18.
 */
@Data
@AllArgsConstructor
final class RuleNodeCtx {
    private final String tenantId;
    private final TbActorRef chainActor;
    private final TbActorRef selfActor;
    private RuleNode self;
}
