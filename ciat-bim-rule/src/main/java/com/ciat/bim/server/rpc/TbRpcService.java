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
package com.ciat.bim.server.rpc;

import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.data.id.RpcId;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.msg.TbMsg;
import com.ciat.bim.msg.TbMsgMetaData;
import com.ciat.bim.server.cluster.TbClusterService;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.utils.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@TbCoreComponent
@Service
@RequiredArgsConstructor
@Slf4j
public class TbRpcService {
    //private final RpcService rpcService;
    private final TbClusterService tbClusterService;

//    public Rpc save(TenantId tenantId, Rpc rpc) {
//        Rpc saved = rpcService.save(rpc);
//        pushRpcMsgToRuleEngine(tenantId, saved);
//        return saved;
//    }

    public void save(TenantId tenantId, RpcId rpcId, RpcStatus newStatus, JsonNode response) {
//        Rpc foundRpc = rpcService.findById(tenantId, rpcId);
//        if (foundRpc != null) {
//            foundRpc.setStatus(newStatus);
//            if (response != null) {
//                foundRpc.setResponse(response);
//            }
//            Rpc saved = rpcService.save(foundRpc);
//            pushRpcMsgToRuleEngine(tenantId, saved);
//        } else {
//            log.warn("[{}] Failed to update RPC status because RPC was already deleted", rpcId);
//        }
    }

    private void pushRpcMsgToRuleEngine(TenantId tenantId, Rpc rpc) {
//        TbMsg msg = TbMsg.newMsg("RPC_" + rpc.getStatus().name(), rpc.getDeviceId(), TbMsgMetaData.EMPTY, JacksonUtil.toString(rpc));
//        tbClusterService.pushMsgToRuleEngine(tenantId, rpc.getDeviceId(), msg, null);
    }

//    public Rpc findRpcById(TenantId tenantId, RpcId rpcId) {
//        return rpcService.findById(tenantId, rpcId);
//    }
//
//    public PageData<Rpc> findAllByDeviceIdAndStatus(TenantId tenantId, DeviceId deviceId, RpcStatus rpcStatus, PageLink pageLink) {
//        return rpcService.findAllByDeviceIdAndStatus(tenantId, deviceId, rpcStatus, pageLink);
//    }

}
