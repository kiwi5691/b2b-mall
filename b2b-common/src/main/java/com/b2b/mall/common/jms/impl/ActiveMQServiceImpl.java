package com.b2b.mall.common.jms.impl;

import com.b2b.mall.common.jms.ActiveMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

/**
 * @auther kiwi
 * @Date 2019/9/8 16:43
 */
@Slf4j
@Service
public class ActiveMQServiceImpl implements ActiveMQService {
    @Autowired
    JmsTemplate template;



    /**
     *@Auther kiwi
     *@Data 2019/9/8
     @param  * @param null
     *@reutn
    * @de发送邮件
     */
    @Override
    public void sendEmail(String to, String subject, String content) throws IOException {
        template.send("registerBySystem", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("content", content);
                    mapMessage.setString("address", to);
                    mapMessage.setString("subject", subject);
                    return mapMessage;
                }
            });
        log.info("发送给消费者");

    }


    @Override
    public void textQueue( String content) throws IOException {
        template.send("textQueue", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("content", content);
                return mapMessage;
            }
        });
        log.info("发送给消费者");

    }
}
