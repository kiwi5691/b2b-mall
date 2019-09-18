package com.b2b.mall.db.mapper;

import com.b2b.mall.db.entity.SearchItem;

import java.util.List;

public interface SearchItemMapper {
    List<SearchItem> getItemList();
	SearchItem getItemById(Integer itemId);
}