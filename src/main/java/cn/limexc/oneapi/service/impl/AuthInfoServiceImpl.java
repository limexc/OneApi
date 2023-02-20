/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.limexc.oneapi.mapper.AuthInfoMapper;
import cn.limexc.oneapi.pojo.AuthInfoVO;
import cn.limexc.oneapi.service.AuthInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author ADMIN
* @description 针对表【t_auth_info(认证信息表)】的数据库操作Service实现
* @createDate 2022-06-02 00:17:42
*/
@Service
public class AuthInfoServiceImpl implements AuthInfoService {

    @Autowired
    AuthInfoMapper authInfoMapper;

    /**
     * 获取认证信息
     *
     * @param id 认证信息Id
     * @return 认证信息详细内容
     */
    @Override
    public AuthInfoVO selectAuthInfo(Integer id) {
        return authInfoMapper.selectAuthInfo(id);
    }
}




