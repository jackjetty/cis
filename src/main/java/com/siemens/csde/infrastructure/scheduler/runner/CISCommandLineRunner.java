package com.siemens.csde.infrastructure.scheduler.runner;

import com.siemens.csde.infrastructure.scheduler.service.RPCService;
import com.siemens.csde.infrastructure.scheduler.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CISCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RPCService rpcService;

    @Autowired
    private TaskService taskService;

    @Override
    public void run(String... args) throws Exception {

        taskService.loadTasks();
       // rpcService.getHttpRequest();
    }
}
