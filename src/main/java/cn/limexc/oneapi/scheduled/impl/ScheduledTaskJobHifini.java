/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.scheduled.impl;

import cn.hutool.core.util.RandomUtil;
import cn.limexc.oneapi.pojo.AuthInfoVO;
import cn.limexc.oneapi.scheduled.ScheduledTaskJob;
import cn.limexc.oneapi.service.AuthInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * www.hifini.com 网站签到
 *
 * @author LIMEXC
 * @since 2022-05-24
 **/
@Slf4j
@Service
public class ScheduledTaskJobHifini implements ScheduledTaskJob {

    @Autowired
    AuthInfoService authInfoService;

    public static void main(String[] args) {
        new ScheduledTaskJobHifini().run();
    }

    @Override
    public void run() {
        // TODO 要处理的业务逻辑
        log.info("ScheduledTask => 01  run  当前线程名称 {} ", Thread.currentThread().getName());
        todoSign();
    }

    public void todoSign() {
        int sleepTime = RandomUtil.randomInt(10000, 1800000);
        log.info("签到任务开始！准备睡眠{}秒", sleepTime / 1000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error("睡眠 出错！");
        }
        log.info("睡眠结束！hifini开始签到！");
        // 获取验证信息
        AuthInfoVO authInfo = authInfoService.selectAuthInfo(2);
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = RequestBody.create(mediaType, "");
            Request request = new Request.Builder()
                    .url("https://www.hifini.com/sg_sign.htm")
                    .method("POST", body)
                    .addHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"102\", \"Microsoft Edge\";v=\"102\"")
                    .addHeader("Accept", "text/plain, */*; q=0.01")
                    .addHeader("DNT", "1")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .addHeader("sec-ch-ua-mobile", "?0")
                    .addHeader("User-Agent", authInfo.getUserAgent())
                    .addHeader("sec-ch-ua-platform", "\"Windows\"")
                    .addHeader("Sec-Fetch-Site", "same-origin")
                    .addHeader("Sec-Fetch-Mode", "cors")
                    .addHeader("Sec-Fetch-Dest", "empty")
                    .addHeader("host", "www.hifini.com")
                    .addHeader("Cookie", authInfo.getAuthentication())
                    .build();
            Response response = client.newCall(request).execute();
            String reqBody = response.body().string();
            JSONObject jsonObject = JSON.parseObject(reqBody);
            log.info(jsonObject.toJSONString());
        } catch (IOException e) {
            log.error("签到时发生错误！错误信息：", e);
        }

    }
}
