package com.b2b.mall.admin.aspect;

import com.b2b.mall.common.properties.Properties;
import com.b2b.mall.common.util.HttpContextUtil;
import com.b2b.mall.common.util.IPUtil;
import com.b2b.mall.db.model.Log;
import com.b2b.mall.db.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * AOP 记录用户操作日志
 *
 * @author kiwi
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private Properties properties;

//    @Autowired
//    private ILogService logService;

    @Pointcut("@annotation(com.b2b.mall.admin.annotation.Log)")
    public void pointcut() {
        // do nothing
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object result;
        long beginTime = System.currentTimeMillis();
        // 执行方法
        result = point.proceed();
        HttpServletRequest request = HttpContextUtil.getHttpServletRequest();
        // 设置 IP地址
        String ip = IPUtil.getIpAddr(request);
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        if (properties.isOpenAopLog()) {
            // 保存日志
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            Log log = new Log();
            if (user != null) {
                log.setUsername(user.getUserName());
            }
            log.setIp(ip);
            log.setTime(time);
//            logService.saveLog(point, log);
        }
        return result;
    }
}
