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
package com.ciat.bim.data.device;
import com.ciat.bim.data.id.DeviceId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.device.entity.Device;

@ApiModel
@Data
public class DeviceInfo extends Device {

    @ApiModelProperty(position = 13, value = "Title of the Customer that owns the device.", readOnly = true)
    private String customerTitle;
    @ApiModelProperty(position = 14, value = "Indicates special 'Public' Customer that is auto-generated to use the devices on public dashboards.", readOnly = true)
    private boolean customerIsPublic;
    @ApiModelProperty(position = 15, value = "Name of the corresponding Device Profile.", readOnly = true)
    private String deviceProfileName;

//    public DeviceInfo() {
//        super();
//    }
//
//    public DeviceInfo(DeviceId deviceId) {
//        super(deviceId);
//    }
//
//    public DeviceInfo(Device device, String customerTitle, boolean customerIsPublic, String deviceProfileName) {
//        super(device);
//        this.customerTitle = customerTitle;
//        this.customerIsPublic = customerIsPublic;
//        this.deviceProfileName = deviceProfileName;
//    }
}
