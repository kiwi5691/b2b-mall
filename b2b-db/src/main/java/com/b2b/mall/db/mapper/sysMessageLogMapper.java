package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.sysMessageLog;

import java.util.List;

public interface sysMessageLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(sysMessageLog record);

    int insertSelective(sysMessageLog record);

    sysMessageLog selectByPrimaryKey(Long id);

    List<sysMessageLog> list();

    sysMessageLog getRecvId(Long id);


    int updateByPrimaryKeySelective(sysMessageLog record);

    int updateByPrimaryKeyWithBLOBs(sysMessageLog record);

    int updateByPrimaryKey(sysMessageLog record);
}