package org.zt.test.infrastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.jeasy.random.EasyRandom;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.infrastructure.persistent.dao.IRaffleActivityOrderDao;
import org.zt.infrastructure.persistent.po.RaffleActivityOrderPO;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/03/17 19:47
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RaffleActivityOrderDaoTest {
    @Resource
    private IRaffleActivityOrderDao raffleActivityOrderDao;
    private final EasyRandom easyRandom = new EasyRandom();


    @Test
    public void test_insert() {
        RaffleActivityOrderPO raffleActivityOrderPO = new RaffleActivityOrderPO();
        raffleActivityOrderPO.setUserId("zt");
        raffleActivityOrderPO.setActivityId(10001L);
        raffleActivityOrderPO.setActivityName("测试活动数据");
        raffleActivityOrderPO.setStrategyId(100001L);
        raffleActivityOrderPO.setOrderId("102");
        raffleActivityOrderPO.setOrderTime(new Date());
        raffleActivityOrderPO.setState("not_used");
        raffleActivityOrderDao.insert(raffleActivityOrderPO);
    }

    @Test
    public void test_insertBatch() {
        for (int i = 0; i < 50; i++) {
            RaffleActivityOrderPO raffleActivityOrderPO = new RaffleActivityOrderPO();
            raffleActivityOrderPO.setUserId(easyRandom.nextObject(String.class));
            raffleActivityOrderPO.setActivityId(10001L);
            raffleActivityOrderPO.setActivityName("测试活动数据");
            raffleActivityOrderPO.setStrategyId(100001L);
            raffleActivityOrderPO.setOrderId(RandomStringUtils.randomNumeric(12));
            raffleActivityOrderPO.setOrderTime(new Date());
            raffleActivityOrderPO.setState("not_used");
            raffleActivityOrderDao.insert(raffleActivityOrderPO);
        }
    }


    @Test
    public void test_queryRaffleActivityOrderListByUserId() {
        String userId = "zt";
        List<RaffleActivityOrderPO> raffleActivityOrderPOList = raffleActivityOrderDao.queryRaffleActivityOrderByUserId(userId);
        System.out.println(JSON.toJSONString(raffleActivityOrderPOList));
    }
}
