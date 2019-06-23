package com.b2b.mall.db.model;

import lombok.Data;

@Data
public class Delivery {

    private Integer id;

    private String deliveryName;

    private String deliveryCode;

    private String deliverySort;

    private String expressNo;

}