package com.b2b.dubbo.frontend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.mall.common", "com.b2b.dubbo.frontend"})
@MapperScan("com.b2b.mall.db.mapper")
public class FrontendServiceApplicationStarter {
    public static void main(String[] args) {
        SpringApplication.run(FrontendServiceApplicationStarter.class, args);
    }
}

