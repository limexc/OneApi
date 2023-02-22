/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.utils;



import cn.limexc.oneapi.config.properties.TencentProperties;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;


/**
 * 使用腾讯的接口通过ip拿到城市信息
 *
 * @author LIMEXC
 * @since 2022-05-13
 **/
@Slf4j
public class IpToAddressUtils {

    private static final String ADDRESS_KEY = TencentProperties.getAddressKey();

    public static Map<String ,String > getCityInfo(String ip)  {
        String s = sendGet(ip, ADDRESS_KEY);
        Map map = JSONObject.parseObject(s, Map.class);
        String message = (String) map.get("message");
        if("Success".equals(message)){
            Map result = (Map) map.get("result");
            return (Map) result.get("ad_info");
        }else{
            log.info(message);
            return null;
        }
    }
    /**
     * 根据在腾讯位置服务上申请的key进行请求操作
     */
    public static String sendGet(String ip, String key) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = "https://apis.map.qq.com/ws/location/v1/ip?ip="+ip+"&key="+key;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader( connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常!{}",e.getMessage(),e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("关闭流异常!{}",e2.getMessage(),e2);
            }
        }
        return result.toString();
    }

}