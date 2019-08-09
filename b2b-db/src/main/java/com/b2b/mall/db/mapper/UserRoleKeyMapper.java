package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.UserRoleKey;

import java.util.List;

public interface UserRoleKeyMapper {
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);
    /**
     * 根据用户获取用户角色中间表数据
     * @param userId
     * @return
     */
    List<UserRoleKey> findByUserId(int userId);
}