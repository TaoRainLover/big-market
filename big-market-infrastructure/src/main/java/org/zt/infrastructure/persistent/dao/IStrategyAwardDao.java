package org.zt.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zt.infrastructure.persistent.po.StrategyAwardPO;

import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/02/24 20:15
 * @Description:
 */
@Mapper
public interface IStrategyAwardDao {
    List<StrategyAwardPO> queryStrategyAwardList();

    List<StrategyAwardPO> queryStrategyAwardListById(Long strategyId);
}
