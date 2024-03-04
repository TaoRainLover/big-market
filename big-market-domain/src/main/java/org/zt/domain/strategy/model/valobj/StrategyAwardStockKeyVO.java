package org.zt.domain.strategy.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Tao
 * @Date: 2024/03/03 15:29
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StrategyAwardStockKeyVO {
    // 策略ID
    private Long strategyId;
    // 奖品ID
    private Integer awardId;
}