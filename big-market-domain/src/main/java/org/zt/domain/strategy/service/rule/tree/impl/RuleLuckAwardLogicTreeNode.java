package org.zt.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:26
 * @Description: 规则树节点 - 兜底奖品
 */

@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long StrategyId, Integer awardId) {
        // 兜底奖品
        DefaultTreeFactory.StrategyAwardVO strategyAwardData = DefaultTreeFactory.StrategyAwardVO.builder()
                .awardId(101)
                .awardRuleValue("1,100")
                .build();
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(strategyAwardData)
                .build();

    }
}
