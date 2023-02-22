/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 与JWT相关配置加载
 *
 * @author LIMEXC
 */

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    private static String jwtSecretKey;

    public static String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public void setJwtSecretKey(String jwtSecretKey) {
        JwtProperties.jwtSecretKey = jwtSecretKey;
    }
}
