package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.service.ApproveService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 2011102394
 * 审批管理的接口
 */
@RestController
@Api(tags="审批管理的接口")
@RequestMapping("/api")
public class ApproveController {
	
	@Resource
	private ApproveService approveservice;

	@GetMapping("/approve/list")
	@ApiOperation("获取审批列表")
	public ServerResponse getApproveList() {
		return approveservice.getApproveList();
	}
	
	@PutMapping("/approve")
	@ApiOperation("修改审批信息")
	@SystemControllerLog(description="修改审批信息")
	public ServerResponse updateApprove(@RequestBody Approve approve) {
		return approveservice.updateApprove(approve);
	}
}














