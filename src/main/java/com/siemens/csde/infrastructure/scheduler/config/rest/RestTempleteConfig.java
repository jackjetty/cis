package com.siemens.csde.infrastructure.scheduler.config.rest;

import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * resttemplate 的配置类
 */
@Configuration
public class RestTempleteConfig {
    @Autowired
    private RestTemplateBuilder builder;

    @Bean
    public RestTemplate restTemplate() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
        //messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        gsonHttpMessageConverter.setGson(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create());
        messageConverters.add(gsonHttpMessageConverter);
        //messageConverters.add(new MappingJackson2HttpMessageConverter());
        builder.additionalMessageConverters(messageConverters);
        builder.setReadTimeout(5000);
        builder.setConnectTimeout(5000);
        return builder.build();
    }
}
