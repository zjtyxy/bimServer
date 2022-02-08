package org.jeecg.modules.alarm.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciat.bim.data.device.AlarmSeverity;
import com.ciat.bim.data.id.EntityId;
import lombok.Builder;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 报警信息
 * @Author: jeecg-boot
 * @Date:   2022-01-25
 * @Version: V1.0
 */
@Data
@TableName("alarm")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="alarm对象", description="报警信息")
@Builder
public class Alarm implements Serializable {
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
	/**租户*/
	@Excel(name = "租户", width = 15)
    @ApiModelProperty(value = "租户")
    private java.lang.String tenantId;
	/**客户*/
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String customerId;
	/**发起者*/
	@Excel(name = "发起者", width = 15)
    @ApiModelProperty(value = "发起者")
    private java.lang.String originatorId;
	/**发起者类型*/
	@Excel(name = "发起者类型", width = 15)
    @ApiModelProperty(value = "发起者类型")
    private java.lang.String originatorType;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private java.lang.String type;
	/**严重程度*/
	@Excel(name = "严重程度", width = 15)
    @ApiModelProperty(value = "严重程度")
    private AlarmSeverity severity;
	/**状态*/
	@Excel(name = "状态", width = 15)
    @ApiModelProperty(value = "状态")
    private AlarmStatus status;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date startTs;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTs;
	/**应答时间*/
	@Excel(name = "应答时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "应答时间")
    private java.util.Date ackTs;
	/**清理时间*/
	@Excel(name = "清理时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "清理时间")
    private java.util.Date clearTs;
	/**处理内容*/
	@Excel(name = "处理内容", width = 15)
    @ApiModelProperty(value = "处理内容")
    private java.lang.String details;
	/**是否传播*/
	@Excel(name = "是否传播", width = 15)
    @ApiModelProperty(value = "是否传播")
    private java.lang.String propagate;
	/**传播关系类型*/
	@Excel(name = "传播关系类型", width = 15)
    @ApiModelProperty(value = "传播关系类型")
    private java.lang.String propagateRelationTypes;

    public EntityId getOriginator() {
        return null;
    }

    public void setOriginator(EntityId originator) {
        this.originatorId =  originator.getId();
        this.originatorType = originator.getEntityType().name();
    }
}
