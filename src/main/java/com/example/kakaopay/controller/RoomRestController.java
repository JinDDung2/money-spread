package com.example.kakaopay.controller;

import com.example.kakaopay.domain.dto.RoomDto;
import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomRestController {
    private final RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @Operation(summary = "방 생성", description = "존재 않은 유저일 경우 에러 발생")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
            @ApiResponse(responseCode = "400", description = "유저를 찾지 못한 경우"),
    })
    @PostMapping("/api/room")
    public ResultData<RoomDto> createRoom(@RequestHeader("X-USER-ID") Long userId) {
        return ResultData.success(roomService.createRoom(userId));
    }

    @Operation(summary = "대화방 입장", description = "유저 혹은 대화방이 없을시 에러 발생")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success"),
            @ApiResponse(responseCode = "400", description = "존재하지 않은 유저일 경우, 존재하지 않는 방일 경우"),
    })
    @PostMapping("/api/room/enter")
    public ResultData<RoomDto> enterRoom(@RequestHeader("X-USER-ID") Long userId,
                                         @RequestHeader("X-ROOM-ID") Long roomId) {
        return ResultData.success(roomService.enterRoom(userId, roomId));
    }
}
