package com.siemens.csde.infrastructure.scheduler.component;
import com.google.common.collect.Lists;
import com.siemens.csde.infrastructure.scheduler.service.TriggerLogService;
import com.siemens.csde.infrastructure.scheduler.util.UUIDUtil;
import java.util.Date;

import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;
import com.siemens.csde.infrastructure.scheduler.pojo.dto.ScheduledTaskDto;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskListener  implements JobListener {


    public static LinkedBlockingQueue<TriggerLogModel> TASKLOG_QUEUE=new LinkedBlockingQueue(Integer.MAX_VALUE);

    @Autowired
    private TriggerLogService triggerLogService;

    @Override
    public String getName() {
        return "taskListener";
    }
    // 任务开始前
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        JobKey jobKey = context.getJobDetail().getKey();
        String groupName = jobKey.getGroup();
        String jobName = jobKey.getName();
        jobDataMap.put("START_TIME",context.getFireTime());

    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }
    // 任务结束后
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {

        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String groupName = context.getJobDetail().getKey().getGroup();
        String jobName = context.getJobDetail().getKey().getName();
        if(jobException!=null){
            log.error("调度app: {},任务：{},出现不可控异常",groupName,jobName,jobException);
            //通知...
            return;
        }
        ScheduledTaskDto scheduledTaskDto= (ScheduledTaskDto)jobDataMap.get("task");
        String resultStatus=jobDataMap.getString("result_status");
        TriggerLogModel triggerLogModel=new TriggerLogModel();
        triggerLogModel.setId(UUIDUtil.getUUID());
        triggerLogModel.setAppId(scheduledTaskDto.getAppId());
        triggerLogModel.setTaskId(scheduledTaskDto.getTaskId());
        triggerLogModel.setStartTime(context.getFireTime());
        triggerLogModel.setEndTime(new Date());
        triggerLogModel.setStatus(resultStatus);
        triggerLogModel.setTaskUrl(scheduledTaskDto.getTaskUrl());
        triggerLogModel.setRetryTimes(scheduledTaskDto.getRetryTimes());
        TASKLOG_QUEUE.offer(triggerLogModel);

    }

    @PreDestroy
    public void clear(){
        //清空队列
        List<TriggerLogModel> triggerLogModels= Lists.newArrayList();
        TriggerLogModel triggerLogModel;
        while(!TaskListener.TASKLOG_QUEUE.isEmpty()){
            triggerLogModel=TaskListener.TASKLOG_QUEUE.poll();
            if(Objects.isNull(triggerLogModel)){
                break;
            }
            triggerLogModels.add(triggerLogModel);
        }
        triggerLogService.addTriggerLogs(triggerLogModels);
    }




}