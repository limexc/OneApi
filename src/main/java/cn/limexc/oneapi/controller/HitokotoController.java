/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.controller;

import cn.limexc.oneapi.annotation.SystemLog;
import cn.limexc.oneapi.pojo.HitokotoVO;
import cn.limexc.oneapi.service.HitokotoService;
import com.alibaba.fastjson.JSONObject;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 一言数据接口
 *
 * @author LIMEXC
 * @since 2022-05-13
 **/
@RestController
@RequestMapping("/v1/hitokoto")
public class HitokotoController {

    @Autowired
    HitokotoService hitokotoService;

    @GetMapping()
    @SystemLog("一言")
    public String simpleHitokoto(
            @RequestParam(value = "c", required = false) String type,
            @RequestParam(value = "encode", required = false) String enCode,
            @RequestParam(value = "max_length", required = false) String maxLength,
            @RequestParam(value = "min_length", required = false) String minLength) {
        HitokotoVO hitokoto = hitokotoService.getHitokoto(type, minLength, maxLength);
        if (StringUtil.isNullOrEmpty(enCode)){
            enCode = "";
        }
        switch (enCode) {
            case "text":
                return hitokoto.getHitokoto();
            case "json":
            default:
                return JSONObject.toJSONString(hitokoto);
        }


    }

}
