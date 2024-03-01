package org.zt.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
*@author: Tao
*@Date: 2024/03/01 15:25
*@Description: 规则树节点 - 次数锁对象
*/

@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long StrategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();
    }
}
