package com.siemens.csde.infrastructure.scheduler.config.scheduler;

import com.siemens.csde.infrastructure.scheduler.component.TaskListener;
import java.io.IOException;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
@Slf4j
public class SchedulerConfig implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private TaskListener taskListener;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

         //初始任务

    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("config/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
       SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
       schedulerFactoryBean.setSchedulerName("CIS-Scheduler");
       schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
        try {
            schedulerFactoryBean.setQuartzProperties(quartzProperties());
        } catch (IOException e) {
            log.error("读取配置文件出错",e);
        }
       //schedulerFactoryBean.setDataSource();
       return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(@Qualifier("schedulerFactoryBean") SchedulerFactoryBean schedulerFactoryBean){
        //SchedulerFactoryBean schedulerFactoryBean=schedulerFactoryBean();
        Scheduler scheduler=schedulerFactoryBean.getScheduler();
        try {
            scheduler.getListenerManager().addJobListener(taskListener);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return  scheduler;
    }

}