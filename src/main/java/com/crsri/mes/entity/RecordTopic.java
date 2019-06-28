package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecordTopic {
    private Integer id;

    private String taskId;

    private Integer taskType;

    private String recordTopic;

    private String recordContent;

    private String recordOperator;

    private Date createTime;

    private Date updateTime;

}