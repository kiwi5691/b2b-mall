package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKeyWithBLOBs(Menu record);

    int updateByPrimaryKey(Menu record);
}