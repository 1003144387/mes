package com.crsri.mes.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.AutomationProjectTaskRecord;
import com.crsri.mes.service.AutomationProjectTaskRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * 〈一句话功能简述〉<br>
 * 〈自动化项目任务工作日志相关的controller〉
 *
 * @author zcj
 * @date 2018/11/30 16:13
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
@Api(tags="自动化项目任务工作日志相关的接口")
public class AutomationProjectTaskRecordController {

	@Resource
	private AutomationProjectTaskRecordService automationProjectTaskRecordService;

	/**
	 * 新增自动化项目任务现场工作日志
	 * 
	 * @param record
	 * @return
	 */
	@PostMapping("/automationProjectTaskRecord")
	@SystemControllerLog(description = "新增自动化项目任务现场工作日志")
	@ApiOperation("新增自动化项目任务现场工作日志")
	public ServerResponse saveRecord(@RequestBody AutomationProjectTaskRecord record) {
		return automationProjectTaskRecordService.saveRecord(record);
	}

	/**
	 * 删除自动化项目任务现场工作日志
	 * @param id
	 * @return
	 */
	@DeleteMapping("/automationProjectTaskRecord/{id}")
	@SystemControllerLog(description = "删除自动化项目任务现场工作日志")
	@RequiresRoles("系统管理员")
	@RequiresPermissions("automationProjectTaskRocord:delete")
	@ApiOperation("删除自动化项目任务现场工作日志")
	public ServerResponse deleteRecord(@PathVariable("id") Integer id,HttpServletRequest request) {
		return automationProjectTaskRecordService.deleteRecord(id);
	}

	/**
	 * 更新自动化项目任务现场工作日志
	 * @param record
	 * @return
	 */
	@PutMapping("/automationProjectTaskRecord")
	@SystemControllerLog(description = "更新自动化项目任务现场工作日志")
	@ApiOperation("更新自动化项目任务现场工作日志")
	public ServerResponse updateRecord(@RequestBody AutomationProjectTaskRecord record) {
		return automationProjectTaskRecordService.updateRecord(record);
	}

	/**
	 * 根据id查找自动化项目任务现场工作日志
	 * @param id
	 * @return
	 */
	@GetMapping("/automationProjectTaskRecord/{id}")
	@ApiOperation("根据id查找自动化项目任务现场工作日志")
	public ServerResponse getRecord(@PathVariable("id") Integer id) {
		return automationProjectTaskRecordService.getRecord(id);
	}

	/**
	 * 根据任务id查找自动化项目任务现场工作日志列表
	 * @param taskId
	 * @return
	 */
	@GetMapping("/automationProjectTaskRecord/list/{taskId}")
	public ServerResponse getRecordListByTaskId(@PathVariable("taskId") String taskId) {
		return automationProjectTaskRecordService.getRecordListByTaskId(taskId);
	}
}
