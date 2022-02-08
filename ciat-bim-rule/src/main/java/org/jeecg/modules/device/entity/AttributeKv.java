package org.jeecg.modules.device.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 属性表
 * @Author: jeecg-boot
 * @Date:   2022-01-19
 * @Version: V1.0
 */
@ApiModel(value="attribute_kv对象", description="属性表")
@Data
@TableName("attribute_kv")
public class AttributeKv implements Serializable {
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
	/**属性范围*/
	@Excel(name = "属性范围", width = 15, dicCode = "attr_type")
    @ApiModelProperty(value = "属性范围")
    private java.lang.String entityType;
	/**实体ID*/
    @ApiModelProperty(value = "实体ID")
    private java.lang.String entityId;
	/**属性名称*/
	@Excel(name = "属性名称", width = 15)
    @ApiModelProperty(value = "属性名称")
    private java.lang.String attributeKey;
	/**属性类型*/
	@Excel(name = "属性类型", width = 15, dicCode = "attr_value_type")
    @ApiModelProperty(value = "属性类型")
    private DataType attributeType;
	/**布尔值*/
	@Excel(name = "布尔值", width = 15)
    @ApiModelProperty(value = "布尔值")
    private java.lang.Integer booleanValue;
	/**字符串值*/
	@Excel(name = "字符串值", width = 15)
    @ApiModelProperty(value = "字符串值")
    private java.lang.String strValue;
	/**整数值*/
	@Excel(name = "整数值", width = 15)
    @ApiModelProperty(value = "整数值")
    private java.lang.Long longValue;
	/**双精度值*/
	@Excel(name = "双精度值", width = 15)
    @ApiModelProperty(value = "双精度值")
    private Double doubleValue;
	/**Json值*/
	@Excel(name = "Json值", width = 15)
    @ApiModelProperty(value = "Json值")
    private java.lang.String jsonValue;
	/**最后更新*/
	@Excel(name = "最后更新", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "最后更新")
    private java.util.Date lastupdatets;

    public String getKey() {
        return attributeKey;
    }

    public String getValueAsString() {
        return "";
    }

//    public DataType getDataType() {
//
//        return null;
//    }
    public AttributeKv()
    {

    }
    public AttributeKv(AttributeKv key,Long value)
    {
        this.setLastupdatets(new Date(value));
        this.setEntityId(key.entityId);
        this.setAttributeType(key.getAttributeType());
        this.setAttributeKey(key.getAttributeKey());
        this.setBooleanValue(key.getBooleanValue());
        this.setLongValue(key.getLongValue());
        this.setDoubleValue(key.getDoubleValue());
        this.setStrValue(key.getStrValue());
        this.setJsonValue(key.getJsonValue());
    }
}
