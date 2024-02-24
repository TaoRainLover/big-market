package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/02/24 20:27
 * @Description:
 */

@Data
public class StrategyRulePO {

    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖奖品ID【规则类型为策略，则不需要奖品ID】 */
    private Integer awardId;
    /** rule_type */
    private Integer ruleType;
    /** 抽象规则类型；1-策略规则、2-奖品规则 */
    private String ruleModel;
    /** 抽奖规则类型【rule_random - 随机值计算、rule_lock - 抽奖几次后解锁、rule_luck_award - 幸运奖(兜底奖品)】 */
    private String ruleValue;
    /** 抽奖规则比值 */
    private String ruleDesc;
    /** 抽奖规则描述 */
    private Date createTime;
    /** 创建时间 */
    private Date updateTime;

}
