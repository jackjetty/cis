package com.siemens.csde.infrastructure.scheduler.service.impl;
import com.siemens.csde.infrastructure.scheduler.constant.TaskConstant;
import com.siemens.csde.infrastructure.scheduler.enums.ErrorEnum;
import com.siemens.csde.infrastructure.scheduler.exception.OperateException;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.AppModel;
import com.siemens.csde.infrastructure.scheduler.pojo.vo.TaskVo;
import com.siemens.csde.infrastructure.scheduler.util.UUIDUtil;
import java.util.Calendar;
import java.util.Date;
import com.google.gson.JsonObject;
import com.siemens.csde.infrastructure.scheduler.component.SchedulerBroker;
import com.siemens.csde.infrastructure.scheduler.mybatis.mapper.AppMapper;
import com.siemens.csde.infrastructure.scheduler.mybatis.mapper.TaskMapper;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.TaskModel;
import com.siemens.csde.infrastructure.scheduler.pojo.bean.ResultBean;
import com.siemens.csde.infrastructure.scheduler.pojo.dto.ScheduledTaskDto;
import com.siemens.csde.infrastructure.scheduler.pojo.qo.AddTaskQo;
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
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public ResultBean addTask(AddTaskQo addTaskQo) {

        String appId=addTaskQo.getAppId();
        String taskName=addTaskQo.getTaskName();
        AppModel appModel=appMapper.selectByPrimaryKey(appId);
        if(!Optional.ofNullable(appModel).isPresent()){
            throw new OperateException("app id :"+appId+" not exist ,please check");
        }
        Optional<TaskModel> taskModelOptional=Optional.ofNullable(taskMapper.selectTaskByAppIdAndTaskName(appId,taskName));
        if(taskModelOptional.isPresent()){
            throw new OperateException("app id :"+appId+" task name:"+taskName+" already exist ,please check");
        }

        String id= UUIDUtil.getUUID();
        Calendar calendar=Calendar.getInstance();
        TaskModel taskModel=new TaskModel();
        taskModel.setId(id);
        taskModel.setAppId(addTaskQo.getAppId());
        taskModel.setTaskName(addTaskQo.getTaskName());
        taskModel.setTaskType(TaskConstant.TYPE_HTTP);
        taskModel.setTaskCron(addTaskQo.getTaskCron());
        taskModel.setTaskDesc(addTaskQo.getTaskDesc());
        taskModel.setEndpoint(addTaskQo.getEndpoint());
        taskModel.setCreateTime(calendar.getTime());
        taskModel.setStatus(TaskConstant.STATUS_ENABLED);
        taskModel.setRetryTimes(addTaskQo.getRetryTimes());
        taskMapper.insert(taskModel);
        taskModel.setAppUrl(appModel.getUrl());
        ScheduledTaskDto scheduledTaskDto=ScheduledTaskDto.createByModel(taskModel);
        try {
            schedulerBroker.addTask(scheduledTaskDto);
        } catch (SchedulerException e) {
            log.error("add task error",e);
            throw new OperateException("add scheduler task error ",e);
        }
        return ResultBean.builder().code(ErrorEnum.SUCCESS.getCode()).id(id).message(ErrorEnum.SUCCESS.getMsg()).build();

    }

    @Override
    @Transactional
    public ResultBean deleteTask(String id) {

        TaskModel taskModel=taskMapper.selectByPrimaryKey(id);
        if(taskModel==null){
            throw new OperateException("task id :"+id+" not exist ,please check");
        }
        Integer status=taskModel.getStatus();
        if(status!=1){
            throw new OperateException("task id :"+id+"  is disabled  ,please check");
        }
        try{
            schedulerBroker.removeTask(taskModel.getAppId(),taskModel.getTaskName());
        }catch(Exception ex){
            log.error("remove task {} error",id,ex);
            throw new OperateException("remove scheduler task "+id+" error " ,ex);
        }
        taskModel.setStatus(TaskConstant.STATUS_DELETED);
        taskMapper.updateByPrimaryKeySelective(taskModel);
        return ResultBean.builder().code(ErrorEnum.SUCCESS.getCode()).message(ErrorEnum.SUCCESS.getMsg()).build();

    }

    @Override
    public TaskVo getTask(String id) {

        TaskModel taskModel=taskMapper.selectByPrimaryKey(id);
        if(taskModel==null){
            throw new OperateException("task id :"+id+" not exist ,please check");
        }
        TaskVo taskVo=new TaskVo();
        taskVo.setId(taskModel.getId());
        taskVo.setAppId(taskModel.getAppId());
        taskVo.setTaskName(taskModel.getTaskName());
        taskVo.setTaskCron(taskModel.getTaskCron());
        taskVo.setTaskDesc(taskModel.getTaskDesc());
        taskVo.setEndpoint(taskModel.getEndpoint());
        taskVo.setRetryTimes(taskModel.getRetryTimes());
        taskVo.setTaskType(taskModel.getTaskType());
        taskVo.setCreateTime(taskModel.getCreateTime());
        taskVo.setStatus(taskModel.getStatus());
        return taskVo;

    }


}