package com.siemens.csde.infrastructure.scheduler.config.scheduler;
import java.util.List;
import org.quartz.JobDetail;
import org.quartz.Trigger;

/**
 * 调度器管理
 *
 */
public interface SchedulerManager {

    public List<TaskScheduled> getAllJobDetail();

    public JobDetail getJobDetailByTriggerName(Trigger trigger);

    /** 获取运行中任务 */
    public List<TaskScheduled> getRuningJobDetail();

    /** 暂停任务 */
    public boolean stopJob(TaskScheduled scheduleJob);

    /** 恢复任务 */
    public boolean resumeJob(TaskScheduled scheduleJob);

    /** 运行任务 */
    public boolean runJob(TaskScheduled scheduleJob);

    /** 刷新调度(新增任务为暂停状态) */
    public boolean refreshScheduler();
}