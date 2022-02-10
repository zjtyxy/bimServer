package org.jeecg.modules.rule.service;

import org.jeecg.modules.rule.entity.NodeRelation;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 节点连接
 * @Author: jeecg-boot
 * @Date:   2022-02-10
 * @Version: V1.0
 */
public interface INodeRelationService extends IService<NodeRelation> {

	public List<NodeRelation> selectByMainId(String mainId);
}
