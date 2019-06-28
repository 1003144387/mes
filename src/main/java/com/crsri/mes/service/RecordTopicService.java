package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.RecordTopic;

public interface RecordTopicService {

	/**
	 * 根据任务id获取记录列表
	 * @param taskId
	 * @return
	 */
	ServerResponse getRecordTopicListByTaskId(String taskId);

	/**
	 * 创建记录
	 * @param recordTopic
	 * @return
	 */
	ServerResponse save(RecordTopic recordTopic);

	/**
	 * 根据记录主题id删除记录及所有的回复
	 * @param id
	 * @return
	 */
	ServerResponse deleteById(Integer id);

}
