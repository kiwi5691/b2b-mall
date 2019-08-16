package com.b2b.mall.admin.controller;

import com.b2b.mall.common.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @auther kiwi
 * @Date 2019/8/16 17:03
 */
@Controller
public class TestController {
    @Autowired
    private JobService logService;

    @RequestMapping("/test")
    public String test(){
        logService.selBeforeLog();
        return "error/404.html";
    }
}
