package com.b2b.mall.common.jms;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @auther kiwi
 * @Date 2019/9/7 22:15
 */


public interface EmailService {
    public  void sendEmail(String to,String subject,String content) throws IOException;
}
