package com.b2b.mall.admin.controller;

import com.b2b.mall.common.service.AuthService;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.model.Permission;
import com.b2b.mall.db.model.Role;
import com.b2b.mall.db.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @auther kiwi
 * @Date 2019/8/11 15:33
 */
@Controller
public class ManageController {


    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserMapper userMapper;


    @Resource
    private AuthService authService;

    @Autowired
    private HttpSession httpSession;

    //自动注入的Bean
    @Autowired
    private JavaMailSender mailSender;

    //读取配置文件中的参数
    @Value("${spring.mail.username}")
    private String Sender;

    @GetMapping("/user/userManage")
    public String userManageGet(Model model) {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User user1 = userMapper.selectByNameAndPwd(user);
        model.addAttribute("user", user1);
        return "manage/userManage";
    }



    @PostMapping("/user/userManage")
    public String userManagePost(Model model, User user, HttpSession httpSession) {
        Date date = new Date();
        user.setUpdateDate(date);
        int i = userMapper.update(user);
        httpSession.setAttribute("user",user);
        return "redirect:userManage";
    }


    @GetMapping("/user/userSearch")
    public String userSearchGet(Model model) {

        List<Permission> permissionList = null;
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        logger.info("id is"+user.getId());
        List<Role> roles = this.authService.getRoleByUser(user.getId());

        if (null != roles && roles.size() > 0) {
            for (Role role : roles) {
                logger.info("role is"+ role.getCode());
                httpSession.setAttribute("role", "role.getCode()");
                permissionList = this.authService.findPermsByRoleId(role
                        .getId());

                }
//                Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
            }
        logger.info("test per s:"+permissionList.get(0).getCode());

        model.addAttribute("permissionList", permissionList);
        return "manage/userSearch";
    }

}
