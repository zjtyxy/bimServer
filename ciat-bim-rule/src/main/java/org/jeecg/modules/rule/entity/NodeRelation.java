package org.jeecg.modules.rule.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jeecg.common.aspect.annotation.Dict;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 节点连接
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
@Data
@TableName("node_relation")
@ApiModel(value="node_relation对象", description="节点连接")
public class NodeRelation implements Serializable {
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
	/**所属规则链*/
    @ApiModelProperty(value = "所属规则链")
    private String mainId;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @Dict(dicCode = "id",dicText = "name",dictTable = "rule_node")
    @ApiModelProperty(value = "来源")
    private String fromId;
	/**来源类型*/
	@Excel(name = "来源类型", width = 15)
    @ApiModelProperty(value = "来源类型")
    private String formType;
	/**目的*/
	@Excel(name = "目的", width = 15)
    @Dict(dicCode = "id",dicText = "name",dictTable = "rule_node")
    @ApiModelProperty(value = "目的")
    private String toId;
	/**目的类型*/
	@Excel(name = "目的类型", width = 15)
    @ApiModelProperty(value = "目的类型")
    private String toType;
	/**关联类型*/
	@Excel(name = "关联类型", width = 15)
    @ApiModelProperty(value = "关联类型")
    private String type;
	/**关联类型组*/
	@Excel(name = "关联类型组", width = 15)
    @ApiModelProperty(value = "关联类型组")
    private String typeGroup;
}
