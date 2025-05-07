package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 用户实体类
 * 
 * 该类定义了系统中用户的数据结构，包含用户基本信息。
 * 使用JPA注解映射到数据库表，表名为"users"。
 */
@Entity
@Table(name = "users")
public class User {
    
    /**
     * 用户ID，主键
     * 使用自增长策略生成
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 用户名
     * 要求非空，且在数据库中唯一
     */
    @NotBlank
    @Column(unique = true)
    private String username;
    
    /**
     * 用户密码
     * 要求非空
     * 注意：实际应用中应当存储加密后的密码，而不是明文
     */
    @NotBlank
    private String password;
    
    /**
     * 用户昵称
     * 可为空
     */
    private String nickname;
    
    /**
     * 用户头像URL
     * 可为空
     */
    private String avatar;
    
    /**
     * 记录创建时间
     * 由@PrePersist方法自动填充
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    /**
     * 记录更新时间
     * 由@PrePersist和@PreUpdate方法自动更新
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * 实体保存前的操作
     * 自动设置创建时间和更新时间为当前时间
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * 实体更新前的操作
     * 自动更新更新时间为当前时间
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
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
     * 获取用户密码
     * 
     * @return 密码
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * 设置用户密码
     * 
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
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
    
    /**
     * 获取记录创建时间
     * 
     * @return 创建时间
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    /**
     * 设置记录创建时间
     * 
     * @param createdAt 创建时间
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    /**
     * 获取记录更新时间
     * 
     * @return 更新时间
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    /**
     * 设置记录更新时间
     * 
     * @param updatedAt 更新时间
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 