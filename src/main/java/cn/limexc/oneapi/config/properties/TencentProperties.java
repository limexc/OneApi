/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 调用腾讯API所需要的配置密钥加载
 *
 * @author LIMEXC
 */
@Component
@ConfigurationProperties(prefix = "tencent")
public class TencentProperties {

    /**
     * 腾讯云账号的 APPID,是与账号 ID 有唯一对应关系的应用 ID,部分腾讯云产品会使用此 APPID。
     */
    private static String appId;

    /**
     * secretId
     */
    private static String secretId;

    /**
     * secretKey
     */
    private static String secretKey;

    /**
     * 腾讯位置服务申请的Key,应该可以用上面的调其他接口解析Ip的位置信息
     */
    private static String addressKey;

    public static String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        TencentProperties.appId = appId;
    }

    public static String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        TencentProperties.secretId = secretId;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        TencentProperties.secretKey = secretKey;
    }

    public static String getAddressKey() {
        return addressKey;
    }

    public void setAddressKey(String addressKey) {
        TencentProperties.addressKey = addressKey;
    }
}
