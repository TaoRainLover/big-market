package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/17 13:27
 * @Description: 抽奖活动次数配置表 持久化对象
 */

@Data
public class RaffleActivityCountPO {
    // 数据库自增ID
    private Long id;
    // 活动次数配置ID
    private Long activityCountId;
    // 总次数
    private Integer totalCount;
    // 日次数
    private Integer dayCount;
    // 月次数
    private Integer monthCount;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;

}
