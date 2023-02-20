/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.mapper;

import cn.limexc.oneapi.scheduled.model.Scheduled;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author LIMEXC
 * @since 2022-05-25
 **/
@Mapper
public interface ScheduledMapper {
    /**
     * 根据task搜索唯一的任务
     *
     * @param task task
     * @return 定时任务信息
     */
    Scheduled selectScheduledByTask(@Param("task") String task);


    /**
     * 按条件查询全部的定时任务
     *
     * @param scheduled 定时任务
     * @return 定时任务列表
     */
    List<Scheduled> selectScheduled(@Param("scheduled") Scheduled scheduled);

}
