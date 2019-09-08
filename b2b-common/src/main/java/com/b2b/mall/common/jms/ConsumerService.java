package com.b2b.mall.common.jms;

import com.b2b.mall.common.util.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.io.IOException;
import java.io.Serializable;

/**
 * @auther kiwi
 * @Date 2019/9/8 16:47
 */
@Slf4j
@Service
public class ConsumerService {

    @Autowired
    private EmailUtil emailUtil;
    /**
     * 通过监听目标队列实现功能
     */
    @JmsListener(destination = "registerBySystem")
    public void sendMail(Message message) throws Exception {
        MapMessage mapMessage = (MapMessage) message;
        String content = mapMessage.getString("content");
        String address = mapMessage.getString("address");
        String subject = mapMessage.getString("subject");
        emailUtil.sendEmail(address,subject,content);
    }


    @JmsListener(destination = "textQueue")
    public void textQueue(Message message) throws Exception {
        MapMessage mapMessage = (MapMessage) message;
        String content = mapMessage.getString("content");
        log.info(content);
    }
}
