package org.zt.test.infrastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.infrastructure.persistent.dao.IStrategyAwardDao;
import org.zt.infrastructure.persistent.dao.IStrategyDao;
import org.zt.infrastructure.persistent.po.StrategyAwardPO;
import org.zt.infrastructure.persistent.po.StrategyPO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tao
 * @description 策略-奖品持久化单元测试
 * @create 2024-02-24 20:36
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyAwardDaoTest {

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Test
    public void test_queryAwardList() {
        List<StrategyAwardPO> strategyAwardList = strategyAwardDao.queryStrategyAwardList();
        log.info("测试结果：{}", JSON.toJSONString(strategyAwardList));
    }

}
