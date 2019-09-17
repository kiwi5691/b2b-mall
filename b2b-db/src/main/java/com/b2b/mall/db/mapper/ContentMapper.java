package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Content;

import java.util.List;

public interface ContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Content record);

    List<Content> getContentListByCategoryId(Long categoryId);

    List<Content> getAllContentList();

    int insertSelective(Content record);

    Content selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Content record);

    int updateByPrimaryKeyWithBLOBs(Content record);

    int updateByPrimaryKey(Content record);
}