package com.b2b.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableDubbo
@SpringBootApplication(scanBasePackages = {"com.b2b.dubbo"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}