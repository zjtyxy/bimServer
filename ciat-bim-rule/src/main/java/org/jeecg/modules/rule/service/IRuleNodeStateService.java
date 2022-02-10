package org.jeecg.modules.rule.service;

import com.ciat.bim.data.id.EntityId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import org.jeecg.modules.rule.entity.RuleNodeState;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 规则节点状态
 * @Author: jeecg-boot
 * @Date:   2022-01-28
 * @Version: V1.0
 */
public interface IRuleNodeStateService extends IService<RuleNodeState> {

    RuleNodeState findByRuleNodeIdAndEntityId(String tenantId, RuleNodeId selfId, EntityId entityId);

    void removeByRuleNodeIdAndEntityId(String tenantId, RuleNodeId selfId, EntityId entityId);

    PageData<RuleNodeState> findByRuleNodeId(String tenantId, RuleNodeId selfId, PageLink pageLink);

    void removeByRuleNodeId(String tenantId, RuleNodeId selfId);
}
