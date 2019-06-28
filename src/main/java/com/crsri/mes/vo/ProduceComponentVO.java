package com.crsri.mes.vo;

import lombok.ToString;

import lombok.Setter;

import java.util.Date;
import java.util.List;

import com.crsri.mes.entity.ProduceComponentParts;

import lombok.Getter;

/**
 * 生产组件的VO对象
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class ProduceComponentVO {

	private String id;

    private String name;

    private String model;
    
    private String image;

    private String remark;

    private String operator;
    
    private Date createTime;
    
    private Date updateTime;

    private List<ProduceComponentParts> parts;
}
