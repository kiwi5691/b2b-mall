package com.b2b.mall.db.model;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Data
public class User extends BaseObject {
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
