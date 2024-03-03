package org.zt.domain.strategy.service.rule.chain.factory;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zt.domain.strategy.model.entity.StrategyEntity;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.domain.strategy.service.rule.chain.AbstractLogicChain;
import org.zt.domain.strategy.service.rule.chain.ILogicChain;

import java.util.Map;

/**
 * @author: Tao
 * @Date: 2024/02/29 14:21
 * @Description:
 */

@Service
public class DefaultChainFactory {
    private final Map<String, ILogicChain> logicChainGroup;
    protected IStrategyRepository strategyRepository;

    public DefaultChainFactory(Map<String, ILogicChain> logicChainGroup, IStrategyRepository strategyRepository) {
        this.logicChainGroup = logicChainGroup;
        this.strategyRepository = strategyRepository;
    }

    /**
     * 通过策略ID，构建责任链
     * @param strategyId
     * @return
     */
    public ILogicChain openLogicChain(Long strategyId) {
        // 查询对应策略配置的规则模型
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        String[] ruleModels = strategyEntity.ruleModels();

        // 如果未配置策略规则，则只装填一个默认责任链
        if (null == ruleModels || 0 == ruleModels.length) return logicChainGroup.get("default");

        // 按照配置顺序装填用户配置的责任链；rule_blacklist、rule_weight
        // 「注意此数据从Redis缓存中获取，如果更新库表，记得在测试阶段手动处理缓存」
        ILogicChain logicChain = logicChainGroup.get(ruleModels[0]);
        ILogicChain current = logicChain;
        for (int i = 1; i < ruleModels.length; i++) {
            ILogicChain nextChain = logicChainGroup.get(ruleModels[i]);
            current = current.appendNext(nextChain);
        }

        // 责任链最后装填默认责任链
        current.appendNext(logicChainGroup.get("default"));
        // 返回的是责任链的第一个处理逻辑
        return logicChain;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StrategyAwardVO {
        /** 抽奖奖品ID - 内部流转使用 */
        private Integer awardId;
        /**  */
        private String logicModel;
    }

    @Getter
    @AllArgsConstructor
    public enum LogicModel {

        RULE_DEFAULT("rule_default", "默认抽奖"),
        RULE_BLACKLIST("rule_blacklist", "黑名单抽奖"),
        RULE_WEIGHT("rule_weight", "权重规则"),
        ;

        private final String code;
        private final String info;

    }
}
