package org.zt.domain.strategy.service.rule.tree.impl;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.zt.domain.strategy.model.valobj.RuleLogicCheckTypeVO;
import org.zt.domain.strategy.model.valobj.StrategyAwardStockKeyVO;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.armory.IStrategyDispatchService;
import org.zt.domain.strategy.service.rule.tree.ILogicTreeNode;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;

import javax.annotation.Resource;

/**
 * @author: Tao
 * @Date: 2024/03/01 15:27
 * @Description: 规则树节点 - 库存管理
 */

@Slf4j
@Component("rule_stock")
public class RuleStockLogicTreeNode implements ILogicTreeNode {
    @Resource
    private IStrategyDispatchService strategyDispatchService;
    @Resource
    private IStrategyRepository strategyRepository;
    @Override
    public DefaultTreeFactory.TreeActionEntity logic(String userId, Long strategyId, Integer awardId, String ruleValue) {
        log.info("规则过滤-库存扣减：userId: {} strategyId: {} awardId: {}", userId, strategyId, awardId);
        // 扣减库存
        Boolean success = strategyDispatchService.subtractionAwardStock(strategyId, awardId);
        // true；库存扣减成功，TAKE_OVER 规则节点接管，返回奖品ID，奖品规则配置
        if (success) {
            log.info("规则过滤-库存扣减-成功：userId: {} strategyId: {} awardId: {}", userId, strategyId, awardId);
            // 写入延迟队列，延迟消费更新数据库记录。【在trigger的job；UpdateAwardStockJob 下消费队列，更新数据库记录】
            strategyRepository.awardStockConsumeSendQueue(StrategyAwardStockKeyVO.builder()
                    .strategyId(strategyId)
                    .awardId(awardId)
                    .build());
            return DefaultTreeFactory.TreeActionEntity.builder()
                    .ruleLogicCheckType(RuleLogicCheckTypeVO.TAKE_OVER)
                    .strategyAwardData(DefaultTreeFactory.StrategyAwardVO.builder().awardId(awardId).awardRuleValue(ruleValue).build())
                    .build();
        }
        // 库存不足
        log.warn("规则过滤-库存扣减-告警，库存不足。userId:{} strategyId:{} awardId:{}", userId, strategyId, awardId);
        return DefaultTreeFactory.TreeActionEntity.builder()
                .ruleLogicCheckType(RuleLogicCheckTypeVO.ALLOW)
                .build();

    }
}
