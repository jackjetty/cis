package com.siemens.csde.infrastructure.scheduler;
import com.google.common.collect.Lists;
import com.siemens.csde.infrastructure.scheduler.component.TaskListener;
import com.siemens.csde.infrastructure.scheduler.mybatis.model.TriggerLogModel;
import com.siemens.csde.infrastructure.scheduler.service.TriggerLogService;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.siemens"})
@MapperScan("com.siemens.csde.infrastructure.scheduler.mybatis.mapper")
public class CISApplication implements ApplicationListener<ContextClosedEvent> {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CISApplication.class, args);

    }

    @Autowired
    private TriggerLogService triggerLogService;

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {

    }

}