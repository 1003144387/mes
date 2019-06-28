package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProduceStockOutApprove {
    private String id;

    private Integer goodsType;

    private String typeName;

    private String codes;

    private Integer number;

    private String receivePeople;

    private String receiveAddress;

    private String operator;

    private Integer approveStatus;

    private Integer approveResult;

    private String image;

    private String remark;

    private Date createTime;

    private Date updateTime;

   
}