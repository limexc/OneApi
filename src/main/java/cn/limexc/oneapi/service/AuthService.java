/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.service;

import cn.limexc.oneapi.dto.UserLoginDTO;
import cn.limexc.oneapi.security.JwtUser;

public interface AuthService {

    JwtUser authLogin(UserLoginDTO userLogin);

    Boolean logout(String token);
}
