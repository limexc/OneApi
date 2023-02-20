/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 认证信息表
 * @TableName t_auth_info
 */
@Data
public class AuthInfoVO implements Serializable {
    /**
     * 唯一标识Id
     */
    private Integer id;

    /**
     * 站点名称
     */
    private String siteName;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 接口url地址
     */
    private String url;

    /**
     * 请求方法：POST/GET/PUT/DELETE
     */
    private String method;

    /**
     * 认证方式：Token/Cookie
     */
    private String authenticationMethod;

    /**
     * 认证信息
     */
    private String authentication;

    /**
     * 内容类型
     */
    private String contentType;

    /**
     * 健康程度：0未知 1健康 2死亡 3过期
     */
    private Integer isHealthy;

    /**
     * 浏览器用户标识
     */
    private String userAgent;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户
     */
    private String createUser;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AuthInfoVO other = (AuthInfoVO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSiteName() == null ? other.getSiteName() == null : this.getSiteName().equals(other.getSiteName()))
            && (this.getJobName() == null ? other.getJobName() == null : this.getJobName().equals(other.getJobName()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
            && (this.getAuthenticationMethod() == null ? other.getAuthenticationMethod() == null : this.getAuthenticationMethod().equals(other.getAuthenticationMethod()))
            && (this.getAuthentication() == null ? other.getAuthentication() == null : this.getAuthentication().equals(other.getAuthentication()))
            && (this.getContentType() == null ? other.getContentType() == null : this.getContentType().equals(other.getContentType()))
            && (this.getIsHealthy() == null ? other.getIsHealthy() == null : this.getIsHealthy().equals(other.getIsHealthy()))
            && (this.getUserAgent() == null ? other.getUserAgent() == null : this.getUserAgent().equals(other.getUserAgent()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSiteName() == null) ? 0 : getSiteName().hashCode());
        result = prime * result + ((getJobName() == null) ? 0 : getJobName().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getAuthenticationMethod() == null) ? 0 : getAuthenticationMethod().hashCode());
        result = prime * result + ((getAuthentication() == null) ? 0 : getAuthentication().hashCode());
        result = prime * result + ((getContentType() == null) ? 0 : getContentType().hashCode());
        result = prime * result + ((getIsHealthy() == null) ? 0 : getIsHealthy().hashCode());
        result = prime * result + ((getUserAgent() == null) ? 0 : getUserAgent().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", siteName=").append(siteName);
        sb.append(", jobName=").append(jobName);
        sb.append(", url=").append(url);
        sb.append(", method=").append(method);
        sb.append(", authenticationMethod=").append(authenticationMethod);
        sb.append(", authentication=").append(authentication);
        sb.append(", contentType=").append(contentType);
        sb.append(", isHealthy=").append(isHealthy);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createUser=").append(createUser);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}