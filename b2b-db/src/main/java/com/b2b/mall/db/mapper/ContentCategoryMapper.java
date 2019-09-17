package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.ContentCategory;

import java.util.List;

public interface ContentCategoryMapper {
    int deleteByPrimaryKey(Long id);

    void insertCategory(ContentCategory contentCategory);

    List<ContentCategory> selectContentCatsByParentId(Long parentId);

    ContentCategory selectContentCatById(Long id);

    void updateContentCategoryById(ContentCategory parentContentCategory);

    int insert(ContentCategory record);

    int insertSelective(ContentCategory record);

    ContentCategory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ContentCategory record);

    int updateByPrimaryKey(ContentCategory record);
}