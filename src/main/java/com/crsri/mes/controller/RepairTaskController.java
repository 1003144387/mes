package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.RepairTask;
import com.crsri.mes.service.RepairTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 维修任务操作相关的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="维修任务操作相关的接口")
public class RepairTaskController {

	@Resource
	private RepairTaskService repairTaskService;

	/**
	 * 获取指定条件下维修任务的统计信息
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/repairTask/count")
	@ApiOperation("获取指定条件下维修任务的统计信息")
	public ServerResponse getRepairOrderCount(@RequestBody JSONObject json) {
		return repairTaskService.getRepairOrderCount(json);
	}

	/**
	 * 获取维修任务列表
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/repairTask/list")
	@ApiOperation("获取维修任务列表")
	public ServerResponse getRepairTaskList(@RequestBody JSONObject json) {
		return repairTaskService.getRepairTaskList(json);
	}
	
	/**
	 * 更新维修任务
	 * @param repairTask
	 * @param request
	 * @return
	 */
	@SystemControllerLog(description = "更新维修任务")
    @PutMapping("/repairTask")
	@ApiOperation("更新维修任务")
    public ServerResponse updateRepairTask(@RequestBody RepairTask repairTask){
        return repairTaskService.updateRepairTask(repairTask);
    }
	/**
	 * 更新维修任务状态
	 * @param repairTask
	 * @return
	 */
	@SystemControllerLog(description = "更新维修任务状态")
	@PutMapping("/repairTask/status")
	@RequiresRoles(value={"系统管理员","开发人员"},logical=Logical.OR)
	@ApiOperation("更新维修任务状态")
	public ServerResponse closeOrRestartTask(@RequestBody RepairTask repairTask){
		return repairTaskService.updateRepairTask(repairTask);
	}
	
	 /**
     * 根据任务id删除维修任务
     */
    @DeleteMapping("/repairTask/{id}")
    @ApiOperation("根据任务id删除维修任务")
    public ServerResponse deleteRepairTask(@PathVariable("id") @ApiParam("维修任务的id")String id) {
    	return repairTaskService.deleteRepairTask(id);
    }

}
