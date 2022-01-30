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
package com.ciat.bim.server.stats;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.msg.MsgType;
import com.ciat.bim.msg.TbActorMsg;
import com.ciat.bim.data.id.TenantId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


@AllArgsConstructor
@Getter
@ToString
public final class StatsPersistMsg implements TbActorMsg {

    private final long messagesProcessed;
    private final long errorsOccurred;
    private final String tenantId;
    private final String entityId;

    @Override
    public MsgType getMsgType() {
        return MsgType.STATS_PERSIST_MSG;
    }

    public boolean isEmpty() {
        return messagesProcessed == 0 && errorsOccurred == 0;
    }

}
