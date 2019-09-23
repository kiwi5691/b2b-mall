package com.b2b.mall.db.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther kiwi
 * @Date 2019/9/23 13:59
 */
public class MallUserDTO implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private String created;

    private String updated;

    public MallUserDTO(Long id, String username, String password, String phone, String email, String created, String updated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }
}
