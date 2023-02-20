/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.scheduled.config;

import cn.limexc.oneapi.scheduled.ScheduledTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author LIMEXC
 * @since 2022-05-24
 **/
@Slf4j
@Component
public class ScheduledTaskRunner implements ApplicationRunner {

    @Autowired
    private ScheduledTaskService scheduledTaskService;

    /**
     * 程序启动完毕后,需要自启的任务
     */
    @Override
    public void run(ApplicationArguments applicationArguments){
        log.info(">>>>>> 项目启动完毕, 开启 => 需要自启的任务 开始!");
        scheduledTaskService.initAllTask();
        log.info(">>>>>> 项目启动完毕, 开启 => 需要自启的任务 结束！");
    }

}
