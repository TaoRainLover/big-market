package org.zt.domain.strategy.service.rule.filter.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.zt.domain.strategy.model.entity.RuleActionEntity;
import org.zt.domain.strategy.model.entity.RuleMatterEntity;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.annotation.LogicStrategy;
import org.zt.domain.strategy.service.rule.filter.ILogicFilter;
import org.zt.domain.strategy.service.rule.filter.factory.DefaultLogicFactory;

import javax.annotation.Resource;

/**
 * @author: Tao
 * @Date: 2024/02/28 13:46
 * @Description:
 */
@Slf4j
@Component
@LogicStrategy(logicMode = DefaultLogicFactory.LogicModel.RULE_LOCK)
public class RuleLockLogicFilter implements ILogicFilter<RuleActionEntity.RaffleCenterEntity> {
    @Resource
    private IStrategyRepository strategyRepository;
    // 用户抽奖次数 TODO：后续完成这部分流程开发的时候，从数据库/Redis进行读取
    private Long userRaffleCount = 0L;

    /**
     * 过滤规则
     * @param ruleMatterEntity
     * @return
     */
    @Override
    public RuleActionEntity<RuleActionEntity.RaffleCenterEntity> filter(RuleMatterEntity ruleMatterEntity) {
        log.info("规则过滤-次数锁 userId:{} strategyId:{} ruleModel:{}", ruleMatterEntity.getUserId(), ruleMatterEntity.getStrategyId(), ruleMatterEntity.getRuleModel());

        // 1. 根据当前奖品查询规则值配置，抽奖中规则对应的限定值（次数锁）。如1，2，,6
        String ruleValue = strategyRepository.queryStrategyRuleValue(ruleMatterEntity.getStrategyId(), ruleMatterEntity.getAwardId(), ruleMatterEntity.getRuleModel());
        long raffleCount = Long.parseLong(ruleValue);

        // 2. 用户抽奖次数大于规则定的限定值（次数锁），规则放行
        if (userRaffleCount >= raffleCount) {
            return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                    .code(RuleLogicCheckTypeVO.ALLOW.getCode())
                    .info(RuleLogicCheckTypeVO.ALLOW.getInfo())
                    .build();
        }

        // 3. 用户抽奖次数小于规则的限定值（次数锁），规则拦截
        return RuleActionEntity.<RuleActionEntity.RaffleCenterEntity>builder()
                .code(RuleLogicCheckTypeVO.TAKE_OVER.getCode())
                .info(RuleLogicCheckTypeVO.TAKE_OVER.getInfo())
                .build();
    }
}
