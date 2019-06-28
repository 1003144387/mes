package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProducePartsSoftInstallApproveService;
import com.crsri.mes.vo.ProducePartsSoftInstallApproveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产部件软件灌装和设备校准审批的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产部件软件灌装和设备校准审批的接口")
public class ProducePartsSoftInstallApproveController {

	@Resource
	private ProducePartsSoftInstallApproveService producePartsSoftInstallApproveService;
	
	@PostMapping("/producePartsSoftInstallApprove/add")
	@ApiOperation("生产部件软件灌装和设备校准审批")
	@SystemControllerLog(description="生产部件软件灌装和设备校准审批")
	public ServerResponse save(ProducePartsSoftInstallApproveVO partsSoftInstallApproveVO) {
		return producePartsSoftInstallApproveService.save(partsSoftInstallApproveVO);
	}
}
