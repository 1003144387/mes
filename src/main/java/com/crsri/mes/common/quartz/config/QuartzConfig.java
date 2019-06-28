package com.crsri.mes.common.quartz.config;

import org.quartz.SchedulerException;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.Resource;

/**
 * 〈一句话功能简述〉<br>
 * 〈定时任务配置类〉
 *
 * @author zcj
 * @date 2018/10/23 14:11
 * @since 1.0.0
 */
@Configuration
public class QuartzConfig implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    public MyScheduler myScheduler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            myScheduler.scheduleJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        return new SchedulerFactoryBean();
    }

}
