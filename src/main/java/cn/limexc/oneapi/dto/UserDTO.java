/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Set;

/**
 * UserDTO
 *
 * @author LIMEXC
 */
@Data
public class UserDTO {

    private String userId;

    private String userName;

    private String nickName;

    private String email;

    private Set<String> roles;

    //使用@JsonIgnore注解，忽略此属性，前端不会拿到该属性
    @JsonIgnore
    private Set<String> auths;

}
