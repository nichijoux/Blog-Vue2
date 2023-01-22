package com.zh.blog.service;

import com.zh.blog.domain.entity.MailBean;

public interface MailService {
    /**
     * 发送邮箱验证码
     *
     * @param recipient    邮件接收人
     * @param validateCode 验证码
     */
    void sendValidateCode(String recipient, String validateCode);

    /**
     * 发送HTML信息
     *
     * @param mailBean 邮件bean
     */
    void sendHTMLMail(MailBean mailBean);
}
