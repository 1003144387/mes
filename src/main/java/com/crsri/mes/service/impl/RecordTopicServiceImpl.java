package com.crsri.mes.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.RecordReplyMapper;
import com.crsri.mes.dao.RecordTopicMapper;
import com.crsri.mes.entity.RecordTopic;
import com.crsri.mes.service.RecordTopicService;

@Service
public class RecordTopicServiceImpl implements RecordTopicService{

	@Resource
	private RecordTopicMapper recordTopicMapper;
	
	@Resource
	private RecordReplyMapper replyMapper;
	
	@Override
	public ServerResponse getRecordTopicListByTaskId(String taskId) {
		List<RecordTopic> res = recordTopicMapper.queryByTaskId(taskId);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse save(RecordTopic recordTopic) {
		recordTopicMapper.insertSelective(recordTopic);
		return ServerResponse.createBySuccessMessage("操作成功");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServerResponse deleteById(Integer id) {
		recordTopicMapper.deleteByPrimaryKey(id);
		replyMapper.deleteByTopicId(id);
		return ServerResponse.createBySuccessMessage("删除记录成功");
	}

}
