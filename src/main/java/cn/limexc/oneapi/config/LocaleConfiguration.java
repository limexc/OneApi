/*
 * Copyright (c) 2024. By LIMEXC
 */

package cn.limexc.oneapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * 自定义LocaleResolver
 *
 * @author limexc
 * @since 2022/3/30 21:25
 */
@Configuration
public class LocaleConfiguration  implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        // 也可以换成 SessionLocalResolver, 区别在于国际化的应用范围
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver("language");
        // 设置默认的语言，国别。 SIMPLIFIED_CHINESE
        cookieLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        // 不允许 JS 读写此 Cookie
        cookieLocaleResolver.setCookieHttpOnly(true);
        return cookieLocaleResolver;
    }

    /**
     * 根据请求参数，来设置本地化
     *
     * @return
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(localeChangeInterceptor());
    }

}

