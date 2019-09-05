package com.b2b.mall.admin.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @auther kiwi
 * @Date 2019/9/5 10:57
 */
@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.mall.common", "com.b2b.mall.admin"})
@MapperScan("com.b2b.mall.db.mapper")
public class StartupApplication {
    public static void main(String[] args) {
        SpringApplication.run(StartupApplication.class, args);
    }
}
