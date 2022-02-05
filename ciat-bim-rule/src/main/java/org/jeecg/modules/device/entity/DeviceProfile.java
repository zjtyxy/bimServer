package org.jeecg.modules.device.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciat.bim.data.device.DeviceTransportType;
import com.ciat.bim.data.device.profile.DeviceProfileData;
import com.ciat.bim.data.device.profile.DeviceProfileTransportConfiguration;
import com.ciat.bim.server.common.data.device.profile.MqttDeviceProfileTransportConfiguration;
import com.fasterxml.jackson.databind.JsonNode;
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
 * @Description: 设备配置
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
@Data
@TableName("device_profile")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="device_profile对象", description="设备配置")
public class DeviceProfile implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private java.lang.String type;
	/**是否缺省*/
	@Excel(name = "是否缺省", width = 15)
    @ApiModelProperty(value = "是否缺省")
    private java.lang.String isDefault;
	/**传输类型*/
	@Excel(name = "传输类型", width = 15, dicCode = "transport_type")
	@Dict(dicCode = "transport_type")
    @ApiModelProperty(value = "传输类型")
    private DeviceTransportType transportType;
	/**供给类型*/
	@Excel(name = "供给类型", width = 15, dicCode = "provision_type")
	@Dict(dicCode = "provision_type")
    @ApiModelProperty(value = "供给类型")
    private java.lang.String provisionType;
	/**设备秘钥*/
	@Excel(name = "设备秘钥", width = 15)
    @ApiModelProperty(value = "设备秘钥")
    private java.lang.String provisionDeviceKey;
	/**缺省面板*/
	@Excel(name = "缺省面板", width = 15)
    @ApiModelProperty(value = "缺省面板")
    private java.lang.String defaultDashboardId;
	/**缺省规则*/
	@Excel(name = "缺省规则", width = 15)
    @ApiModelProperty(value = "缺省规则")
    private java.lang.String defaultRuleChain;
	/**缺省规则*/
	@Excel(name = "缺省规则", width = 15)
    @ApiModelProperty(value = "缺省规则")
    private java.lang.String defaultRuleChainId;
	/**缺省队列*/
	@Excel(name = "缺省队列", width = 15)
    @ApiModelProperty(value = "缺省队列")
    private java.lang.String defaultQueueName;
	/**固件*/
	@Excel(name = "固件", width = 15)
    @ApiModelProperty(value = "固件")
    private java.lang.String firmwareId;
	/**软件*/
	@Excel(name = "软件", width = 15)
    @ApiModelProperty(value = "软件")
    private java.lang.String softwareId;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private java.lang.String image;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String description;
	/**搜索信息*/
	@Excel(name = "搜索信息", width = 15)
    @ApiModelProperty(value = "搜索信息")
    private java.lang.String searchtext;
	/**配置数据*/
	@Excel(name = "配置数据", width = 15)
    @ApiModelProperty(value = "配置数据")
    private java.lang.String profileData;

    public DeviceProfileData fetchProfileData() {
        DeviceProfileData rst = new DeviceProfileData();
        DeviceProfileTransportConfiguration dptc = new MqttDeviceProfileTransportConfiguration();
        rst.setTransportConfiguration(dptc);
        return rst;
        // return getJson(() -> additionalInfo, () -> additionalInfoBytes);
    }
}
