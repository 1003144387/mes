package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProducePartsDefendApprove {
    private String id;

    private String codes;

    private String typeName;

    private Integer number;

    private String operator;

    private String image;

    private String remark;

    private Integer approveStatus;

    private Integer approveResult;

    private Date createTime;

    private Date updateTime;

}