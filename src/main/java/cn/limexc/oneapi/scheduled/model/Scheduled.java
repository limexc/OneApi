/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.scheduled.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author LIMEXC
 * @since 2022-05-24
 **/
@Data
@Accessors(chain = true)
public class Scheduled {
    /**
     * scheduled.id
     */
    private Integer id;

    /**
     * scheduled.task_key
     * 任务key值（使用bean名称）
     */
    private String task;

    /**
     * scheduled.name
     * 任务名称
     */
    private String name;

    /**
     * scheduled.cron
     * 任务表达式
     */
    private String cron;

    /**
     * scheduled.status
     * 状态(0.禁用; 1.启用)
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * scheduled.create_time
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * scheduled.create_user
     * 创建人
     */
    private LocalDateTime createUser;

    /**
     * scheduled.update_time
     * 更新时间
     */
    private LocalDateTime updateTime;

}
