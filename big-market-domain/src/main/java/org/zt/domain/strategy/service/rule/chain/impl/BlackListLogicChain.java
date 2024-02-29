package org.zt.domain.strategy.service.rule.chain.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.rule.chain.AbstractLogicChain;
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
    public Integer logic(String userId, Long strategyId) {
        log.info("抽奖责任链-黑名单-开始 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        // 查询规则值配置
        String ruleValue = strategyRepository.queryStrategyRuleValue(strategyId, ruleModel());
        String[] splitRuleValue = ruleValue.split(Constants.COLON);
        Integer awardId = Integer.parseInt(splitRuleValue[0]);

        // 黑名单判断
        String[] userBlackIds = splitRuleValue[1].split(Constants.SPLIT);
        for (String userBlackId : userBlackIds) {
            if (userId.equals(userBlackId)) {
                log.info("抽奖责任链-黑名单-接管 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
                return awardId;
            }
        }

        // 过滤其他责任链
        log.info("抽奖责任链-黑名单-放行 userId: {} strategyId: {} ruleModel: {}", userId, strategyId, ruleModel());
        return next().logic(userId, strategyId);
    }

    @Override
    protected String ruleModel() {
        return "rule_blacklist";
    }
}
