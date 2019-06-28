package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Permission {
    private Integer id;

    private String permissionName;

    private String permissionDesc;

    private Integer permissionParentId;

    private String permissionUrl;

    private String permissionIcon;

    private Integer permissionOrder;

    private Integer permissionType;

    private Date createTime;

    private Date updateTime;

   
}