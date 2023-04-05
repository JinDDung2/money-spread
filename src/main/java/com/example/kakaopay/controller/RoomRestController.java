package com.example.kakaopay.controller;

import com.example.kakaopay.domain.dto.RoomDto;
import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.service.RoomService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomRestController {
    private final RoomService roomService;

    public RoomRestController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping("/api/room")
    public ResultData<RoomDto> createRoom(@RequestHeader("X-USER-ID") Long userId) {
        return ResultData.success(roomService.createRoom(userId));
    }

    @PostMapping("/api/room/enter")
    public ResultData<RoomDto> enterRoom(@RequestHeader("X-USER-ID") Long userId,
                                         @RequestHeader("X-ROOM-ID") Long roomId) {
        return ResultData.success(roomService.enterRoom(userId, roomId));
    }
}
