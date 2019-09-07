package com.b2b.mall.admin.controller.auth;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.admin.controller.user.UserController;
import com.b2b.mall.common.service.AuthService;
import com.b2b.mall.common.service.UserService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.Permission;
import com.b2b.mall.db.model.Role;
import com.b2b.mall.db.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

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
    private UserService userService;

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

    @Log("打开个人资料修改")
    @GetMapping("/user/userManage")
    public String userManageGet(Model model) {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User user1 = userMapper.selectByNameAndPwd(user);
        model.addAttribute("user", user1);
        return "manage/userManage";
    }


    @Log("提交个人资料修改")
    @PostMapping("/user/userManage")
    public String userManagePost(Model model, User user, HttpSession httpSession) {
        Date date = new Date();
        user.setUpdateDate(date);
        int i = userMapper.update(user);
        httpSession.setAttribute("user",user);
        return "redirect:userManage";
    }

    @Log("打开管理员管理")
    @GetMapping("/user/managerManangement")
    public String managerManangementGet(Model model){
        //TODO 准备添加用户
        List<User> userList = null;
        userList = userMapper.selectAll();
        userList.forEach(user -> {
            user.setAddDateStr(DateUtil.preciseDate(user.getAddDate()));
            user.setUpDateStr(DateUtil.preciseDate(user.getUpdateDate()));
            user.setStateStr(BaseHTMLStringCase.lockCheck(String.valueOf(user.getState())));
        });
        model.addAttribute("userList",userList);
        return "manage/managerManangement";
    }

    @Log("打开管理员修改")
    @GetMapping("/user/managerEdit")
    public String managerEdit(Model model,User user){
        User user1=null;
        user1=userMapper.selectById(user.getUserName());
        user1.setAddDateStr(DateUtil.preciseDate(user1.getAddDate()));
        user1.setUpDateStr(DateUtil.preciseDate(user1.getUpdateDate()));
        user1.setStateStr(BaseHTMLStringCase.lockCheck(String.valueOf(user1.getState())));
        model.addAttribute("user",user1);
        return "manage/managerEdit";
    }

    @Log("提交管理员修改")
    @RequiresPermissions(value ="system")
    @PostMapping("/user/managerEdit")
    public String userManageEditEditPost(Model model, HttpServletRequest request, User user, HttpSession httpSession) {

        userService.userManagePost(model,request,user,httpSession);
        return "redirect:userSearch";
    }

    @Log("打开用户/权限查询")
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
                httpSession.setAttribute("role", role.getRoleName());
                permissionList = this.authService.findAllPermsByRoleId(role
                        .getId());
                }
            }

        model.addAttribute("permissionList", permissionList);
        return "manage/userSearch";
    }

    @Log("打开权限修改")
    @RequiresPermissions(value ="system")
    @GetMapping("/user/userPermissionEdit")
    public String userPermissionEditEditGet(Model model, Permission permission) {

        authService.permissionEditGet(model,permission);
        return "manage/userPermissionEdit";
    }

    @Log("提交权限修改")
    @RequiresPermissions(value ="system")
    @PostMapping("/user/userPermissionEdit")
    public String userPermissionEditEditPost(Model model, HttpServletRequest request, Permission permission, HttpSession httpSession) {

        authService.permissionEditPost(model,request,permission,httpSession);
        return "redirect:userSearch";
    }

}
