package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.RecordReplyMapper;
import com.crsri.mes.dao.RecordTopicMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.entity.RecordReply;
import com.crsri.mes.entity.RecordTopic;
import com.crsri.mes.service.RecordReplyService;
import com.crsri.mes.util.dingtalk.DingTalkMessageUtil;
import com.crsri.mes.vo.RecordReplyVO;
import com.crsri.mes.vo.RecordTopicVO;

@Service
public class RecordReplyServiceImpl implements RecordReplyService {

	@Resource
	private RecordReplyMapper replyMapper;

	@Resource
	private RecordTopicMapper topicMapper;
	
	@Resource
	private UserMapper userMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ServerResponse save(RecordReply reply) {
		replyMapper.insertSelective(reply);
		// 更新主题记录的更新时间
		Integer topicId = reply.getTopicId();
		RecordTopic topic = topicMapper.selectByPrimaryKey(topicId);
		topicMapper.updateByPrimaryKeySelective(topic);
		// 给发帖人发送钉钉通知
		sendDingTalkMessage(reply);
		
		
		return ServerResponse.createBySuccessMessage("发表回复成功");
	}

	private void sendDingTalkMessage(RecordReply reply) {
		RecordTopic topic = topicMapper.selectByPrimaryKey(reply.getTopicId());
		Integer taskType = topic.getTaskType();
		String type = null;
		String recordTopic = topic.getRecordTopic();
		if(recordTopic.length()>25) {
			recordTopic = recordTopic.substring(0, 24)+"……";
		}
		
		if(taskType==0) {
			//维修任务
			type = "维修";
		}else {
			//售后任务
			type = "售后";
		}
		Map<String, Object> map = new HashMap<>();
		Integer parentId = reply.getParentId();
		String username = null;
		
		if(parentId==0) {
			//对主题记录进行回复
			username = topic.getRecordOperator();
			
		}else {
			//对回复记录进行回复
			RecordReply reply2 = replyMapper.selectByPrimaryKey(parentId);
			username = reply2.getReplyOperator();
		}
		String receiver = userMapper.queryUserByName(username).getUserId();
		map.put("sender", reply.getReplyOperator());
		map.put("receiver", receiver);
		map.put("message", reply.getReplyOperator()+"对您的"+type+"记录进行了回复。\n"
				+ type+"任务编号："+ topic.getTaskId()+"\n"
				+type+"记录主题：“"+recordTopic+"”\n详情信息请登录平台查看");
		DingTalkMessageUtil.sendOAMessage(JSONObject.parseObject(JSONObject.toJSONString(map)));
	}

	@Override
	public ServerResponse getRecordReplyListByTopicId(Integer topicId) {
		List<RecordReply> replies = replyMapper.queryByTopicId(topicId);
		List<RecordTopicVO> topicVOs = new ArrayList<>();
		for (RecordReply recordReply : replies) {
			RecordTopicVO topicVO = new RecordTopicVO();
			BeanUtils.copyProperties(recordReply, topicVO);
			List<RecordReplyVO> vos = new ArrayList<>();
			List<RecordReplyVO> recordReplyVOs = findChildReply(vos, recordReply);
			//对获取到的回复列表按发表时间进行逆序排序
			Collections.sort(recordReplyVOs,Comparator.comparing(RecordReplyVO::getUpdateTime).reversed());
			topicVO.setChildren(recordReplyVOs);
			topicVOs.add(topicVO);
		}

		return ServerResponse.createBySuccess(topicVOs);
	}

	
	/**
	 * 递归获取指定回复记录下的所有回复
	 * @param vos
	 * @param recordReply
	 * @return
	 */
	private List<RecordReplyVO> findChildReply(List<RecordReplyVO> vos,RecordReply recordReply) {
		List<RecordReplyVO> recordReplyVOs = replyMapper.queryByParentId(recordReply.getId());
		if(CollectionUtils.isNotEmpty(recordReplyVOs)) {
			recordReplyVOs.forEach(item->item.setReplyTarget(recordReply.getReplyOperator()));
			vos.addAll(recordReplyVOs);
		}
		for (RecordReplyVO recordReplyVO : recordReplyVOs) {
			RecordReply reply = new RecordReply();
			BeanUtils.copyProperties(recordReplyVO, reply);
			findChildReply(vos, reply);
		}
		return vos;
	}

}
