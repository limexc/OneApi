/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.controller;

import cn.limexc.oneapi.annotation.SystemLog;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LIMEXC
 * @since 2022-05-15
 **/
@RestController
@RequestMapping("/v1/test")
public class TestController {

    @RequestMapping("/oneApi")
    @SystemLog("测试接口")
    public String testOneApi(){
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("code",200);
        res.put("msg","success! Test OneApi");
        return JSONObject.toJSONString(res);
    }
}
