package com.siemens.csde.infrastructure.scheduler.mybatis.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskModel {
    private String id;

    private String appId;

    private String taskName;

    private Integer taskType;

    private String taskCron;

    private String taskDesc;

    private String endpoint;

    private Date lastTime;

    private Date createTime;

    private Date udpateTime;

    private Integer status;

    private Integer retryTimes;


}