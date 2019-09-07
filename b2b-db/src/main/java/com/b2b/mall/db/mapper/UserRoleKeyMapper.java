package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.UserRoleKey;

import java.util.List;

public interface UserRoleKeyMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);

    int updateUserId(UserRoleKey record);

    List<UserRoleKey> findByUserId(int userId);
}