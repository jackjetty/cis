package com.siemens.csde.infrastructure.scheduler.controller;

import com.siemens.csde.infrastructure.scheduler.enums.ErrorEnum;
import com.siemens.csde.infrastructure.scheduler.pojo.bean.ResultBean;
import com.siemens.csde.infrastructure.scheduler.pojo.qo.AddTaskQo;
import com.siemens.csde.infrastructure.scheduler.pojo.vo.TaskVo;
import com.siemens.csde.infrastructure.scheduler.service.TaskService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TestController{


    @GetMapping(value = "/test/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResultBean test(HttpServletRequest request,
            HttpServletResponse response,@PathVariable(value = "id", required = true) String id){

        return ResultBean.builder().code(ErrorEnum.SUCCESS.getCode()).message(ErrorEnum.SUCCESS.getMsg()).id(id).build();

    }

}