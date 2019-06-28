package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Role {
    private Integer id;

    private String roleName;

    private String roleDesc;

    private Date createTime;

    private Date updateTime;

    
}