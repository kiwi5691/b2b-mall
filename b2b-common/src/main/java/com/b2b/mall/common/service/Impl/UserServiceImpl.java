package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.UserService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.MD5Util;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.mapper.UserRoleKeyMapper;
import com.b2b.mall.db.model.User;
import com.b2b.mall.db.model.UserRoleKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @author kiwi
 * @Date: 2019/6/26 13:09
 */
@Slf4j
@Service
public class UserServiceImpl  implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleKeyMapper userRoleKeyMapper;

    @Override
    public User selectAllByName(String userName) {
        return userMapper.selectAllByName(userName);
    }

    @Override
    public void userManagePost(Model model, User user) {

        log.info(user.toString());

        user.setState(BaseHTMLStringCase.lockCheckToInt(user.getStateStr()));
        log.info("temp state"+user.getStateStr()+"temp int"+user.getState());
        userMapper.update(user);
        Integer roleId=0;
        //插入rolekey表
        roleId =userMapper.selectRoleIdByBiz(userMapper.selectById(user.getUserName()).getId());
        log.info("roId is"+roleId);

        UserRoleKey userRoleKey=new UserRoleKey();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userMapper.selectById(user.getUserName()).getId());
        userRoleKeyMapper.updateUserId(userRoleKey);
    }

    @Override
    public int register(Model model, User user) {

        Integer roleId=0;

        String pwd=user.getPassword();
        user.setPassword(MD5Util.encrypt(user.getUserName(), pwd));
        Date date = new Date();
        user.setAddDate(date);
        user.setUpdateDate(date);
        user.setBusiness("客维员");
        userMapper.insert(user);
        //插入rolekey表
        roleId =userMapper.selectRoleIdByBiz(userMapper.selectById(user.getUserName()).getId());
        log.info("roId is"+roleId);
        UserRoleKey userRoleKey=new UserRoleKey();
        userRoleKey.setRoleId(roleId);
        userRoleKey.setUserId(userMapper.selectById(user.getUserName()).getId());
        userRoleKeyMapper.insert(userRoleKey);


        model.addAttribute("error", "恭喜您，注册成功！");


        return 0;
    }
}
