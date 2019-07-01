package com.b2b.mall.db.mapper;


import com.b2b.mall.db.model.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    User selectByNameAndPwd(User user);

    int insert(User user);

    int update(User user);

    int selectIsName(User user);

    String selectPasswordByName(User user);

    User selectAllByName(String userName);
}
