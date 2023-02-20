/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO  extends AbstractAuditingEntity{
  private static final long serialVersionUID = -8707066666923233355L;

  private long id;
  private String username;
  private String password;
  private String nickName;
  private String cellPhone;
  private String sign;
  private String pictureId;
  private String sex;
  private String mail;
  private Timestamp birthday;
  private String status;
  private String accountType;
  private String inviteCode;
  private String address;

}
