package com.siemens.csde.infrastructure.scheduler.mybatis.model;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TriggerLogModel {
    private String id;

    private String appId;

    private String taskId;

    private Date startTime;

    private Date endTime;

    private String status;

    private String taskUrl;

    private Integer retryTimes;


}