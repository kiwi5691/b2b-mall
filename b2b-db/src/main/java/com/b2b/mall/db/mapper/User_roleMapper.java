package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.User_role;

public interface User_roleMapper {
    int insert(User_role record);

    int insertSelective(User_role record);
}