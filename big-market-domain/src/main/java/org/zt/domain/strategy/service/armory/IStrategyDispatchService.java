package org.zt.domain.strategy.service.armory;

/**
 * @author: Tao
 * @Date: 2024/02/26 13:53
 * @Description:
 */

public interface IStrategyDispatchService {
    /**
     * 根据策略id获取抽奖策略装配下的随机结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     *
     * @param strategyId
     * @param ruleWeightValue
     * @return
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

    /**
     * 从Redis缓存中扣减奖品库存
     * @param strategyId
     * @param awardId
     * @return
     */
    Boolean subtractionAwardStock(Long strategyId, Integer awardId);

}
