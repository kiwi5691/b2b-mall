package com.b2b.mall.common.redis.KeyPrefix;

/**
 * @auther kiwi
 * @date 2019/6/29 16:00
 */
public class AuthenticationKey extends BasePrefix {
    //默认一天
    public static final int BOARD_EXPIRE = 3600*24 *1;

    /**
     * 防止被外面实例化
     */
    private AuthenticationKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段
     */
    public static AuthenticationKey board = new AuthenticationKey(BOARD_EXPIRE,"authentication");
}
