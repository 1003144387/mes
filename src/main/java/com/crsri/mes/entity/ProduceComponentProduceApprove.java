package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceComponentProduceApprove {
    private String id;
    
    private String name;

    private String codes;

    private Integer number;

    private String operator;

    private Integer approveStatus;

    private Integer approveResult;

    private String image;

    private String remark;

    private Date createTime;

    private Date updateTime;

    
}