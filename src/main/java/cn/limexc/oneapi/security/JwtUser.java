/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.security;

import cn.limexc.oneapi.dto.UserDTO;
import lombok.Data;

/**
 * JwtUserDTO
 *
 * @author LIMEXC
 */
@Data
public class JwtUser {

    private UserDTO user;

    private String token;

    public JwtUser(String token, UserDTO user) {
        this.user = user;
        this.token = token;
    }
}
