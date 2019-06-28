package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.JobLog;

public interface JobLogMapper {
    int deleteByPrimaryKey(Long logId);

    int insert(JobLog record);

    int insertSelective(JobLog record);

    JobLog selectByPrimaryKey(Long logId);

    int updateByPrimaryKeySelective(JobLog record);

    int updateByPrimaryKeyWithBLOBs(JobLog record);

    int updateByPrimaryKey(JobLog record);
}