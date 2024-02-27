package org.zt.domain.strategy.service.rule;

import org.zt.domain.strategy.model.entity.RuleActionEntity;
import org.zt.domain.strategy.model.entity.RuleMatterEntity;

/**
 * @author: Tao
 * @Date: 2024/02/27 16:48
 * @Description:
 */

public interface ILogicFilter<T extends RuleActionEntity.RaffleEntity> {
    RuleActionEntity<T> filter(RuleMatterEntity ruleMatterEntity);
}
