package com.siemens.csde.infrastructure.scheduler.service.impl;

import com.google.gson.JsonObject;
import com.siemens.csde.infrastructure.scheduler.component.SchedulerBroker;
import com.siemens.csde.infrastructure.scheduler.mybatis.mapper.AppMapper;
import com.siemens.csde.infrastructure.scheduler.mybatis.mapper.TaskMapper;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.TaskModel;
import com.siemens.csde.infrastructure.scheduler.pojo.dto.ScheduledTaskDto;
import com.siemens.csde.infrastructure.scheduler.service.RPCService;
import com.siemens.csde.infrastructure.scheduler.service.TaskService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private AppMapper appMapper;

    @Override
    public void loadTasks() {

         List<TaskModel> taskModels= taskMapper.selectEnabledTasks();
         Optional.ofNullable(taskModels)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(taskModel -> StringUtils.isNotEmpty(taskModel.getTaskCron()))
                .map(ScheduledTaskDto::createByModel).forEach(scheduledTaskDto -> {
                     try{
                         schedulerBroker.addTask(scheduledTaskDto);
                     }catch (Exception ex){
                         log.error("初始化新增任务task {},出现异常",scheduledTaskDto.getTaskId(),ex);
                     }
                 });


    }

    @Override
    public void addTask() {

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