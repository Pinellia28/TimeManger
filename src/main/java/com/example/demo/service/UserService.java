package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 用户服务类
 * 
 * 该类负责处理与用户相关的业务逻辑，包括用户注册、登录、获取当前用户信息和登出等功能。
 * 作为控制层和数据访问层之间的桥梁，处理业务规则和数据转换。
 */
@Service
public class UserService {
    
    /**
     * 会话中存储当前用户ID的键名
     */
    private static final String USER_SESSION_KEY = "current_user";
    
    /**
     * 用户数据访问对象
     * 使用Spring的依赖注入
     */
    @Autowired
    private UserRepository userRepository;
    
    /**
     * 用户注册
     * 
     * 验证用户名是否已存在，创建新用户并保存到数据库。
     * 
     * @param registerRequest 注册请求DTO，包含用户名、密码和昵称
     * @return 注册成功的用户信息
     * @throws RuntimeException 如果用户名已存在则抛出异常
     */
    public UserResponse register(RegisterRequest registerRequest) {
        // 检查用户名是否已存在
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        
        // 创建新用户
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword()); // 注意：实际应用中应当加密密码
        user.setNickname(registerRequest.getNickname());
        
        // 保存用户到数据库
        user = userRepository.save(user);
        
        // 转换为响应DTO并返回
        return convertToUserResponse(user);
    }
    
    /**
     * 用户登录
     * 
     * 验证用户名和密码，成功后将用户ID存入Session。
     * 
     * @param loginRequest 登录请求DTO，包含用户名和密码
     * @return 登录成功的用户信息
     * @throws RuntimeException 如果用户名不存在或密码错误则抛出异常
     */
    public UserResponse login(LoginRequest loginRequest) {
        // 根据用户名查找用户
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());
        
        // 验证用户存在且密码正确
        if (optionalUser.isEmpty() || !optionalUser.get().getPassword().equals(loginRequest.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }
        
        User user = optionalUser.get();
        
        // 转换为响应DTO并返回
        return convertToUserResponse(user);
    }
    
    /**
     * 获取当前登录用户信息
     * 
     * 从Session中获取用户ID，然后查询用户详细信息。
     * 
     * @param userId 用户ID
     * @return 当前登录用户的信息
     * @throws RuntimeException 如果用户不存在则抛出异常
     */
    public UserResponse getUserById(Long userId) {
        if (userId == null) {
            throw new RuntimeException("User ID cannot be null");
        }
        
        // 根据ID查找用户
        Optional<User> optionalUser = userRepository.findById(userId);
        
        // 检查用户是否存在
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        // 转换为响应DTO并返回
        return convertToUserResponse(optionalUser.get());
    }
    
    /**
     * 将User实体转换为UserResponse DTO
     * 
     * 提取需要返回给客户端的用户信息，排除敏感字段如密码。
     * 
     * @param user 用户实体对象
     * @return 用户响应DTO
     */
    private UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());
        return response;
    }
    /**
     * 更新用户头像和昵称
     *
     * @param id 用户ID
     * @param userResponse 包含昵称和头像的更新信息
     * @return 更新后的用户信息
     */
    public UserResponse updateUserProfile(Long id, UserResponse userResponse) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("用户未找到"));

        // 更新昵称
        if (userResponse.getNickname() != null && !userResponse.getNickname().trim().isEmpty()) {
            user.setNickname(userResponse.getNickname());
        }

        // 更新头像
        if (userResponse.getAvatar() != null && !userResponse.getAvatar().trim().isEmpty()) {
            user.setAvatar(userResponse.getAvatar());
        }

        // 保存到数据库
        user = userRepository.save(user);

        // 将更新后的实体转换为 UserResponse
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setNickname(user.getNickname());
        response.setAvatar(user.getAvatar());

        return response;
    }

} 