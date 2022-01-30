package org.jeecg.modules.rule.entity;

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
 * @Description: 规则节点状态
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
@Data
@TableName("rule_node_state")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rule_node_state对象", description="规则节点状态")
public class RuleNodeState implements Serializable {
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
	/**规则节点*/
	@Excel(name = "规则节点", width = 15)
    @ApiModelProperty(value = "规则节点")
    private java.lang.String ruleNodeId;
	/**实体类型*/
	@Excel(name = "实体类型", width = 15)
    @ApiModelProperty(value = "实体类型")
    private java.lang.String entityType;
	/**实体*/
	@Excel(name = "实体", width = 15)
    @ApiModelProperty(value = "实体")
    private java.lang.String entityId;
	/**状态数据*/
	@Excel(name = "状态数据", width = 15)
    @ApiModelProperty(value = "状态数据")
    private java.lang.String stateData;
}
