package com.b2b.mall.admin.controller.user;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.common.service.AuthService;
import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.util.*;
import com.b2b.mall.db.mapper.*;
import com.b2b.mall.db.model.*;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户管理
 */
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private ILoginLogService loginLogService;


    @Autowired
    private EhCacheManager ecm;

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

    /**
     * 初始化跳转
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String IndexGet(Model model) {
        return "redirect:user/login";
    }

    /**
     * 登录跳转
     *
     * @param model
     * @return
     */
    @Log("打开登录")
    @GetMapping({"/user/login","/user/login.html"})
    public String loginGet(Model model) {
        return "user/login";
    }

    /**
     * 登录
     *这里有一个测试权限的用例待删除。
     * @param
     * @param model
     * @param
     * @return
     */
    @Log("提交登录")
    @PostMapping("/user/login")
    public String loginPost(User users, Model model) {

        String username = users.getUserName();
        logger.info("用户paswd为:"+users.getPassword());
        UsernamePasswordToken token = new UsernamePasswordToken(username, users.getPassword(),true);
        Subject currentUser = SecurityUtils.getSubject();
        try {
            //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            //每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
            logger.info("对用户[" + username + "]进行登录验证..验证开始");
            currentUser.login(token);
            logger.info("对用户[" + username + "]进行登录验证..验证通过");
        }catch(UnknownAccountException uae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,未知账户or state=0");
            model.addAttribute("error", "无此账户/未激活");
        }catch(IncorrectCredentialsException ice){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误的凭证");
            model.addAttribute("error", "密码不正确");
        }catch(LockedAccountException lae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,账户已锁定");
            model.addAttribute("error", "账户已锁定");
        }catch(ExcessiveAttemptsException eae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,错误次数过多");
            model.addAttribute("error", "用户名或密码错误次数过多");
        }catch(AuthenticationException ae){
            logger.info("对用户[" + username + "]进行登录验证..验证未通过,堆栈轨迹如下");
            model.addAttribute("error", "用户名或密码不正确");
            ae.printStackTrace();
        }
        Cache<String, AtomicInteger> passwordRetryCache = ecm
                .getCache("passwordRetryCache");
        if (null != passwordRetryCache) {
            int retryNum = (passwordRetryCache.get(users.getUserName()) == null ? 0
                    : passwordRetryCache.get(users.getUserName())).intValue();
            logger.debug("输错次数：" + retryNum);
            if (retryNum > 0 && retryNum < 6) {
                model.addAttribute("error", "用户名或密码错误" + retryNum + "次,再输错"
                        + (6 - retryNum) + "次账号将锁定");
            }
        }
        //验证是否登录成功
        if(currentUser.isAuthenticated()){
            logger.info("用户[" + username + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
            LoginLog loginLog = new LoginLog();
            loginLog.setUsername(username);
            loginLog.setSystemBrowserInfo();
            loginLogService.saveLoginLog(loginLog);
            users= userMapper.selectAllByName(username);
            users.setUpDateStr(DateUtil.preciseDate(users.getUpdateDate()));
            httpSession.setAttribute("user", users);

            users.setUpdateDate(new Date());

            userMapper.updateLust(users);
            String timeQuannum="";
            timeQuannum =DateUtil.checkTimeQuantum();
            httpSession.setAttribute("time",timeQuannum);
            User name = (User) httpSession.getAttribute("manage");
            return "redirect:dashboard";
        }else {
            token.clear();
            return "user/login";
        }
    }


    /**
     * 注册
     *
     * @param model
     * @return
     */
    @Log("打开注册")
    @GetMapping({"/user/register","/user/register.html"})
    public String register(Model model) {
        return "user/register";
    }

    /**
     * 注册
     *
     * @param model
     * @return
     */
    @Log("提交注册")
    @PostMapping("/user/register")
    public String registerPost(User user, Model model) {
        try {
            userMapper.selectIsName(user);
            model.addAttribute("error", "该账号已存在！");
        } catch (Exception e) {
            String pwd=user.getPassword();
            user.setPassword(MD5Util.encrypt(user.getUserName(), pwd));
            Date date = new Date();
            user.setAddDate(date);
            user.setUpdateDate(date);
            userMapper.insert(user);
            model.addAttribute("error", "恭喜您，注册成功！");
            return "user/login";
        }
        return "user/register";
    }

    /**
     * 登录跳转
     *
     * @param model
     * @return
     */
    @Log("打开忘记密码")
    @GetMapping("/user/forget")
    public String forgetGet(Model model) {
        return "user/forget";
    }


    @Log("用户退出登录")
    @GetMapping("/user/logout")
    public String logout(HttpServletResponse response) {
        Subject subject=SecurityUtils.getSubject();
        subject.logout();
        response.setStatus(302);
        return "redirect:user/login";
    }

    /**
     * 登录
     *
     * @param
     * @param model
     * @param
     * @return
     */
    @Log("提交忘记登录")
    @PostMapping("/user/forget")
    public String forgetPost(User user, Model model) {
        String password = userMapper.selectPasswordByName(user);
        if (password == null) {
            model.addAttribute("error", "帐号不存在或邮箱不正确！");
        } else {
            String email = user.getEmail();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Sender);
            message.setTo(email); //接收者邮箱
            message.setSubject("YX后台信息管理系统-密码找回");
            StringBuilder sb = new StringBuilder();
            sb.append(user.getUserName() + "用户您好！您的注册密码是：" + password + "。感谢您使用YX信息管理系统！");
            message.setText(sb.toString());
            mailSender.send(message);
            model.addAttribute("error", "密码已发到您的邮箱,请查收！");
        }
        return "user/forget";

    }




}
