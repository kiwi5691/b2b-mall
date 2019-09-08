package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.sysMessage;

public interface sysMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sysMessage record);

    sysMessage checkCode(String code);

    int insertSelective(sysMessage record);

    sysMessage selectByPrimaryKey(Integer id);

    int getIdByMessageId(String uuid);

    int updateByPrimaryKeySelective(sysMessage record);

    int updateByPrimaryKeyWithBLOBs(sysMessage record);

    int updateByPrimaryKey(sysMessage record);
}