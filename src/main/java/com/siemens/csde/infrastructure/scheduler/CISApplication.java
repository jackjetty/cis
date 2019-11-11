package com.siemens.csde.infrastructure.scheduler;
import java.util.TimeZone;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
@Slf4j
@SpringBootApplication
@ServletComponentScan
@ComponentScan(basePackages = {"com.siemens"})
@MapperScan("com.siemens.csde.infrastructure.scheduler.mybatis.mapper")
public class CISApplication{

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(CISApplication.class, args);

    }

}