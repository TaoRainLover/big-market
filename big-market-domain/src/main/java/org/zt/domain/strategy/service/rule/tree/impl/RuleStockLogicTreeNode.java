package org.zt.domain.strategy.service.rule.tree.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:27
 * @Description: 规则树节点 - 库存管理
 */

@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long StrategyId, Integer awardId) {
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();

    }
}
