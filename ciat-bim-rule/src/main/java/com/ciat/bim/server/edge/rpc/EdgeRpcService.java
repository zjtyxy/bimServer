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
package com.ciat.bim.server.edge.rpc;


import com.ciat.bim.data.id.EdgeId;
import com.ciat.bim.data.id.TenantId;
import org.jeecg.modules.edge.entity.Edge;

public interface EdgeRpcService {

    void updateEdge(String tenantId, Edge edge);

    void deleteEdge(String tenantId, EdgeId edgeId);

    void onEdgeEvent(String tenantId, EdgeId edgeId);

    void startSyncProcess(String tenantId, EdgeId edgeId);
}
