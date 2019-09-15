package com.b2b.dubbo.sso.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.model.TbUser;


public interface UserService {
    Result checkData(String param, Integer type);
    Result register(TbUser tbUser);
    Result login(String username, String password);
    Result getUserByToken(String token);
}
