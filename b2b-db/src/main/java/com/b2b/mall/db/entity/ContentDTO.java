package com.b2b.mall.db.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/16 22:54
 */
public class ContentDTO implements Serializable {

    private Long total;

    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}

