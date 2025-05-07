package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 注册请求数据传输对象（DTO）
 * 
 * 该类用于封装客户端发送的注册请求数据，包含用户名、密码和昵称字段。
 * 使用Bean Validation注解来确保数据的合法性。
 */
public class RegisterRequest {
    
    /**
     * 用户名
     * 使用@NotBlank注解确保字段非空
     * 使用@Size注解限制字符长度在4-20之间
     */
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;
    
    /**
     * 密码
     * 使用@NotBlank注解确保字段非空
     * 使用@Size注解确保最小长度为6
     */
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
    
    /**
     * 昵称
     * 可为空，用户可选填
     */
    private String nickname;
    
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
    
    /**
     * 获取昵称
     * 
     * @return 昵称
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * 设置昵称
     * 
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
} 