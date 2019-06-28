package com.crsri.mes.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RecordReply {
	
    private Integer id;

    private Integer topicId;
    
    private Integer parentId;

    private String replyContent;

    private String replyOperator;

    private Date createTime;

    private Date updateTime;

  
}