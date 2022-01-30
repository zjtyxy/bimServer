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

import com.ciat.bim.data.id.DashboardId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import javax.validation.Valid;
import java.io.Serializable;

@ApiModel
@Data
public class AlarmRule implements Serializable {

    @Valid
    @ApiModelProperty(position = 1, value = "JSON object representing the alarm rule condition")
    private AlarmCondition condition;
    @ApiModelProperty(position = 2, value = "JSON object representing time interval during which the rule is active")
    private AlarmSchedule schedule;

    @ApiModelProperty(position = 3, value = "String value representing the additional details for an alarm rule")
    private String alarmDetails;
    @ApiModelProperty(position = 4, value = "JSON object with the dashboard Id representing the reference to alarm details dashboard used by mobile application")
    private DashboardId dashboardId;

}
