package org.zt.infrastructure.persistent.repository;

import org.springframework.stereotype.Repository;
import org.zt.domain.strategy.model.entity.StrategyAwardEntity;
import org.zt.domain.strategy.model.entity.StrategyEntity;
import org.zt.domain.strategy.model.entity.StrategyRuleEntity;
import org.zt.domain.strategy.model.valobj.*;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.infrastructure.persistent.dao.*;
import org.zt.infrastructure.persistent.po.*;
import org.zt.infrastructure.persistent.redis.IRedisService;
import org.zt.types.common.Constants;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Tao
 * @Date: 2024/02/25 16:24
 * @Description:
 */

@Repository
public class StrategyRepository implements IStrategyRepository {
    @Resource
    private IStrategyAwardDao strategyAwardDao;
    @Resource
    private IStrategyDao strategyDao;
    @Resource
    private IStrategyRuleDao strategyRuleDao;
    @Resource
    private IRedisService redisService;
    @Resource
    private IRuleTreeDao ruleTreeDao;
    @Resource
    private IRuleTreeNodeDao ruleTreeNodeDao;
    @Resource
    private IRuleTreeNodeLineDao ruleTreeNodeLineDao;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardListById(Long strategyId) {
        // 1. 从缓存中进行查询
        String key = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(key);
        if (null != strategyAwardEntities && !strategyAwardEntities.isEmpty()) return strategyAwardEntities;


        // 2. 从数据库中进行查询
        List<StrategyAwardPO> strategyAwardPOList = strategyAwardDao.queryStrategyAwardListById(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwardPOList.size());
        for (StrategyAwardPO strategyAwardPO : strategyAwardPOList) {
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                        .strategyId(strategyAwardPO.getStrategyId())
                        .awardId(strategyAwardPO.getAwardId())
                        .awardCount(strategyAwardPO.getAwardCount())
                        .awardCountSurplus(strategyAwardPO.getAwardCountSurplus())
                        .awardRate(strategyAwardPO.getAwardRate())
                        .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(key, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void storeStrategyAwardSearchRateTable(String strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable) {
        // 1. 存储抽奖策略范围值，如10000，用于生成1000以内的随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId, rateRange);
        // 2. 存储概率查找表
        Map<Integer, Integer> cacheRateTable = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId);
        cacheRateTable.putAll(shuffleStrategyAwardSearchRateTable);
    }

    @Override
    public Integer getRateRange(Long strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public Integer getRateRange(String key) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key);
    }

    @Override
    public Integer getStrategyAwardAssemble(String strategyId, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, rateKey);
    }

    /**
     * 根据策略ID查询策略实体
     * @param strategyId
     * @return
     */
    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 1. 从缓存中进行查询
        String key = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(key);
        if (null != strategyEntity) return strategyEntity;

        // 2. 从数据库中进行查询
        StrategyPO strategyPO = strategyDao.queryStrategyById(strategyId);
        strategyEntity = StrategyEntity.builder()
                .strategyId(strategyPO.getStrategyId())
                .strategyDesc(strategyPO.getStrategyDesc())
                .ruleModels(strategyPO.getRuleModels())
                .build();

        // 3. 将查询结果存放在Redis中
        redisService.setValue(key, strategyEntity);
        return strategyEntity;
    }

    /**
     * 根据策略id以及规则模型查询对应的策略规则实体
     * @param strategyId
     * @param ruleModel
     * @return
     */
    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleModel) {
        StrategyRulePO strategyRuleReq = new StrategyRulePO();
        strategyRuleReq.setStrategyId(strategyId);
        strategyRuleReq.setRuleModel(ruleModel);

        StrategyRulePO strategyRulePO = strategyRuleDao.queryStrategyRule(strategyRuleReq);
        StrategyRuleEntity strategyRuleEntity = StrategyRuleEntity.builder()
                .strategyId(strategyRulePO.getStrategyId())
                .awardId(strategyRulePO.getAwardId())
                .ruleType(strategyRulePO.getRuleType())
                .ruleModel(strategyRulePO.getRuleModel())
                .ruleValue(strategyRulePO.getRuleValue())
                .ruleDesc(strategyRulePO.getRuleDesc())
                .build();
        return strategyRuleEntity;
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, Integer awardId, String ruleModel) {
        StrategyRulePO strategyRule = new StrategyRulePO();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setAwardId(awardId);
        strategyRule.setRuleModel(ruleModel);
        return strategyRuleDao.queryStrategyRuleValue(strategyRule);
    }

    @Override
    public String queryStrategyRuleValue(Long strategyId, String ruleModel) {
        return queryStrategyRuleValue(strategyId, null, ruleModel);
    }

    /**
     * 根据规则树ID，查询树结构信息
     *
     * @param treeId 规则树ID
     * @return 树结构信息
     */
    @Override
    public RuleTreeVO queryRuleTreeVOByTreeId(String treeId) {
        // 优先从缓存获取
        String cacheKey = Constants.RedisKey.RULE_TREE_VO_KEY + treeId;
        RuleTreeVO ruleTreeVOCache = redisService.getValue(cacheKey);
        if (null != ruleTreeVOCache) return ruleTreeVOCache;

        // 从数据库获取
        RuleTreePO ruleTree = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        List<RuleTreeNodePO> ruleTreeNodes = ruleTreeNodeDao.queryRuleTreeNodeListByTreeId(treeId);
        List<RuleTreeNodeLinePO> ruleTreeNodeLines = ruleTreeNodeLineDao.queryRuleTreeNodeLineListByTreeId(treeId);

        // 1. tree node line 转换Map结构
        Map<String, List<RuleTreeNodeLineVO>> ruleTreeNodeLineMap = new HashMap<>();
        for (RuleTreeNodeLinePO ruleTreeNodeLine : ruleTreeNodeLines) {
            RuleTreeNodeLineVO ruleTreeNodeLineVO = RuleTreeNodeLineVO.builder()
                    .treeId(ruleTreeNodeLine.getTreeId())
                    .ruleNodeFrom(ruleTreeNodeLine.getRuleNodeFrom())
                    .ruleNodeTo(ruleTreeNodeLine.getRuleNodeTo())
                    .ruleLimitType(RuleLimitTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitType()))
                    .ruleLimitValue(RuleLogicCheckTypeVO.valueOf(ruleTreeNodeLine.getRuleLimitValue()))
                    .build();

            List<RuleTreeNodeLineVO> ruleTreeNodeLineVOList = ruleTreeNodeLineMap.computeIfAbsent(ruleTreeNodeLine.getRuleNodeFrom(), k -> new ArrayList<>());
            ruleTreeNodeLineVOList.add(ruleTreeNodeLineVO);
        }

        // 2. tree node 转换为Map结构
        Map<String, RuleTreeNodeVO> treeNodeMap = new HashMap<>();
        for (RuleTreeNodePO ruleTreeNode : ruleTreeNodes) {
            RuleTreeNodeVO ruleTreeNodeVO = RuleTreeNodeVO.builder()
                    .treeId(ruleTreeNode.getTreeId())
                    .ruleKey(ruleTreeNode.getRuleKey())
                    .ruleDesc(ruleTreeNode.getRuleDesc())
                    .ruleValue(ruleTreeNode.getRuleValue())
                    .treeNodeLineVOList(ruleTreeNodeLineMap.get(ruleTreeNode.getRuleKey()))
                    .build();
            treeNodeMap.put(ruleTreeNode.getRuleKey(), ruleTreeNodeVO);
        }

        // 3. 构建 Rule Tree
        RuleTreeVO ruleTreeVODB = RuleTreeVO.builder()
                .treeId(ruleTree.getTreeId())
                .treeName(ruleTree.getTreeName())
                .treeDesc(ruleTree.getTreeDesc())
                .treeRootRuleNode(ruleTree.getTreeRootRuleKey())
                .treeNodeMap(treeNodeMap)
                .build();

        redisService.setValue(cacheKey, ruleTreeVODB);
        return ruleTreeVODB;
    }

    @Override
    public StrategyAwardRuleModelVO queryStrategyAwardRuleModelVO(Long strategyId, Integer awardId) {
        StrategyAwardPO strategyAwardPO = new StrategyAwardPO();
        strategyAwardPO.setStrategyId(strategyId);
        strategyAwardPO.setAwardId(awardId);
        String ruleModels = strategyAwardDao.queryStrategyAwardRuleModels(strategyAwardPO);
        return StrategyAwardRuleModelVO.builder().ruleModels(ruleModels).build();
    }
}
