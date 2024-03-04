package org.zt.domain.strategy.service;

import org.zt.domain.strategy.model.valobj.StrategyAwardStockKeyVO;

/**
 * @author: Tao
 * @Date: 2024/03/03 16:05
 * @Description:
 */

public interface IRaffleStock {

    /**
     * 更新奖品库存消耗记录
     *
     * @param strategyId 策略ID
     * @param awardId    奖品ID
     */
    void updateStrategyAwardStock(Long strategyId, Integer awardId);

    /**
     * 获取奖品库存消耗队列
     *
     * @return 奖品库存Key信息
     * @throws InterruptedException 异常
     */
    StrategyAwardStockKeyVO takeQueueValue() throws InterruptedException;
}
