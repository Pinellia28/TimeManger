package com.example.demo.exception;

import com.example.demo.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 * 
 * 该类负责捕获和处理整个应用程序中抛出的异常，将其转换为规范的API响应格式返回给客户端。
 * 使用@RestControllerAdvice注解，使其作用于所有的@RestController。
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * 处理参数校验异常
     * 
     * 当请求数据校验失败时（违反@Valid注解的规则），捕获MethodArgumentNotValidException异常，
     * 提取具体的错误字段和错误信息，并返回400状态码（Bad Request）。
     * 
     * @param ex 参数校验异常
     * @return 包含详细错误信息的API响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ApiResponse.<Map<String, String>>fail(400, "Validation failed").setData(errors);
    }
    
    /**
     * 处理运行时异常
     * 
     * 捕获所有未被特定处理器捕获的RuntimeException，返回500状态码（Internal Server Error）。
     * 将异常消息作为响应消息返回给客户端。
     * 
     * @param ex 运行时异常
     * @return API响应
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleRuntimeException(RuntimeException ex) {
        return ApiResponse.<Void>fail(500, ex.getMessage());
    }
} 