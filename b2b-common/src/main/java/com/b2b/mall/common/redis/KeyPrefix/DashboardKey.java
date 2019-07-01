package com.b2b.mall.common.redis.KeyPrefix;


public class DashboardKey extends BasePrefix {

    //默认两天
    public static final int BOARD_EXPIRE = 3600*24 *2;

    /**
     * 防止被外面实例化
     */
    private DashboardKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static DashboardKey board = new DashboardKey(BOARD_EXPIRE,"board");

}
