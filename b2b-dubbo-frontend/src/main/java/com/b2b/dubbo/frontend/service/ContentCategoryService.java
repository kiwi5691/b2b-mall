package com.b2b.dubbo.frontend.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.BaseUIDTO;

import java.util.List;

public interface ContentCategoryService {
    List<BaseUIDTO> getContentCategoryList(Long parentId);
    Result addContentCategory(long parentId, String name);
}
