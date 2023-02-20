/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.scheduled.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.limexc.oneapi.mapper.ScheduledMapper;
import cn.limexc.oneapi.scheduled.ScheduledTaskJob;
import cn.limexc.oneapi.scheduled.ScheduledTaskService;
import cn.limexc.oneapi.scheduled.constant.ScheduledStatus;
import cn.limexc.oneapi.scheduled.model.Scheduled;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author LIMEXC
 * @since 2022-05-24
 **/
@Slf4j
@Service
public class ScheduledTaskServiceImpl implements ScheduledTaskService {

    @Value("${task.enabled}")
    private Boolean taskEnable;

    /**
     * 可重入锁
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * 定时任务线程池
     */
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /**
     * 存放已经启动的任务map
     */
    private Map<String, ScheduledFuture> scheduledFutureMap = new ConcurrentHashMap<>();

    @Autowired
    private ScheduledMapper scheduledMapper;

    /**
     * 描述: 根据任务key 启动任务
     *
     * @param task
     * @param scheduled
     * @return java.lang.Boolean
     * @author lv617
     * @date 2020/9/24 11:16
     */
    @Override
    public Boolean start(String task, Scheduled scheduled) {
        log.info(">>>>>> 启动任务 {} 开始 >>>>>>", task);
        //添加锁放一个线程启动，防止多人启动多次
        log.info(">>>>>> 添加任务启动锁");
        lock.lock();
        try {
            //校验是否已经启动
            if (this.isStart(task)) {
                log.info(">>>>>> 当前任务已经启动，无需重复启动！");
                return false;
            }
            //查询配置
            if(scheduled == null) {
                scheduled = this.getByTask(task);
            }
            if(scheduled == null) {
                return false;
            }
            //启动任务
            this.doStartTask(scheduled);
        } finally {
            // 释放锁
            lock.unlock();
            log.info(">>>>>> 释放任务启动锁完毕");
        }
        log.info(">>>>>> 启动任务 {} 结束 >>>>>>", task);
        return true;
    }

    /**
     * 描述: 查询定时任务配置参数
     *
     * @param task 定时任务Task名
     * @return com.yihaocard.main.module.scheduled.model.Scheduled
     * @author lv617
     * @date 2020/9/24 11:14
     */
    private Scheduled getByTask(String task) {
        Scheduled scheduled = new Scheduled();
        scheduled.setStatus(ScheduledStatus.ENABLE.getCode());
        scheduled.setTask(task);
        List<Scheduled> scheduleds = scheduledMapper.selectScheduled(scheduled);
        if(scheduleds == null || scheduleds.size() < 1) {
            return null;
        }
        return scheduleds.get(0);
    }

    /**
     * 描述: 根据 key 停止任务
     *
     * @param task 定时任务Task名
     * @return java.lang.Boolean
     * @author lv617
     * @date 2020/9/24 11:17
     */
    @Override
    public Boolean stop(String task) {
        log.info(">>>>>> 进入停止任务 {}  >>>>>>", task);
        //当前任务实例是否存在
        boolean taskStartFlag = scheduledFutureMap.containsKey(task);
        log.info(">>>>>> 当前任务实例是否存在 {}", taskStartFlag);
        if (taskStartFlag) {
            //获取任务实例
            ScheduledFuture scheduledFuture = scheduledFutureMap.get(task);
            //关闭实例
            boolean cancel = scheduledFuture.cancel(true);
            log.info("cancel:{}", cancel);
            //删除关闭的任务实例
            scheduledFutureMap.remove(task);
        }
        log.info(">>>>>> 结束停止任务 {}  >>>>>>", task);
        return taskStartFlag;
    }

    /**
     * 描述: 根据任务key 重启任务
     *
     * @param task 定时任务Task名
     * @param scheduled
     * @return java.lang.Boolean
     * @author lv617
     * @date 2020/9/24 11:18
     */
    @Override
    public Boolean restart(String task, Scheduled scheduled) {
        log.info(">>>>>> 进入重启任务 {}  >>>>>>", task);
        //先停止
        this.stop(task);
        //查询配置
        if(scheduled == null) {
            scheduled = this.getByTask(task);
        }
        if(scheduled == null) {
            return false;
        }
        //再启动
        return this.start(task,scheduled);
    }

    /**
     * 初始化  ==> 启动所有正常状态的任务
     */
    @Override
    public void initAllTask() {
        if(!taskEnable){
            log.info("配置文件禁用了定时任务----");
            return;
        }
        Scheduled corn = new Scheduled();
        corn.setStatus(ScheduledStatus.ENABLE.getCode());
        List<cn.limexc.oneapi.scheduled.model.Scheduled> scheduleds = scheduledMapper.selectScheduled(corn);
        log.info("初始化  ==> 启动所有正常状态的任务开始 ！size={}", scheduleds == null ? 0 : scheduleds.size());
        if (scheduleds == null || scheduleds.size() < 1) {
            return;
        }
        for (Scheduled scheduled : scheduleds) {
            //任务 key
            String task = scheduled.getTask();
            //校验是否已经启动
            if (this.isStart(task)) {
                // 重启任务
                this.restart(task,scheduled);
            } else {
                // 启动任务
                this.doStartTask(scheduled);
            }
        }
        log.info("初始化  ==> 启动所有正常状态的任务结束 ！");
    }

    /**
     * 执行启动任务
     */
    private void doStartTask(Scheduled scheduled) {
        if (scheduled == null) {
            return;
        }
        //任务key
        String task = scheduled.getTask();
        //定时表达式
        String taskCron = scheduled.getCron();
        //获取需要定时调度的接口
        try {
            ScheduledTaskJob scheduledTaskJob = SpringUtil.getBean(task);
            log.info(">>>>>> 任务 [ {} ] ,cron={}", scheduled.getName(), taskCron);
            ScheduledFuture<?> scheduledFuture = threadPoolTaskScheduler.schedule(
                    scheduledTaskJob,
                    (TriggerContext triggerContext) -> new CronTrigger(taskCron).nextExecution(triggerContext));
            //将启动的任务放入 map
            scheduledFutureMap.put(task, scheduledFuture);
        }catch (Exception e){
            log.error(">>>>>> 任务[ {} ]启动失败！失败原因：{}", scheduled.getName(),e.getMessage());
        }
    }

    /**
     * 任务是否已经启动
     */
    private Boolean isStart(String taskKey) {
        //校验是否已经启动
        if (scheduledFutureMap.containsKey(taskKey)) {
            return !scheduledFutureMap.get(taskKey).isCancelled();
        }
        return false;
    }


}
