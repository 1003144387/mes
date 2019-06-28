package com.crsri.mes.controller;

import javax.annotation.Resource;

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
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.CustomerTask;
import com.crsri.mes.service.CustomerTaskService;
import com.crsri.mes.vo.CustomerTaskVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**
 * 售后任务相关的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="售后任务相关的接口")
public class CustomerTaskController {

	
	@Resource
	private CustomerTaskService customerTaskService;
	
	
	/**
	 * 按条件查询售后任务的列表
	 * @param json
	 * @return
	 */
    @PostMapping("/customerTask/list")
    @ApiOperation("按条件查询售后任务列表")
    public ServerResponse getCustomerTaskList(@RequestBody JSONObject json){
        return customerTaskService.getCustomerTaskList(json);
    }

    /**
     * 按条件查询售后任务数量
     * @param json
     * @return
     */
    @PostMapping("/customerTaskCount")
    @ApiOperation("按条件查询售后任务的数量")
    public ServerResponse getCustomerTaskCount(@RequestBody JSONObject json){
        return customerTaskService.getCustomerTaskCount(json);
    }
    
    /**
     * 更新售后任务
     * @param task
     * @return
     */
    @PutMapping("/customerTask") 
    @SystemControllerLog(description="更新售后任务")
    @ApiOperation("更新售后任务")
    public ServerResponse updateCustomerTask(@RequestBody @ApiParam("售后任务")CustomerTask task){
    	return customerTaskService.updateCustomerTask(task);
    }
    
    /**
     * 根据任务id获取售后任务
     * @param id
     * @return
     */
    @GetMapping("/customerTask/{id}")
    @ApiOperation("根据id获取售后任务")
    public ServerResponse getCustomerTask(@PathVariable("id") @ApiParam("售后任务的id")String id) {
    	return customerTaskService.getCustomerTask(id);
    }
    
    /**
     * 根据任务id删除售后任务
     */
    @DeleteMapping("/customerTask/{id}")
    @ApiOperation("根据任务id删除售后任务")
    public ServerResponse deleteCustomerTask(@PathVariable("id") @ApiParam("售后任务的id")String id) {
    	return customerTaskService.deleteCustomerTask(id);
    }
}















