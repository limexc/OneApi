/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.dto;

import lombok.Data;

import java.util.Set;

/**
 * UserDTO
 *
 * @author LIMEXC
 */
@Data
public class UserDTO {

    /**
     * 用户Id
     */
    private String userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * email
     */
    private String email;

    /**
     * 角色Id集合
     */
    private Set<String> roles;

    /**
     * 权限名称集合
     */
    private Set<String> auths;

}
