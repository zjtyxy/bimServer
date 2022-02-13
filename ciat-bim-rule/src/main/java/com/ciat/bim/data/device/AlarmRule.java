/**
 * Copyright © 2016-2021 The Thingsboard Authors
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * 报警规则
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ciat.bim.data.device;

import com.ciat.bim.data.id.DashboardId;
import com.ciat.bim.server.common.data.device.profile.AnyTimeSchedule;
import com.ciat.bim.server.common.data.device.profile.DurationAlarmConditionSpec;
import com.ciat.bim.server.common.data.device.profile.SimpleAlarmConditionSpec;
import com.ciat.bim.server.common.data.device.profile.SpecificTimeSchedule;
import com.ciat.bim.server.common.data.query.BooleanFilterPredicate;
import com.ciat.bim.server.common.data.query.NumericFilterPredicate;
import com.ciat.bim.server.common.data.query.StringFilterPredicate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecg.modules.alarm.entity.BimAlarmRule;


import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;

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

    public AlarmRule(BimAlarmRule alarm) {
        this.alarmDetails = alarm.getAlarmDetails();
        switch (alarm.getAlarmSchedule()) {
            case "ANYTIME":
                this.schedule = new AnyTimeSchedule();
                break;
            case "SPECIFIC_TIME":
                this.schedule = new SpecificTimeSchedule();
                break;
        }

        this.condition = new AlarmCondition();
        this.condition.setCondition(new ArrayList<>());
        if(alarm.getFilterPredicate().equals("BETWEEN"))
        {
            this.condition.setSpec(new DurationAlarmConditionSpec());
        }
        else
        {
            this.condition.setSpec(new SimpleAlarmConditionSpec());
        }
        AlarmConditionFilter alarmConditionFilter = new AlarmConditionFilter();
        alarmConditionFilter.setKey(new AlarmConditionFilterKey(AlarmConditionKeyType.ATTRIBUTE,alarm.getAlarmConditionKey()));
        alarmConditionFilter.setValue(alarm.getValue());
        alarmConditionFilter.setValueType(alarm.getValueType());
        switch (alarm.getFilterPredicate()) {
            case "EQUAL":
            case "NOTEQUAL"://这两个要根据值得类型分别创建
                switch(alarm.getValueType())
                {
                    case BOOLEAN:
                        BooleanFilterPredicate booleanFilterPredicate = new BooleanFilterPredicate();
                        booleanFilterPredicate.setOperation(BooleanFilterPredicate.BooleanOperation.valueOf(alarm.getFilterPredicate()));
                        alarmConditionFilter.setPredicate(booleanFilterPredicate);
                        break;
                    case STRING:
                        StringFilterPredicate stringFilterPredicate = new StringFilterPredicate();
                        stringFilterPredicate.setOperation(StringFilterPredicate.StringOperation.valueOf(alarm.getFilterPredicate()));
                        alarmConditionFilter.setPredicate(stringFilterPredicate);
                        break;
                    case NUMERIC:
                    case DATETIME:
                        NumericFilterPredicate numericFilterPredicate = new NumericFilterPredicate();
                        numericFilterPredicate.setOperation(NumericFilterPredicate.NumericOperation.valueOf(alarm.getFilterPredicate()));
                        alarmConditionFilter.setPredicate(numericFilterPredicate);
                        break;
                }
                break;
            case "GREATER":
            case "LESS":
            case "GREATEROREQUAL":
            case "LESSOREQUAL":
                NumericFilterPredicate numericFilterPredicate = new NumericFilterPredicate();
                numericFilterPredicate.setOperation(NumericFilterPredicate.NumericOperation.valueOf(alarm.getFilterPredicate()));
                alarmConditionFilter.setPredicate(numericFilterPredicate);
                break;
            case "STARTSWITH":
            case "ENDSWITH":
            case "CONTAINS":
            case "NOTCONTAINS":
                StringFilterPredicate stringFilterPredicate = new StringFilterPredicate();
                stringFilterPredicate.setOperation(StringFilterPredicate.StringOperation.valueOf(alarm.getFilterPredicate()));
                alarmConditionFilter.setPredicate(stringFilterPredicate);
                break;

        }
        this.condition.getCondition().add(alarmConditionFilter);


    }
}
