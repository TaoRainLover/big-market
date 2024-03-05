package org.zt.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tao
 * @Date: 2024/03/04 15:12
 * @Description: 抽奖奖品返回应答对象
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardResponseDTO {
    // 奖品ID
    private Integer awardId;
    // 奖品标题
    private String awardTitle;
    // 奖品副标题【抽奖1次后解锁】
    private String awardSubtitle;
    // 排序编号
    private Integer sort;
}
