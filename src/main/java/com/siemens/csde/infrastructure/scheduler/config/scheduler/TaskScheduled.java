package com.siemens.csde.infrastructure.scheduler.config.scheduler;

import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 计划任务信息
 */
@Setter
@Getter
@Slf4j
@SuppressWarnings("serial")
public class TaskScheduled implements Serializable {
    /** 任务id */
    private String id;
    /** 任务名称 */
    private String taskName;
    /** 任务分组 */
    private String taskGroup;
    /** 任务状态 0禁用 1启用 2删除 */
    private String status;
    /** 任务运行时间表达式 */
    private String taskCron;
    /** 最后一次执行时间 */
    private Date previousFireTime;
    /** 下次执行时间 */
    private Date nextFireTime;
    /** 任务描述 */
    private String desc;
}