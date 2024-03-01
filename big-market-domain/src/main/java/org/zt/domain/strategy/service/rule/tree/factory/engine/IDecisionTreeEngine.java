package org.zt.domain.strategy.service.rule.tree.factory.engine;

import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:35
 * @Description: 决策树引擎接口
 */

public interface IDecisionTreeEngine {
    DefaultTreeFactory.StrategyAwardData process(String userId, Long strategyId, Integer awardId);
}
