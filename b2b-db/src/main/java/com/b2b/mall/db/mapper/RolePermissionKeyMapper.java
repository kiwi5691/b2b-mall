package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.RolePermissionKey;

import java.util.List;

public interface RolePermissionKeyMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);

    List<RolePermissionKey> findByRole(int roleId);

}