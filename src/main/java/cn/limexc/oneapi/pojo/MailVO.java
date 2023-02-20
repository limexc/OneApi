/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 邮件
 *
 * @author LIMEXC
 * @since 2022-06-22
 **/
@Data
public class MailVO {
    /**
     * 邮件id
     */
    private String id;
    /**
     * 同一messageId可视为同一封邮件
     */
    private String messageId;
    /**
     * 邮件发送人
     */
    private String from;
    /**
     * 邮件接收人（多个邮箱则用逗号","隔开）
     */
    private String to;
    /**
     * 抄送（多个邮箱则用逗号","隔开）
     */
    private String cc;
    /**
     * 密送（多个邮箱则用逗号","隔开）
     */
    private String bcc;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String text;
    /**
     * 发送时间
     */
    private Date sentDate;
    /**
     * 状态
     */
    private String status;
    /**
     * 报错信息
     */
    private String error;
    /**
     * 邮件附件
     */
    @JsonIgnore
    private MultipartFile[] multipartFiles;
}
