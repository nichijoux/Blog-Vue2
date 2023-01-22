package com.zh.blog.service.impl;

import com.zh.blog.domain.entity.MailBean;
import com.zh.blog.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String MAIL_SENDER;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendValidateCode(String target, String validateCode) {
        Context context = new Context();
        context.setVariable("recipient", target);
        context.setVariable("validateCode", validateCode);
        String mailContent = templateEngine.process("codeTemplate", context);
        MailBean mailBean = MailBean.builder()
                .recipient(target)
                .subject("[nichijoux's blog] ：请查收您的验证码")
                .content(mailContent)
                .build();
        sendHTMLMail(mailBean);
    }

    @Override
    public void sendHTMLMail(MailBean mailBean) {
        MimeMessage mimeMailMessage;
        try {
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            // 邮件发送人
            mimeMessageHelper.setFrom(MAIL_SENDER);
            // 邮件接收人
            mimeMessageHelper.setTo(mailBean.getRecipient());
            // 邮件主题
            mimeMessageHelper.setSubject(mailBean.getSubject());
            // 邮件内容
            mimeMessageHelper.setText(mailBean.getContent(), true);
            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
