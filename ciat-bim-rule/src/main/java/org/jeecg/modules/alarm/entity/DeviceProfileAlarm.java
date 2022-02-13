package org.jeecg.modules.alarm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.TreeMap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciat.bim.data.device.AlarmRule;
import com.ciat.bim.data.device.AlarmSeverity;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备报警规则
 * @Author: jeecg-boot
 * @Date:   2022-02-13
 * @Version: V1.0
 */
@ApiModel(value="device_profile_alarm对象", description="设备报警规则")
@Data
@TableName("device_profile_alarm")
public class DeviceProfileAlarm implements Serializable {
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
    @TableField(exist = false)
    private TreeMap<AlarmSeverity, AlarmRule> createRules;
	/**清除规则*/
	@TableField(exist = false)
    private AlarmRule clearRule;
	/**是否传播*/
	@Excel(name = "是否传播", width = 15)
    @ApiModelProperty(value = "是否传播")
    private java.lang.String propagate;
	/**传播关系类型*/
	@Excel(name = "传播关系类型", width = 15)
    @ApiModelProperty(value = "传播关系类型")
    private java.lang.String propagateRelationTypes;


}
