package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/17 13:46
 * @Description: 抽检活动单 PO对象
 */

@Data
public class RaffleActivityOrderPO {
    // 自增ID
    private Long id;
    // 用户ID
    private String userId;
    // 活动ID
    private Long activityId;
    // 活动名字
    private String activityName;
    // 策略ID
    private Long strategyId;
    // 订单ID
    private String orderId;
    // 下单时间
    private Date orderTime;
    // 订单状态(not_used, used, expire)
    private String state;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
