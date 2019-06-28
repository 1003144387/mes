package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Approve {
    private Integer id;

    private String name;

    private String code;

    private String approverList;

    private String ccList;

    private String approverUser;

    private String ccUser;

    private Date createTime;

    private Date updateTime;

}