package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RolePermission {
    private Integer id;

    private Integer roleId;

    private Integer permissionId;

    private Date createTime;

    private Date updateTime;

    
}