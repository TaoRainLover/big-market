package org.zt.test.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.domain.strategy.service.armory.IStrategyArmoryService;

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

    @Test
    public void test_strategyArmory() {
        boolean status = strategyArmoryService.assembleLotteryStrategy(100001L);
        log.info("装配状态：{}", status);
    }

    @Test
    public void test_getAssembleRandomVal() {
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
        log.info("测试结果: 奖品ID-{}" , strategyArmoryService.getRandomAwardId(100001L));
    }

}
