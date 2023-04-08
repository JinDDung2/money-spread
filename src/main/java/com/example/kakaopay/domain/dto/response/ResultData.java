package com.example.kakaopay.domain.dto.response;

import lombok.Getter;

@Getter
public class ResultData<T> {

    private boolean success;
    private final T result;

    public ResultData(boolean success, T result) {
        this.success = success;
        this.result = result;
    }

    public static <T> ResultData<T> success(T result) {
        return new ResultData<>(true, result);
    }

    public static <T> ResultData<ResultError> error(ResultError error) {
        return new ResultData<>(false, new ResultError(error.getErrorCode(), error.getMessage()));
    }

}
