package org.jeecg.modules.alarm.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciat.bim.data.device.AlarmConditionKeyType;
import com.ciat.bim.data.device.AlarmSeverity;
import com.ciat.bim.data.device.EntityKeyValueType;
import com.ciat.bim.server.common.data.query.DynamicValueSourceType;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
@ApiModel(value="alarm_rule对象", description="报警规则")
@Data
@TableName("alarm_rule")
public class BimAlarmRule implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**主表ID*/
    @ApiModelProperty(value = "主表ID")
    private java.lang.String mainId;
	/**触发条件*/
	@Excel(name = "触发条件", width = 15)
    @ApiModelProperty(value = "触发条件")
    private AlarmConditionKeyType alarmCondition;
	/**触发时间设置*/
	@Excel(name = "触发时间设置", width = 15, dicCode = "alarm_schedule")
    @ApiModelProperty(value = "触发时间设置")
    private java.lang.String alarmSchedule;
	/**报警结果*/
	@Excel(name = "报警结果", width = 15)
    @ApiModelProperty(value = "报警结果")
    private java.lang.String alarmDetails;
	/**设备属性*/
	@Excel(name = "设备属性", width = 15)
    @ApiModelProperty(value = "设备属性")
    private java.lang.String alarmConditionKey;
	/**值类型*/
	@Excel(name = "值类型", width = 15, dicCode = "attr_value_type")
    @ApiModelProperty(value = "值类型")
    private EntityKeyValueType valueType;
	/**设置值*/
	@Excel(name = "设置值", width = 15)
    @ApiModelProperty(value = "设置值")
    private java.lang.String value;
	/**触发条件判断*/
	@Excel(name = "触发条件判断", width = 15, dicCode = "numeric_operation")
    @ApiModelProperty(value = "触发条件判断")
    private java.lang.String filterPredicate;

    @ApiModelProperty(value = "严重程度")
    private AlarmSeverity alarmSeverity;
    @ApiModelProperty(value = "创建和是取消")
    private String isCreate;

    @ApiModelProperty(value = "动态值范围")
    private DynamicValueSourceType dynamicType;

    @ApiModelProperty(value = "动态值来源")
    private String dynamicAttr;
}
