package com.b2b.mall.common.jms;

import com.b2b.mall.common.util.EmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @auther kiwi
 * @Date 2019/9/7 23:48
 */
@Slf4j
@Service
@PropertySource("email.properties")
public class EmailServiceImpl implements EmailService {


    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to, String subject, String content) throws  IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper =null;
        try {
            helper = new MimeMessageHelper(message,true);
            helper.setFrom(sender);
            helper.setSubject(subject);
            helper.setTo(to);

            helper.setText(content,true);
            javaMailSender.send(message);
            log.info("邮件已经发送");
        }catch (MessagingException e){
            log.error("邮件发送异常",e);
        }
    }
}
