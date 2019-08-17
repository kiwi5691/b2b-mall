package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.Log;
import com.b2b.mall.db.model.LogWithBlobs;

import java.util.Date;
import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LogWithBlobs record);

    int insertSelective(LogWithBlobs record);

    LogWithBlobs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LogWithBlobs record);

    int updateByPrimaryKeyWithBLOBs(LogWithBlobs record);

    int updateByPrimaryKey(Log record);

    int delBeforeOpData(Date date);


    List<LogWithBlobs> selBeforeOpData(Date date);
}