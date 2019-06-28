package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
public class CustomerTaskDetail {
    private Integer id;

    private String taskId;

    private String troubleCategory;

    private String troubleDesc;

    private String image;

    private Date createTime;

    private Date updateTime;

    
}