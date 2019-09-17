package com.b2b.dubbo.item.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.BaseUIDTO;
import com.b2b.mall.db.entity.ContentDTO;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.ItemDesc;

public interface ItemService {
    Item getItemById(Integer itemId);
    ContentDTO getItemList(int page, int rows);
    ItemDesc getItemDescById(Integer itemId);
}
