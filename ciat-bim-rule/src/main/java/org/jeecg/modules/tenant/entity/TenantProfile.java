package org.jeecg.modules.tenant.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 住户配置
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Data
@TableName("tenant_profile")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tenant_profile对象", description="住户配置")
public class TenantProfile implements Serializable {
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
	/**配置名称*/
	@Excel(name = "配置名称", width = 15)
    @ApiModelProperty(value = "配置名称")
    private java.lang.String name;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String description;
	/**搜索内容*/
	@Excel(name = "搜索内容", width = 15)
    @ApiModelProperty(value = "搜索内容")
    private java.lang.String searchtext;
	/**是否缺省*/
	@Excel(name = "是否缺省", width = 15)
    @ApiModelProperty(value = "是否缺省")
    private java.lang.String isdefault;
	/**是否核心*/
	@Excel(name = "是否核心", width = 15)
    @ApiModelProperty(value = "是否核心")
    private java.lang.String isolatedTbCore;
	/**是否规则引擎*/
	@Excel(name = "是否规则引擎", width = 15)
    @ApiModelProperty(value = "是否规则引擎")
    private java.lang.String isolatedTbRuleEngine;
	/**配置数据*/
	@Excel(name = "配置数据", width = 15)
    @ApiModelProperty(value = "配置数据")
    private java.lang.String profileData;
}
