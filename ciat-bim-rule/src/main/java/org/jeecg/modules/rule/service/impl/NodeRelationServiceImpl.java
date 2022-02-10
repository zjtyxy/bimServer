package org.jeecg.modules.rule.service.impl;

import org.jeecg.modules.rule.entity.NodeRelation;
import org.jeecg.modules.rule.mapper.NodeRelationMapper;
import org.jeecg.modules.rule.service.INodeRelationService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 节点连接
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
@Service
public class NodeRelationServiceImpl extends ServiceImpl<NodeRelationMapper, NodeRelation> implements INodeRelationService {
	
	@Autowired
	private NodeRelationMapper nodeRelationMapper;
	
	@Override
	public List<NodeRelation> selectByMainId(String mainId) {
		return nodeRelationMapper.selectByMainId(mainId);
	}
}
