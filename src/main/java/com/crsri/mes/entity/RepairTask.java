package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RepairTask {
    private String id;

    private String projectName;

    private String deviceCategory;

    private String deviceId;

    private String contact;

    private String phone;

    private String troubleDesc;

    private String image;

    private String repairOperator;
    
    private Integer status;

    private Date createTime;

    private Date updateTime;
    
}