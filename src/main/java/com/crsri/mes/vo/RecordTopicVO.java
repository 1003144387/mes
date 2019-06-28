package com.crsri.mes.vo;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RecordTopicVO {

	private Integer id;

	private Integer topicId;

	private String replyContent;

	private String replyOperator;

	private Date createTime;

	private Date updateTime;
	
	private List<RecordReplyVO> children;
}
