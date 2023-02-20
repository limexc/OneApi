/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.controller;

import cn.limexc.oneapi.annotation.SystemLog;
import cn.limexc.oneapi.mapper.MobileNumberBaseMapper;
import cn.limexc.oneapi.pojo.MobileNumberBaseVO;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LIMEXC
 * @since 2022-05-11
 **/
@RestController
@RequestMapping("/v1/mobile")
public class MobileController {

    @Autowired
    MobileNumberBaseMapper mobileNumberBaseMapper;

    @SystemLog("手机归属地查询")
    @GetMapping("/base")
    public MobileNumberBaseVO testApi(@RequestParam(value = "mobile",required = false)String mobile) {
        int length = 7;
        if (StringUtil.isNullOrEmpty(mobile)){
            return new MobileNumberBaseVO();
        }
        if (mobile.length()<length){
            return new MobileNumberBaseVO();
        }
        if (mobile.length()>length){
            mobile = mobile.substring(0,7);
        }
        MobileNumberBaseVO vo = mobileNumberBaseMapper.selectMobileNumberBase(mobile);
        return vo;
    }

}
