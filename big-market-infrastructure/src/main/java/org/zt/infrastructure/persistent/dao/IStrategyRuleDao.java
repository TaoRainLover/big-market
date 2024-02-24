package org.zt.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zt.infrastructure.persistent.po.StrategyRulePO;

import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/02/24 20:15
 * @Description: StrategyRule 数据库操作接口
 */

@Mapper
public interface IStrategyRuleDao {
    List<StrategyRulePO> queryStrategyRuleList();
}
