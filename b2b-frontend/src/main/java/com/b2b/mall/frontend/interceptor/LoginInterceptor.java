package com.b2b.mall.frontend.interceptor;

import com.b2b.dubbo.sso.service.UserService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.common.util.CookieUtils;
import com.b2b.mall.db.model.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登录拦截器
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Reference(version = "${dubbo.version}")
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、从Cookie中取出token
        String token = CookieUtils.getCookieValue(request, "E3_TOKEN");
        // 2、没有token，直接放行
        if (StringUtils.isBlank(token)) {
            return true;
        }
        // 3、取到token，调用sso服务取出user信息
        Result result = userService.getUserByToken(token);
        // 4、没有用户信息直接放行
        if (result.getStatus() != 200) {
            return true;
        }
        // 5、存在用户信息，则保存至request中
        request.setAttribute("user", (TbUser) result.getData());
        return true;
    }
}
