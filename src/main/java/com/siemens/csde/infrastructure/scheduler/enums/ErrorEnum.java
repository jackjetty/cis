package com.siemens.csde.infrastructure.scheduler.enums;

public enum ErrorEnum{

    ERROR_OPERATE(500,"业务异常"),
    ERROR_NOTFOUND(400,"记录没找到"),
    ERROR_UNAUTHORIZED(403,"没有相应权限"),
    ERROR_UNLOGIN(300,"用户未登录"),
    ERROR_UNKNOW(500,"未知错误");


    private int code;
    private String msg;

    private ErrorEnum(int code,String msg){

        this.code=code;
        this.msg=msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}