package com.siemens.csde.infrastructure.scheduler.service.impl;

import com.google.gson.JsonObject;
import com.siemens.csde.infrastructure.scheduler.component.SchedulerBroker;
import com.siemens.csde.infrastructure.scheduler.pojo.dto.ScheduledTaskDto;
import com.siemens.csde.infrastructure.scheduler.service.RPCService;
import com.siemens.csde.infrastructure.scheduler.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private SchedulerBroker schedulerBroker;

    @Override
    public void loadTasks() {
        ScheduledTaskDto scheduledTaskDto=new ScheduledTaskDto();
        scheduledTaskDto.setAppId("macb-admin");
        scheduledTaskDto.setTaskId("001");
        scheduledTaskDto.setTaskName("refresh_token");
        scheduledTaskDto.setTaskCron("0/5 * * * * ? ");
        scheduledTaskDto.setRetryTimes(1);
        scheduledTaskDto.setTaskUrl("http://localhost:25000/task/123");
        scheduledTaskDto.setTaskDesc("刷新token");
        try {
            schedulerBroker.addTask(scheduledTaskDto);
        } catch (SchedulerException e) {
            log.error("add task error",e);
        }
    }
}