/*
 * Copyright (c) 2023. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleVO  extends AbstractAuditingEntity{
  private static final long serialVersionUID = -8707066666923250154L;

  private long id;
  private String roleName;
  private String roleExplain;

}
