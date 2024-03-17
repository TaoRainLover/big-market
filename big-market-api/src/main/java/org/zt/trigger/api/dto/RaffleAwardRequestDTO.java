package org.zt.trigger.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tao
 * @Date: 2024/03/04 15:12
 * @Description:
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RaffleAwardRequestDTO {
    private Long strategyId;
}
