package org.jeecg.modules.rule.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciat.bim.common.data.rule.RuleChainType;
import com.ciat.bim.rule.RuleNodeId;
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
 * @Description: 规则链
 * @Author: jeecg-boot
 * @Date:   2022-01-27
 * @Version: V1.0
 */
@Data
@TableName("rule_chain")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rule_chain对象", description="规则链")
public class RuleChain implements Serializable {
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
	/**所属租户*/
	@Excel(name = "所属租户", width = 15)
    @ApiModelProperty(value = "所属租户")
    private java.lang.String tenantId;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**类型*/
	@Excel(name = "类型", width = 15, dicCode = "rule_chain_type")
	@Dict(dicCode = "rule_chain_type")
    @ApiModelProperty(value = "类型")
    private RuleChainType type;
	/**规则首节点*/
	@Excel(name = "规则首节点", width = 15)
    @ApiModelProperty(value = "规则首节点")
    private java.lang.String ruleNodeId;
	/**是否根节点*/
	@Excel(name = "是否根节点", width = 15)
    @ApiModelProperty(value = "是否根节点")
    private java.lang.String root;
	/**调试模式*/
	@Excel(name = "调试模式", width = 15)
    @ApiModelProperty(value = "调试模式")
    private java.lang.String debugMode;
	/**配置信息*/
	@Excel(name = "配置信息", width = 15)
    @ApiModelProperty(value = "配置信息")
    private java.lang.String configuration;

    public boolean fetchRoot() {
        return  "Y".equals(this.root);
    }

//    public String getFirstRuleNodeId() {
//        return RuleNodeId.fromString(ruleNodeId);
//    }
}
