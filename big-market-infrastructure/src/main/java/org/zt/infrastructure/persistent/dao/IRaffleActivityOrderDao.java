package org.zt.infrastructure.persistent.dao;

import cn.bugstack.middleware.db.router.annotation.DBRouter;
import cn.bugstack.middleware.db.router.annotation.DBRouterStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.zt.infrastructure.persistent.po.RaffleActivityOrderPO;

import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/03/17 14:06
 * @Description:
 */

@Mapper
@DBRouterStrategy(splitTable = true)
public interface IRaffleActivityOrderDao {
    @DBRouter(key = "userId")
    void insert(RaffleActivityOrderPO raffleActivityOrderPO);

    @DBRouter
    List<RaffleActivityOrderPO> queryRaffleActivityOrderByUserId(String userId);
}
