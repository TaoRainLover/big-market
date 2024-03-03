package org.zt.domain.strategy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.zt.domain.strategy.model.entity.RaffleAwardEntity;
import org.zt.domain.strategy.model.entity.RaffleFactorEntity;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.armory.IStrategyDispatchService;
import org.zt.domain.strategy.service.rule.chain.factory.DefaultChainFactory;
import org.zt.domain.strategy.service.rule.tree.factory.DefaultTreeFactory;
import org.zt.types.enums.ResponseCode;
import org.zt.types.exception.AppException;

/**
 * @author: Tao
 * @Date: 2024/02/27 16:40
 * @Description: 抽奖策略抽象类，定义抽奖的标准流程（模版模式）
 */

@Slf4j
public abstract class AbstractRaffleStrategy implements IRaffleStrategy {
    // 策略仓储服务 -> domain层像一个大厨，仓储层提供米面粮油
    protected IStrategyRepository strategyRepository;
    // 策略调度服务 -> 只负责抽奖处理，通过新增接口的方式，隔离职责，不需要使用方关心或者调用抽奖的初始化
    protected IStrategyDispatchService strategyDispatchService;
    // 抽奖前置规则的责任链
    protected final DefaultChainFactory defaultChainFactory;
    // 抽奖的决策树 -> 负责抽奖中到抽奖后的规则过滤，如抽奖到A奖品ID，之后要做次数的判断和库存的扣减等。
    protected final DefaultTreeFactory defaultTreeFactory;

    public AbstractRaffleStrategy(IStrategyRepository strategyRepository, IStrategyDispatchService strategyDispatchService,
                                  DefaultChainFactory defaultChainFactory, DefaultTreeFactory defaultTreeFactory) {
        this.strategyRepository = strategyRepository;
        this.strategyDispatchService = strategyDispatchService;
        this.defaultChainFactory = defaultChainFactory;
        this.defaultTreeFactory = defaultTreeFactory;
    }

    /**
     * 定义抽奖模版
     * @param raffleFactorEntity 抽奖因子实体对象，根据入参信息计算抽奖结果
     * @return
     */
    @Override
    public RaffleAwardEntity performRaffle(RaffleFactorEntity raffleFactorEntity) {
        // 1. 参数校验
        String userId = raffleFactorEntity.getUserId();
        Long strategyId = raffleFactorEntity.getStrategyId();
        if (null == strategyId || StringUtils.isBlank(userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }


        // 2. 责任链抽奖计算【这步拿到的是初步的抽奖ID，之后需要根据ID处理抽奖】注意；黑名单、权重等非默认抽奖的直接返回抽奖结果
        DefaultChainFactory.StrategyAwardVO chainStrategyAwardVO = raffleLogicChain(strategyId, userId);
        log.info("抽奖策略计算-责任链 {} {} {} {}", userId, strategyId, chainStrategyAwardVO.getAwardId(), chainStrategyAwardVO.getLogicModel());
        // 判断责任链抽奖结果是否是默认奖品
        if (!DefaultChainFactory.LogicModel.RULE_DEFAULT.getCode().equals(chainStrategyAwardVO.getLogicModel())) {
            return RaffleAwardEntity.builder().awardId(chainStrategyAwardVO.getAwardId()).build();
        }

        // 3. 规则树抽奖过滤【奖品ID，会根据抽奖次数判断、库存判断、兜底兜里返回最终的可获得奖品信息】
        DefaultTreeFactory.StrategyAwardVO treeStrategyAwardVO = raffleLogicTree(userId, strategyId, chainStrategyAwardVO.getAwardId());
        log.info("抽奖策略计算-规则树 {} {} {} {}", userId, strategyId, treeStrategyAwardVO.getAwardId(), treeStrategyAwardVO.getAwardRuleValue());

        // 4. 返回抽奖结果
        return RaffleAwardEntity.builder()
                .awardId(treeStrategyAwardVO.getAwardId())
                .awardConfig(treeStrategyAwardVO.getAwardRuleValue())
                .build();
    }

    /**
     * 抽奖计算，责任链抽象方法
     * @param strategyId
     * @param userId
     * @return
     */
    public abstract DefaultChainFactory.StrategyAwardVO raffleLogicChain(Long strategyId, String userId);

    /**
     * 抽奖结果过滤，决策树抽象方法
     * @param userId
     * @param strategyId
     * @param awardId
     * @return
     */
    protected abstract DefaultTreeFactory.StrategyAwardVO raffleLogicTree(String userId, Long strategyId, Integer awardId);

    // protected abstract RuleActionEntity<RuleActionEntity.RaffleBeforeEntity> doCheckRaffleBeforeLogic(RaffleFactorEntity raffleFactorEntity, String... logics);

    // protected abstract RuleActionEntity<RuleActionEntity.RaffleCenterEntity> doCheckRaffleCenterLogic(RaffleFactorEntity build, String... logics);
}
