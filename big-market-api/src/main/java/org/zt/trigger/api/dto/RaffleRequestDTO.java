package org.zt.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tao
 * @Date: 2024/03/04 15:17
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RaffleRequestDTO {
    // 策略id
    private Long strategyId;
    // 用户id
    private String userId;
}
