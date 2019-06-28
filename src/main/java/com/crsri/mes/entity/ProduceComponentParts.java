package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 生产组件-部件组成信息
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponentParts {
    private Integer id;

    private String produceComponentId;

    private String producePartsId;

    private String producePartsName;

    private Integer producePartsCount;

    private Date createTime;

    private Date updateTime;

   
}