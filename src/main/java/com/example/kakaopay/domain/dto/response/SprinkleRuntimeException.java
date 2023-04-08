package com.example.kakaopay.domain.dto.response;

import lombok.Getter;

@Getter
public class SprinkleRuntimeException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public SprinkleRuntimeException(String message) {
        this.message = message;
    }

    public SprinkleRuntimeException(ErrorCode errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String toString() {
        if (message == null) return errorCode.getMessage();
        return message;
    }
}
