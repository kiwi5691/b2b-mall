package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.ItemDesc;

public interface ItemDescMapper {
    int deleteByPrimaryKey(Long itemId);

    int insert(ItemDesc record);

    int insertSelective(ItemDesc record);

    ItemDesc selectByPrimaryKey(Long itemId);

    int updateByPrimaryKeySelective(ItemDesc record);

    ItemDesc selectItemDescByPrimaryKey(Integer itemId);

    int updateByPrimaryKeyWithBLOBs(ItemDesc record);

    int updateByPrimaryKey(ItemDesc record);
}