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
package com.ciat.bim.server.queue.common;


import com.ciat.bim.msg.TbMsgCallback;
import com.ciat.bim.rule.RuleEngineException;
import com.ciat.bim.server.queue.queue.TbQueueCallback;
import com.ciat.bim.server.queue.queue.TbQueueMsgMetadata;

public class TbQueueTbMsgCallbackWrapper implements TbQueueCallback {

    private final TbMsgCallback tbMsgCallback;

    public TbQueueTbMsgCallbackWrapper(TbMsgCallback tbMsgCallback) {
        this.tbMsgCallback = tbMsgCallback;
    }

    @Override
    public void onSuccess(TbQueueMsgMetadata metadata) {
        tbMsgCallback.onSuccess();
    }

    @Override
    public void onFailure(Throwable t) {
        tbMsgCallback.onFailure(new RuleEngineException(t.getMessage()));
    }
}
