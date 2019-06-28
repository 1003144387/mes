package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.AutomationProjectTask;
import com.crsri.mes.entity.RepairTask;
import com.crsri.mes.service.AutomationProjectTaskService;
import com.crsri.mes.service.CustomerTaskService;
import com.crsri.mes.service.RepairTaskService;
import com.crsri.mes.vo.CustomerTaskVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 公开访问的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/public/api")
@Api(tags="公开访问的接口")
public class PublicController {

	@Resource
	private AutomationProjectTaskService automationProjectTaskService;
	

	@Resource
	private RepairTaskService repairTaskService;

	/**
	 * 创建维修单
	 * @param repairTask
	 * @return
	 */
	@PostMapping("/repairOrder")
	@ApiOperation("创建维修单")
    public ServerResponse save(@RequestBody RepairTask repairTask){
        return repairTaskService.save(repairTask);
    }
	
	@Resource
	private CustomerTaskService customerTaskService;
	
	/**
	 * 新增售后任务
	 * @param task
	 * @return
	 */
	@PostMapping("/customerTask")
	@ApiOperation("新增售后任务")
	private ServerResponse save(@RequestBody CustomerTaskVO vo) {
		return customerTaskService.save(vo);
	}
	
	/**
	 * 客户提交对自动化项目解决方案的意见
	 * 
	 * @param automationProjectTask
	 * @return
	 */
	@PutMapping("/automationProjectTask/solution")
	@ApiOperation("客户提交对自动化项目解决方案的意见")
	public ServerResponse customerConfirmation(@RequestBody AutomationProjectTask automationProjectTask) {
		return automationProjectTaskService.customerConfirmation(automationProjectTask);
	}

	/**
	 * 公开访问获取自动化项目解决方案
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/automationProjectTask/solution")
	@ApiOperation("公开访问获取自动化项目解决方案")
	public ServerResponse getAutomationProjectTaskSolution(@RequestBody JSONObject json) {
		String id = json.getString("id");
		return automationProjectTaskService.getAutomationProjectTaskSolution(id);
	}
}
