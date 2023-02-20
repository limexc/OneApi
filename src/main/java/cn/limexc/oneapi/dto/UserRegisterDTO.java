/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.dto;

import lombok.Data;

/**
 * UserDTO
 *
 * @author LIMEXC
 **/
@Data
public class UserRegisterDTO {

    private String userName;

    private String nickName;

    private String password;

    private String email;
}