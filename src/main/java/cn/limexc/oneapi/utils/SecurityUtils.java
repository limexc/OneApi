/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import cn.limexc.oneapi.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * SecurityUtils
 *
 * <p>
 * 用于获取当前登录的用户名
 *
 * @author LIMEXC
 **/
public class SecurityUtils {

    private SecurityUtils() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    /**
     * 从上下文中获取当前登录的用户信息
     */
    public static Optional<String> getCurrentUserLogin() {
        // 获取上下文对象
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取验证信息
        Authentication authentication = context.getAuthentication();
        // 返回上下文中的用户信息
        return Optional.ofNullable(authentication)
                .map(auth -> {
                    if (auth.getPrincipal() instanceof UserDetails) {
                        UserDetails userDetails = (UserDetails) auth.getPrincipal();
                        return userDetails.getUsername();
                    } else if (auth.getPrincipal() instanceof String) {
                        return (String) auth.getPrincipal();
                    }
                    return null;
                });

    }

    /**
     * 从上下问中获取当前用户基础信息
     *
     * @return UserDTO user
     */
    public static UserDTO getCurrentUser() {
        // 获取上下文对象
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取验证信息
        Authentication authentication = context.getAuthentication();
        UserDTO user = new UserDTO();
        if (authentication instanceof UserDTO) {
            user = (UserDTO) authentication.getPrincipal();
        }
        return user;
    }
}
