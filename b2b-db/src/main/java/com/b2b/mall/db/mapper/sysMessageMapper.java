package com.b2b.mall.db.mapper;

import com.b2b.mall.db.entity.MessageVo;
import com.b2b.mall.db.model.sysMessage;

import java.util.List;

public interface sysMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(sysMessage record);

    sysMessage checkCode(String code);

    int insertSelective(sysMessage record);

    int count();

    List<MessageVo> list(MessageVo record);

    sysMessage selectByPrimaryKey(Integer id);

    int getIdByMessageId(String uuid);

    int updateByPrimaryKeySelective(sysMessage record);

    int updateByPrimaryKeyWithBLOBs(sysMessage record);

    int updateByPrimaryKey(sysMessage record);
}