package org.zt.domain.strategy.service.armory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.zt.domain.strategy.model.entity.StrategyAwardEntity;
import org.zt.domain.strategy.repository.IStrategyRepository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collector;

/**
 * @author: Tao
 * @Date: 2024/02/25 16:19
 * @Description: 策略装配库(兵工厂)，负责初始化策略计算
 */

@Service
@Slf4j
public class StrategyArmoryService implements IStrategyArmoryService{
    @Resource
    private IStrategyRepository strategyRepository;
    /**
     * 装配抽奖策略配置「触发的时机可以为活动审核通过后进行调用」
     *
     * @param strategyId 策略ID
     * @return 装配结果
     */
    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 1. 查询抽奖策略id下的所有的策略奖品
        List<StrategyAwardEntity> strategyAwardEntityList
         = strategyRepository.queryStrategyAwardListById(strategyId);
        // 2. 获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntityList.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        // 3. 获取概率值总和
        BigDecimal totalAwardRate = strategyAwardEntityList.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 4. 用 1 % 0.0001 获取概率范围
        BigDecimal rateRange = totalAwardRate.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 5. 生成策略奖品概率查找表 「这里指需要在list集合中，存放上对应的奖品占位即可，占位越多等于概率越高」
        List<Integer> strategyAwardSearchRateTables = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity : strategyAwardEntityList) {
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            // 计算出每个概率值需要存放到查找表的数量，循环填充
            for (int i = 0; i < rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue(); i++) {
                strategyAwardSearchRateTables.add(awardId);
            }
        }
        // 6. 对存储的奖品进行乱序操作
        Collections.shuffle(strategyAwardSearchRateTables);

        // 7. 生成出 Map 集合，key 值 对应的就是后续的概率。通过概率来获得对应的奖品ID
        Map<Integer, Integer> shuffleStrategyAwardSearchRateTable = new LinkedHashMap<>();
        for (int i = 0; i < strategyAwardSearchRateTables.size(); i++) {
            shuffleStrategyAwardSearchRateTable.put(i, strategyAwardSearchRateTables.get(i));
        }

        // 8. 存 Redis
        strategyRepository.storeStrategyAwardSearchRateTable(strategyId, shuffleStrategyAwardSearchRateTable.size(), shuffleStrategyAwardSearchRateTable);
        return true;
    }

    /**
     * 获取抽奖策略装配的随机结果
     *
     * @param strategyId 策略ID
     * @return 抽奖结果
     */
    @Override
    public Integer getRandomAwardId(Long strategyId) {
        // 分布式部署下，不一定为当前应用做的策略装配，也就是值不一定会保存在本地，而是分布式应用，所以需要从Redis中进行获取
        int rateRange = strategyRepository.getRateRange(strategyId);
        // 通过生成的随机值，获取概率值奖品查找表的结果
        return strategyRepository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
