package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.RecordReply;
import com.crsri.mes.service.RecordReplyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 记录回复相关的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="记录回复相关的接口")
public class RecordReplyController {

	@Resource
	private RecordReplyService replyService;
	
	
	/**
	 * 新增回复
	 * @param reply
	 * @return
	 */
	@PostMapping("/recordReply")
	@ApiOperation("新增回复")
	@SystemControllerLog(description="新增回复")
	public ServerResponse save(@RequestBody RecordReply reply) {
		return replyService.save(reply);
	}
	
	/**
	 * 根据主题记录的id获取回复列表
	 * @param topicId
	 * @return
	 */
	@GetMapping("/recordReply/list/{topicId}")
	@ApiOperation("根据主题记录的id获取回复列表")
	public ServerResponse getRecordReplyListByTopicId(@PathVariable("topicId")Integer topicId) {
		return replyService.getRecordReplyListByTopicId(topicId);
	}
}


















