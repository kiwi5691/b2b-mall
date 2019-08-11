package com.b2b.mall.admin.config;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author kiwi
 * 整体MVC休整
 */
@Configuration
class MyExceptionResolver  implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof UnauthorizedException) {
            ModelAndView mv = new ModelAndView("error/403");
            return mv;
        }
//        else if(ex instanceof )
        return null;
    }
}
