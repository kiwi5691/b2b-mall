package com.b2b.mall.common.job;

import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS
 * @Auther:kiwi
 * 同步天气数据的定时任务
 * @Date: 2019/6/15 14:04
 */
@Component(value = "LoggerScanJob")
public class LoggerScanJob extends QuartzJobBean {
    private final static Logger logger = LoggerFactory.getLogger(LoggerScanJob.class);


    @Override
    protected void executeInternal(org.quartz.JobExecutionContext context) throws JobExecutionException {
        logger.info("自动删除登录日志表数据任务");


    }
}
