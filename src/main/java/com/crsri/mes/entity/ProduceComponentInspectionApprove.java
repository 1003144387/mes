package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 生产组件检验的审批实体类
 * @author 2011102394
 *
 */

@Getter
@Setter
@ToString
public class ProduceComponentInspectionApprove {
    private String id;

    private String componentName;

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