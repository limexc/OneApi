/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步配置(线程池)
 *
 * @author LIMEXC
 * @since 2022-05-15
 **/
@Configuration
@EnableAsync
public class AsyncConfiguration {

    /**
     * 保存日志到数据库线程
     *
     * @return Executor 保存日志到数据库
     */
    @Bean("saveLogToRepoExecutor")
    public Executor logToRepositoryExecutor() {
        ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("logExecutor-");
        return getThreadPoolTaskExecutor(executor);
    }

    /**
     * 阿里云发送短信线程
     *
     * @return Executor 阿里云发送短信
     */
    @Bean("aliYunSmsExecutor")
    public Executor aliYunSmsExecutor() {
        ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("smsExecutor-");
        return getThreadPoolTaskExecutor(executor);
    }

    /**
     * S3文件操作线程
     *
     * @return Executor S3
     */
    @Bean("s3Executor")
    public Executor s3Executor() {
        ThreadPoolTaskExecutor executor =  new ThreadPoolTaskExecutor();
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("s3Executor-");
        return getThreadPoolTaskExecutor(executor);
    }

    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor(ThreadPoolTaskExecutor executor) {
        // 核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(5);
        // 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(10);
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(100);
        // 允许线程的空闲时间60秒：当超过了核心线程之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 缓冲队列满了之后的拒绝策略：由调用线程处理（一般是主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

}
