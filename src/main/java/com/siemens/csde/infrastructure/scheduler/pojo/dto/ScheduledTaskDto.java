package com.siemens.csde.infrastructure.scheduler.pojo.dto;

import com.siemens.csde.infrastructure.scheduler.base.BaseDto;
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

}