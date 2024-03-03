package org.zt.domain.strategy.service.rule.chain.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.rule.chain.AbstractLogicChain;
import org.zt.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import org.zt.types.common.Constants;

import javax.annotation.Resource;

/**
 * @author: Tao
 * @Date: 2024/02/29 13:45
 * @Description: 黑名单责任链
 */

@Slf4j
@Component("rule_blacklist")
public class BlackListLogicChain extends AbstractLogicChain {

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public DefaultChainFactory.StrategyAwardVO logic(String userId, Long strategyId) {
        log.info("抽奖责任链-黑名单-开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        // 查询规则值配置
        String ruleValue = strategyRepository.queryStrategyRuleValue(strategyId, ruleModel());
        if (ruleValue == null || ruleValue.isEmpty()) {
            throw new RuntimeException("策略对应黑名单规则配置错误：该策略配置了blacklist,但是未在数据库中配置对应的黑名单用户信息");
        }
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 黑名单判断
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-黑名单-接管 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
                return DefaultChainFactory.StrategyAwardVO.builder()
                        .awardId(awardId)
                        .logicModel(ruleModel())
                        .build();
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链-黑名单-放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return DefaultChainFactory.LogicModel.RULE_BLACKLIST.getCode();
    }
}
