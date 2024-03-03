package org.zt.test.infrastructure;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zt.infrastructure.persistent.dao.IRuleTreeDao;
import org.zt.infrastructure.persistent.dao.IStrategyRuleDao;
import org.zt.infrastructure.persistent.po.RuleTreePO;
import org.zt.infrastructure.persistent.po.StrategyRulePO;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Tao
 * @description 奖品规则持久化单元测试
 * @create 2024-02-24 20:34
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RuleTreeDaoTest {

    @Resource
    private IRuleTreeDao ruleTreeDao;

    @Test
    public void test_queryAwardList() {
        String treeId = "tree_lock";
        RuleTreePO ruleTreePO = ruleTreeDao.queryRuleTreeByTreeId(treeId);
        log.info("测试结果：{}", JSON.toJSONString(ruleTreePO));
    }

}
