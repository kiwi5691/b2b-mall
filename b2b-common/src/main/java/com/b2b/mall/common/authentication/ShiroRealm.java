package com.b2b.mall.common.authentication;

/**
 * @auther kiwi
 * @Date 2019/6/29 17:19
 */


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.b2b.mall.common.service.UserService;
import com.b2b.mall.db.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
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
//        logger.info("##################执行Shiro权限认证##################");
//        // 获取当前登录输入的用户名，等价于(String)
//        String loginName = (String) super
//                .getAvailablePrincipal(principalCollection);
//        // 到数据库查是否有此对象
//        User user = userService.selectAllByName(loginName);
//        if (user != null) {
//            // 权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
//            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//            // 用户的角色集合
//            Set<String> set = new HashSet<String>();
//            set.add(user.getRname());
//            info.setRoles(set);
//            // 用户的权限集合
//            List<SysRolePermission> srpList = sysRolePermissionService
//                    .selectByRid(user.getRid());
//            List<String> pNameList = new ArrayList<String>();
//            for (SysRolePermission sysRolePermission : srpList) {
//                pNameList.add(sysPermissionService.selectByPrimaryKey(
//                        sysRolePermission.getPid()).getPermission());
//            }
//            info.addStringPermissions(pNameList);
//            return info;
//        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

}
