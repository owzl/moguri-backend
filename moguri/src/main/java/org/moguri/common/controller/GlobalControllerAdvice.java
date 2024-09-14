package org.moguri.common.controller;

import org.moguri.common.response.ApiResponse;
import org.moguri.exception.MoguriException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(MoguriException.class)
    public ApiResponse<?> handleEbffException(MoguriException e) {
        return ApiResponse.of(e.getReturnCode());
    }
}
