package org.jeecg.modules.rule.service;

import com.ciat.bim.common.data.rule.RuleChainType;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.common.data.relation.EntityRelation;
import org.jeecg.modules.rule.entity.RuleChain;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.rule.entity.RuleNode;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 规则链
 * @Author: jeecg-boot
 * @Date:   2022-01-27
 * @Version: V1.0
 */
public interface IRuleChainService extends IService<RuleChain> {

    PageData<RuleChain> findTenantRuleChainsByType(String tenantId, RuleChainType core, PageLink link);

    List<RuleNode> getRuleChainNodes(String tenantId, String entityId);

    RuleNode findRuleNodeById(String tenantId, String entityId);

    List<EntityRelation> getRuleNodeRelations(String sysTenantId, String id);

    /**
     * 删除一对多
     */
    public void delMain (String id);

    /**
     * 批量删除一对多
     */
    public void delBatchMain (Collection<? extends Serializable> idList);

}
