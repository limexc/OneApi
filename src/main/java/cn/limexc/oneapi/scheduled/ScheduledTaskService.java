/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.scheduled;

import cn.limexc.oneapi.scheduled.model.Scheduled;

/**
 * The interface Scheduled task service.
 *
 * @author LIMEXC
 * @since 2022 -05-24
 */
public interface ScheduledTaskService {

    /**
     * 根据任务key 启动任务
     *
     * @param task   the task key
     * @param scheduled the scheduled
     * @return the boolean
     */
    Boolean start(String task, Scheduled scheduled);

    /**
     * 根据任务key 停止任务
     *
     * @param task the task key
     * @return the boolean
     */
    Boolean stop(String task);

    /**
     * 根据任务key 重启任务
     *
     * @param task   the task key
     * @param scheduled the scheduled
     * @return the boolean
     */
    Boolean restart(String task, Scheduled scheduled);

    /**
     * 初始化  ==> 启动所有正常状态的任务
     */
    void initAllTask();

}
