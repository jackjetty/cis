package com.siemens.csde.infrastructure.scheduler.handler;

import com.siemens.csde.infrastructure.scheduler.enums.ErrorEnum;
import com.siemens.csde.infrastructure.scheduler.exception.OperateException;
import com.siemens.csde.infrastructure.scheduler.pojo.bean.ErrorBean;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler{

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = OperateException.class)
    @ResponseBody
    public ErrorBean handleException(OperateException ex, HttpServletResponse response) {

        log.error("OperateException：", ex);
        ErrorBean errorBean = new ErrorBean(ErrorEnum.ERROR_OPERATE.getCode(),ex );
        return errorBean;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorBean handleUnknown(Exception ex, HttpServletResponse response) {

        log.error("Exception：", ex);
        ErrorBean errorBean = new ErrorBean(ErrorEnum.ERROR_UNKNOW.getCode(),ex );
        return errorBean;
    }

}