package com.siemens.csde.infrastructure.scheduler.service;

import com.siemens.csde.infrastructure.scheduler.pojo.bean.ResultBean;
import com.siemens.csde.infrastructure.scheduler.pojo.qo.AddTaskQo;
import com.siemens.csde.infrastructure.scheduler.pojo.vo.TaskVo;

public interface TaskService{

    void loadTasks();

    ResultBean addTask( AddTaskQo addTaskQo);

    ResultBean deleteTask(String id);

    TaskVo getTask(String id);

}