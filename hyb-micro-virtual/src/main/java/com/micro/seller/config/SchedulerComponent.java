package com.micro.seller.config;

import com.micro.seller.quartz.ScanMicroBuyRecordQuartz;
import com.micro.seller.quartz.ScanMicroVirutalIncomeQuartz;
import com.micro.seller.quartz.ScanVirutalIncomeBakQuartz;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
@Configuration
public class SchedulerComponent {

    protected static final Logger logger = LoggerFactory.getLogger(SchedulerComponent.class);

    @Value("${scan.virtual.income}")
    private String scan_virtual_income;

    @Value("${scan.product.cron}")
    private String product_cron;//


//    @Value("${scan.virtualexception.income}")
//    private String virtualexception;

    @Autowired
    SchedulerJobFactory schedulerJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(schedulerJobFactory);
        logger.info("***************schedulerFactoryBean init***************");
        return schedulerFactoryBean;
    }

    public void scheduleJobs(SchedulerFactoryBean schedulerFactoryBean ) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();

        startScanVirtualIncomeAmount(scheduler);

        startScanProduct(scheduler);

//        startScanVirtualIncomeAmountException(scheduler);
    }

    /**
     * @param scheduler
     * @throws SchedulerException
     */
    public void startScanVirtualIncomeAmount(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScanMicroVirutalIncomeQuartz.class)
                .withIdentity("job2", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scan_virtual_income);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
    }

    /**
     * @param scheduler
     * @throws SchedulerException
     */
//    public void startScanVirtualIncomeAmountException(Scheduler scheduler) throws SchedulerException {
//        JobDetail jobDetail = JobBuilder.newJob(ScanVirutalIncomeBakQuartz.class)
//                .withIdentity("job3", "group1").build();
//        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(virtualexception);
//        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
//                .withSchedule(scheduleBuilder).build();
//        scheduler.scheduleJob(jobDetail,cronTrigger);
//        scheduler.start();
//    }

    /**
     * @param scheduler
     * @throws SchedulerException
     */
    public void startScanProduct(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(ScanMicroBuyRecordQuartz.class)
                .withIdentity("job1", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(product_cron);
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.start();
    }



    @PreDestroy
    public void onDestroy(){
        try {
            schedulerFactoryBean().getScheduler().shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }



}
