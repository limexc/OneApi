/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.mapper;

import cn.limexc.oneapi.dto.UserLoginDTO;
import cn.limexc.oneapi.pojo.AuthVO;
import cn.limexc.oneapi.pojo.RoleVO;
import cn.limexc.oneapi.pojo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限表，用于 系统权限相关 数据
 */
@Mapper
public interface SystemAuthMapper {

    /**
     * 查询用户 通过UserLogin信息 用于登录
     *
     * @param user
     * @return
     */
    UserVO selectUserByUserLogin(UserLoginDTO user);

    /**
     * 通过用户Id查询所拥有的角色信息
     *
     * @param id 用户Id
     * @return 角色信息列表
     */
    List<RoleVO> selectUserRoleByUserId(@Param("id") String id);

    /**
     * 通过角色Ids查询拥有的权限信息列表
     *
     * @param roleIds 角色ids
     * @return 权限信息列表
     */
    List<AuthVO> selectRoleAuthByRoleIsd(@Param("roleIds") String roleIds);

}
