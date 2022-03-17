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
 * @Description: 房屋单元
 * @Author: jeecg-boot
 * @Date:   2022-03-17
 * @Version: V1.0
 */
@Data
@TableName("heating_unit")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="heating_unit对象", description="房屋单元")
public class HeatingUnit implements Serializable {
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
	/**所属建筑*/
	@Excel(name = "所属建筑", width = 15, dictTable = "building", dicText = "building_number", dicCode = "id")
	@Dict(dictTable = "building", dicText = "building_number", dicCode = "id")
    @ApiModelProperty(value = "所属建筑")
    private String roomBuiding;
	/**描述*/
	@Excel(name = "描述", width = 15)
    @ApiModelProperty(value = "描述")
    private String name;
	/**供热方式*/
	@Excel(name = "供热方式", width = 15, dicCode = "heating_type")
	@Dict(dicCode = "heating_type")
    @ApiModelProperty(value = "供热方式")
    private String heatingMode;
	/**建筑结构*/
	@Excel(name = "建筑结构", width = 15, dicCode = "building_structure")
	@Dict(dicCode = "building_structure")
    @ApiModelProperty(value = "建筑结构")
    private String buildingStructure;
	/**建筑面积*/
	@Excel(name = "建筑面积", width = 15)
    @ApiModelProperty(value = "建筑面积")
    private String buildingArea;
	/**建筑高度*/
	@Excel(name = "建筑高度", width = 15)
    @ApiModelProperty(value = "建筑高度")
    private Double buildingHeight;
	/**所在楼层*/
	@Excel(name = "所在楼层", width = 15)
    @ApiModelProperty(value = "所在楼层")
    private Integer roomLayer;
	/**所在单元*/
	@Excel(name = "所在单元", width = 15)
    @ApiModelProperty(value = "所在单元")
    private String roomUnit;
	/**基础高度*/
	@Excel(name = "基础高度", width = 15)
    @ApiModelProperty(value = "基础高度")
    private Double baseHeight;
	/**户型图*/
	@Excel(name = "户型图", width = 15)
    @ApiModelProperty(value = "户型图")
    private String roomDiagram;
	/**房间编号*/
	@Excel(name = "房间编号", width = 15)
    @ApiModelProperty(value = "房间编号")
    private String roomNumber;
}
