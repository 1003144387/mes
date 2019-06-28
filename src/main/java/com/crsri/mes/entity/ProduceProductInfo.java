package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceProductInfo {
    private String id;

    private String name;

    private String model;

    private String image;

    private String remark;

    private String operator;

    private Date createTime;

    private Date updateTime;

    
}