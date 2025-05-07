package com.example.demo.common;

import java.time.LocalDateTime;

/**
 * 通用API响应包装类
 * 
 * 该类用于统一封装REST API的响应数据格式，支持泛型。
 * 包含状态码、消息、数据和时间戳字段，便于客户端处理响应。
 * 
 * @param <T> 响应数据的泛型类型
 */
public class ApiResponse<T> {
    
    /**
     * 状态码
     * 200表示成功，其他值表示各类错误
     */
    private int code;
    
    /**
     * 响应消息
     * 用于描述请求处理的结果
     */
    private String message;
    
    /**
     * 响应数据
     * 泛型设计，可适应不同类型的响应数据
     */
    private T data;
    
    /**
     * 响应时间戳
     * 记录响应生成的时间
     */
    private LocalDateTime timestamp;
    
    /**
     * 私有构造函数
     * 通过静态工厂方法创建实例，确保时间戳的设置
     */
    private ApiResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    /**
     * 创建成功响应
     * 
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功的API响应对象
     */
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage("Success");
        response.setData(data);
        return response;
    }
    
    /**
     * 创建带自定义消息的成功响应
     * 
     * @param message 成功消息
     * @param data 响应数据
     * @param <T> 数据类型
     * @return 成功的API响应对象
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(200);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
    
    /**
     * 创建失败响应
     * 
     * @param code 错误码
     * @param message 错误消息
     * @param <T> 数据类型
     * @return 失败的API响应对象
     */
    public static <T> ApiResponse<T> fail(int code, String message) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
    
    // Getters and Setters
    /**
     * 获取状态码
     * 
     * @return 状态码
     */
    public int getCode() {
        return code;
    }
    
    /**
     * 设置状态码
     * 支持方法链式调用
     * 
     * @param code 状态码
     * @return 当前对象
     */
    public ApiResponse<T> setCode(int code) {
        this.code = code;
        return this;
    }
    
    /**
     * 获取响应消息
     * 
     * @return 响应消息
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * 设置响应消息
     * 支持方法链式调用
     * 
     * @param message 响应消息
     * @return 当前对象
     */
    public ApiResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    
    /**
     * 获取响应数据
     * 
     * @return 响应数据
     */
    public T getData() {
        return data;
    }
    
    /**
     * 设置响应数据
     * 支持方法链式调用
     * 
     * @param data 响应数据
     * @return 当前对象
     */
    public ApiResponse<T> setData(T data) {
        this.data = data;
        return this;
    }
    
    /**
     * 获取响应时间戳
     * 
     * @return 响应时间戳
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    /**
     * 设置响应时间戳
     * 支持方法链式调用
     * 
     * @param timestamp 响应时间戳
     * @return 当前对象
     */
    public ApiResponse<T> setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

} 