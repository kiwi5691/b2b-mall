package com.b2b.mall.common.service.Impl;

import com.b2b.mall.common.service.UserService;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.model.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author kiwi
 * @Date: 2019/6/26 13:09
 */
@Service
public class UserServiceImpl  implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User selectAllByName(String userName) {
        return userMapper.selectAllByName(userName);
    }

    @Override
    public User userManagePost(Model model, HttpServletRequest request, User user, HttpSession httpSession) {


        return null;
        //TODO 添加pOST改值
    }
}
