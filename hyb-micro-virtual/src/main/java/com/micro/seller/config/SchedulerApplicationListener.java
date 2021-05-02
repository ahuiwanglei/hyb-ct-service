package com.micro.seller.config;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    public SchedulerComponent schedulerComponent;


    @Autowired
    SchedulerFactoryBean schedulerFactoryBean;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            schedulerComponent.scheduleJobs(schedulerFactoryBean);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

//    @Bean
//    public SchedulerFactoryBean schedulerFactoryBean(){
//        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
//        return schedulerFactoryBean;
//    }
}
