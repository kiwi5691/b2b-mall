package com.b2b.dubbo.item.service;

import com.b2b.mall.db.entity.BaseUIDTO;

import java.util.List;

public interface ItemCategoryService {
    List<BaseUIDTO> getCatList(Integer parentId);
}
