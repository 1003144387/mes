package com.crsri.mes.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_NULL)
public class ProduceParts {
    private String id;

    private String name;

    private String model;

    private String providerName;

    private String providerPhone;

    private String image;

    private BigDecimal price;

    private String remark;

    private String operator;

    private Date createTime;

    private Date updateTime;

    
}