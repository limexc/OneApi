/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.dto;

import lombok.Data;

/**
 * UserLoginDTO
 *
 * @author LIMEXC
 */
@Data
public class UserLoginDTO {

    private String userName;

    private String password;

    /**
     * 是否记住我，默认 false
     */
    private Boolean rememberMe = false;
}
