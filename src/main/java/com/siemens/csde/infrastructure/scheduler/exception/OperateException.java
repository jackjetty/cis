package com.siemens.csde.infrastructure.scheduler.exception;
public class OperateException extends  RuntimeException{

    public OperateException() {
    }

    public OperateException(String message) {
        super(message);
    }

    public OperateException(Throwable cause) {
        super(cause);
    }

    public OperateException(String message, Throwable cause) {
        super(message, cause);
    }

}