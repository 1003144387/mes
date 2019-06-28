package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.RecordReply;

public interface RecordReplyService {

	/**
	 * 新增回复
	 * @param reply
	 * @return
	 */
	ServerResponse save(RecordReply reply);

	/**
	 * 根据主题id获取回复记录列表
	 * @param topicId
	 * @return
	 */
	ServerResponse getRecordReplyListByTopicId(Integer topicId);

}
