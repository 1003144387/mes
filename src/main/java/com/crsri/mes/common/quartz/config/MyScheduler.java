package com.crsri.mes.common.quartz.config;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import com.crsri.mes.common.quartz.job.DatabaseBackupJob;
import com.crsri.mes.common.quartz.job.OSSJob;
import com.crsri.mes.common.quartz.job.StockWeekReportJob;
import com.crsri.mes.common.quartz.job.UnFinishedCustomerTaskJob;
import com.crsri.mes.common.quartz.job.UnFinishedRepairTaskJob;

/**
 * 〈一句话功能简述〉<br>
 * 〈任务配置〉
 *
 * @author zcj
 * @date 2018/12/3 21:29
 * @since 1.0.0
 */
@Component
public class MyScheduler {
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void scheduleJobs() throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        startJob1(scheduler);
        startJob2(scheduler);
        startJob3(scheduler);
        startJob4(scheduler);
        startJob5(scheduler);
    }

    /**
     * 定时发周报
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJob1(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(StockWeekReportJob.class)
                .withIdentity("job1", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 18 ? * FRI");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 定时发送未处理完成的维修任务
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJob2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(UnFinishedRepairTaskJob.class)
                .withIdentity("job2", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 8 * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 定时发送未处理完成的售后任务
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJob3(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(UnFinishedCustomerTaskJob.class)
                .withIdentity("job3", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 30 8 * * ? ");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger3", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 备份数据库信息的定时任务
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJob4(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(DatabaseBackupJob.class)
                .withIdentity("job4", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 25 22 * * ? ");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger4", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 备份资源文件到阿里云OSS的定时任务
     * @param scheduler
     * @throws SchedulerException
     */
    private void startJob5(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(OSSJob.class)
                .withIdentity("job5", "group1").build();
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 28 22 * * ? ");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("trigger5", "group1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }
}
