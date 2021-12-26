package org.jeecg.modules.bim.service;

import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import org.jeecg.modules.bim.entity.BimModelAttrsCategories;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 模型参数属性类别
 * @Author: jeecg-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
public interface IBimModelAttrsCategoriesService extends IService<BimModelAttrsCategories> {

	/**
	 * 添加一对多
	 * 
	 */
	public void saveMain(BimModelAttrsCategories bimModelAttrsCategories,List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList) ;
	
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(BimModelAttrsCategories bimModelAttrsCategories,List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList);
	
	/**
	 * 删除一对多
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
