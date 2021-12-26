package org.jeecg.modules.bim.service.impl;

import org.jeecg.modules.bim.entity.BimModelAttrsCategories;
import org.jeecg.modules.bim.entity.BimModelAttrsCategoriesProps;
import org.jeecg.modules.bim.mapper.BimModelAttrsCategoriesPropsMapper;
import org.jeecg.modules.bim.mapper.BimModelAttrsCategoriesMapper;
import org.jeecg.modules.bim.service.IBimModelAttrsCategoriesService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 模型参数属性类别
 * @Author: jeecg-boot
 * @Date:   2021-12-25
 * @Version: V1.0
 */
@Service
public class BimModelAttrsCategoriesServiceImpl extends ServiceImpl<BimModelAttrsCategoriesMapper, BimModelAttrsCategories> implements IBimModelAttrsCategoriesService {

	@Autowired
	private BimModelAttrsCategoriesMapper bimModelAttrsCategoriesMapper;
	@Autowired
	private BimModelAttrsCategoriesPropsMapper bimModelAttrsCategoriesPropsMapper;
	
	@Override
	@Transactional
	public void saveMain(BimModelAttrsCategories bimModelAttrsCategories, List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList) {
		bimModelAttrsCategoriesMapper.insert(bimModelAttrsCategories);
		if(bimModelAttrsCategoriesPropsList!=null && bimModelAttrsCategoriesPropsList.size()>0) {
			for(BimModelAttrsCategoriesProps entity:bimModelAttrsCategoriesPropsList) {
				//外键设置
				entity.setMainId(bimModelAttrsCategories.getId());
				bimModelAttrsCategoriesPropsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void updateMain(BimModelAttrsCategories bimModelAttrsCategories,List<BimModelAttrsCategoriesProps> bimModelAttrsCategoriesPropsList) {
		bimModelAttrsCategoriesMapper.updateById(bimModelAttrsCategories);
		
		//1.先删除子表数据
		bimModelAttrsCategoriesPropsMapper.deleteByMainId(bimModelAttrsCategories.getId());
		
		//2.子表数据重新插入
		if(bimModelAttrsCategoriesPropsList!=null && bimModelAttrsCategoriesPropsList.size()>0) {
			for(BimModelAttrsCategoriesProps entity:bimModelAttrsCategoriesPropsList) {
				//外键设置
				entity.setMainId(bimModelAttrsCategories.getId());
				bimModelAttrsCategoriesPropsMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional
	public void delMain(String id) {
		bimModelAttrsCategoriesPropsMapper.deleteByMainId(id);
		bimModelAttrsCategoriesMapper.deleteById(id);
	}

	@Override
	@Transactional
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			bimModelAttrsCategoriesPropsMapper.deleteByMainId(id.toString());
			bimModelAttrsCategoriesMapper.deleteById(id);
		}
	}
	
}
