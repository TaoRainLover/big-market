package org.zt.trigger.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.zt.domain.strategy.model.entity.RaffleAwardEntity;
import org.zt.domain.strategy.model.entity.RaffleFactorEntity;
import org.zt.domain.strategy.model.entity.StrategyAwardEntity;
import org.zt.domain.strategy.service.IRaffleAward;
import org.zt.domain.strategy.service.IRaffleStrategy;
import org.zt.domain.strategy.service.armory.IStrategyArmoryService;
import org.zt.trigger.api.IRaffleService;
import org.zt.trigger.api.dto.RaffleAwardRequestDTO;
import org.zt.trigger.api.dto.RaffleAwardResponseDTO;
import org.zt.trigger.api.dto.RaffleRequestDTO;
import org.zt.trigger.api.dto.RaffleResponseDTO;
import org.zt.types.enums.ResponseCode;
import org.zt.types.exception.AppException;
import org.zt.types.model.Response;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Tao
 * @Date: 2024/03/04 15:05
 * @Description:
 */

@Slf4j
@RestController()
@CrossOrigin("${app.config.cross-origin}")
@RequestMapping("/api/${app.config.api-version}/raffle/")
public class RaffleController implements IRaffleService {
    @Resource
    private IStrategyArmoryService strategyArmoryService;
    @Resource
    private IRaffleStrategy raffleStrategy;
    @Resource
    private IRaffleAward raffleAward;

    /**
     * 抽奖策略装配接口
     * @param strategyId
     * @return
     */
    @GetMapping("strategy_armory")
    @Override
    public Response<Boolean> raffleStrategyArmory(Long strategyId) {
        try {
            log.info("抽奖策略装配开始 strategyId: {}", strategyId);
            boolean status = strategyArmoryService.assembleLotteryStrategy(strategyId);
            Response<Boolean> response = Response.<Boolean>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(status)
                    .build();
            log.info("抽奖策略装配成功 strategyId: {} response: {}", strategyId, status);
            return response;
        } catch (Exception e){
            log.error("抽奖策略装配失败 strategyId: {} error: {}", strategyId, e);
            return Response.<Boolean>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 查询策略的奖品列表
     * @param requestDTO
     * @return
     */
    @PostMapping("/query_raffle_award_list")
    @Override
    public Response<List<RaffleAwardResponseDTO>> queryRaffleAwardList(@RequestBody RaffleAwardRequestDTO requestDTO) {
        try {
            log.info("查询抽奖奖品列表 {}", requestDTO);
            // 查询奖品配置
            List<StrategyAwardEntity> strategyAwardEntities = raffleAward.queryRaffleStrategyAwardList(requestDTO.getStrategyId());
            List<RaffleAwardResponseDTO> raffleAwardResponseDTOList = new ArrayList<>(strategyAwardEntities.size());
            for (StrategyAwardEntity strategyAward : strategyAwardEntities) {
                raffleAwardResponseDTOList.add(RaffleAwardResponseDTO.builder()
                        .awardId(strategyAward.getAwardId())
                        .awardTitle(strategyAward.getAwardTitle())
                        .awardSubtitle(strategyAward.getAwardSubtitle())
                        .sort(strategyAward.getSort())
                        .build());
            }
            // 返回查询结果
            Response<List<RaffleAwardResponseDTO>> response = Response.<List<RaffleAwardResponseDTO>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(raffleAwardResponseDTOList)
                    .build();
            return response;
        } catch (Exception e) {
            log.error("查询抽奖奖品列表配置失败 strategyId：{}", requestDTO.getStrategyId(), e);
            return Response.<List<RaffleAwardResponseDTO>>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }

    /**
     * 随机抽奖接口
     * @param requestDTO
     * @return
     */
    @PostMapping("random_raffle")
    @Override
    public Response<RaffleResponseDTO> randomRaffle(@RequestBody RaffleRequestDTO requestDTO) {
        try {
            log.info("随机抽奖开始 strategyId: {}", requestDTO.getStrategyId());
            // 调用抽奖接口
            RaffleFactorEntity raffleFactorEntity = RaffleFactorEntity.builder()
                    .strategyId(requestDTO.getStrategyId())
                    .userId(requestDTO.getUserId())
                    .build();
            RaffleAwardEntity raffleAwardEntity = raffleStrategy.performRaffle(raffleFactorEntity);
            // 封装返回结果
            Response<RaffleResponseDTO> response = Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(RaffleResponseDTO.builder()
                            .awardId(raffleAwardEntity.getAwardId())
                            .awardIndex(raffleAwardEntity.getSort())
                            .build())
                    .build();
            return response;
        } catch (AppException e) {
            log.error("随机抽奖失败 strategyId：{} {}", requestDTO.getStrategyId(), e.getInfo());
            return Response.<RaffleResponseDTO>builder()
                    .code(e.getCode())
                    .info(e.getInfo())
                    .build();
        } catch (Exception e) {
            log.error("随机抽奖失败 strategyId：{}", requestDTO.getStrategyId());
            return Response.<RaffleResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(ResponseCode.UN_ERROR.getInfo())
                    .build();
        }
    }
}
