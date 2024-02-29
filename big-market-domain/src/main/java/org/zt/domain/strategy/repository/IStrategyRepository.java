package org.zt.domain.strategy.repository;

import org.zt.domain.strategy.model.entity.StrategyAwardEntity;
import org.zt.domain.strategy.model.entity.StrategyEntity;
import org.zt.domain.strategy.model.entity.StrategyRuleEntity;
import org.zt.domain.strategy.model.valobj.StrategyAwardRuleModelVO;

import java.util.List;
import java.util.Map;

/**
 * @author: Tao
 * @Date: 2024/02/25 16:21
 * @Description:
 */

public interface IStrategyRepository {
    /**
     * 查询对应id下的策略奖品
     * @param strategyId
     * @return
     */
    List<StrategyAwardEntity> queryStrategyAwardListById(Long strategyId);

    void storeStrategyAwardSearchRateTable(String strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable);

    Integer getRateRange(Long strategyId);

    Integer getRateRange(String key);

    Integer getStrategyAwardAssemble(String strategyId, int rateKey);

    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);

    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleWeight);

    String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel);

    StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId);

    String queryStrategyRuleValue(Long strategyId, String ruleModel);
}
