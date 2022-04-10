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


import com.ciat.bim.data.id.DeviceId;
import com.ciat.bim.server.common.data.exception.ThingsboardException;
import com.ciat.bim.server.queue.util.TbCoreComponent;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;


import javax.servlet.http.HttpServletRequest;

import static org.jeecg.modules.rpc.controller.rpc.ControllerConstants.*;


@RestController
//@TbCoreComponent
@RequestMapping("/bim/api/rpc")
public class RpcV2Controller extends AbstractRpcController {

    private static final String RPC_REQUEST_DESCRIPTION = "Sends the one-way remote-procedure call (RPC) request to device. " +
            "The RPC call is A JSON that contains the method name ('method'), parameters ('params') and multiple optional fields. " +
            "See example below. We will review the properties of the RPC call one-by-one below. " +
            "\n\n" + MARKDOWN_CODE_BLOCK_START +
            "{\n" +
            "  \"method\": \"setGpio\",\n" +
            "  \"params\": {\n" +
            "    \"pin\": 7,\n" +
            "    \"value\": 1\n" +
            "  },\n" +
            "  \"persistent\": false,\n" +
            "  \"timeout\": 5000\n" +
            "}" +
            MARKDOWN_CODE_BLOCK_END +
            "\n\n### Server-side RPC structure\n" +
            "\n" +
            "The body of server-side RPC request consists of multiple fields:\n" +
            "\n" +
            "* **method** - mandatory, name of the method to distinct the RPC calls.\n" +
            "  For example, \"getCurrentTime\" or \"getWeatherForecast\". The value of the parameter is a string.\n" +
            "* **params** - mandatory, parameters used for processing of the request. The value is a JSON. Leave empty JSON \"{}\" if no parameters needed.\n" +
            "* **timeout** - optional, value of the processing timeout in milliseconds. The default value is 10000 (10 seconds). The minimum value is 5000 (5 seconds).\n" +
            "* **expirationTime** - optional, value of the epoch time (in milliseconds, UTC timezone). Overrides **timeout** if present.\n" +
            "* **persistent** - optional, indicates persistent RPC. The default value is \"false\".\n" +
            "* **retries** - optional, defines how many times persistent RPC will be re-sent in case of failures on the network and/or device side.\n" +
            "* **additionalInfo** - optional, defines metadata for the persistent RPC that will be added to the persistent RPC events.";

    private static final String ONE_WAY_RPC_RESULT = "\n\n### RPC Result\n" +
            "In case of persistent RPC, the result of this call is 'rpcId' UUID. In case of lightweight RPC, " +
            "the result of this call is either 200 OK if the message was sent to device, or 504 Gateway Timeout if device is offline.";

    private static final String TWO_WAY_RPC_RESULT = "\n\n### RPC Result\n" +
            "In case of persistent RPC, the result of this call is 'rpcId' UUID. In case of lightweight RPC, " +
            "the result of this call is the response from device, or 504 Gateway Timeout if device is offline.";

    private static final String ONE_WAY_RPC_REQUEST_DESCRIPTION = "Sends the one-way remote-procedure call (RPC) request to device. " + RPC_REQUEST_DESCRIPTION + ONE_WAY_RPC_RESULT + TENANT_OR_CUSTOMER_AUTHORITY_PARAGRAPH;

    private static final String TWO_WAY_RPC_REQUEST_DESCRIPTION = "Sends the two-way remote-procedure call (RPC) request to device. " + RPC_REQUEST_DESCRIPTION + TWO_WAY_RPC_RESULT + TENANT_OR_CUSTOMER_AUTHORITY_PARAGRAPH;

    @ApiOperation(value = "Send one-way RPC request", notes = ONE_WAY_RPC_REQUEST_DESCRIPTION)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Persistent RPC request was saved to the database or lightweight RPC request was sent to the device."),
//            @ApiResponse(code = 400, message = "Invalid structure of the request."),
//            @ApiResponse(code = 401, message = "User is not authorized to send the RPC request. Most likely, User belongs to different Customer or Tenant."),
//            @ApiResponse(code = 504, message = "Timeout to process the RPC call. Most likely, device is offline."),
//    })
   // @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/oneway/{deviceId}", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> handleOneWayDeviceRPCRequest(
            @ApiParam(value = DEVICE_ID_PARAM_DESCRIPTION)
            @PathVariable("deviceId") String deviceIdStr,
            @ApiParam(value = "A JSON value representing the RPC request.")
            @RequestBody String requestBody, HttpServletRequest req) throws ThingsboardException {

        return handleDeviceRPCRequest(true, new DeviceId(deviceIdStr), requestBody, HttpStatus.GATEWAY_TIMEOUT, HttpStatus.GATEWAY_TIMEOUT,req);
    }

    @RequestMapping(value = "/onewayCmd/{deviceId}", method = RequestMethod.POST)
    @ResponseBody
    public Result<?> handleOneWayDeviceCmdRequest(
            @ApiParam(value = DEVICE_ID_PARAM_DESCRIPTION)
            @PathVariable("deviceId") String deviceIdStr,
            @ApiParam(value = "A JSON value representing the RPC request.")
            @RequestBody String requestBody, HttpServletRequest req) throws ThingsboardException {

        return handleDeviceCmdRequest(true, new DeviceId(deviceIdStr), requestBody, HttpStatus.GATEWAY_TIMEOUT, HttpStatus.GATEWAY_TIMEOUT,req);
    }

    @ApiOperation(value = "Send two-way RPC request", notes = TWO_WAY_RPC_REQUEST_DESCRIPTION)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Persistent RPC request was saved to the database or lightweight RPC response received."),
//            @ApiResponse(code = 400, message = "Invalid structure of the request."),
//            @ApiResponse(code = 401, message = "User is not authorized to send the RPC request. Most likely, User belongs to different Customer or Tenant."),
//            @ApiResponse(code = 504, message = "Timeout to process the RPC call. Most likely, device is offline."),
//    })
   // @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'TENANT_ADMIN', 'CUSTOMER_USER')")
    @RequestMapping(value = "/twoway/{deviceId}", method = RequestMethod.POST)
    @ResponseBody
    public DeferredResult<ResponseEntity> handleTwoWayDeviceRPCRequest(
            @PathVariable(DEVICE_ID) String deviceIdStr,
            @RequestBody String requestBody, HttpServletRequest req) throws ThingsboardException {
        return  new DeferredResult<ResponseEntity>();
       // return handleDeviceRPCRequest(false, new DeviceId(deviceIdStr), requestBody, HttpStatus.GATEWAY_TIMEOUT, HttpStatus.GATEWAY_TIMEOUT,req);
    }

//    @ApiOperation(value = "Get persistent RPC request", notes = "Get information about the status of the RPC call." + TENANT_OR_CUSTOMER_AUTHORITY_PARAGRAPH)
//   // @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
//    @RequestMapping(value = "/persistent/{rpcId}", method = RequestMethod.GET)
//    @ResponseBody
//    public Rpc getPersistedRpc(
//            @ApiParam(value = RPC_ID_PARAM_DESCRIPTION, required = true)
//            @PathVariable(RPC_ID) String strRpc) throws ThingsboardException {
//        checkParameter("RpcId", strRpc);
//        try {
//            RpcId rpcId = new RpcId(strRpc);
//            return checkRpcId(rpcId, Operation.READ);
//        } catch (Exception e) {
//            throw handleException(e);
//        }
//    }

//    @ApiOperation(value = "Get persistent RPC requests", notes = "Allows to query RPC calls for specific device using pagination." + TENANT_OR_CUSTOMER_AUTHORITY_PARAGRAPH)
//   // @PreAuthorize("hasAnyAuthority('TENANT_ADMIN', 'CUSTOMER_USER')")
//    @RequestMapping(value = "/persistent/device/{deviceId}", method = RequestMethod.GET)
//    @ResponseBody
//    public DeferredResult<ResponseEntity> getPersistedRpcByDevice(
//            @ApiParam(value = DEVICE_ID_PARAM_DESCRIPTION, required = true)
//            @PathVariable(DEVICE_ID) String strDeviceId,
//            @ApiParam(value = PAGE_SIZE_DESCRIPTION, required = true)
//            @RequestParam int pageSize,
//            @ApiParam(value = PAGE_NUMBER_DESCRIPTION, required = true)
//            @RequestParam int page,
//            @ApiParam(value = "Status of the RPC", required = true, allowableValues = RPC_STATUS_ALLOWABLE_VALUES)
//            @RequestParam RpcStatus rpcStatus,
//            @ApiParam(value = RPC_TEXT_SEARCH_DESCRIPTION)
//            @RequestParam(required = false) String textSearch,
//            @ApiParam(value = SORT_PROPERTY_DESCRIPTION, allowableValues = RPC_SORT_PROPERTY_ALLOWABLE_VALUES)
//            @RequestParam(required = false) String sortProperty,
//            @ApiParam(value = SORT_ORDER_DESCRIPTION, allowableValues = SORT_ORDER_ALLOWABLE_VALUES)
//            @RequestParam(required = false) String sortOrder) throws ThingsboardException {
//        checkParameter("DeviceId", strDeviceId);
//        try {
//            TenantId tenantId = getCurrentUser().getTenantId();
//            PageLink pageLink = createPageLink(pageSize, page, textSearch, sortProperty, sortOrder);
//            DeviceId deviceId = new DeviceId(UUID.fromString(strDeviceId));
//            final DeferredResult<ResponseEntity> response = new DeferredResult<>();
//            accessValidator.validate(getCurrentUser(), Operation.RPC_CALL, deviceId, new HttpValidationCallback(response, new FutureCallback<>() {
//                @Override
//                public void onSuccess(@Nullable DeferredResult<ResponseEntity> result) {
//                    PageData<Rpc> rpcCalls = rpcService.findAllByDeviceIdAndStatus(tenantId, deviceId, rpcStatus, pageLink);
//                    response.setResult(new ResponseEntity<>(rpcCalls, HttpStatus.OK));
//                }
//
//                @Override
//                public void onFailure(Throwable e) {
//                    ResponseEntity entity;
//                    if (e instanceof ToErrorResponseEntity) {
//                        entity = ((ToErrorResponseEntity) e).toErrorResponseEntity();
//                    } else {
//                        entity = new ResponseEntity(HttpStatus.UNAUTHORIZED);
//                    }
//                    response.setResult(entity);
//                }
//            }));
//            return response;
//        } catch (Exception e) {
//            throw handleException(e);
//        }
//    }

//    @ApiOperation(value = "Delete persistent RPC", notes = "Deletes the persistent RPC request." + TENANT_AUTHORITY_PARAGRAPH)
//   // @PreAuthorize("hasAnyAuthority('TENANT_ADMIN')")
//    @RequestMapping(value = "/persistent/{rpcId}", method = RequestMethod.DELETE)
//    @ResponseBody
//    public void deleteResource(
//            @ApiParam(value = RPC_ID_PARAM_DESCRIPTION, required = true)
//            @PathVariable(RPC_ID) String strRpc) throws ThingsboardException {
//        checkParameter("RpcId", strRpc);
//        try {
//            RpcId rpcId = new RpcId(UUID.fromString(strRpc));
//            Rpc rpc = checkRpcId(rpcId, Operation.DELETE);
//
//            if (rpc != null) {
//                if (rpc.getStatus().equals(RpcStatus.QUEUED)) {
//                    RemoveRpcActorMsg removeMsg = new RemoveRpcActorMsg(getTenantId(), rpc.getDeviceId(), rpc.getUuidId());
//                    log.trace("[{}] Forwarding msg {} to queue actor!", rpc.getDeviceId(), rpc);
//                    tbClusterService.pushMsgToCore(removeMsg, null);
//                }
//
//                rpcService.deleteRpc(getTenantId(), rpcId);
//
//                TbMsg msg = TbMsg.newMsg(RPC_DELETED, rpc.getDeviceId(), TbMsgMetaData.EMPTY, JacksonUtil.toString(rpc));
//                tbClusterService.pushMsgToRuleEngine(getTenantId(), rpc.getDeviceId(), msg, null);
//            }
//        } catch (Exception e) {
//            throw handleException(e);
//        }
//    }
}
