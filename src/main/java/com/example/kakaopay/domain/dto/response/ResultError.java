package com.example.kakaopay.domain.dto.response;

import lombok.Getter;

@Getter
public class ResultError {
    private ErrorCode errorCode;
    private String message;

    public ResultError(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public ResultError(String message) {
        this.message = message;
    }
}
