/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LIMEXC
 * @since 2022-05-14
 **/
@Data
public class SystemLogVO implements Serializable {
    private Integer id;
    private String domain;
    private String method;
    private String apiName;
    private String ipAddr;
    private String device;
    private String url;
    private String apiMethod;
    private Integer totalTime;
    private Date time;
    private String nation;
    private String province;
    private String city;
    private String param;
    private String result;
    private String osType;
    private String browser;
    private String browserVersion;
}
