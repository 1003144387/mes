package com.crsri.mes.dao;

import java.util.List;

import com.crsri.mes.entity.RecordTopic;

public interface RecordTopicMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(RecordTopic record);

	int insertSelective(RecordTopic record);

	RecordTopic selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RecordTopic record);

	int updateByPrimaryKey(RecordTopic record);

	/**
	 * 通过任务id获取记录列表
	 * 
	 * @param taskId
	 * @return
	 */
	List<RecordTopic> queryByTaskId(String taskId);
}