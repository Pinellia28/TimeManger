package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

/**
// * 登录请求数据传输对象（DTO）
 * 
 * 该类用于封装客户端发送的登录请求数据，包含用户名和密码字段。
 * 使用Bean Validation注解来确保数据的合法性。
 */
public class LoginRequest {
    
    /**
     * 用户名
     * 使用@NotBlank注解确保字段非空
     */
    @NotBlank(message = "Username cannot be empty")
    private String username;
    
    /**
     * 密码
     * 使用@NotBlank注解确保字段非空
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;
    
    // Getters and Setters
    /**
     * 获取用户名
     * 
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * 设置用户名
     * 
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * 获取密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 设置密码
     * 
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }
} 