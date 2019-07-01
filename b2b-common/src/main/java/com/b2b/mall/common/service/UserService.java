package com.b2b.mall.common.service;

import com.b2b.mall.db.model.User;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:53
 */
public interface UserService {
    User selectAllByName(String userName);
}
