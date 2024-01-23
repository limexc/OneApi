/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;


/**
 * HTTP工具类
 *
 * @author ADMIN
 */
@Slf4j
public final class HttpUtils {

    private static final Integer CODE_5 = 5;
    private static final Integer CODE_20 = 20;
    private static final Integer CODE_200 = 200;


    /**
     * getClient
     *
     * @return OkHttpClient
     */

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder().
                connectTimeout(CODE_5, TimeUnit.SECONDS).
                readTimeout(CODE_20, TimeUnit.SECONDS).
                writeTimeout(CODE_20, TimeUnit.SECONDS).
                build();
    }


    /**
     * getRequest
     *
     * @param url url
     * @return Request
     * @throws MalformedURLException 异常
     */
    private static Request getRequest(String url) throws MalformedURLException {
        Request.Builder builder = getBuilder();
        URL uri = new URL(url);
        return builder.url(uri).build();
    }


    /**
     * doGet
     *
     * @param url url
     * @param param param
     * @return 结果
     */
    public static String doGet(String url, String param) {
        if (param != null) {
            url = url + "?" + param;
        }
        OkHttpClient okHttpClient = getClient();

        String result = null;
        try {
            Request request = getRequest(url);
            Response response = okHttpClient.newCall(request).execute();
            int code = response.code();
            String msg = response.message();
            if (code == CODE_200) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
                log.error("GET请求地址:" + url + "返回状态异常,code值为:" + code + "异常信息:" + msg);
            }
        } catch (IOException e) {
            log.error("GET请求异常,url = {}", url, e);
        }
        return result;
    }

    /**
     * getBuilder
     *
     * @return Builder
     */
    private static Request.Builder getBuilder() {
        Request.Builder builder = new Request.Builder();

        builder.addHeader("accept", "application/json").
                addHeader("connection", "Keep-Alive").
                addHeader("Content-Type", "application/json;charset=UTF-8");
        return builder;
    }

    /**
     * doPost
     *
     * @param url url
     * @param param param
     * @return 请求结果
     */
    public static String doPost(String url, String param) {

        OkHttpClient okHttpClient = getClient();
        Request.Builder builder = getBuilder();
        String result = null;
        try {
            RequestBody requestBody = RequestBody.create(param.getBytes(StandardCharsets.UTF_8),
                    MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE));
            builder.post(requestBody);
            Request request = builder.url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            int code = response.code();
            String msg = response.message();
            if (code == CODE_200) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
                log.error("POST请求地址:" + url + "参数:" + param + "返回状态异常,code值为:" + code + "异常信息:" + msg);
            }
        } catch (Exception e) {
            log.error("POST请求异常,url = {}", url, e);
        }
        return result;
    }

    /**
     * doDelete
     *
     * @param url url
     * @param param param
     * @return 请求结果
     */
    public static String doDelete(String url, String param) {

        OkHttpClient okHttpClient = getClient();
        Request.Builder builder = getBuilder();
        String result = null;
        try {
            if (param != null) {
                RequestBody requestBody = RequestBody.create(param.getBytes(StandardCharsets.UTF_8),
                        MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE));
                builder.delete(requestBody);
            } else {
                builder.delete();
            }
            Request request = builder.url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            int code = response.code();
            String msg = response.message();
            if (code == CODE_200) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
                log.error("DELETE请求地址:" + url + "参数:" + param + "返回状态异常,code值为:" + code + "异常信息:" + msg);
            }
        } catch (Exception e) {
            log.error("DELETE请求异常,url = {}", url, e);
        }
        return result;
    }

    /**
     * doPut
     *
     * @param url url
     * @param param param
     * @return 请求结果
     */
    public static String doPut(String url, String param) {

        OkHttpClient okHttpClient = getClient();
        Request.Builder builder = getBuilder();
        String result = null;
        try {
            RequestBody requestBody = RequestBody.create(param.getBytes(StandardCharsets.UTF_8),
                    MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE));
            builder.put(requestBody);
            Request request = builder.url(url).build();
            Response response = okHttpClient.newCall(request).execute();
            int code = response.code();
            String msg = response.message();
            if (code == CODE_200) {
                ResponseBody body = response.body();
                if (body != null) {
                    result = body.string();
                } else {
                    throw new IOException("response body is null");
                }
            } else {
                response.close();
                log.error("PUT请求地址:" + url + "参数:" + param + "返回状态异常,code值为:" + code + "异常信息:" + msg);
            }
        } catch (Exception e) {
            log.error("PUT请求异常,url = {}", url, e);
        }
        return result;
    }

    private HttpUtils() {

    }
}

