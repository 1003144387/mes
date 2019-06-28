package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.log.annontation.SystemServiceLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.AutomationProjectTask;
import com.crsri.mes.service.AutomationProjectTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 〈一句话功能简述〉<br>
 * 〈自动化项目任务相关的controller〉
 *
 * @author zcj
 * @date 2018/11/26 16:34
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@Api(tags="自动化任务相关的接口")
public class AutomationProjectTaskController {

	@Resource
	private AutomationProjectTaskService automationProjectTaskService;

	/**
	 * 创建自动化项目任务
	 * 
	 * @param automationProjectTask
	 * @return
	 */
	@ApiOperation("创建自动化项目任务")
	@PostMapping("/automationProjectTask")
	@SystemControllerLog(description = "创建自动化项目任务")
	public ServerResponse save(@RequestBody AutomationProjectTask automationProjectTask) {
		return automationProjectTaskService.save(automationProjectTask);
	}

	/**
	 * 获取指定条件的自动化项目任务列表
	 * @param json
	 * @return
	 */
	@ApiOperation("获取指定条件的自动化项目任务列表")
	@PostMapping("/automationProjectTask/list")
	public ServerResponse getAutomationProjectTaskList(@RequestBody JSONObject json) {
		return automationProjectTaskService.getAutomationProjectTaskList(json);
	}

	/**
	 * 获取指定条件自动化项目任务统计数据
	 * @param json
	 * @return
	 */
	@PostMapping("/automationProjectTask/count")
	@ApiOperation("获取指定条件自动化项目任务统计数据")
	public ServerResponse getAutomationProjectTaskCount(@RequestBody JSONObject json) {
		return automationProjectTaskService.getAutomationProjectTaskCount(json);
	}

	/**
	 * 删除指定id的自动化项目任务
	 * @param id
	 * @return
	 */
	@SystemServiceLog(description = "删除指定id的自动化项目任务")
	@DeleteMapping("/automationProjectTask/{id}")
	@RequiresRoles("系统管理员")
	@ApiOperation("删除指定id的自动化项目任务")
	public ServerResponse deleteAutomationProjectTaskById(@PathVariable("id") @ApiParam("自动化任务id")String id) {
		return automationProjectTaskService.deleteAutomationProjectTaskById(id);
	}
	
	/**
	 * 更新自动化项目任务
	 * @param task
	 * @return
	 */
	@SystemControllerLog(description = "更新自动化项目任务")
	@PutMapping("/automationProjectTask")
	@ApiOperation("更新自动化项目任务")
	public ServerResponse updateAutomationProjectTask(@RequestBody AutomationProjectTask task) {
		return automationProjectTaskService.updateAutomationProjectTask(task);
	}


	/**
	 * 根据id获取自动化项目任务信息
	 * @param id
	 * @return
	 */
	@GetMapping("/automationProjectTask/{id}")
	@ApiOperation("根据id获取自动化项目任务信息")
	public ServerResponse getAutomationProjectTaskById(@PathVariable("id") @ApiParam("自动化项目id")String id) {
		return automationProjectTaskService.getAutomationProjectTaskById(id);
	}

	/**
	 * 按月统计自动化项目任务的完成情况
	 * @param json
	 * @return
	 */
	@PostMapping("/automationProjectTask/count/month")
	@ApiOperation("按月统计自动化项目任务的完成情况")
	public ServerResponse getAutomationProjectTaskCountGroupByMonth(@RequestBody JSONObject json) {
		return automationProjectTaskService.getAutomationProjectTaskCountGroupByMonth(json);
	}

}
