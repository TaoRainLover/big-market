package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/17 13:17
 * @Description: 抽奖活动表 持久化对象
 */

@Data
public class RaffleActivityPO {
    // 自增ID
    private Long id;
    // 活动ID
    private Long activityId;
    // 活动名称
    private String activityName;
    // 活动描述
    private String activityDesc;
    // 活动开始时间
    private Date beginDateTime;
    // 活动结束时间
    private Date endDateTime;
    // 活动库存总量
    private Integer stockCount;
    // 活动剩余库存
    private Integer stockCountSurplus;
    // 活动参与次数配置ID
    private Long activityCountId;
    // 活动抽奖策略
    private Long strategyId;
    // 活动状态
    private String state;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
