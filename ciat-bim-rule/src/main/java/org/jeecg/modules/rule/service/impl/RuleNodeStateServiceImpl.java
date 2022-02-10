package org.jeecg.modules.rule.service.impl;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import org.jeecg.modules.rule.entity.RuleNodeState;
import org.jeecg.modules.rule.mapper.RuleNodeStateMapper;
import org.jeecg.modules.rule.service.IRuleNodeStateService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 规则节点状态
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
@Service
public class RuleNodeStateServiceImpl extends ServiceImpl<RuleNodeStateMapper, RuleNodeState> implements IRuleNodeStateService {

    @Override
    public RuleNodeState findByRuleNodeIdAndEntityId(String tenantId, RuleNodeId selfId, EntityId entityId) {
        return null;
    }

    @Override
    public void removeByRuleNodeIdAndEntityId(String tenantId, RuleNodeId selfId, EntityId entityId) {

    }

    @Override
    public PageData<RuleNodeState> findByRuleNodeId(String tenantId, RuleNodeId selfId, PageLink pageLink) {
        return null;
    }

    @Override
    public void removeByRuleNodeId(String tenantId, RuleNodeId selfId) {

    }
}
