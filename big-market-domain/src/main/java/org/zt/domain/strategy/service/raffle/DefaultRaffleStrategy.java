package org.zt.domain.strategy.service.raffle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zt.domain.strategy.model.valobj.RuleTreeVO;
import org.zt.domain.strategy.model.valobj.StrategyAwardRuleModelVO;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.AbstractRaffleStrategy;
import org.zt.domain.strategy.service.armory.IStrategyDispatchService;
import org.zt.domain.strategy.service.rule.chain.ILogicChain;
import org.zt.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import org.zt.domain.strategy.service.rule.tree.factory.engine.IDecisionTreeEngine;

/**
 * @author: Tao
 * @Date: 2024/02/27 16:44
 * @Description: 抽奖模版的默认实现类
 */

@Slf4j
@Service
public class DefaultRaffleStrategy extends AbstractRaffleStrategy {

    public DefaultRaffleStrategy(IStrategyRepository repository, IStrategyDispatchService strategyDispatch,
                                 DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        super(repository, strategyDispatch, defaultChainFactory, defaultTreeFactory);
    }


    /**
     * 抽奖前置-逻辑链处理
     * @param strategyId
     * @param userId
     * @return
     */
    @Override
    public DefaultChainFactory.StrategyAwardVO raffleLogicChain(Long strategyId, String userId) {
        // 通过策略ID，构建责任链
        ILogicChain logicChain = defaultChainFactory.openLogicChain(strategyId);
        // 执行并返回责任链抽奖结果
        return logicChain.logic(userId, strategyId);
    }

    /**
     * 抽奖中-规则树处理
     * @param userId
     * @param strategyId
     * @param awardId
     * @return
     */
    @Override
    protected DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId) {
        // 1. 查询 策略&奖品 配置的的规则模型信息
        StrategyAwardRuleModelVO strategyAwardRuleModelVO = strategyRepository.queryStrategyAwardRuleModelVO(strategyId, awardId);
        // 2. 判断是否为空
        if (strategyAwardRuleModelVO == null) {
            return DefaultTreeFactory.StrategyAwardVO.builder()
                    .awardId(awardId)
                    .build();
        }
        RuleTreeVO ruleTreeVO = strategyRepository.queryRuleTreeVOByTreeId(strategyAwardRuleModelVO.getRuleModels());
        if (null == ruleTreeVO) {
            throw new RuntimeException("存在抽奖策略配置的规则模型 Key，未在库表 rule_tree、rule_tree_node、rule_tree_line 配置对应的规则树信息" + strategyAwardRuleModelVO.getRuleModels());
        }
        IDecisionTreeEngine treeEngine = defaultTreeFactory.openLogicTree(ruleTreeVO);
        return treeEngine.process(userId, strategyId, awardId);
    }

}
