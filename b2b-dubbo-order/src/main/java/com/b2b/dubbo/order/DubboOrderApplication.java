package com.b2b.dubbo.order;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.concurrent.CountDownLatch;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.dubbo.order"})
@MapperScan("com.b2b.mall.db.mapper")
public class DubboOrderApplication {

    public static void main(String[] args) throws InterruptedException {
        new SpringApplicationBuilder()
                .sources(DubboOrderApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();
    }

}