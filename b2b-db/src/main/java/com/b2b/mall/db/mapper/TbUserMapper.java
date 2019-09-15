package com.b2b.mall.db.mapper;

import com.b2b.mall.db.model.TbUser;

import java.util.List;
import java.util.Map;

public interface TbUserMapper {
    int deleteByPrimaryKey(Long id);

    List<TbUser> selectByKey(Map<String, Object> paramMap);

    List<TbUser> selectUserByNameOrPwd(Map<String, Object> paramMap);

//    TbUser selectUserByNameAndPwd(TbUser tbUser);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    TbUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}