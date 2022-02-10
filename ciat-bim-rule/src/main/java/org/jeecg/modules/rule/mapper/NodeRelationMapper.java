package org.jeecg.modules.rule.mapper;

import java.util.List;
import org.jeecg.modules.rule.entity.NodeRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 节点连接
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
public interface NodeRelationMapper extends BaseMapper<NodeRelation> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<NodeRelation> selectByMainId(@Param("mainId") String mainId);

}
