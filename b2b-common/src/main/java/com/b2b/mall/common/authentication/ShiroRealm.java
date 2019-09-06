package com.b2b.mall.common.authentication;

/**
 * @auther kiwi
 * @Date 2019/6/29 17:19
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.b2b.mall.common.service.AuthService;
import com.b2b.mall.common.service.UserService;
import com.b2b.mall.db.mapper.UserMapper;
import com.b2b.mall.db.model.Permission;
import com.b2b.mall.db.model.Role;
import com.b2b.mall.db.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import javax.annotation.Resource;
import java.util.List;

/**
 * 获取用户的角色和权限信息
 */
public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private UserService userService;

    @Resource
    private AuthService authService;


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
//            Object principal = user.getUserName();
            Object credentials = user.getPassword();
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUserName());

            //判定管理员有没有锁
         if(userMapper.selectStatus(user.getUserName())!=1){
                throw new LockedAccountException("账号:"+user.getUserName()+" 已经被锁定 ");
            }
            return new SimpleAuthenticationInfo(user, credentials, credentialsSalt, getName());
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
        logger.debug("授予角色和权限");



        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        logger.info("user is0"+user.getId()+user.getUserName());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        User users = userService.selectAllByName(user.getUserName());

        logger.info("user biz is"+user.getBusiness());
        if (users.getBusiness().equals("超级无敌管理员")) {
            // 超级管理员，添加所有角色、添加所有权限
            authorizationInfo.addRole("*");
            authorizationInfo.addStringPermission("*");
        } else {
            // 普通用户，查询用户的角色，根据角色查询权限

            logger.info("usr Id is"+ user.getId());

            Integer userId = user.getId();
            List<Role> roles = this.authService.getRoleByUser(userId);
            if (null != roles && roles.size() > 0) {
                for (Role role : roles) {
                    authorizationInfo.addRole(role.getCode());
                    // 角色对应的权限数据
                    List<Permission> perms = this.authService.findPermsByRoleId(role
                            .getId());
                    if (null != perms && perms.size() > 0) {
                        // 授权角色下所有权限
                        for (Permission perm : perms) {
                            authorizationInfo.addStringPermission(perm
                                    .getCode());
                        }

                        perms.forEach(System.out::println);
                    }
                }
            }
        }
        return authorizationInfo;
    }

}
