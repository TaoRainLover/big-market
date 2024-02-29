package org.zt.domain.strategy.service.rule.chain;

/**
 * @author: Tao
 * @Date: 2024/02/29 13:37
 * @Description: 抽奖策略规则责任链接口: 实现责任链模式
 */

public interface ILogicChain {
    /**
     * 责任链接口
     *
     * @param userId     用户ID
     * @param strategyId 策略ID
     * @return 奖品ID
     */
    Integer logic(String userId, Long strategyId);
    ILogicChain next();
    ILogicChain appendNext(ILogicChain logicChain);
}
