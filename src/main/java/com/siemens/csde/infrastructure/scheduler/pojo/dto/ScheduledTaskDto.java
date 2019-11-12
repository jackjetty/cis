package com.siemens.csde.infrastructure.scheduler.pojo.dto;

import com.siemens.csde.infrastructure.scheduler.base.BaseDto;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.TaskModel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
@Getter
public class ScheduledTaskDto  extends BaseDto {

    private static final long serialVersionUID = 9014013549560418678L;

    private String appId;
    private String taskId;
    private String taskName;
    private String taskCron;
    private Integer retryTimes;
    private String taskUrl;
    private String taskDesc;

    public static  ScheduledTaskDto createByModel(TaskModel taskModel){

        ScheduledTaskDto scheduledTaskDto=new ScheduledTaskDto();
        scheduledTaskDto.setAppId(taskModel.getAppId());
        scheduledTaskDto.setTaskId(taskModel.getId());
        scheduledTaskDto.setTaskName(taskModel.getTaskName());
        scheduledTaskDto.setTaskCron(taskModel.getTaskCron());
        scheduledTaskDto.setRetryTimes(taskModel.getRetryTimes());
        scheduledTaskDto.setTaskUrl(taskModel.getAppUrl().concat(taskModel.getEndpoint()));
        scheduledTaskDto.setTaskDesc(taskModel.getTaskDesc());
        return scheduledTaskDto;

    }

}