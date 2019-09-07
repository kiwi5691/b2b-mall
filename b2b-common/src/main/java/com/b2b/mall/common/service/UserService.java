package com.b2b.mall.common.service;

import com.b2b.mall.db.model.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:53
 */
public interface UserService {
    User selectAllByName(String userName);
    User userManagePost(Model model, HttpServletRequest request, User user, HttpSession httpSession);
    int register(Model model,User user);
}
