package com.b2b.mall.common.service;

import com.b2b.mall.db.model.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Auther:kiwi
 * @Date: 2019/6/26 12:53
 */
public interface UserService {
    User selectAllByName(String userName);
    void userManagePost(Model model, User user);
    int register(Model model,User user) throws IOException;
    void checkCode(String code);
}
