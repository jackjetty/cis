package com.siemens.csde.infrastructure.scheduler.component;

import com.google.common.collect.Lists;
import com.siemens.csde.infrastructure.scheduler.config.scheduler.TaskScheduled;
import com.siemens.csde.infrastructure.scheduler.job.HttpJob;
import com.siemens.csde.infrastructure.scheduler.pojo.dto.ScheduledTaskDto;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SchedulerBroker {

    public static final String PREFIX_JOB="Job";

    public static final String PREFIX_TRIGGER="Trigger";

    @Autowired
    private Scheduler scheduler;

    //@PostConstruct
    public void loadTasks() throws SchedulerException {

        JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)
                .withIdentity("jobName", "jobGroup").build();
        jobDetail.getJobDataMap().put("test", true);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ? ");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("jobName1", "jobGroup1")
                .withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);

    }



    public void addTask(ScheduledTaskDto scheduledTaskDto) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("task", scheduledTaskDto);

        JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)
                .withIdentity(PREFIX_JOB+scheduledTaskDto.getTaskName(), scheduledTaskDto.getAppId())
                .withDescription(scheduledTaskDto.getTaskDesc()).storeDurably(true).usingJobData(jobDataMap).build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduledTaskDto.getTaskCron()))
                .withIdentity(PREFIX_TRIGGER+scheduledTaskDto.getTaskName(), scheduledTaskDto.getAppId())
                .withDescription(scheduledTaskDto.getTaskDesc()).forJob(jobDetail).usingJobData(jobDataMap).build();
        //scheduler.scheduleJob(jobDetail,trigger);
        this.saveOrUpdateJobDetail(jobDetail);
        this.saveOrUpdateTrigger(trigger);
        //scheduler.scheduleJob(trigger);
    }







    public  JobDetail getJobDetailByTriggerName( Trigger trigger) {
        try {
            return scheduler.getJobDetail(trigger.getJobKey());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }


    public List<ScheduledTaskDto> getGroupScheduledTasks(String group ) {
        List<ScheduledTaskDto> scheduledTaskDtos = Lists.newLinkedList();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains(group);
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    ScheduledTaskDto scheduledTaskDto = new ScheduledTaskDto();
                    scheduledTaskDto.setTaskName(jobKey.getName());
                    scheduledTaskDto.setAppId(jobKey.getGroup());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    //triggerState.name() ;
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        scheduledTaskDto.setTaskCron(cronExpression);
                    }
                    //job.setPreviousFireTime(trigger.getPreviousFireTime());
                    //job.setNextFireTime(trigger.getNextFireTime());
                    scheduledTaskDto.setTaskDesc(trigger.getJobDataMap().getString("desc"));
                    scheduledTaskDtos.add(scheduledTaskDto);
                }
            }
        } catch (Exception e) {
            log.error("Try to load All JobDetail cause error : ", e);
        }
        return scheduledTaskDtos;
    }













    private void saveOrUpdateJobDetail(JobDetail jobDetail) {
        JobDetail oldJobDetail = null;
        try {
            oldJobDetail =  scheduler.getJobDetail(jobDetail.getKey());
        } catch (Exception e) {
        }
        try {
            if (oldJobDetail == null) {
                log.info("Try to add jobDetail : {}" , jobDetail);
                scheduler.addJob(jobDetail, true);
            } else {
                scheduler.addJob(jobDetail, true);
            }
        } catch (Exception e) {
            log.error("Try to add jobDetail : {}, the old jobDetail is : {} cause error : ",jobDetail,oldJobDetail, e);
        }
    }


    private void saveOrUpdateTrigger(Trigger trigger) {
        Trigger oldTrigger = null;
        try {
            oldTrigger = scheduler.getTrigger(trigger.getKey());
        } catch (Exception e) {
        }
        try {
            if (oldTrigger == null) {
                log.info("Try to add trigger : {}" , trigger);
                scheduler.scheduleJob(trigger);
            } else {
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            }
            /*if (!trigger.getJobDataMap().getBoolean("enable")) {
                scheduler.pauseTrigger(trigger.getKey());
            }*/
        } catch (SchedulerException e) {
            log.error("Try to add trigger : {}  cause error : ",trigger, e);
        }
    }









}