package com.b2b.mall.common.job;

import com.b2b.mall.common.service.ILoginLogService;
import com.b2b.mall.common.service.JobService;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS
 * @Auther:kiwi
 * 删除日志的定时任务
 * @Date: 2019/6/15 14:04
 */
@Component(value = "LoggerScanJob")
public class LoggerScanJob extends QuartzJobBean {

    private final static Logger logger = LoggerFactory.getLogger(LoggerScanJob.class);

    @Resource
    private JobService jobService;

    @Override
    protected void executeInternal(org.quartz.JobExecutionContext context) throws JobExecutionException {
        logger.info("自动删除登录/操作日志表数据任务");
     //   jobService.delBeforeLog();
        //jobService.delBeforeOpLog();
        logger.info("完成删除任务，下一个运行时间为一星期后");

    }
}
