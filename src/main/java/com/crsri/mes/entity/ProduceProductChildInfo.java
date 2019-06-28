package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceProductChildInfo {
    private Integer id;

    private String productId;

    private String childId;

    private String childName;

    private Integer childCount;

    private Integer childType;

    private Date createTime;

    private Date updateTime;

}