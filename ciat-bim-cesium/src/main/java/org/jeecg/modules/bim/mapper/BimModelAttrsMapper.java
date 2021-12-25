package org.jeecg.modules.bim.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.bim.entity.BimModelAttrs;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 模型属性
 * @Author: jeecg-boot
 * @Date:   2021-12-20
 * @Version: V1.0
 */
public interface BimModelAttrsMapper extends BaseMapper<BimModelAttrs> {

	/**
	 * 编辑节点状态
	 * @param id
	 * @param status
	 */
	void updateTreeNodeStatus(@Param("id") String id,@Param("status") String status);

	/***
	 * 更新节点关系
	 */
	@Update("UPDATE bim_model_attrs set has_child ='1' where db_id IN (select a.pid from (select distinct parent_id pid from bim_model_attrs) a)")
	void updateTree();


}
