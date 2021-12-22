package org.jeecg.modules.bim.service;

import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 模型属性类别属性
 * @Author: jeecg-boot
 * @Date:   2021-12-20
 * @Version: V1.0
 */
public interface IBimModelAttrsCategoriesPropsService extends IService<BimModelAttrsCategoriesProps> {

	public List<BimModelAttrsCategoriesProps> selectByMainId(String mainId);
}
