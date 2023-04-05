package com.example.kakaopay.domain.dto.response;

import lombok.Getter;

@Getter
public class ResultError {
    private String message;
    private final int status;

    public ResultError(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
