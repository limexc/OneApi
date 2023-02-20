/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.service;

import cn.limexc.oneapi.pojo.AuthInfoVO;

/**
* @author ADMIN
* @description 针对表【t_auth_info(认证信息表)】的数据库操作Service
* @createDate 2022-06-02 00:17:42
*/
public interface AuthInfoService {

    /**
     * 获取一条认证信息
     *
     * @param id 认证信息Id
     * @return 认证信息详细内容
     */
    AuthInfoVO selectAuthInfo(Integer id);

}
