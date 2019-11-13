package com.siemens.csde.infrastructure.scheduler.controller;

import com.siemens.csde.infrastructure.scheduler.pojo.bean.ResultBean;
import com.siemens.csde.infrastructure.scheduler.pojo.qo.AddTaskQo;
import com.siemens.csde.infrastructure.scheduler.pojo.vo.TaskVo;
import com.siemens.csde.infrastructure.scheduler.service.TaskService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TaskController{

    @Autowired
    private TaskService taskService;

    @GetMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TaskVo getTask(HttpServletRequest request,
            HttpServletResponse response,@PathVariable(value = "id", required = true) String id){

        TaskVo  taskVo=taskService.getTask(id);
        return taskVo;
    }



    @PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean addTask(HttpServletRequest request,
            HttpServletResponse response, @RequestBody AddTaskQo addTaskQo) {

        ResultBean resultBean = taskService.addTask(addTaskQo);
        return resultBean;

    }

    @DeleteMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean deleteTask(HttpServletRequest request,
            HttpServletResponse response ,@PathVariable(value = "id", required = true) String id) {

        ResultBean resultBean = taskService.deleteTask(id);
        return resultBean;

    }


}