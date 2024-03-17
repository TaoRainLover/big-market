package org.zt.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zt.infrastructure.persistent.po.RaffleActivityPO;

import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/03/17 14:06
 * @Description:
 */

@Mapper
public interface IRaffleActivityDao {
    List<RaffleActivityPO> queryRaffleActivityList();

    RaffleActivityPO queryRaffleActivityByActivityId(Long activityId);

}
