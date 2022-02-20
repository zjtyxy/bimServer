package org.jeecg.modules.tenant.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 租户
 * @Author: jeecg-boot
 * @Date:   2022-01-21
 * @Version: V1.0
 */
@Data
@TableName("tenant")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tenant对象", description="租户")
public class Tenant implements Serializable {
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
	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    @TableField("name")
    private java.lang.String title;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String searchtext;
	/**地区*/
	@Excel(name = "地区", width = 15)
    @ApiModelProperty(value = "地区")
    private java.lang.String region;
	/**国家*/
	@Excel(name = "国家", width = 15)
    @ApiModelProperty(value = "国家")
    private java.lang.String country;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private java.lang.String address;
	/**备用地址*/
	@Excel(name = "备用地址", width = 15)
    @ApiModelProperty(value = "备用地址")
    private java.lang.String address2;
	/**区号*/
	@Excel(name = "区号", width = 15)
    @ApiModelProperty(value = "区号")
    private java.lang.String zip;
	/**电话*/
	@Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private java.lang.String phone;
	/**电子邮件*/
	@Excel(name = "电子邮件", width = 15)
    @ApiModelProperty(value = "电子邮件")
    private java.lang.String email;
	/**附加信息*/
	@Excel(name = "附加信息", width = 15)
    @ApiModelProperty(value = "附加信息")
    private java.lang.String additionalInfo;
	/**租户配置*/
	@Excel(name = "租户配置", width = 15)
    @ApiModelProperty(value = "租户配置")
    private java.lang.String tenantProfileId;
}
