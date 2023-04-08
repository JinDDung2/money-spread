package com.example.kakaopay.domain.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    NOT_FOUND_USER(BAD_REQUEST, "유저 아이디를 찾을 수 없습니다."),
    NOT_FOUND_ROOM(BAD_REQUEST, "방을 찾을 수 없습니다."),
    NOT_ENTER_ROOM(BAD_REQUEST, "해당 방에 참여하고 있지 않습니다."),
    NOT_FOUND_TOKEN(BAD_REQUEST, "토큰을 찾을 수 없습니다."),
    NOT_FOUND_SPRINKLE(BAD_REQUEST, "카카오페이 뿌리기를 찾을 수 없습니다."),
    NOT_ENOUGH_BALANCE(BAD_REQUEST, "잔액이 부족합니다."),
    NOT_RECEIVE_MY_SPRINKLE(BAD_REQUEST, "자신이 뿌린 유저는 받을 수 없습니다."),
    NOT_EQUAL_OWNER_SPRINKLE(BAD_REQUEST, "해당 유저가 뿌리기 한 것이 아닙니다."),
    ALREADY_USER_RECEIVED(BAD_REQUEST, "이미 받은 유저는 다시 받을 수 없습니다."),
    ALREADY_ALL_RECEIVED(BAD_REQUEST, "이미 모두 받았습니다."),
    TIME_OUT(BAD_REQUEST, "유효하지 않은 시간입니다."),
    NOT_ALLOWED_HTTP_METHOD(METHOD_NOT_ALLOWED, "잘못된 http 메서드 요청 입니다.");

    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
