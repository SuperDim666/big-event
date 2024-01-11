package org.zixiangxu.exception;/*
 * @author Zixiang Xu
 * @date 2024/1/1 22:54
 */


import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zixiangxu.pojo.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<?> handleException(Exception e) {
        e.printStackTrace(System.err);
        return Result.error(StringUtils.hasLength(e.getMessage()) ? e.getMessage() : "Operation Failed");
    }
}