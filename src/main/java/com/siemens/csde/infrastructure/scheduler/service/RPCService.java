package com.siemens.csde.infrastructure.scheduler.service;

import org.springframework.http.ResponseEntity;

public interface RPCService{

    ResponseEntity getHttpRequest(String url,Integer retryTimes);

}