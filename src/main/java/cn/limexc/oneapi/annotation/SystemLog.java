/*
 * Copyright (c) 2021-2022. By LIMEXC
 */

package cn.limexc.oneapi.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 *
 * @author LIMEXC
 * @since 2022 -05-12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface SystemLog {
    /**
     * Value string.
     *
     * @return the string
     */
    String value() default "日志注解";
}
