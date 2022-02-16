package org.jeecg.modules.device.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
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
 * @Description: 设备遥测数据
 * @Author: jeecg-boot
 * @Date:   2022-02-16
 * @Version: V1.0
 */
@Data
@TableName("ts_kv")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ts_kv对象", description="设备遥测数据")
public class TsKv implements KvEntry {
    private static final int MAX_CHARS_PER_DATA_POINT = 512;
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    @ApiModelProperty(value = "实体ID")
    @TableField(value = "entity_id")
    private java.lang.String entityId;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**属性名*/
	@Excel(name = "属性名", width = 15)
    @ApiModelProperty(value = "属性名")
    private java.lang.String entityKey;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date ts;
	/**开关类型值*/
	@Excel(name = "开关类型值", width = 15)
    @ApiModelProperty(value = "开关类型值")
    private java.lang.String booleanValue;
	/**字符串值*/
	@Excel(name = "字符串值", width = 15)
    @ApiModelProperty(value = "字符串值")
    private java.lang.String strValue;
	/**整形值*/
	@Excel(name = "整形值", width = 15)
    @ApiModelProperty(value = "整形值")
    private java.lang.Long longValue;
	/**双精度值*/
	@Excel(name = "双精度值", width = 15)
    @ApiModelProperty(value = "双精度值")
    private java.lang.Double doubleValue;

    /**JSON值*/
    @Excel(name = "JSON值", width = 15)
    @ApiModelProperty(value = "JSON值")
    private java.lang.String jsonValue;

    private  DataType  dataType;
    public  TsKv()
    {}

    public TsKv(KvEntry kv, long ts) {
    }

    @Override
    public String getKey() {
        return this.entityKey;
    }

    @Override
    public String getValueAsString() {
        return null;
    }

    @Override
    public Object getValue() {
        return false;
    }

    public int getDataPoints() {
        int length;
        switch (getDataType()) {
            case STRING:
                length = getStrValue().length();
                break;
            case JSON:
                length = getJsonValue().length();
                break;
            default:
                return 1;
        }
        return Math.max(1, (length + MAX_CHARS_PER_DATA_POINT - 1) / MAX_CHARS_PER_DATA_POINT);
    }
}
