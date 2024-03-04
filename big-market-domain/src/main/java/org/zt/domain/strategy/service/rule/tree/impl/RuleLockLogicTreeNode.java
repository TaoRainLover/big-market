package org.zt.domain.strategy.service.rule.tree.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

/**
*@author: Tao
*@Date: 2024/03/01 15:25
*@Description: 规则树节点 - 次数锁对象
*/

@Slf4j
@Component("rule_lock")
public class RuleLockLogicTreeNode implements ILogicTreeNode {
    // 用户抽奖次数，后续完成这部分流程开发的时候，从数据库/Redis中读取
    private Long userRaffleCount = 10L;
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-次数锁 userId: {} strategyId: {} awardId: {} ruleValue: {}", userId, strategyId, awardId, ruleValue);
        long raffleCount = 0L;
        try{
            raffleCount = Long.parseLong(ruleValue);
        } catch (Exception e) {
            throw new RuntimeException("规则过滤-次数锁 ruleValue: " + ruleValue + " 配置不正确");
        }
        // 用户抽奖次数大于规定的限定值，规则放行
        if (userRaffleCount >= raffleCount) {
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                    .build();
        }
        // 用户抽奖次数小于规定的限定值，规则拦截
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                .build();
    }
}
