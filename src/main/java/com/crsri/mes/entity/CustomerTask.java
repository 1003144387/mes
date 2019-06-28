package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class CustomerTask {
	
	
    private String id;

    private String customerCompany;

    private String projectName;

    private String contact;

    private String phone;

    private String customerOperator;

    private Integer status;

    private Date createTime;

    private Date updateTime;

}