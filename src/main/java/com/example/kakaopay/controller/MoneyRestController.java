package com.example.kakaopay.controller;

import com.example.kakaopay.domain.dto.MoneyDto;
import com.example.kakaopay.domain.dto.ReceivedMoneyUserDto;
import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.domain.dto.response.SprinkleRuntimeException;
import com.example.kakaopay.service.MoneyService;
import com.example.kakaopay.service.ReceivedMoneyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import static com.example.kakaopay.domain.dto.response.ErrorCode.ALREADY_ALL_RECEIVED;
import static com.example.kakaopay.domain.dto.response.ErrorCode.NOT_ENOUGH_BALANCE;

@RestController
public class MoneyRestController {
    private final MoneyService moneyService;
    private final ReceivedMoneyUserService receivedMoneyUserService;

    public MoneyRestController(MoneyService moneyService, ReceivedMoneyUserService receivedMoneyUserService) {
        this.moneyService = moneyService;
        this.receivedMoneyUserService = receivedMoneyUserService;
    }

    /**
     * 뿌리기
     * userId
     * roomId
     * budget
     */
    @Operation(summary = "뿌리기 생성", description = "유저 혹은 대화방이 없거나 잔액 부족시 에러 발생")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "400", description = "유저 혹은 대화방을 찾지 못한경우, 잔액 부족"),
    })
    @PostMapping("/api/money")
    public ResultData<MoneyDto> createMoneySprinkle(@RequestHeader("X-USER-ID") Long userId,
                                            @RequestHeader("X-ROOM-ID") Long roomId,
                                            @RequestBody MoneyDto moneyDto) {
        return ResultData.success(moneyService.createMoneySprinkle(userId, roomId, moneyDto));
    }

    /**
     * 받기
     * userId
     * roomId
     */
    @Operation(summary = "돈 받기", description = "유저 혹은 대화방이 없거나 모두 받았을 시 에러 발생")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "유저 혹은 대화방을 찾지 못한경우, 모든 인원이 받았을 경우, 토큰을 찾지 못한 경우, 자신이 뿌린 경우, 이미 받은 유저일 경우, 유효하지 않은 시간일 경우"),
    })
    @PostMapping("/api/money/receive")
    public ResultData<ReceivedMoneyUserDto> receiveMoneySprinkle(@RequestHeader("X-USER-ID") Long userId,
                                                                 @RequestHeader("X-ROOM-ID") Long roomId,
                                                                 @RequestBody MoneyDto moneyDto) {
        if (moneyDto.getBudget() < moneyDto.getQuantity() ) {
            throw new SprinkleRuntimeException(NOT_ENOUGH_BALANCE, "잔액이 부족합니다.");
        }

        ReceivedMoneyUserDto receivedMoneyUserDto = receivedMoneyUserService.receiveMoneySprinkle(moneyDto, userId, roomId);
        // 이미 다 받았을 경우
        if (receivedMoneyUserDto == null) {
            throw new SprinkleRuntimeException(ALREADY_ALL_RECEIVED, "이미 모두 받았습니다.");
        }
        return ResultData.success(receivedMoneyUserDto);
    }

    /**
     * 조회
     * userId
     */
    @Operation(summary = "뿌리기 정보 학인", description = "존재하지 않는 토큰, 뿌린 유저가 아닌 경우, 조회 기간이 만료된 경우 에러 발생")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "존재하지 않는 토큰일 경우, 뿌린 유저가 아닌 경우, 조회 기간이 만료된 경우")
    })
    @GetMapping("/api/money/info")
    public ResultData<MoneyDto> getInfo(@RequestHeader("X-USER-ID") Long userId,
                                        @RequestParam String token) {
        return ResultData.success(moneyService.getMoneySprinkle(userId, token));
    }
}
