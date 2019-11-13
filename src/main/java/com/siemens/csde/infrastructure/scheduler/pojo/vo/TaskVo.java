package com.siemens.csde.infrastructure.scheduler.pojo.vo;

import com.siemens.csde.infrastructure.scheduler.base.BaseVo;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskVo extends BaseVo{

    private String id;
    private String appId;
    private String taskName;
    private String taskCron;
    private String taskDesc;
    private String endpoint;
    private Integer retryTimes;
    private Integer taskType;
    private Date createTime;
    private Integer status;

}