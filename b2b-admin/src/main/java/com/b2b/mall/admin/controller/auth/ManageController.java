package com.b2b.mall.admin.controller.auth;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.admin.controller.user.UserController;
import com.b2b.mall.common.service.AuthService;
import com.b2b.mall.common.service.UserService;
import com.b2b.mall.common.util.BaseHTMLStringCase;
import com.b2b.mall.common.util.DateUtil;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.model.*;
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

    @Resource
    private UserService userService;
    @Resource
    private AuthService authService;

    @GetMapping("/user/userManage")
    public String userManageGet(Model model) {

        authService.userManageGet(model);
        return "manage/userManage";
    }


    @Log("提交个人资料修改")
    @PostMapping("/user/userManage")
    public String userManagePost(Model model, User user, HttpSession httpSession) {
        authService.userManagePost(model,user,httpSession);
        return "redirect:userManage";
    }

    @Log("打开管理员管理")
    @GetMapping("/user/managerManangement")
    public String managerManangementGet(Model model){
       authService.managerManangementGet(model);
        return "manage/managerManangement";
    }

    @Log("打开管理员修改")
    @GetMapping("/user/managerEdit")
    public String managerEdit(Model model,User user){
        authService.managerEdit(model,user);
        return "manage/managerEdit";
    }

    @Log("提交管理员修改")
    @RequiresPermissions(value ="system")
    @PostMapping("/user/managerEdit")
    public String userManageEditPost(Model model, User user) {
        userService.userManagePost(model,user);
        return "redirect:userSearch";
    }

    @Log("打开用户/权限查询")
    @GetMapping("/user/userSearch")
    public String userSearchGet(Model model) {
        authService.userSearchGet(model);
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
    public String userPermissionEditEditPost(Model model) {

        return "redirect:userSearch";
    }

}
