package org.jeecg.modules.base.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 小模型库
 * @Author: jeecg-boot
 * @Date:   2021-12-10
 * @Version: V1.0
 */
@Data
@TableName("bim_gtlf_model")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="bim_gtlf_model对象", description="小模型库")
public class BimGtlfModel implements Serializable {
    private static final long serialVersionUID = 1L;

	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**图片*/
	@Excel(name = "图片", width = 15)
    @ApiModelProperty(value = "图片")
    private java.lang.String image;
	/**类型*/

    @TableField(exist = false)
    private java.lang.String type = "model";
	/**模型地址*/
	@Excel(name = "模型地址", width = 15)
    @ApiModelProperty(value = "模型地址")
    private java.lang.String url;
	/**id*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "id")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新时间")
    private java.util.Date updateTime;

    @TableField(exist = false)
	private String edittype;//前台展示用
    @Excel(name = "类型", width = 15, dicCode = "gltf_type")
    @Dict(dicCode = "gltf_type")
    @ApiModelProperty(value = "类型")
    @TableField(value = "type")
    private String classType;

    @ApiModelProperty(value = "默认尺寸")
    private Double defaultSize;
}
