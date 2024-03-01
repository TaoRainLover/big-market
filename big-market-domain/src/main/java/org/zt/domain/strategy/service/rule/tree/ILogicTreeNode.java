package org.zt.domain.strategy.service.rule.tree;

import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:20
 * @Description: 规则树接口
 */

public interface ILogicTreeNode {
    DefaultTreeFactory.TreeActionEntity logic(String userId, Long StrategyId, Integer awardId);
}
