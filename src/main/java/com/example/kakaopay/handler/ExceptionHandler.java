package com.example.kakaopay.handler;

import com.example.kakaopay.domain.dto.response.ResultData;
import com.example.kakaopay.domain.dto.response.ResultError;
import com.example.kakaopay.domain.dto.response.SprinkleRuntimeException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(SprinkleRuntimeException.class)
    public ResultData<?> sprinkleAppException(SprinkleRuntimeException e) {
        return ResultData.error(new ResultError(e.getErrorCode(), e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResultData<?> appException(Exception e) {
        return ResultData.error(new ResultError(e.getMessage()));
    }
}
