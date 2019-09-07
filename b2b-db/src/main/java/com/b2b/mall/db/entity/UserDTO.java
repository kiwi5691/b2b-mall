package com.b2b.mall.db.entity;


import java.util.Date;
import java.util.regex.Pattern;

public class UserDTO {
    private int id;
    private String userName;
    private String password;
    private String realName;
    private String business;
    private String email;
    private String headPicture;
    private Date addDate;
    private Date updateDate;
    private int state;
    private Date userLudt;
    //下面是vo转值
    private String addDateStr;
    private String upDateStr;
    private String stateStr;
}