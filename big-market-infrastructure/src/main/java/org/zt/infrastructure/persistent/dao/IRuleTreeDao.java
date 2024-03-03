package org.zt.infrastructure.persistent.dao;

import org.apache.ibatis.annotations.Mapper;
import org.zt.infrastructure.persistent.po.RuleTreePO;

/**
 * @author: Tao
 * @Date: 2024/03/02 14:46
 * @Description:
 */

@Mapper
public interface IRuleTreeDao {
    RuleTreePO queryRuleTreeByTreeId(String treeId);
}
