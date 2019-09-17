package com.b2b.dubbo.frontend.service;

import com.b2b.mall.common.entity.Result;
import com.b2b.mall.db.entity.ContentDTO;
import com.b2b.mall.db.model.Content;


import java.util.List;

public interface ContentService {
    Result addContent(Content content);
    ContentDTO getContentListByCategoryId(Long categoryId, int page, int rows);
    List<Content> getContentList(Long cid);
}
