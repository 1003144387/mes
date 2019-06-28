package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class SysLog {
    private Integer id;

    private String description;

    private String method;

    private String type;

    private String requestIp;

    private String exceptionCode;

    private String exceptionDetail;

    private String params;

    private String deviceType;

    private String response;

    private String operator;

    private Date operatorTime;

}