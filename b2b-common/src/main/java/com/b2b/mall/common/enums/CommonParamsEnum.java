package com.b2b.mall.common.enums;

/**
 * @author kiwi
 */

public enum CommonParamsEnum {
    /**
     * 数字10
     */
    TEN(10),

    /**
     * 数字5
     */
    FIVE(5),


    /**
     *UnknowError","未知错误"
    */
    UNKNOW_ERROR(-1),

    /**
     *,"UserNotFind","用户不存在"
    */
    USER_NOT_FIND(-101),

    /**
     *,"BadRequest","请求有误"
    */
    BAD_REQUEST(400),

    /**
     * 数字500
     */
    SERVER_ERROR(500),

    /**
     * 数字1024
     */
    BYTE(1024);



    private Integer value;

    CommonParamsEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
