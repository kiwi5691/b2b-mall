package com.b2b.mall.common.config;

import com.b2b.mall.common.job.LoggerScanJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther:kiwi
 * @Date: 2019/6/15 14:16
 */
@Configuration
public class QuartzConfiguration {

    //更新频率为一星期
    private final int TIME = 604800000;

    /**
     *@Auther kiwi
     *@Data 2019/6/15
     * 定义特点的job
     @param  * @param
     *@reutn org.quartz.JobDetail
    */
    @Bean
    public JobDetail DataSyncJobJobDetail(){
        return JobBuilder.newJob(LoggerScanJob.class).withIdentity("LoggerScanJob").storeDurably().build();
    }


    /**
     *@Auther kiwi
     *@Data 2019/6/15
     * 定义了一星期秒来触发特定的Job
     @param  * @param
     *@reutn org.quartz.Trigger
    */
    @Bean
    public Trigger sampleJobTrigger(){
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(TIME).repeatForever();
        return TriggerBuilder.newTrigger().forJob(DataSyncJobJobDetail())
                .withIdentity("LoggerScanJobTrigger").withSchedule(simpleScheduleBuilder).build();
    }
}
