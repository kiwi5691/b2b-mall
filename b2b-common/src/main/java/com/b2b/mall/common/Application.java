package com.b2b.mall.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.mall.common"})
@MapperScan("com.b2b.mall.db.mapper")

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}