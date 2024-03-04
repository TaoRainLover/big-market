package org.zt.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import org.zt.types.common.Constants;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:26
 * @Description: 规则树节点 - 兜底奖品
 */

@Slf4j
@Component("rule_luck_award")
public class RuleLuckAwardLogicTreeNode implements ILogicTreeNode {
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-兜底奖品 userId: {} strategyId: {} awardId: {} ruleValue: {}", userId, strategyId, awardId, ruleValue);
        // 解析兜底奖品
        String[] split = ruleValue.split(Constants.COLON);
        if (split.length == 0) {
            log.error("规则过滤-兜底奖品，兜底奖品未配置告警 userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
            throw new RuntimeException("兜底奖品未配置 " + ruleValue);
        }
        Integer luckAwardId = Integer.valueOf(split[0]);
        String awardRuleValue = split.length > 1 ? split[1] : "";

        // 返回兜底奖品
        DefaultTreeFactory.StrategyAwardVO strategyAwardData = DefaultTreeFactory.StrategyAwardVO.builder()
                .awardId(luckAwardId)
                .awardRuleValue(awardRuleValue)
                .build();
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .strategyAwardData(strategyAwardData)
                .build();

    }
}
