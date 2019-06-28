package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProducePartsFunctionInspectionApproveService;
import com.crsri.mes.vo.ProducePartsFunctionInspectionApproveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产部件功能检测审批的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产部件功能检测审批的接口")
public class ProducePartsFunctionInspectionApproveController {

	
	@Resource
	private ProducePartsFunctionInspectionApproveService producePartsFunctionInspectionApproveService;
	
	@PostMapping("/producePartsFunctionInspectionApprove/add")
	@ApiOperation("生产部件功能检测")
	@SystemControllerLog(description="生产部件功能检测")
	public ServerResponse save(ProducePartsFunctionInspectionApproveVO partsFunctionInspectionApproveVO) {
		return producePartsFunctionInspectionApproveService.save(partsFunctionInspectionApproveVO);
	}
}
