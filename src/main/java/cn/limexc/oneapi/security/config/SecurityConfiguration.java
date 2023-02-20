/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.security.config;

import cn.limexc.oneapi.filter.JwtAuthorizationFilter;
import cn.limexc.oneapi.security.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;


/**
 * Web 安全配置
 *
 * @author LIMEXC
 **/
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private CorsFilter corsFilter;

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                // 配置无需权限即可访问的地址
                // 所有V1版本接口无需设置权限拦截
                .requestMatchers( "/v1/**").permitAll()
//                .requestMatchers(HttpMethod.POST, "/v1/auth/login").permitAll()
//                .requestMatchers(HttpMethod.POST,"/v1/auth/register").permitAll()
//                .requestMatchers(HttpMethod.POST,"/v1/auth/logout").permitAll()
                // 其他请求需验证
                .anyRequest().authenticated()
                // 开启模拟请求，比如API POST测试工具的测试，不开启时，API POST为报403错误
                .and().cors()
                // 基于 token,不需要 csrf
                .and().csrf().disable()
                // 基于 token,不需要 session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                //添加拦截器
                .addFilter(new JwtAuthorizationFilter(authenticationManager))
                .httpBasic();

        // 禁用缓存
        http.headers().cacheControl();
        //允许跨域
        http.headers().frameOptions().disable();
        return http.build();
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     *
     * @param authenticationConfiguration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * 忽略静态资源
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        configuration.setExposedHeaders(Collections.singletonList(SecurityConstants.TOKEN_HEADER));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
