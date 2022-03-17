package org.jeecg.modules.project.entity;

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
 * @Description: 楼栋信息
 * @Author: jeecg-boot
 * @Date:   2022-03-17
 * @Version: V1.0
 */
@Data
@TableName("building")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="building对象", description="楼栋信息")
public class Building implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**所属项目*/
	@Excel(name = "所属项目", width = 15, dictTable = "bim_project", dicText = "name", dicCode = "id")
	@Dict(dictTable = "bim_project", dicText = "name", dicCode = "id")
    @ApiModelProperty(value = "所属项目")
    private java.lang.String project;
	/** 楼栋编号*/
	@Excel(name = " 楼栋编号", width = 15)
    @ApiModelProperty(value = " 楼栋编号")
    private java.lang.String buildingNumber;
	/**单元数量*/
	@Excel(name = "单元数量", width = 15)
    @ApiModelProperty(value = "单元数量")
    private java.lang.Integer unitsCounts;
	/**模型类型*/
	@Excel(name = "模型类型", width = 15, dicCode = "building_model_type")
	@Dict(dicCode = "building_model_type")
    @ApiModelProperty(value = "模型类型")
    private java.lang.String modelType;
}
