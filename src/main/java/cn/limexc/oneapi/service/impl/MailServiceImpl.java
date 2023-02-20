/*
 * Copyright (c) 2022. By LIMEXC
 */

package cn.limexc.oneapi.service.impl;

import cn.limexc.oneapi.service.MailService;

/**
 * 邮件发送实现类
 *
 * @author LIMEXC
 * @since 2022-06-22
 **/
public class MailServiceImpl implements MailService {
    /**
     * 发送普通文本邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {

    }

    /**
     * 发送HTML邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容（可以包含<html>等标签）
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {

    }

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     */
    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filePath) {

    }

    /**
     * 发送带图片的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 文本
     * @param rscPath 图片路径
     * @param rscId   图片ID，用于在 img 标签中使用，从而显示图片
     */
    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

    }

    /**
     * 发送带图片的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  文本
     * @param filePath 附件路径
     * @param rscPath  图片路径
     * @param rscId    图片ID，用于在 img 标签中使用，从而显示图片
     */
    @Override
    public void sendInlineAndAttachMail(String to, String subject, String content, String filePath, String rscPath, String rscId) {

    }
}
