package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Tao
 * @description 奖品表
 * @create 2023-12-16 13:21
 */
@Data
public class StrategyPO {

    /** 自增ID */
    private Long id;
    /** 抽奖策略ID */
    private Long strategyId;
    /** 抽奖策略描述 */
    private String strategyDesc;
    /** 规则模型，rule配置的模型同步到此表，便于使用 */
    private String ruleModels;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
