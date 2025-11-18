package com.seonggyun.escapelog.common;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BaseException.class)
    public String handleBaseException(BaseException e, Model model) {
        model.addAttribute("error", new ErrorResponseDto(
                e.getStatus(),
                e.getCode(),
                e.getMessage()
        ));
        return "error/customError";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("error", new ErrorResponseDto(
                500,
                "UNEXPECTED_ERROR",
                "알 수 없는 서버 오류가 발생했습니다."
        ));
        return "error/custom-error";
    }
}
