package com.b2b.mall.common.authentication;

/**
 * @auther kiwi
 * @Date 2019/6/29 17:19
 */


import com.b2b.mall.common.service.UserService;
import com.b2b.mall.db.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.annotation.Resource;

/**
 * 获取用户的角色和权限信息
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private UserService userService;


    /**
     * 登录认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        logger.info("验证当前Subject时获取到token为：" + token.toString());
        // 查出是否有此用户
        User user = userService.selectAllByName(token.getUsername());
        if (user != null) {
            Session session = SecurityUtils.getSubject().getSession();
            //成功则放入session
            session.setAttribute("user", user);
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro进行密码对比校验
            Object principal = user.getUserName();
            //2)credentials：密码
            Object credentials = user.getPassword();
            //3)realmName：当前realm对象的name，调用父类的getName()方法即可
            String realmName = getName();
            //4)credentialsSalt盐值
            //使用账号作为盐值
            ByteSource credentialsSalt = ByteSource.Util.bytes(principal);

            return new SimpleAuthenticationInfo(principal, credentials, credentialsSalt, realmName);
        }
        return null;
    }

    /**
     * 权限认证
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principalCollection) {
        //授权
        logger.debug("授予角色和权限");
        // 添加权限 和 角色信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 获取当前登陆用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if (user.getBusiness().equals("超级无敌管理员")) {
            // 超级管理员，添加所有角色、添加所有权限
            authorizationInfo.addRole("*");
            authorizationInfo.addStringPermission("*");
        } else {
            // 普通用户，查询用户的角色，根据角色查询权限
//           Integer userId = user.getId();
//            List<Role> roles = this.authService.getRoleByUser(userId);
//            if (null != roles && roles.size() > 0) {
//                for (Role role : roles) {
//                    authorizationInfo.addRole(role.getCode());
//                    // 角色对应的权限数据
//                    List<Permission> perms = this.authService.findPermsByRoleId(role
//                            .getId());
//                    if (null != perms && perms.size() > 0) {
//                        // 授权角色下所有权限
//                        for (Permission perm : perms) {
//                            authorizationInfo.addStringPermission(perm
//                                    .getCode());
//                        }
//                    }
//                }
//            }
        }
        return authorizationInfo;
    }

}
