package com.b2b.mall.db.entity;

import java.io.Serializable;

/**
 * @auther kiwi
 * @Date 2019/9/16     22:16
 */
public class BaseUIDTO implements Serializable {

    private long id;
    private String text;
    private String state;
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }

}
