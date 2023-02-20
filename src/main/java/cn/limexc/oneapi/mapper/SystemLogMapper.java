/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.mapper;

import cn.limexc.oneapi.pojo.SystemLogVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author LIMEXC
 * @since 2022-05-13
 **/
@Mapper
public interface SystemLogMapper {

    /**
     * 写系统日志表
     *
     * @param log 日志vo
     */
    void insertSystemLogData(@Param("log") SystemLogVO log);
}
