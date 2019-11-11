package com.siemens.csde.infrastructure.scheduler.service.impl;

import com.google.gson.JsonObject;
import com.siemens.csde.infrastructure.scheduler.service.RPCService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RPCServiceImpl implements RPCService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity getHttpRequest(String url,Integer retryTimes) {
        HttpHeaders requestHeaders = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
        //String url="http://api.map.baidu.com/telematics/v3/weather?location=%E5%98%89%E5%85%B4&output=json&ak=5slgyqGDENN7Sy7pw29IUvrZ";
        //url="http://localhost:25000/task/123";
        ResponseEntity<JsonObject> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JsonObject.class);
        log.info("http code:{},body:{}",response.getStatusCode(),response.getBody());
        if(response.getStatusCode()== HttpStatus.OK){
            return response;
        }
        while(retryTimes>0){
            response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, JsonObject.class);
            if(response.getStatusCode()== HttpStatus.OK){
                return response;
            }
            retryTimes--;
        }
        return response;

    }
}
