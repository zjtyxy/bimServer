package org.jeecg.modules.rule.mapper;

import java.util.List;
import org.jeecg.modules.rule.entity.RuleNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 规则节点
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
public interface RuleNodeMapper extends BaseMapper<RuleNode> {

	public boolean deleteByMainId(@Param("mainId") String mainId);
    
	public List<RuleNode> selectByMainId(@Param("mainId") String mainId);

}
