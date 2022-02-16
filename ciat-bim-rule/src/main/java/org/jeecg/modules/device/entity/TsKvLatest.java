package org.jeecg.modules.device.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @Description: 最新遥测数据
 * @Author: jeecg-boot
 * @Date:   2022-02-16
 * @Version: V1.0
 */
@Data
@TableName("ts_kv_latest")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ts_kv_latest对象", description="最新遥测数据")
public class TsKvLatest implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String entityKey;
	/**更新日期*/
	@Excel(name = "更新日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date ts;
	/**开关值*/
	@Excel(name = "开关值", width = 15)
    @ApiModelProperty(value = "开关值")
    private java.lang.String booleanValue;
	/**整形值*/
	@Excel(name = "整形值", width = 15)
    @ApiModelProperty(value = "整形值")
    private java.lang.Integer longValue;
	/**字符串值*/
	@Excel(name = "字符串值", width = 15)
    @ApiModelProperty(value = "字符串值")
    private java.lang.String strValue;
	/**双精度值*/
	@Excel(name = "双精度值", width = 15)
    @ApiModelProperty(value = "双精度值")
    private java.lang.Double doubleValue;
	/**JSON值*/
	@Excel(name = "JSON值", width = 15)
    @ApiModelProperty(value = "JSON值")
    private java.lang.String jsonValue;


}
