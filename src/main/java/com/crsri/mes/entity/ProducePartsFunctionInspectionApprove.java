package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProducePartsFunctionInspectionApprove {
    private String id;

    private String codes;
    
    private Integer number;

    private String operator;

    private Integer status;

    private String image;

    private String remark;

    private Integer approveStatus;

    private Integer approveResult;

    private Date createTime;

    private Date updateTime;

}