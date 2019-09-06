package com.b2b.mall.admin.controller.monitor;

import com.b2b.mall.admin.annotation.Log;
import com.b2b.mall.db.entity.JobVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther kiwi
 * @Date 2019/9/6 16:14
 */
@Slf4j
@Controller
public class JobController {

    List<JobVO> jobVOs =new ArrayList<>();

    @RequestMapping("/user/jobWatchDog")
    @Log("查看调度任务")
    public String watchDog(Model model){

        JobVO jobVO = new JobVO("自动清理登录日志","Quartz：jobService.delBeforeLog()","一个星期");
        JobVO jobVO1 = new JobVO("自动清理操作日志","Quartz：jobService.delBeforeOpLog()","一个星期");
        log.info("test is"+jobVO.getDesc());

        jobVOs.add(jobVO);
        jobVOs.add(jobVO1);
        log.info(jobVOs.get(0).getDesc());
        model.addAttribute("jobVOs", jobVOs);
        return "log/watchDog";
    }
}
