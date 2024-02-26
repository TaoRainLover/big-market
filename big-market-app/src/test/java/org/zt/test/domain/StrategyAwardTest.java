package org.zt.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.domain.strategy.service.armory.IStrategyArmoryService;
import org.zt.domain.strategy.service.armory.IStrategyDispatchService;

import javax.annotation.Resource;

/**
 * @author: Tao
 * @Date: 2024/02/25 18:16
 * @Description:
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyAwardTest {
    @Resource
    private IStrategyArmoryService strategyArmoryService;

    @Resource
    private IStrategyDispatchService strategyDispatchService;

    @Before
    public void test_strategyArmory() {
        boolean status = strategyArmoryService.assembleLotteryStrategy(100001L);
        log.info("装配状态：{}", status);
    }

    @Test
    public void test_getAssembleRandomVal() {
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyDispatchService.getRandomAwardId(100001L));
    }

    /**
     * 根据策略ID+权重值，从装配的策略中随机获取奖品ID值
     */
    @Test
    public void test_getRandomAwardId_ruleWeightValue() {
        // 4000:102,103,104,105 5000:102,103,104,105,106,107 6000:102,103,104,105,106,107,108,109
        log.info("测试结果：{} - 4000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 4000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 4000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "4000:102,103,104,105"));
        log.info("测试结果：{} - 5000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 5000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 5000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "5000:102,103,104,105,106,107"));
        log.info("测试结果：{} - 6000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
        log.info("测试结果：{} - 6000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
        log.info("测试结果：{} - 6000 策略配置", strategyDispatchService.getRandomAwardId(100001L, "6000:102,103,104,105,106,107,108,109"));
    }


}
