package com.b2b.dubbo.sso.service.Impl;


import com.b2b.dubbo.sso.service.IDubboDemoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @auther kiwi
 * @Date 2019/9/13 14:21
 */

@Service(version = "${dubbo.version}")
@Component
public class DubboDemoServiceImpl implements IDubboDemoService {
    @Override
    public String helloDubbo() {
        return "hello dubbo, I'm server!";
    }
}
