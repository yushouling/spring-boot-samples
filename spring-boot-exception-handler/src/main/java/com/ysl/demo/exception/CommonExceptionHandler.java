package com.ysl.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CommonExceptionHandler {

    /**
     * 处理所有 Exception.class异常
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String handler500(Exception e) {
        // 只返回了异常信息，跳转到原页面
        String response = "服务器异常，" + e.getMessage() + ", code:" + HttpStatus.INTERNAL_SERVER_ERROR.value();
        return response;
    }

    /**
     * 处理所有 OrderFailedException.class异常
     */
    @ExceptionHandler(value = OrderFailedException.class)
    public String handler503(Model model, Exception e) {
        String response = e.getMessage() + " code:" + HttpStatus.SERVICE_UNAVAILABLE.value();
        model.addAttribute("errorMsg", response);
        // 跳转到error.html
        return "error";
    }
}
