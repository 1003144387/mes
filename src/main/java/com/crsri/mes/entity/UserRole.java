package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserRole {
	
    private Integer id;

    private String userId;

    private Integer roleId;

    private Date createTime;

    private Date updateTime;
}