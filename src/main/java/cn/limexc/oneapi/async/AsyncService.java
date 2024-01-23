/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.async;

import cn.limexc.oneapi.mapper.SystemLogMapper;
import cn.limexc.oneapi.pojo.SystemLogVO;
import cn.limexc.oneapi.utils.IpAddrUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author LIMEXC
 * @since 2022-05-15
 **/
@Slf4j
@Service
public class AsyncService {

    @Autowired
    SystemLogMapper systemLogMapper;

    @Value("${aliYun.accessKeyId}")
    private String accessKeyId;
    @Value("${aliYun.accessKeySecret}")
    private String accessKeySecret;

    @Async("saveLogToRepoExecutor")
    public void logToRepo(SystemLogVO systemLog, HttpServletRequest request,
                          String apiMethod, String annotation, String reqParam,
                          Object result, Long totalTimeMillis) {
        log.info("开始记录日志 --> saveLogToRepoExecutor");

        try {
            // 5.组装日志记录信息
            String ip = IpAddrUtils.getIpAddr(request);
            userAgent(request, systemLog);
            ipToCity(ip, systemLog);
            systemLog.setIpAddr(ip);
            systemLog.setApiName(annotation);
            systemLog.setParam(reqParam);
            if (result instanceof String){
                systemLog.setResult((String) result);
            } else {
                systemLog.setResult(JSONObject.toJSONString(result));
            }
            systemLog.setTotalTime(Math.toIntExact(totalTimeMillis));
            systemLog.setApiMethod(apiMethod);
            systemLogMapper.insertSystemLogData(systemLog);
        } catch (Exception e) {
            log.error("写系统日志异常！", e);
        }
        log.info("结束日志记录 --> saveLogToRepoExecutor");
    }

    @Async("aliYunSmsExecutor")
    public void sendSms(String phoneNum, String signName, String templateCode, String templateParam) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setPhoneNumbers(phoneNum);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam(templateParam);
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            log.info(new Gson().toJson(response));
        } catch (ServerException e) {
            log.error("AliYun Error", e);
        } catch (ClientException e) {
            log.error("AliYunSMS ERROR --> ErrCode:" + e.getErrCode() + "ErrMsg:" + e.getErrMsg() + "RequestId:" + e.getRequestId());
        }
    }


    /**
     * 获取用户设备信息
     *
     * @param request 请求
     */
    private void userAgent(HttpServletRequest request, SystemLogVO systemLogVO) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("user-agent"));
        String clientType = toString(userAgent.getOperatingSystem().getDeviceType());
        String os = userAgent.getOperatingSystem().getName();
        String browser = toString(userAgent.getBrowser());
        String browserVersion = toString(userAgent.getBrowserVersion());
        log.info("客户端: {} 系统:{}  浏览器:{} 版本:{}", clientType, os, browser, browserVersion);
        systemLogVO.setBrowser(browser);
        systemLogVO.setDevice(clientType);
        systemLogVO.setOsType(os);
        systemLogVO.setBrowserVersion(browserVersion);
    }

    private void ipToCity(String ip, SystemLogVO systemLogVO) {
        Map<String, String> addressInfo = IpAddrUtils.getCityInfo(ip);
        if (null != addressInfo) {
            String nation = addressInfo.get("nation");
            String province = addressInfo.get("province");
            String city = addressInfo.get("city");
            systemLogVO.setNation(nation);
            systemLogVO.setProvince(province);
            systemLogVO.setCity(city);
            log.info("发起请求ip:{} ,来自：{},- {},- {}", ip, nation, province, city);
        }
    }

    /**
     * 转换obj为String，并防止直接使用obj.toString时空指针
     *
     * @param obj 任意obj
     * @return string或null
     */
    private String toString(Object obj) {
        return obj == null ? null : obj.toString();
    }

}
