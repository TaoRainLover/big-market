package org.zt.infrastructure.persistent.repository;

import org.springframework.stereotype.Repository;
import org.zt.domain.strategy.model.entity.StrategyAwardEntity;
import org.zt.domain.strategy.repository.IStrategyRepository;
import org.zt.infrastructure.persistent.dao.IStrategyAwardDao;
import org.zt.infrastructure.persistent.po.StrategyAwardPO;
import org.zt.infrastructure.persistent.redis.IRedisService;
import org.zt.types.common.Constants;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private IRedisService redisService;

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
    public void storeStrategyAwardSearchRateTable(Long strategyId, int rateRange, Map<Integer, Integer> shuffleStrategyAwardSearchRateTable) {
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
    public Integer getStrategyAwardAssemble(Long strategyId, int rateKey) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, rateKey);
    }
}
