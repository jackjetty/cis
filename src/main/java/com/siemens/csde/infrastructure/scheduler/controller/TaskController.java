package com.siemens.csde.infrastructure.scheduler.controller;

import com.siemens.csde.infrastructure.scheduler.pojo.vo.TaskVo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TaskController{

    @GetMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public TaskVo getTask(HttpServletRequest request,
            HttpServletResponse response,@PathVariable(value = "id", required = true) String id){

        TaskVo taskVo=new TaskVo();
        taskVo.setId(id);
        return taskVo;

    }


}