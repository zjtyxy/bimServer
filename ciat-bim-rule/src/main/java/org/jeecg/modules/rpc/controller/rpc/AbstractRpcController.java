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
package org.jeecg.modules.rpc.controller.rpc;

import com.ciat.bim.server.security.AccessValidator;
import com.ciat.bim.server.security.Operation;
import com.google.common.util.concurrent.FutureCallback;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.rpc.controller.exception.ToErrorResponseEntity;
import com.ciat.bim.data.DataConstants;
import com.ciat.bim.data.id.*;
import com.ciat.bim.server.common.data.exception.ThingsboardErrorCode;
import com.ciat.bim.server.common.data.exception.ThingsboardException;
import com.ciat.bim.server.common.msg.rpc.FromDeviceRpcResponse;
import com.ciat.bim.server.common.msg.rpc.RpcError;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequest;
import com.ciat.bim.server.common.msg.rpc.ToDeviceRpcRequestBody;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import com.ciat.bim.server.rpc.TbCoreDeviceRpcService;
import com.ciat.bim.server.security.SecurityUser;
import com.ciat.bim.server.utils.JacksonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.async.DeferredResult;


import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by ashvayka on 22.03.18.
 */
@TbCoreComponent
@Slf4j
public abstract class AbstractRpcController  {

    @Autowired
    protected TbCoreDeviceRpcService deviceRpcService;

    @Autowired
    protected AccessValidator accessValidator;

    @Value("${server.rest.server_side_rpc.min_timeout:5000}")
    protected long minTimeout;

    @Value("${server.rest.server_side_rpc.default_timeout:10000}")
    protected long defaultTimeout;

    protected Result<?> handleDeviceRPCRequest(boolean oneWay, DeviceId deviceId, String requestBody, HttpStatus timeoutStatus, HttpStatus noActiveConnectionStatus, HttpServletRequest req) throws ThingsboardException {
        try {
            JsonNode rpcRequestBody = JacksonUtil.toJsonNode(requestBody);
            ToDeviceRpcRequestBody body = new ToDeviceRpcRequestBody(rpcRequestBody.get("method").asText(), JacksonUtil.toString(rpcRequestBody.get("params")));
            SecurityUser currentUser = getCurrentUser(req);
            TenantId tenantId = currentUser.getTenantId();
            final Result<ResponseEntity> response = new Result<>();
            long timeout = rpcRequestBody.has(DataConstants.TIMEOUT) ? rpcRequestBody.get(DataConstants.TIMEOUT).asLong() : defaultTimeout;
            long expTime = rpcRequestBody.has(DataConstants.EXPIRATION_TIME) ? rpcRequestBody.get(DataConstants.EXPIRATION_TIME).asLong() : System.currentTimeMillis() + Math.max(minTimeout, timeout);
            UUID rpcRequestUUID = rpcRequestBody.has("requestUUID") ? UUID.fromString(rpcRequestBody.get("requestUUID").asText()) : UUID.randomUUID();
            boolean persisted = rpcRequestBody.has(DataConstants.PERSISTENT) && rpcRequestBody.get(DataConstants.PERSISTENT).asBoolean();
            String additionalInfo =  JacksonUtil.toString(rpcRequestBody.get(DataConstants.ADDITIONAL_INFO));
            Integer retries = rpcRequestBody.has(DataConstants.RETRIES) ? rpcRequestBody.get(DataConstants.RETRIES).asInt() : null;
            accessValidator.validate(currentUser, Operation.RPC_CALL, deviceId, new HttpValidationCallback(response, new FutureCallback<>() {
                @Override
                public void onSuccess(Result<ResponseEntity> result) {
                    ToDeviceRpcRequest rpcRequest = new ToDeviceRpcRequest(rpcRequestUUID,
                            tenantId,
                            deviceId,
                            oneWay,
                            expTime,
                            body,
                            persisted,
                            retries,
                            additionalInfo
                    );

            deviceRpcService.processRestApiRpcRequest(rpcRequest, fromDeviceRpcResponse -> reply(new LocalRequestMetaData(rpcRequest, currentUser, result), fromDeviceRpcResponse, timeoutStatus, noActiveConnectionStatus), currentUser);
                }
                @Override
                public void onFailure(Throwable e) {
                    ResponseEntity entity;
                    if (e instanceof ToErrorResponseEntity) {
                        entity = ((ToErrorResponseEntity) e).toErrorResponseEntity();
                    } else {
                        entity = new ResponseEntity(HttpStatus.UNAUTHORIZED);
                    }
                    logRpcCall(currentUser, deviceId, body, oneWay, Optional.empty(), e);
                    response.setResult(entity);
                }
            }));
            return response;
        } catch (IllegalArgumentException ioe) {
            throw new ThingsboardException("Invalid request body", ioe, ThingsboardErrorCode.BAD_REQUEST_PARAMS);
        }
    }

    private SecurityUser getCurrentUser(HttpServletRequest req) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        SecurityUser securityUser = new SecurityUser(sysUser);
        return  securityUser;
    }

    public void reply(LocalRequestMetaData rpcRequest, FromDeviceRpcResponse response, HttpStatus timeoutStatus, HttpStatus noActiveConnectionStatus) {
        Optional<RpcError> rpcError = response.getError();
        Result<ResponseEntity> responseWriter = rpcRequest.getResponseWriter();
        if (rpcError.isPresent()) {
            logRpcCall(rpcRequest, rpcError, null);
            RpcError error = rpcError.get();
            switch (error) {
                case TIMEOUT:
                    responseWriter.setResult(new ResponseEntity<>(timeoutStatus));
                    break;
                case NO_ACTIVE_CONNECTION:
                    responseWriter.setResult(new ResponseEntity<>(noActiveConnectionStatus));
                    break;
                default:
                    responseWriter.setResult(new ResponseEntity<>(timeoutStatus));
                    break;
            }
        } else {
            Optional<String> responseData = response.getResponse();
            if (responseData.isPresent() && !StringUtils.isEmpty(responseData.get())) {
                String data = responseData.get();
                try {
                    logRpcCall(rpcRequest, rpcError, null);
                    responseWriter.setResult(new ResponseEntity<>(JacksonUtil.toJsonNode(data), HttpStatus.OK));
                } catch (IllegalArgumentException e) {
                    log.debug("Failed to decode device response: {}", data, e);
                    logRpcCall(rpcRequest, rpcError, e);
                    responseWriter.setResult(new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE));
                }
            } else {
                logRpcCall(rpcRequest, rpcError, null);
                responseWriter.setResult(new ResponseEntity<>(HttpStatus.OK));
            }
        }
    }

    private void logRpcCall(LocalRequestMetaData rpcRequest, Optional<RpcError> rpcError, Throwable e) {
        logRpcCall(rpcRequest.getUser(), rpcRequest.getRequest().getDeviceId(), rpcRequest.getRequest().getBody(), rpcRequest.getRequest().isOneway(), rpcError, null);
    }


    private void logRpcCall(SecurityUser user, EntityId entityId, ToDeviceRpcRequestBody body, boolean oneWay, Optional<RpcError> rpcError, Throwable e) {
        String rpcErrorStr = "";
        if (rpcError.isPresent()) {
            rpcErrorStr = "RPC Error: " + rpcError.get().name();
        }
        String method = body.getMethod();
        String params = body.getParams();

//        auditLogService.logEntityAction(
//                user.getTenantId(),
//                user.getCustomerId(),
//                user.getId(),
//                user.getName(),
//                (UUIDBased & EntityId) entityId,
//                null,
//                ActionType.RPC_CALL,
//                BaseController.toException(e),
//                rpcErrorStr,
//                oneWay,
//                method,
//                params);
    }

    protected void checkParameter(String name, String param) throws ThingsboardException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(param)) {
            throw new ThingsboardException("Parameter '" + name + "' can't be empty!", ThingsboardErrorCode.BAD_REQUEST_PARAMS);
        }
    }
    protected  <T> T checkNotNull(T reference) throws ThingsboardException {
        return checkNotNull(reference, "Requested item wasn't found!");
    }
    <T> T checkNotNull(T reference, String notFoundMessage) throws ThingsboardException {
        if (reference == null) {
            throw new ThingsboardException(notFoundMessage, ThingsboardErrorCode.ITEM_NOT_FOUND);
        }
        return reference;
    }

    <T> T checkNotNull(Optional<T> reference) throws ThingsboardException {
        return checkNotNull(reference, "Requested item wasn't found!");
    }

    <T> T checkNotNull(Optional<T> reference, String notFoundMessage) throws ThingsboardException {
        if (reference.isPresent()) {
            return reference.get();
        } else {
            throw new ThingsboardException(notFoundMessage, ThingsboardErrorCode.ITEM_NOT_FOUND);
        }
    }
//    protected Rpc checkRpcId(RpcId rpcId, Operation operation) throws ThingsboardException {
//        try {
//            validateId(rpcId, "Incorrect rpcId " + rpcId);
//            Rpc rpc = rpcService.findById(getCurrentUser().getTenantId(), rpcId);
//            checkNotNull(rpc, "RPC with id [" + rpcId + "] is not found");
//           // accessControlService.checkPermission(getCurrentUser(), Resource.RPC, operation, rpcId, rpc);
//            return rpc;
//        } catch (Exception e) {
//            throw handleException(e, false);
//        }
//    }
}
