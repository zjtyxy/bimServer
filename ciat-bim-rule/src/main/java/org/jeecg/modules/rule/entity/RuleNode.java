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
 * @Description: 规则节点
 * @Author: jeecg-boot
 * @Date:   2022-01-27
 * @Version: V1.0
 */
@Data
@TableName("rule_node")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="rule_node对象", description="规则节点")
public class RuleNode implements Serializable {
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
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private java.lang.String name;
	/**所属规则链*/
	@Excel(name = "所属规则链", width = 15)
    @ApiModelProperty(value = "所属规则链")
    private java.lang.String ruleChainId;
	/**类型*/
	@Excel(name = "类型", width = 15)
    @ApiModelProperty(value = "类型")
    private java.lang.String type;
	/**搜索内容*/
	@Excel(name = "搜索内容", width = 15)
    @ApiModelProperty(value = "搜索内容")
    private java.lang.String searchtext;
	/**附加信息*/
	@Excel(name = "附加信息", width = 15)
    @ApiModelProperty(value = "附加信息")
    private java.lang.String additionalInfo;
	/**调试状态*/
	@Excel(name = "调试状态", width = 15)
    @ApiModelProperty(value = "调试状态")
    private java.lang.String debugmode;
	/**配置信息*/
	@Excel(name = "配置信息", width = 15)
    @ApiModelProperty(value = "配置信息")
    private java.lang.String configuration;
}
