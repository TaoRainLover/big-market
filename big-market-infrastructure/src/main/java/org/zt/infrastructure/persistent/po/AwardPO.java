package org.zt.infrastructure.persistent.po;

import lombok.Data;

import java.util.Date;

/**
 * @author Tao
 * @description 奖品表
 * @create 2023-12-16 13:21
 */
@Data
public class AwardPO {

    /** 自增ID */
    private Long id;
    /** 抽奖奖品ID - 内部流转使用 */
    private Integer awardId;
    /** 奖品对接标识 - 每一个都是一个对应的发奖策略 */
    private String awardKey;
    /** 奖品配置信息 */
    private String awardConfig;
    /** 奖品内容描述 */
    private String awardDesc;
    /** 创建时间 */
    private Date createTime;
    /** 更新时间 */
    private Date updateTime;

}
