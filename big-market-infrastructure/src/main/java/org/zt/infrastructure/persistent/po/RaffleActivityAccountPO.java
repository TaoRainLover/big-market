package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/17 13:16
 * @Description: 抽奖活动用户账户表 持久化对象
 */

@Data
public class RaffleActivityAccountPO {
    // 数据库自增ID
    private Long id;
    // 用户ID
    private String userId;
    // 活动策略ID
    private Long activityId;
    // 总次数
    private Integer totalCount;
    // 总次数-剩余
    private Integer totalCountSurplus;
    // 日次数
    private Integer dayCount;
    // 日次数-剩余
    private Integer dayCountSurplus;
    // 月次数
    private Integer monthCount;
    // 月次数-剩余
    private Integer monthCountSurplus;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
