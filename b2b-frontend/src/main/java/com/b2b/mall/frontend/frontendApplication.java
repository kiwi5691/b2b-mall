package com.b2b.mall.frontend;

import com.b2b.mall.common.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @auther kiwi
 * @Date 2019/7/18 21:47
 */
@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.mall.common", "com.b2b.mall.admin"})
@MapperScan("com.b2b.mall.db.mapper")
@EnableTransactionManagement
public class frontendApplication {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
