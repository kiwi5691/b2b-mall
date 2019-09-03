package com.b2b.mall.admin.controller.system;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.db.model.Item;
import com.b2b.mall.db.model.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther kiwi
 * @Date 2019/8/12 15:15
 * 登录日志
 */
@Controller
public class SystemController {

    @Autowired
    private ILoginLogService loginLogService;

    @Log("打开登录日志")
    @RequestMapping("/user/loginLog_{pageCurrent}_{pageSize}_{pageCount}")
    public String loginLog(LoginLog loginLog, @PathVariable Integer pageCurrent,
                           @PathVariable Integer pageSize,
                           @PathVariable Integer pageCount,
                           Model model) {
        loginLogService.findLoginLogs(loginLog,pageCurrent,pageSize,pageCount,model);
        return "log/loginLog";
    }

    @Log("打开操作日志")
    @RequestMapping("/user/opLog_{pageCurrent}_{pageSize}_{pageCount}")
    public String opLog(LoginLog loginLog, @PathVariable Integer pageCurrent,
                           @PathVariable Integer pageSize,
                           @PathVariable Integer pageCount,
                           Model model) {
       // loginLogService.findLoginLogs(loginLog,pageCurrent,pageSize,pageCount,model);
        return "log/opLog";
    }
}
