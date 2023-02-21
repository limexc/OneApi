/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.limexc.oneapi.dto.UserDTO;
import cn.limexc.oneapi.dto.UserLoginDTO;
import cn.limexc.oneapi.mapper.SystemAuthMapper;
import cn.limexc.oneapi.pojo.AuthVO;
import cn.limexc.oneapi.pojo.RoleVO;
import cn.limexc.oneapi.pojo.UserVO;
import cn.limexc.oneapi.security.JwtUser;
import cn.limexc.oneapi.security.constant.SecurityConstants;
import cn.limexc.oneapi.security.constant.UserRoleConstants;
import cn.limexc.oneapi.service.AuthService;
import cn.limexc.oneapi.utils.JwtUtils;
import cn.limexc.oneapi.utils.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
        // 通过角色查询拥有的权限
        List<AuthVO> auths = systemAuthMapper.selectRoleAuthByRoleIsd(String.join(",",roleIds));
        Set<String> authNames = new HashSet<>();
        for (AuthVO authVO : auths) {
            authNames.add(authVO.getName());
        }
        // 用户信息
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUsername());
        userDTO.setUserId(String.valueOf(user.getId()));
        userDTO.setEmail(user.getMail());
        userDTO.setRoles(roleIds);
        userDTO.setAuths(authNames);

        // 生成 token
        String token = JwtUtils.generateToken(String.valueOf(user.getId()), roleIds, userLogin.getRememberMe());
        // 过期时间
        long expiration = userLogin.getRememberMe() ? SecurityConstants.EXPIRATION_REMEMBER_TIME : SecurityConstants.EXPIRATION_TIME;
        // 将token与用户权限相关信息存储到redis中
        RedisUtils.StringOps.setEx("ONEAPI_ONLINE_USER:"+token, JSON.toJSONString(userDTO),expiration, TimeUnit.SECONDS);
        // 认证成功后，设置认证信息到 Spring Security 上下文中
        Authentication authentication = JwtUtils.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new JwtUser(token, userDTO);
    }

    /**
     * 用户退出登录
     * 清除 Spring Security 上下文中的认证信息
     */
    @Override
    public Boolean logout(String token) {
        // 清除redis中的认证信息
        Boolean deleteFlag = RedisUtils.KeyOps.delete("ONEAPI_ONLINE_USER:"+token);
        // 清除上下文认证信息
        SecurityContextHolder.clearContext();
        return deleteFlag;
    }
}
