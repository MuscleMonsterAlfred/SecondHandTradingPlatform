package com.gla.catarina.util;

import com.gla.catarina.config.EnvConfig;
import freemarker.template.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 〈功能详细描述〉
 * 邮件发送工具
 *
 * @author yangfan1
 * @date 2021/10/31
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@Component
@Slf4j
public class EmailUtils {

    @Resource
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Value("${spring.mail.nickname}")
    private String nickname;

    @Resource
    private EnvConfig envConfig;

    @Resource
    private Configuration configuration;

    /**
     * 发送文本邮件
     *
     * @param to      收件人
     * @param subject 标题
     * @param content 正文
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        //发件人
        message.setFrom(nickname+'<'+from+'>');
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /**
     * 发送html邮件
     */
    public String sendHtmlMail(String to, String subject, String content) {
        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(nickname+'<'+from+'>');
            helper.setTo(to.split(";"));
            helper.setSubject(subject);
            //第二个参数：格式是否为html
            helper.setText(content, true);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
            return e.getMessage();
        }
        return null;
    }

    /**
     * 一些默认的参数
     *
     * @param param 参数
     */
    public void addParam(Map<String, Object> param) {
        List<EnvConfig.Entry> envs = envConfig.getEnvs();
        if (CollectionUtils.isNotEmpty(envs)) {
            envs.forEach(e -> {
                param.put(e.getKey(), e.getValue());
            });
        }
    }

    /**
     * 发送模板邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param template 模板文件的地址，在templates文件下
     * @param param    参数
     */
    public String sendTemplateMail(String to, String subject, String template, Map<String, Object> param) {
        try {
            // 获得模板
            Template temp = configuration.getTemplate(template);

            addParam(param);

            // 传入数据模型到模板，替代模板中的占位符，并将模板转化为html字符串
            String templateHtml = FreeMarkerTemplateUtils.processTemplateIntoString(temp, param);
            // 该方法本质上还是发送html邮件，调用之前发送html邮件的方法
            return this.sendHtmlMail(to, subject, templateHtml);
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
            return e.getMessage();
        }
    }

    /**
     * 发送带附件的邮件
     *
     * @param to       收件人
     * @param subject  主题
     * @param content  内容
     * @param filePath 文件路径
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //要带附件第二个参数设为true
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(nickname+'<'+from+'>');
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送邮件时发生异常！", e);
        }

    }

    /**
     * 发送带附件的邮件模板
     *
     * @param to       收件人
     * @param subject  主题
     * @param filePath 文件路径
     * @param template 模板路径
     * @param param    参数
     */
    public void sendAttachmentsMail(String to, String subject, String filePath, String template, Map<String, Object> param) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            //要带附件第二个参数设为true
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(nickname+'<'+from+'>');
            helper.setTo(to);
            helper.setSubject(subject);

            // 获得模板
            Template temp = configuration.getTemplate(template);
            // 传入数据模型到模板，替代模板中的占位符，并将模板转化为html字符串
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(temp, param);
            //第二个参数：格式是否为html
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("发送邮件时发生异常！", e);
        }

    }
}
