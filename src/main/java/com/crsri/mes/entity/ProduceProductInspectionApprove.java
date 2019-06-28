package com.crsri.mes.entity;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceProductInspectionApprove {
    private String id;

    private String productName;

    private String operator;
    
    private String codes;

    private Integer number;

    private Integer status;

    private Integer approveStatus;

    private Integer approveResult;

    private String remark;

    private String image;

    private Date createTime;

    private Date updateTime;


    
}