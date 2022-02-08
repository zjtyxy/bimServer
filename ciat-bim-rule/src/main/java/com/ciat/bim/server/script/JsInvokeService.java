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
package com.ciat.bim.server.script;

import com.ciat.bim.data.id.CustomerId;
import com.ciat.bim.data.id.TenantId;
import com.google.common.util.concurrent.ListenableFuture;


import java.util.UUID;

public interface JsInvokeService {

    ListenableFuture<UUID> eval(TenantId tenantId, JsScriptType scriptType, String scriptBody, String... argNames);

    ListenableFuture<Object> invokeFunction(TenantId tenantId, CustomerId customerId, UUID scriptId, Object... args);

    ListenableFuture<Void> release(UUID scriptId);

}
