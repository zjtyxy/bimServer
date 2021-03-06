package org.jeecg.modules.device.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.function.Supplier;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 设备信息
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
@ApiModel(value="device对象", description="设备信息")
@Data
@TableName("device")
public class Device implements Serializable {
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
	/**租户ID*/
	@Excel(name = "租户ID", width = 15)
    @ApiModelProperty(value = "租户ID")
    private java.lang.String tenantId;
	/**客户*/
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String customerName;
	/**客户*/
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String customerId;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private java.lang.String type;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**标记*/
	@Excel(name = "标记", width = 15)
    @ApiModelProperty(value = "标记")
    private java.lang.String labelT;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private java.lang.String searchText;
	/**附加信息*/
	@Excel(name = "附加信息", width = 15)
    @ApiModelProperty(value = "附加信息")
    private java.lang.String additionalInfo;
	/**设备配置*/
	@Excel(name = "设备配置", width = 15, dictTable = "device_profile", dicText = "name", dicCode = "id")
    @Dict(dictTable = "device_profile", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "设备配置")
    private java.lang.String deviceProfileId;
	/**固件ID*/
	@Excel(name = "固件ID", width = 15)
    @ApiModelProperty(value = "固件ID")
    private java.lang.String firmwareId;
	/**软件ID*/
	@Excel(name = "软件ID", width = 15)
    @ApiModelProperty(value = "软件ID")
    private java.lang.String softwareId;
	/**设备数据*/
	@Excel(name = "设备数据", width = 15)
    @ApiModelProperty(value = "设备数据")
    private java.lang.String deviceData;
    /**凭证类型*/
    @Excel(name = "凭证类型", width = 15, dicCode = "auth_type")
    @Dict(dicCode = "auth_type")
    @ApiModelProperty(value = "凭证类型")
    private java.lang.String authType;
    /**凭证*/
    @Excel(name = "凭证", width = 15)
    @ApiModelProperty(value = "凭证")
    private java.lang.String authToken;
	/**地理数据*/
	@Excel(name = "地理数据", width = 15)
    @ApiModelProperty(value = "地理数据")
    private java.lang.String geoInfo;
    @Excel(name = "所属项目", width = 15, dictTable = "bim_project", dicText = "name", dicCode = "id")
    @Dict(dictTable = "bim_project", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "所属项目")
    private java.lang.String project;
    /**所属建筑*/
    @Excel(name = "所属建筑", width = 15)
    @ApiModelProperty(value = "所属建筑")
    private java.lang.String buiding;
    /**所属房屋*/
    @Excel(name = "所属房屋", width = 15)
    @ApiModelProperty(value = "所属房屋")
    private java.lang.String house;

    /**建筑id*/
    @Excel(name = "建筑id", width = 15)
    @ApiModelProperty(value = "建筑id")
    private java.lang.String buidingId;
    /**房屋id*/
    @Excel(name = "房屋id", width = 15)
    @ApiModelProperty(value = "房屋id")
    private java.lang.String houseId;

    public JsonNode fetchAdditionalInfo() {
        return null;
       // return getJson(() -> additionalInfo, () -> additionalInfoBytes);
    }
    public static final ObjectMapper mapper = new ObjectMapper();
    public static JsonNode getJson(Supplier<JsonNode> jsonData, Supplier<byte[]> binaryData) {
        JsonNode json = jsonData.get();
        if (json != null) {
            return json;
        } else {
            byte[] data = binaryData.get();
            if (data != null) {
                try {
                    return mapper.readTree(new ByteArrayInputStream(data));
                } catch (IOException e) {
                   // log.warn("Can't deserialize json data: ", e);
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
