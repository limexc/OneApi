/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public abstract class AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = -8707066666923250254L;

    private String createUser;
    private Timestamp createTime;
    private String updateUser;
    private Timestamp updateTime;


}
