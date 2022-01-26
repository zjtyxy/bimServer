package org.jeecg.modules.edge.entity;

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
 * @Description: 边缘
 * @Author: jeecg-boot
 * @Date:   2022-01-25
 * @Version: V1.0
 */
@Data
@TableName("edge")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="edge对象", description="边缘")
public class Edge implements Serializable {
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
	/**客户*/
	@Excel(name = "客户", width = 15)
    @ApiModelProperty(value = "客户")
    private java.lang.String customerId;
	/**规则链*/
	@Excel(name = "规则链", width = 15)
    @ApiModelProperty(value = "规则链")
    private java.lang.String rootRuleChainId;
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
    private java.lang.String labelX;
	/**搜索内容*/
	@Excel(name = "搜索内容", width = 15)
    @ApiModelProperty(value = "搜索内容")
    private java.lang.String searchText;
	/**路由键*/
	@Excel(name = "路由键", width = 15)
    @ApiModelProperty(value = "路由键")
    private java.lang.String routingKey;
	/**安全*/
	@Excel(name = "安全", width = 15)
    @ApiModelProperty(value = "安全")
    private java.lang.String secret;
	/**边缘许可键*/
	@Excel(name = "边缘许可键", width = 15)
    @ApiModelProperty(value = "边缘许可键")
    private java.lang.String edgeLicenseKey;
	/**云端*/
	@Excel(name = "云端", width = 15)
    @ApiModelProperty(value = "云端")
    private java.lang.String cloudEndpoint;
	/**附件信息*/
	@Excel(name = "附件信息", width = 15)
    @ApiModelProperty(value = "附件信息")
    private java.lang.String additionalInfo;
}
