package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.RoleMenu;

public interface RoleMenuMapper {
    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);
}