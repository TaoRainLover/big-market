package org.zt.trigger.api;

import org.zt.trigger.api.dto.RaffleAwardRequestDTO;
import org.zt.trigger.api.dto.RaffleAwardResponseDTO;
import org.zt.trigger.api.dto.RaffleRequestDTO;
import org.zt.trigger.api.dto.RaffleResponseDTO;
import org.zt.types.model.Response;

import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/03/04 15:02
 * @Description: 抽奖服务API定义接口
 */

public interface IRaffleService {

    /**
     * 抽奖策略装配
     *
     * @param strategyId
     * @return
     */
    org.zt.types.model.Response<Boolean> raffleStrategyArmory(Long strategyId);

    /**
     * 查询抽奖奖品列表接口
     *
     * @param requestDTO
     * @return
     */
    Response<List<RaffleAwardResponseDTO>> queryRaffleAwardList(RaffleAwardRequestDTO requestDTO);

    /**
     * 随机抽奖接口
     *
     * @param requestDTO
     * @return
     */
    Response<RaffleResponseDTO> randomRaffle(RaffleRequestDTO requestDTO);
}
