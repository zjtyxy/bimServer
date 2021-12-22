package org.jeecg.modules.bim.vo;

import java.util.List;
import org.jeecg.modules.bim.entity.BimModelAttrsCategories;
import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 模型参数属性类别
 * @Author: jeecg-boot
 * @Date:   2021-12-20
 * @Version: V1.0
 */
@Data
@ApiModel(value="bim_model_attrs_categoriesPage对象", description="模型参数属性类别")
public class BimModelAttrsCategoriesPage {

	/**主键*/
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
    private java.lang.String name;
	/**数量*/
	@Excel(name = "数量", width = 15)
	@ApiModelProperty(value = "数量")
    private java.lang.String count;
	/**主表ID*/
	@Excel(name = "主表ID", width = 15)
	@ApiModelProperty(value = "主表ID")
    private java.lang.String mainId;

	@ExcelCollection(name="模型属性类别属性")
	@ApiModelProperty(value = "模型属性类别属性")
	private List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList;

}
