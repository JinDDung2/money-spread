package com.example.kakaopay.controller;

import com.example.kakaopay.domain.dto.MoneyDto;
import com.example.kakaopay.domain.dto.ReceivedMoneyUserDto;
import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.domain.dto.response.SprinkleRuntimeException;
import com.example.kakaopay.service.MoneyService;
import com.example.kakaopay.service.ReceivedMoneyUserService;
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
    @GetMapping("/api/money/info")
    public ResultData<MoneyDto> getInfo(@RequestHeader("X-USER-ID") Long userId,
                                        @RequestParam String token) {
        return ResultData.success(moneyService.getMoneySprinkle(userId, token));
    }
}
