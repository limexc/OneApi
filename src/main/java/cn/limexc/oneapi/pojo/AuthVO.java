/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthVO extends AbstractAuditingEntity{

  private static final long serialVersionUID = -8707016666923250254L;

  private long id;
  private String name;
  private String authUrl;
  private String permission;
  private String authType;
  private String pid;
  private String icon;

}
