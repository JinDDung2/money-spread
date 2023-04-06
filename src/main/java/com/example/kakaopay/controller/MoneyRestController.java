package com.example.kakaopay.controller;

import com.example.kakaopay.domain.dto.MoneyDto;
import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.service.MoneyService;
import com.example.kakaopay.service.ReceivedMoneyUserService;
import com.example.kakaopay.service.RoomService;
import com.example.kakaopay.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoneyRestController {
    private final UserService userService;
    private final RoomService roomService;
    private final MoneyService moneyService;
    private final ReceivedMoneyUserService receivedMoneyUserService;

    public MoneyRestController(UserService userService, RoomService roomService, MoneyService moneyService,
                               ReceivedMoneyUserService receivedMoneyUserService) {
        this.userService = userService;
        this.roomService = roomService;
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
    public ResultData<MoneyDto> createMoney(@RequestHeader("X-USER-ID") Long userId,
                                            @RequestHeader("X-ROOM-ID") Long roomId,
                                            @RequestBody MoneyDto moneyDto) {
        return ResultData.success(moneyService.createMoneySprinkle(userId, roomId, moneyDto));
    }
}
