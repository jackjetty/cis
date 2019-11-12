package com.siemens.csde.infrastructure.scheduler.runner;
import com.google.common.collect.Lists;
import com.siemens.csde.infrastructure.scheduler.component.TaskListener;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;
import com.siemens.csde.infrastructure.scheduler.service.RPCService;
import com.siemens.csde.infrastructure.scheduler.service.TaskService;
import com.siemens.csde.infrastructure.scheduler.service.TriggerLogService;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogQueueCommandLineRunner implements CommandLineRunner {

    @Autowired
    private TriggerLogService triggerLogService;

    private Timer timer = new Timer();

    public void run(String... args) throws Exception {

        timer.schedule(new LogQueueTimerTask(triggerLogService)  , 450,15*1000);

    }

    @PreDestroy
    public void clear(){
        timer.cancel();
    }

    class LogQueueTimerTask extends TimerTask {

        private TriggerLogService triggerLogService;

        public LogQueueTimerTask(TriggerLogService triggerLogService) {
            this.triggerLogService = triggerLogService;
        }

        public void run() {
            List<TriggerLogModel> triggerLogModels=Lists.newArrayList();
            int elementSize=0;
            TriggerLogModel triggerLogModel;
            while(!TaskListener.TASKLOG_QUEUE.isEmpty()){
                triggerLogModel=TaskListener.TASKLOG_QUEUE.poll();
                if(Objects.isNull(triggerLogModel)){
                    break;
                }
                triggerLogModels.add(triggerLogModel);
                elementSize++;
                if(elementSize>100){
                    break;
                }
            }
            if(elementSize==0){
                return;
            }
            triggerLogService.addTriggerLogs(triggerLogModels);
        }
    }
}