/*
 * Copyright (c) 2022-2023. By LIMEXC
 */

package cn.limexc.oneapi.interceptor;

import cn.hutool.core.date.StopWatch;
import cn.limexc.oneapi.annotation.SystemLog;
import cn.limexc.oneapi.async.AsyncService;
import cn.limexc.oneapi.pojo.SystemLogVO;
import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

/**
 * @author LIMEXC
 * @since 2022-05-12
 **/

@Slf4j
@Aspect
@Component
public class SystemLogInterceptor {

    @Autowired
    AsyncService asyncService;

    /**
     * 在controller中各方法执行之前和之后运行
     *
     * @param point 切入点
     * @return
     * @throws Throwable
     */
    @Around("execution(* cn.limexc.oneapi.controller.*.*(..)) && (@annotation(cn.limexc.oneapi.annotation.SystemLog))")
    public Object interceptor(ProceedingJoinPoint point) throws Throwable {
        // Spring计时器StopWatch
        StopWatch stopWatch = new StopWatch();

        stopWatch.start();


        // 1.获取请求路径
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = requestAttributes.getRequest();
        String url = httpServletRequest.getRequestURI();
        // 2.获取请求参数
        Object[] args = point.getArgs();
        String reqParam = null;
        String requestContentType = httpServletRequest.getContentType()== null ? "" : httpServletRequest.getContentType();
        SystemLogVO systemLog = new SystemLogVO();
        systemLog.setMethod(httpServletRequest.getMethod());
        systemLog.setDomain(httpServletRequest.getServerName());
        systemLog.setUrl(httpServletRequest.getRequestURI());
        // 不同请求类型使用不同的方法获取入参
        try{
            if (requestContentType.contains("application/json")){
                // args数组里面存储的就是controller里方法里面的入参顺序参数，一般JSON格式入参就一个对象接收参数，那就取第0个，否则可以全部输出args
                reqParam = JSONObject.toJSONString(args[0]);
            }else{
                reqParam =  JSONObject.toJSONString(httpServletRequest.getParameterMap());
            }
        }catch (Exception e){
            log.error("错误！获取入参时出现错误！",e);
        }

        // 3.获取方法相关信息
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        SystemLog annotation = method.getAnnotation(SystemLog.class);

        String methodStr = point.getSignature().getDeclaringTypeName() + "." + point.getSignature().getName();

        log.info("请求开始，请求路径：{},请求方法：{},请求方法说明：{},请求参数：{}",url,methodStr,annotation.value(),reqParam);

        // 4.获取响应数据
        Object result = point.proceed();

        stopWatch.stop();
        asyncService.logToRepo(systemLog,httpServletRequest,methodStr,annotation.value(),reqParam,result,stopWatch.getTotalTimeMillis());
        log.info("请求结束,耗时{}毫秒，响应参数：{}",stopWatch.getTotalTimeMillis(),result);

        return result;
    }



}
