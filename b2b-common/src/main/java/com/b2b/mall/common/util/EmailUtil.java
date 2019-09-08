package com.b2b.mall.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * @auther kiwi
 * @Date 2019/9/8 19:26
 */
@Slf4j
@Component
@PropertySource(value = {"classpath:email.properties"})
public class EmailUtil {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.password}")
    private String pwd;


    public void sendEmail(String to, String subject, String content) throws Exception {

        log.info("host is" +host);
        log.info("pwd is" +pwd);
        log.info("send is" +sender);
        Properties props = new Properties();

        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", host);
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        // 开启debug模式，可以看到更多详细的输入日志
        MimeMessage message = this.addEmail(session,to,subject, content);

        //获取传输通道
        Transport transport = session.getTransport();
        transport.connect(host, sender, pwd);
        //连接，并发送邮件
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();


    }
        /**
         * 创建邮件
         */
        public MimeMessage addEmail(Session session,String to, String subject, String content) throws Exception {
            // 根据会话创建邮件
            MimeMessage msg = new MimeMessage(session);

            // 设置发送邮件方
            msg.setFrom(new InternetAddress(sender));
            // 设置邮件接收方
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // 设置邮件标题

//            helper = new MimeMessageHelper(message,true);
//
//            helper.setText(content,true);
            msg.setSubject(subject, "utf-8");
            msg.setContent(content,"text/html;charset=utf-8");
            // 设置显示的发件时间
            msg.setSentDate(new Date());
            // 保存设置
            msg.saveChanges();
            return msg;
        }

}

