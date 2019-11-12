package com.siemens.csde.infrastructure.scheduler.pojo.bean;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

public class ErrorBean implements Serializable {

    private static final long serialVersionUID = -7614691892970694815L;
    private ErrorResponse error_response;

    public ErrorBean(){
        this.error_response=new ErrorResponse();
    }

    public ErrorBean(int code,String msg){
        this();
        this.error_response.setCode(code);
        this.error_response.setMsg(msg);
    }

    public ErrorBean(int code,Throwable throwable){
        this();
        this.error_response.setCode(code);
        this.error_response.setMsg(throwable.getMessage());
    }



    @Setter
    @Getter
    class ErrorResponse{

        private int code;
        private String msg;

    }
}