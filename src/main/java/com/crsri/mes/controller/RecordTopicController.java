package com.crsri.mes.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.RecordTopic;
import com.crsri.mes.service.RecordTopicService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 记录主题操作相关的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="记录主题操作相关的接口")
public class RecordTopicController {

	@Resource
	private RecordTopicService recordTopicService;

	/**
	 * 获取记录主题列表
	 * @param taskId
	 * @return
	 */
	@ApiOperation("获取记录主题列表")
	@GetMapping("/recordTopic/list")
	private ServerResponse getRecordTopicListByTaskId(HttpServletRequest request) {
		String taskId = request.getParameter("taskId");
		return recordTopicService.getRecordTopicListByTaskId(taskId);
	}

	/**
	 * 发表记录主题
	 * @param recordTopic
	 * @return
	 */
	@PostMapping("/recordTopic")
	@ApiOperation("发表记录主题")
	private ServerResponse save(@RequestBody RecordTopic recordTopic) {
		return recordTopicService.save(recordTopic);
	}

	/**
	 * 删除指定id的记录主题
	 * @param id
	 * @return
	 */
	@DeleteMapping("/recordTopic/{id}")
	@ApiOperation("删除指定id的记录主题")
	private ServerResponse deleteById(@PathVariable("id") Integer id) {
		return recordTopicService.deleteById(id);
	}

}
