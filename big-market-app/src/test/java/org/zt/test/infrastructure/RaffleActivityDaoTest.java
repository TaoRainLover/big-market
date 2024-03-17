package org.zt.test.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.infrastructure.persistent.dao.IRaffleActivityDao;
import org.zt.infrastructure.persistent.po.RaffleActivityPO;

import javax.annotation.Resource;
import javax.swing.*;
import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/03/17 14:44
 * @Description:
 */


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RaffleActivityDaoTest {
    @Resource
    private IRaffleActivityDao raffleActivityDao;
    @Test
    public void test_queryRaffleActivityList() {
        List<RaffleActivityPO> raffleActivityList = raffleActivityDao.queryRaffleActivityList();
        System.out.println(raffleActivityList.toString());
    }
}
