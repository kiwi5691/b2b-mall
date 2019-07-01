package com.b2b.mall.common.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @author MrBird
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:shiro.properties"})
public class
Properties {

    private ShiroProperties shiro = new ShiroProperties();
    private boolean openAopLog = true;
}
