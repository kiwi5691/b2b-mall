package com.b2b.dubbo.service;


import org.apache.dubbo.config.annotation.Service;
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
