package org.zt.domain.strategy.service.rule.chain.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.service.armory.IStrategyDispatchService;
import org.zt.domain.strategy.service.rule.chain.AbstractLogicChain;
import org.zt.domain.strategy.service.rule.chain.factory.DefaultChainFactory;

import javax.annotation.Resource;

/**
 * @author: Tao
 * @Date: 2024/02/29 13:47
 * @Description: 默认责任链
 */

@Slf4j
@Component("default")
public class DefaultLogicChain extends AbstractLogicChain {
    @Resource
    private IStrategyDispatchService strategyDispatchService;
    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        // 直接根据策略ID进行抽奖
        Integer awardId = strategyDispatchService.getRandomAwardId(strategyId);
        log.info("抽奖责任链-默认处理 userId: {} strategyId: {} ruleModel: {} awardId: {}", userId, strategyId, ruleModel(), awardId);
        return DefaultChainFactory.StrategyAwardVO.builder()
                .awardId(awardId)
                .logicModel(ruleModel())
                .build();
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode();
    }
}
