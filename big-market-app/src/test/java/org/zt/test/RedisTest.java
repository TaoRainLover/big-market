package org.zt.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.infrastructure.persistent.redis.IRedisService;

import javax.annotation.Resource;
import javax.swing.*;

/**
 * @author: Tao
 * @Date: 2024/02/25 15:31
 * @Description: redis 连接测试
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private IRedisService redisService;

    @Test
    public void test_redis(){
        RMap<Object, Object> map = redisService.getMap("strategy_id_100001");
        map.put(1, 101);
        map.put(2, 101);
        map.put(3, 101);
        map.put(4, 102);
        map.put(5, 102);
        map.put(6, 103);
        map.put(7, 103);
        log.info("测试结果：{}", redisService.getFromMap("strategy_id_100001", 1).toString());
    }
}
