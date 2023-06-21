package com.example.kakaopay.controller;

import com.example.kakaopay.domain.dto.UserDto;
import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "success"),
    })
    @PostMapping("/user")
    public ResultData<UserDto> createUser(@RequestBody UserDto userDto) {
        return ResultData.success(userService.createUser(userDto));
    }
}
