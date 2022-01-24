package org.jeecg.modules.resource.entity;

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
 * @Description: TB资源表
 * @Author: jeecg-boot
 * @Date:   2022-01-24
 * @Version: V1.0
 */
@Data
@TableName("tb_resource")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="tb_resource对象", description="TB资源表")
public class TbResource implements Serializable {
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
    private java.lang.String title;
	/**资源类型*/
	@Excel(name = "资源类型", width = 15)
    @ApiModelProperty(value = "资源类型")
    private java.lang.String resourceType;
	/**资源标识*/
	@Excel(name = "资源标识", width = 15)
    @ApiModelProperty(value = "资源标识")
    private java.lang.String resourceKey;
	/**搜索内容*/
	@Excel(name = "搜索内容", width = 15)
    @ApiModelProperty(value = "搜索内容")
    private java.lang.String searchText;
	/**数据*/
	@Excel(name = "数据", width = 15)
    @ApiModelProperty(value = "数据")
    private java.lang.String data;
}
