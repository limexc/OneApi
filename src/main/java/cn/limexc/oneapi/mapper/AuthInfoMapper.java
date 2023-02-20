/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.mapper;

import cn.limexc.oneapi.pojo.AuthInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author ADMIN
* @description 针对表【t_auth_info(认证信息表)】的数据库操作Mapper
* @createDate 2022-06-02 00:17:42
* @Entity cn.limexc.oneapi.pojo.AuthInfoVO
*/
@Mapper
public interface AuthInfoMapper {

    /**
     * 获取认证信息
     *
     * @param id 认证信息Id
     * @return 认证信息详细内容
     */
    AuthInfoVO selectAuthInfo(@Param("id") Integer id);

}




