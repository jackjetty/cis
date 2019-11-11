package com.siemens.csde.infrastructure.scheduler.config.scheduler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.InitializingBean;
/**
 * 默认的定时任务管理器
 */
@Setter
@Getter
@Slf4j
public class DefaultSchedulerManager implements SchedulerManager, InitializingBean {
    private Scheduler scheduler;

    @Override
    public void afterPropertiesSet() throws Exception {

    }
    //private List<TriggerLoader> triggerLoaders;

    private List<JobListener> jobListeners;

    @Override
    public List<TaskScheduled> getAllJobDetail() {
        List<TaskScheduled> result = new LinkedList<TaskScheduled>();
        try {
            GroupMatcher<JobKey> matcher = GroupMatcher.jobGroupContains("");
            Set<JobKey> jobKeys = scheduler.getJobKeys(matcher);
            for (JobKey jobKey : jobKeys) {
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger : triggers) {
                    TaskScheduled job = new TaskScheduled();
                    job.setTaskName(jobKey.getName());
                    job.setTaskGroup(jobKey.getGroup());
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    job.setStatus(triggerState.name());
                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        String cronExpression = cronTrigger.getCronExpression();
                        job.setTaskCron(cronExpression);
                    }
                    job.setPreviousFireTime(trigger.getPreviousFireTime());
                    job.setNextFireTime(trigger.getNextFireTime());
                    job.setDesc(trigger.getJobDataMap().getString("desc"));
                    result.add(job);
                }
            }
        } catch (Exception e) {
            log.error("Try to load All JobDetail cause error : ", e);
        }
        return result;
    }

    @Override
    public JobDetail getJobDetailByTriggerName(Trigger trigger) {
        try {
            return this.scheduler.getJobDetail(trigger.getJobKey());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<TaskScheduled> getRuningJobDetail() {
        List<TaskScheduled> jobList = null;
        try {
            List<JobExecutionContext> executingJobs = scheduler.getCurrentlyExecutingJobs();
            jobList = new ArrayList<TaskScheduled>(executingJobs.size());
            for (JobExecutionContext executingJob : executingJobs) {
                TaskScheduled job = new TaskScheduled();
                JobDetail jobDetail = executingJob.getJobDetail();
                JobKey jobKey = jobDetail.getKey();
                Trigger trigger = executingJob.getTrigger();
                job.setTaskName(jobKey.getName());
                job.setTaskGroup(jobKey.getGroup());
                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                job.setStatus(triggerState.name());
                if (trigger instanceof CronTrigger) {
                    CronTrigger cronTrigger = (CronTrigger) trigger;
                    String cronExpression = cronTrigger.getCronExpression();
                    job.setTaskCron(cronExpression);
                }
                job.setPreviousFireTime(trigger.getPreviousFireTime());
                job.setNextFireTime(trigger.getNextFireTime());
                job.setDesc(trigger.getJobDataMap().getString("desc"));
                jobList.add(job);
            }
        } catch (Exception e) {
            log.error("Try to load running JobDetail cause error : ", e);
        }
        return jobList;
    }

    @Override
    public boolean stopJob(TaskScheduled scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.pauseJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("Try to stop Job cause error : ", e);
        }
        return false;
    }

    @Override
    public boolean resumeJob(TaskScheduled scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.resumeJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("Try to resume Job cause error : ", e);
        }
        return false;
    }

    @Override
    public boolean runJob(TaskScheduled scheduleJob) {
        try {
            JobKey jobKey = JobKey.jobKey(scheduleJob.getTaskName(), scheduleJob.getTaskGroup());
            scheduler.triggerJob(jobKey);
            return true;
        } catch (Exception e) {
            log.error("Try to resume Job cause error : ", e);
        }
        return false;
    }

    @Override
    public boolean refreshScheduler() {
        return false;
    }


}