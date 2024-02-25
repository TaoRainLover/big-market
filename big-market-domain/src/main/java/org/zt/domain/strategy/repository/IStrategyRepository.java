package org.zt.domain.strategy.repository;

import org.zt.domain.strategy.model.entity.StrategyAwardEntity;

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

    void storeStrategyAwardSearchRateTable(Long strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable);

    Integer getRateRange(Long strategyId);

    Integer getStrategyAwardAssemble(Long strategyId, int rateKey);
}
