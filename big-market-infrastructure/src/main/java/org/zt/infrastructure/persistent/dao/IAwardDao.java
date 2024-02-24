package org.zt.infrastructure.persistent.dao;

import org.zt.infrastructure.persistent.po.AwardPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Tao
 * @description 奖品表DAO
 * @create 2023-12-16 13:23
 */
@Mapper
public interface IAwardDao {

    List<AwardPO> queryAwardList();

}
