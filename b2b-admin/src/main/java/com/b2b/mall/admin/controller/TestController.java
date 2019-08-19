package com.b2b.mall.admin.controller;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.service.JobService;
import com.b2b.mall.db.model.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/8/16 17:03
 */
@RestController
public class TestController {
    @Autowired
    private JobService logService;

    @Autowired
    private ILoginLogService loginLogService;

    @Log("打开登录日志")
    @RequestMapping("/loginLog")
    public List<?> loginLog(LoginLog loginLog,Model model) {
        return loginLogService.findLoginLogs(loginLog,0,0,0,model);
    }

    @RequestMapping("/test")
    public List<?> testlogin(){

        return logService.selBeforeLog();
    }

    @RequestMapping("/testOp")
    public List<?> testop(){

        return logService.selBeforeOpLog();
    }
}
