package org.jeecg.modules.rule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ciat.bim.common.data.rule.RuleChainType;
import com.ciat.bim.data.id.TenantId;
import com.ciat.bim.rule.RuleChainId;
import com.ciat.bim.rule.RuleNodeId;
import com.ciat.bim.server.common.data.page.PageData;
import com.ciat.bim.server.common.data.page.PageLink;
import com.ciat.bim.server.common.data.relation.EntityRelation;
import org.jeecg.modules.rule.entity.RuleChain;
import org.jeecg.modules.rule.entity.RuleNode;
import org.jeecg.modules.rule.mapper.RuleChainMapper;
import org.jeecg.modules.rule.service.IRuleChainService;
import org.jeecg.modules.rule.service.IRuleNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 规则链
 * @Author: jeecg-boot
 * @Date:   2022-01-27
 * @Version: V1.0
 */
@Service
public class RuleChainServiceImpl extends ServiceImpl<RuleChainMapper, RuleChain> implements IRuleChainService {
    @Autowired
    private IRuleNodeService ruleNodeService;
    @Override
    public PageData<RuleChain> findTenantRuleChainsByType(String tenantId, RuleChainType core, PageLink link) {

        QueryWrapper<RuleChain>  qw = new QueryWrapper<>();
        qw.eq("tenant_id",tenantId);
        List<RuleChain> rcs = this.list(qw);
        PageData<RuleChain> rst = new PageData<>(rcs,rcs.size(),rcs.size(),false);
        return rst;
    }

    @Override
    public List<RuleNode> getRuleChainNodes(String tenantId, String entityId) {
        QueryWrapper<RuleNode>  qw = new QueryWrapper<>();
      //  qw.eq("tenant_id",tenantId);
        qw.eq("rule_chain_id",entityId);
        return  ruleNodeService.list(qw);
    }

    @Override
    public RuleNode findRuleNodeById(String tenantId, String entityId) {
        return ruleNodeService.getById(entityId);
    }

    @Override
    public List<EntityRelation> getRuleNodeRelations(String sysTenantId, String id) {
        return new ArrayList<EntityRelation>();
    }
}
