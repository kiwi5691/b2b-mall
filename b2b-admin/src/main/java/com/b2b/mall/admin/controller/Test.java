package com.b2b.mall.admin.controller;

import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.mapper.UserRoleKeyMapper;
import com.b2b.mall.db.model.LogWithBlobs;
import com.b2b.mall.db.model.User;
import com.b2b.mall.db.model.UserRoleKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/5 20:25
 */
@Slf4j
@RestController
public class Test {

@Autowired
private UserMapper userMapper;
@Autowired
private UserRoleKeyMapper userRoleKeyMapper;

    @RequestMapping("/test")
    public String TestfindOpLogs(User user){

        user.setId(23);
        log.info("id is"+userMapper.selectRoleIdByBiz(user.getId()));

        UserRoleKey userRoleKey=new UserRoleKey();
        userRoleKey.setRoleId(userMapper.selectRoleIdByBiz(user.getId()));
        userRoleKey.setUserId(user.getId());
        userRoleKeyMapper.insert(userRoleKey);

        return String.valueOf(userMapper.selectRoleIdByBiz(user.getId()));
    }

}
