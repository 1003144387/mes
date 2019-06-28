package com.crsri.mes.entity;

import java.util.Date;

import lombok.Data;

@Data
public class ProduceShipApprove {
    private String id;

    private String name;

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