/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.mapper;

import cn.limexc.oneapi.pojo.MobileNumberBaseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LIMEXC
 * @since 2022-05-11
 **/
@Mapper
public interface MobileNumberBaseMapper {

    /**
     * 查询手机号归属地
     *
     * @param mobileNumber 手机号前7位
     * @return 归属地信息
     */
    MobileNumberBaseVO selectMobileNumberBase(@Param("mobileNumber") String mobileNumber);
}
