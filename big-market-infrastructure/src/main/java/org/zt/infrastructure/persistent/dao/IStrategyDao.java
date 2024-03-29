package org.zt.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zt.infrastructure.persistent.po.StrategyPO;

import java.util.List;

@Mapper
public interface IStrategyDao {
    List<StrategyPO> queryStrategyList();

    /**
     * 根据id进行查询
     * @param strategyId
     * @return
     */
    StrategyPO queryStrategyById(Long strategyId);
}
