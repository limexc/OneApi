/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.hutool.core.codec.Base64;
import cn.limexc.oneapi.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;

/**
 * @author LIMEXC
 * @since 2022-05-17
 **/
@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    @Value("${seniverse.private-key}")
    private String TIANQI_API_SECRET_KEY;
    @Value("${seniverse.public-key}")
    private String TIANQI_API_USER_ID;

    /**
     * 使用给定的数据字符串和密钥生成HmacSHA1签名
     *
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // 从原始密钥字节中获取hmac_sha1密钥
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            // 获取一个hmac_sha1 Mac实例并使用签名密钥进行初始化
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // 计算输入数据字节的hmac
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            result = Base64.encode(rawHmac);
        } catch (Exception e) {
            log.error("生成HMAC失败 : " ,e);
            throw new SignatureException("生成HMAC失败 : " + e.getMessage());
        }
        return result;
    }

    /**
     * 生成获取天气日志的URL
     *
     * @param location
     * @param language
     * @param unit
     * @param start
     * @param days
     * @return
     */
    @Override
    public String generateGetDiaryWeatherURL( String location, String language, String unit, String start, String days ) {
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
            String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), StandardCharsets.UTF_8);
            return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
        }catch (Exception e){
            log.error("生成天气api地址错误！",e);
        }
        return null;
    }

}
