package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Job;

public interface JobMapper {
    int deleteByPrimaryKey(Long jobId);

    int insert(Job record);

    int insertSelective(Job record);

    Job selectByPrimaryKey(Long jobId);

    int updateByPrimaryKeySelective(Job record);

    int updateByPrimaryKey(Job record);
}