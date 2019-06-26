package com.b2b.mall.admin.controller;


import com.b2b.mall.admin.service.DashboardService;
import com.b2b.mall.admin.service.Impl.DashboardServiceImpl;
import com.b2b.mall.common.redis.RedisService;
import com.b2b.mall.common.util.RunnableThreadWebCount;
import com.b2b.mall.db.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 仪表板页面
 * @author Kiwi
 */
@Controller
public class DashboardController {

    private final DashboardService dashboardService;

    @Autowired
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/user/dashboard")
    public String dashboard(Model model, Stats stats) {

        dashboardService.dashboardInit(model,stats);
        return "dashboard";
    }


    /**
     *@auther kiwi
     *@Data 2019/6/26
     * 运行时的访问人数
     @param  * @param key
     *@reutn int
    */
    @RequestMapping(value = "/border/website/count/")
    @ResponseBody
    public int count(@RequestParam("key") String key) {
        return RunnableThreadWebCount.addCount(key);
    }
}