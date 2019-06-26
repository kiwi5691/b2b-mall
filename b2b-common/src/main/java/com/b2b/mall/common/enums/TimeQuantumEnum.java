package com.b2b.mall.common.enums;

/**
 * @author kiwi
 * @Date 2019/6/26 20:42
 */
public enum TimeQuantumEnum {
    //正常数字
    zero(0),
    six(6),
    twelve(12),
    thirteen(13),
    eighteen(18),
    twetytwo(24);

    private Integer value;

    TimeQuantumEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
