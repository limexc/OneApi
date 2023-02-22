/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LIMEXC
 * @since 2022-06-18
 **/
@Component
@ConfigurationProperties(prefix = "backblaze.s3")
public class S3Properties {
    private static String endpoint;
    private static String bucketName;
    private static String accessKey;
    private static String secretKey;

    public static String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        S3Properties.endpoint = endpoint;
    }

    public static String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        S3Properties.bucketName = bucketName;
    }

    public static String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        S3Properties.accessKey = accessKey;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        S3Properties.secretKey = secretKey;
    }
}
