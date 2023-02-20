/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.controller;

import cn.limexc.oneapi.dto.UserDTO;
import cn.limexc.oneapi.dto.UserLoginDTO;
import cn.limexc.oneapi.security.JwtUser;
import cn.limexc.oneapi.security.constant.SecurityConstants;
import cn.limexc.oneapi.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于权限相关的请求
 * 包括 登录、注册、登出 接口
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO userLogin) {
        // 用户登录认证
        JwtUser jwtUser = authService.authLogin(userLogin);
        // 认证成功后，将 token 存入响应头中返回
        HttpHeaders httpHeaders = new HttpHeaders();
        // 添加 token 前缀 "Bearer "
        httpHeaders.set(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + jwtUser.getToken());

        return new ResponseEntity<>(jwtUser.getUser(), httpHeaders, HttpStatus.OK);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
