package com.crsri.mes.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class AutomationProjectTaskRecord {

    private Integer id;

    private String taskId;

    private String recordTitle;

    private String recordAbstract;

    private String recordContent;

    private String operator;

    private Date createTime;

    private Date updateTime;


}