package com.example.demo.dto;

/**
 * 用户响应数据传输对象（DTO）
 * 
 * 该类用于封装返回给客户端的用户信息，不包含敏感数据如密码。
 * 用于登录、注册和获取当前用户信息等API的响应。
 */
public class UserResponse {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 用户昵称
     */
    private String nickname;
    
    /**
     * 用户头像URL
     */
    private String avatar;
    
    // Getters and Setters
    /**
     * 获取用户ID
     * 
     * @return 用户ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 设置用户ID
     * 
     * @param id 用户ID
     */
    public void setId(Long id) {
        this.id = id;
    }
    
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
     * 获取用户昵称
     * 
     * @return 昵称
     */
    public String getNickname() {
        return nickname;
    }
    
    /**
     * 设置用户昵称
     * 
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
    /**
     * 获取用户头像URL
     * 
     * @return 头像URL
     */
    public String getAvatar() {
        return avatar;
    }
    
    /**
     * 设置用户头像URL
     * 
     * @param avatar 头像URL
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
