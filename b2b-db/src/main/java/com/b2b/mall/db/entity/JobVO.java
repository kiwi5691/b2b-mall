package com.b2b.mall.db.entity;

import lombok.Data;

/**
 * @auther kiwi
 * @Date 2019/9/6 16:23
 */
public class JobVO {
    public String desc;
    public String function;
    public String timer;

    public JobVO(String desc, String function, String timer) {
        this.desc = desc;
        this.function = function;
        this.timer = timer;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }
}
