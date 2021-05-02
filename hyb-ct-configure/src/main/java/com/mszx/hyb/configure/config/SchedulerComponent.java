package com.mszx.hyb.configure.config;

import com.mszx.hyb.configure.quartz.ScanConfigureTask;
import com.mszx.hyb.configure.quartz.ScanWxGiftTask;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SchedulerComponent {

    protected static final Logger logger = LoggerFactory.getLogger(SchedulerComponent.class);

    @Value("${scan.configure.cron}")
    private String configure_cron;

    @Value("${scan.wxgift.cron}")
    private String wx_gift_cron;

    @Autowired
    SchedulerJobFactory schedulerJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(schedulerJobFactory);
        logger.info("***************schedulerFactoryBean init***************");
        return schedulerFactoryBean;
    }

    /**
     * 结算
     * @param schedulerFactoryBean
     * @throws SchedulerException
     */
    public void scheduleJobs(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        startRemindJob(scheduler);

        startWxGiftJob(scheduler);
    }

    private void startWxGiftJob(Scheduler scheduler) throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(ScanWxGiftTask.class)
                .withIdentity("job2", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(wx_gift_cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }


    public void startRemindJob(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScanConfigureTask.class)
                .withIdentity("job1", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(configure_cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }



}
