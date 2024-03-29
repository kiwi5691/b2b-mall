package com.b2b.mall.frontend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther kiwi
 * @Date 2019/7/18 21:47
 */
@EnableDubbo
@SpringBootApplication(scanBasePackages = "com.b2b.mall.frontend")
public class frontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(frontendApplication.class, args);
    }

}
