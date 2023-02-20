/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 简单的Api提供服务
 *
 * @author ADMIN
 */
@EnableAsync
@EnableScheduling
@SpringBootApplication
public class OneApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneApiApplication.class, args);
    }

}
