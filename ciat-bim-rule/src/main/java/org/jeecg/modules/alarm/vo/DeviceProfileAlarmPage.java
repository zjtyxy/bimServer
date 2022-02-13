package org.jeecg.modules.alarm.vo;

import java.util.List;

import org.jeecg.modules.alarm.entity.BimAlarmRule;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
@Data
@ApiModel(value="device_profile_alarmPage对象", description="设备报警规则")
public class DeviceProfileAlarmPage {

	/**主键*/
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
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**设备配置*/
	@Excel(name = "设备配置", width = 15, dictTable = "device_profile", dicText = "name", dicCode = "id")
    @Dict(dictTable = "device_profile", dicText = "name", dicCode = "id")
	@ApiModelProperty(value = "设备配置")
    private java.lang.String mainId;
	/**报警类型*/
	@Excel(name = "报警类型", width = 15)
	@ApiModelProperty(value = "报警类型")
    private java.lang.String alarmType;
	/**创建规则*/
	@Excel(name = "创建规则", width = 15)
	@ApiModelProperty(value = "创建规则")
    private java.lang.String createRules;
	/**清除规则*/
	@Excel(name = "清除规则", width = 15)
	@ApiModelProperty(value = "清除规则")
    private java.lang.String clearRule;
	/**是否传播*/
	@Excel(name = "是否传播", width = 15)
	@ApiModelProperty(value = "是否传播")
    private java.lang.String propagate;
	/**传播关系类型*/
	@Excel(name = "传播关系类型", width = 15)
	@ApiModelProperty(value = "传播关系类型")
    private java.lang.String propagateRelationTypes;

	@ExcelCollection(name="报警规则")
	@ApiModelProperty(value = "报警规则")
	private List<BimAlarmRule> alarmRuleList;

}
