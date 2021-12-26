package org.jeecg.modules.bim.mapper;

import java.util.List;
import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 模型属性类别属性
 * @Author: jeecg-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
public interface BimModelAttrsCategoriesPropsMapper extends BaseMapper<BimModelAttrsCategoriesProps> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<BimModelAttrsCategoriesProps> selectByMainId(@Param("mainId") String mainId);
}
