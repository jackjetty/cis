package com.siemens.csde.infrastructure.scheduler.pojo.bean;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResultBean implements Serializable {

    private static final long serialVersionUID = -7100768610528862719L;
    private int code;
    private String message;
    private String id;
}