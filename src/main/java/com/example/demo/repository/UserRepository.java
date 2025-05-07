package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 用户数据访问层接口
 * 
 * 该接口继承自JpaRepository，提供用户实体的基本CRUD操作以及自定义的查询方法。
 * Spring Data JPA会自动实现该接口，无需手动编写实现类。
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 根据用户名查找用户
     * 
     * 该方法会根据方法命名规则自动生成SQL查询语句，等同于：
     * SELECT * FROM users WHERE username = ?
     * 
     * @param username 要查询的用户名
     * @return 包含用户的Optional对象，如果未找到则返回空的Optional
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 检查指定用户名是否已存在
     * 
     * 该方法会根据方法命名规则自动生成SQL查询语句，等同于：
     * SELECT count(*) > 0 FROM users WHERE username = ?
     * 
     * @param username 要检查的用户名
     * @return 如果用户名已存在则返回true，否则返回false
     */
    boolean existsByUsername(String username);
} 