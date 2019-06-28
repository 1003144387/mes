package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProducePartsDefendService;
import com.crsri.mes.vo.ProducePartsDefendApproveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author 2011102394
 *
 */
@Api(tags = "三防审批相关的接口")
@RestController
@RequestMapping("/api")
public class ProducePartsDefendController {

	@Resource
	private ProducePartsDefendService partsDefendService;

	@ApiOperation("发起三防审批")
	@PostMapping("/producePartsDefend/add")
	@SystemControllerLog(description="发起三防审批")
	public ServerResponse save(@ApiParam("三防审批的数据VO对象") ProducePartsDefendApproveVO partsDefendApproveVO) {
		return partsDefendService.save(partsDefendApproveVO);
	}

}
