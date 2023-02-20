/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.limexc.oneapi.dto.UserDTO;
import cn.limexc.oneapi.dto.UserLoginDTO;
import cn.limexc.oneapi.mapper.SystemAuthMapper;
import cn.limexc.oneapi.pojo.RoleVO;
import cn.limexc.oneapi.pojo.UserVO;
import cn.limexc.oneapi.security.JwtUser;
import cn.limexc.oneapi.security.constant.UserRoleConstants;
import cn.limexc.oneapi.service.AuthService;
import cn.limexc.oneapi.utils.JwtUtils;
import cn.limexc.oneapi.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    SystemAuthMapper systemAuthMapper;


    /**
     * 校验登录
     *
     * @param userLogin 用户登录传入信息
     * @return JwtUser
     */
    @Override
    public JwtUser authLogin(UserLoginDTO userLogin) {

        // 根据登录名密码获取用户信息
        UserVO user = systemAuthMapper.selectUserByUserLogin(userLogin);
        if (ObjectUtil.isEmpty(user)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        // 赋予用户相应权限并生成用户认证信息

        List<RoleVO> roles = systemAuthMapper.selectUserRoleByUserId(String.valueOf(user.getId()));
        Set<String> roleIds = new HashSet<>();
        for (RoleVO role:roles) {
            roleIds.add(String.valueOf(role.getId()));
        }
        // 如果用户角色为空，则默认赋予 ROLE_USER 角色
        if (roleIds.isEmpty()) {
            roleIds.add(UserRoleConstants.ROLE_USER);
        }
        // 生成 token
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), roleIds, userLogin.getRememberMe());

        // 认证成功后，设置认证信息到 Spring Security 上下文中
        Authentication authentication = JwtUtils.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 将token存储到redis中
        RedisUtils.StringOps.set("ONEAPI_ONLINE_USER:"+token,"角色权限等信息");
        // 用户信息
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUsername());
        userDTO.setUserId(String.valueOf(user.getId()));
        userDTO.setEmail(user.getMail());
        userDTO.setRoles(roleIds);
        return new JwtUser(token, userDTO);
    }

    /**
     * 用户退出登录
     * 清除 Spring Security 上下文中的认证信息
     */
    @Override
    public void logout() {
        // 清除redis中的认证信息

        // 清除上下文认证信息
        SecurityContextHolder.clearContext();
    }
}
