package org.zt.domain.strategy.service.armory;

/**
 * @author: Tao
 * @Date: 2024/02/25 16:18
 * @Description:
 */

public interface IStrategyArmoryService {
    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    boolean assembleLotteryStrategy(Long strategyId);
}
