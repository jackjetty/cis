package com.siemens.csde.infrastructure.scheduler.job;

import com.siemens.csde.infrastructure.scheduler.pojo.dto.ScheduledTaskDto;
import com.siemens.csde.infrastructure.scheduler.service.RPCService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;

@DisallowConcurrentExecution
@Slf4j
public class HttpJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        JobKey key = context.getJobDetail().getKey();//获取JobDetail的标识信息
        TriggerKey triggerKey = context.getTrigger().getKey();//获取Trigger的标识信息
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        ScheduledTaskDto scheduledTaskDto= (ScheduledTaskDto)jobDataMap.get("task");
        SchedulerContext schedulerContext = null;
        try {
            schedulerContext = context.getScheduler().getContext();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        ApplicationContext applicationContext = (ApplicationContext)schedulerContext.get("applicationContext");
        RPCService jobService= (RPCService)applicationContext.getBean(RPCService.class);
        ResponseEntity responseEntity=jobService.getHttpRequest(scheduledTaskDto.getTaskUrl(),scheduledTaskDto.getRetryTimes());
        jobDataMap.putAsString("result_status",responseEntity.getStatusCodeValue());


    }
}