package org.zt.domain.strategy.repository;

import org.zt.domain.strategy.model.entity.StrategyAwardEntity;
import org.zt.domain.strategy.model.entity.StrategyEntity;
import org.zt.domain.strategy.model.entity.StrategyRuleEntity;
import org.zt.domain.strategy.model.valobj.RuleTreeVO;
import org.zt.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import org.zt.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

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

    /**
     * 根据规则树ID查询构建规则树值对象
     * @param treeId
     * @return
     */
    RuleTreeVO queryRuleTreeVOByTreeId(String treeId);

    /**
     * 缓存策略下奖品库存
     * @param cacheKey
     * @param awardCount
     */
    void cacheStrategyAwardCount(String cacheKey, Integer awardCount);

    /**
     * 扣减策略下奖品库存
     * @param cacheKey
     * @return
     */
    Boolean subtractionAwardStock(String cacheKey);

    /**
     * 写入奖品库存消费队列
     *
     * @param strategyAwardStockKeyVO 对象值对象
     */
    void awardStockConsumeSendQueue(StrategyAwardStockKeyVO strategyAwardStockKeyVO);

    /**
     * 更新奖品库存消耗
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 获取奖品库存消费队列
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;
}
