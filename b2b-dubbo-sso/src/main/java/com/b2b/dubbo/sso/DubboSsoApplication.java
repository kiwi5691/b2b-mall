package com.b2b.dubbo.sso;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.dubbo.sso"})
@MapperScan("com.b2b.mall.db.mapper")
public class DubboSsoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboSsoApplication.class, args);
    }

}