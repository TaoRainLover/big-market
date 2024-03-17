package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: Tao
 * @Date: 2024/03/17 13:49
 * @Description: 抽奖活动账户流水表 持久化对象
 */

@Data
public class RaffleActivityAccountFlowPO {
    // 自增ID（使用了分库分表之后，不同表中的自增ID可能存在相同）
    private Long id;
    // 用户ID
    private String userId;
    // 活动ID
    private Long activityId;
    // 总次数
    private Integer totalCount;
    // 日次数
    private Integer dayCount;
    // 月次数
    private Integer monthCount;
    // 流水ID-生成的唯一ID
    private String flowId;
    // 流水渠道（activity-活动领取、sale-购买、redeem-兑换、free-免费赠送）
    private String flowChannel;
    // 业务ID（外部透传，活动ID、订单ID）
    private String bizId;
    // 创建时间
    private Date createTime;
    // 更新时间
    private Date updateTime;
}
