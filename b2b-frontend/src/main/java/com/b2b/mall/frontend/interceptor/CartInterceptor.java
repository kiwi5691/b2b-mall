package com.b2b.mall.frontend.interceptor;

import com.b2b.dubbo.cart.service.CartService;
import com.b2b.dubbo.sso.service.UserService;
import com.b2b.mall.common.entity.Result;
import com.b2b.mall.common.util.CookieUtils;
import com.b2b.mall.common.util.JsonUtils;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义登录拦截器
 */
@Component
public class CartInterceptor implements HandlerInterceptor {

    @Reference(version = "${dubbo.version}")
    private UserService userService;
    @Reference(version = "${dubbo.version}")
    private CartService cartService;
    @Value("${SSO_SERVICE_URL}")
    private String SSO_SERVICE_URL;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、从Cookie中取出token
        String token = CookieUtils.getCookieValue(request, "token");
        // 2、没有token，直接跳转登录
        if (StringUtils.isBlank(token)) {
            response.sendRedirect(SSO_SERVICE_URL + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        // 3、取到token，调用sso服务取出user信息
        Result result = userService.getUserByToken(token);
        // 4、没有用户信息跳转登录
        if (result.getStatus() != 200) {
            response.sendRedirect(SSO_SERVICE_URL + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        // 5、存在用户信息，则保存至request中
        TbUser user = (TbUser) result.getData();
        request.setAttribute("user", user);
        // 6、判断Cookie中是否含有购物车商品，有则合并
        String jsonCartList = CookieUtils.getCookieValue(request, "E3_CART", true);
        if (StringUtils.isNotBlank(jsonCartList)) {
            // 合并
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(jsonCartList, Item.class));
        }
        return true;
    }
}
