package org.jeecg.modules.device.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciat.bim.data.security.DeviceCredentialsType;
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
 * @Description: 认证信息
 * @Author: jeecg-boot
 * @Date:   2022-02-04
 * @Version: V1.0
 */
@Data
@TableName("device_credentials")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="device_credentials对象", description="认证信息")
public class DeviceCredentials implements Serializable {
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
	/**设备*/
	@Excel(name = "设备", width = 15)
    @ApiModelProperty(value = "设备")
    private java.lang.String deviceId;
	/**认证类型*/
	@Excel(name = "认证类型", width = 15)
    @ApiModelProperty(value = "认证类型")
    private DeviceCredentialsType deviceCredentialsType;
	/**认证ID*/
	@Excel(name = "认证ID", width = 15)
    @ApiModelProperty(value = "认证ID")
    private java.lang.String credentialsId;
	/**认证值*/
	@Excel(name = "认证值", width = 15)
    @ApiModelProperty(value = "认证值")
    private java.lang.String credentialsValue;
}
