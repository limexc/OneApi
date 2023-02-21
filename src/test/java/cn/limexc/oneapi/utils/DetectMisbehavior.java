/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.utils;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.tiia.v20190529.TiiaClient;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorRequest;
import com.tencentcloudapi.tiia.v20190529.models.DetectMisbehaviorResponse;

/**
 * 不良行为检测
 *
 * @author LIMEXC
 * @since 2022-06-23
 **/
public class DetectMisbehavior {
    public static void main(String[] args) {
        try {
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            // 删除该密钥
            Credential cred = new Credential("", "");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("tiia.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            TiiaClient client = new TiiaClient(cred, "ap-shanghai", clientProfile);
            // 实例化一个请求对象,每个接口都会对应一个request对象
            DetectMisbehaviorRequest req = new DetectMisbehaviorRequest();
            req.setImageUrl("https://file.limexc.cn/images/202206/79e0b9f092ec574ebbae276b1aff6e55.jpg");
            // 返回的resp是一个DetectMisbehaviorResponse的实例，与请求对象对应
            DetectMisbehaviorResponse resp = client.DetectMisbehavior(req);
            // 输出json格式的字符串回包
            System.out.println(DetectMisbehaviorResponse.toJsonString(resp));
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}