package com.b2b.mall.db.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Delivery implements Serializable {

    private Integer id;

    private String deliveryName;

    private String deliveryCode;

    private String deliverySort;

    private String expressNo;

}