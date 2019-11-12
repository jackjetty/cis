package com.siemens.csde.infrastructure.scheduler.pojo.qo;

import com.siemens.csde.infrastructure.scheduler.base.BaseQo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddTaskQo extends BaseQo{

    private static final long serialVersionUID = 3787739885174062270L;
    private String appId;
    private String taskName;
    private String taskCron;
    private String taskDesc;
    private String endpoint;
    private Integer retryTimes;

}