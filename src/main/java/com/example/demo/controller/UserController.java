package com.example.demo.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关接口控制器
 * 
 * 该控制器负责处理与用户认证相关的HTTP请求，包括注册、登录、获取当前用户信息和登出等功能。
 * 所有接口都遵循RESTful设计风格，以JSON格式进行数据交换。
 * 接口路径前缀为"/api/user"，支持跨域访问。
 */
@RestController
@RequestMapping("/api/user")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class UserController {
    
    /**
     * 用户服务
     * 使用Spring的依赖注入
     */
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册接口
     * 
     * 处理POST请求，接收用户注册信息并创建新用户。
     * 使用@Valid注解确保请求数据符合验证规则。
     * 
     * @param registerRequest 注册请求DTO，包含用户名、密码和昵称
     * @return 封装了新创建用户信息的API响应
     */
    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse userResponse = userService.register(registerRequest);
        return ApiResponse.success("Registration successful", userResponse);
    }
    
    /**
     * 用户登录接口
     * 
     * 处理POST请求，验证用户凭据并创建会话。
     * 使用@Valid注解确保请求数据符合验证规则。
     * 
     * @param loginRequest 登录请求DTO，包含用户名和密码
     * @return 封装了登录用户信息的API响应
     */
    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        UserResponse userResponse = userService.login(loginRequest);
        return ApiResponse.success("Login successful", userResponse);
    }
    
    /**
     * 获取当前登录用户信息接口
     * 
     * 处理GET请求，返回当前会话关联的用户信息。
     * 如果用户未登录或不存在，将返回401状态码。
     * 
     * @param userId 用户ID，可选
     * @return 封装了当前用户信息的API响应
     */
    @GetMapping("/current")
    public ApiResponse<UserResponse> getCurrentUser(@RequestHeader(value = "X-User-ID", required = false) Long userId) {
        try {
            if (userId == null) {
                return ApiResponse.fail(401, "Not logged in");
            }
            UserResponse userResponse = userService.getUserById(userId);
            return ApiResponse.success(userResponse);
        } catch (RuntimeException e) {
            return ApiResponse.fail(401, e.getMessage());
        }
    }
    
    /**
     * 用户登出接口
     * 
     * 处理POST请求，返回成功响应。
     * 注意：在基于请求头的认证中，登出仅需要客户端清除本地存储的用户信息。
     * 
     * @return 封装了登出结果的API响应
     */
    @PostMapping("/logout")
    public ApiResponse<Void> logout() {
        return ApiResponse.success("Logout successful", null);
    }
    /**
     * 更新用户头像和昵称
     *
     * @param id 用户ID
     * @param userResponse 包含昵称和头像的更新请求
     * @return 封装的API响应
     */
    @PutMapping("/{id}/update-profile")
    public ApiResponse<UserResponse> updateUserProfile(@PathVariable Long id, @RequestBody UserResponse userResponse) {
        UserResponse updatedUser = userService.updateUserProfile(id, userResponse);
        return ApiResponse.success("用户头像和昵称更新成功", updatedUser);
    }

} 