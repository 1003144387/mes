package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 生产组件基本信息表
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponent {
    private String id;

    private String name;

    private String model;

    private String image;

    private String remark;

    private String operator;

    private Date createTime;

    private Date updateTime;

}