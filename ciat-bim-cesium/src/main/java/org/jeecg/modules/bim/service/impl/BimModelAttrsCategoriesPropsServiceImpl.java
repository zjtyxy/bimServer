package org.jeecg.modules.bim.service.impl;

import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import org.jeecg.modules.bim.mapper.BimModelAttrsCategoriesPropsMapper;
import org.jeecg.modules.bim.service.IBimModelAttrsCategoriesPropsService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 模型属性类别属性
 * @Author: jeecg-boot
 * @Date:   2021-12-20
 * @Version: V1.0
 */
@Service
public class BimModelAttrsCategoriesPropsServiceImpl extends ServiceImpl<BimModelAttrsCategoriesPropsMapper, BimModelAttrsCategoriesProps> implements IBimModelAttrsCategoriesPropsService {
	
	@Autowired
	private BimModelAttrsCategoriesPropsMapper bimModelAttrsCategoriesPropsMapper;
	
	@Override
	public List<BimModelAttrsCategoriesProps> selectByMainId(String mainId) {
		return bimModelAttrsCategoriesPropsMapper.selectByMainId(mainId);
	}
}
