package com.b2b.mall.common.entity.dto;

import lombok.Data;

import java.util.Date;

/**
 * @auther kiwi
 * @Date 2019/8/18     20:30
 */
@Data
public class LoginLogDto {
    private Long id;

    private String username;

    private String loginTime;

    private String location;

    private String ip;

    private String system;

    private String browser;

}
