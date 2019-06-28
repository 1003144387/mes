package com.crsri.mes.vo;

import java.util.Date;
import java.util.List;

import com.crsri.mes.entity.RecordReply;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 记录回复的VO对象
 * 
 * @author 2011102394
 *
 */
@Getter
@Setter
@ToString
public class RecordReplyVO {

	private Integer id;

	private Integer topicId;
	
	private Integer parentId;

	private String replyContent;

	private String replyOperator;

	private Date createTime;

	private Date updateTime;
	
	private String replyTarget;

}
