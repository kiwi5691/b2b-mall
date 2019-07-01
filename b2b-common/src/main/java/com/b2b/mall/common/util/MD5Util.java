package com.b2b.mall.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author kiwi
 */
public class MD5Util {

    protected MD5Util() {

    }

    //加密方式
    private static final String ALGORITH_NAME = "md5";
    //加密1024次
    private static final int HASH_ITERATIONS = 1024;

    public static String encrypt(String username, String password) {

        Object crdentials = password;
        //以账号作为盐值
        ByteSource salt = ByteSource.Util.bytes(username);
        String source = StringUtils.lowerCase(username);
        password = StringUtils.lowerCase(password);
        return new SimpleHash(ALGORITH_NAME, crdentials, salt, HASH_ITERATIONS).toHex();
    }
}
