/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 手机号码归属地实体
 *
 * @author LIMEXC
 * @since 2022-05-11
 **/
@Data
public class MobileNumberBaseVO implements Serializable {
    /**
     * 号段 7位
     */
    private Integer paragraph;
    /**
     * 号段 3位
     */
    private Integer prefix;
    /**
     * 所属省
     */
    private String province;
    /**
     * 所属市
     */
    private String city;
    /**
     * 运营商
     */
    private String isp;
    /**
     * 邮编
     */
    private String zipCode;
    /**
     * 区号
     */
    private String zoneDescription;
}
