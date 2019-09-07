package com.b2b.mall.db.mapper;


import com.b2b.mall.db.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {

    User selectByNameAndPwd(User user);


    int insert(User user);

    int update(User user);

    int selectIsName(User user);

    List<User> selectAll();

    int updateLust(User user);

    int selectStatus(String userName);

    String selectPasswordByName(User user);

    User selectAllByName(String userName);

    Integer selectRoleIdByBiz(Integer integer);

    User selectById(String userName);
}
