package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProducePartsInspectionApprove {
    private String id;

    private String partsId;

    private String partsName;

    private String partsCodes;

    private String operator;

    private Integer partsStatus;

    private Integer specification;

    private Integer number;

    private String image;

    private String remark;

    private Integer approveStatus;

    private Integer approveResult;

    private Date createTime;

    private Date updateTime;

   
}