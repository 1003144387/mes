package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProducePartsInspectionApproveService;
import com.crsri.mes.vo.ProducePartsApproveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产部件检验审批的接口
 * 
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产部件检验审批的接口")
public class ProducePartsInspectionApproveController {

	@Resource
	private ProducePartsInspectionApproveService producePartsApproveService;

	/**
	 * 部件来料检验审批
	 * 
	 * @param partsApproveVO
	 * @return
	 */
	@PostMapping("/producePartsApprove")
	@ApiOperation("部件来料检验审批")
	@SystemControllerLog(description="部件来料检验审批")
	public ServerResponse save(ProducePartsApproveVO partsApproveVO) {
		return producePartsApproveService.save(partsApproveVO);
	}

	/**
	 * 获取待入库的部件的id和name
	 * 
	 * @return
	 */
	@GetMapping("/producePartsWaitStockIn/List")
	@ApiOperation("获取待入库的部件的id和name")
	public ServerResponse getProducePartsWaittingStockInList() {
		return producePartsApproveService.getProducePartsWaittingStockInList();
	}

	/**
	 * 按条件获取来料检验的统计数量
	 * @param json
	 * @return
	 */
	@PostMapping("/parts/inspection/count")
	@ApiOperation("按条件获取来料检验的统计数量")
	public ServerResponse getPartsInspectionCount(@RequestBody JSONObject json) {
		return producePartsApproveService.getPartsInspectionCount(json);
	}
	
	/**
	 * 获取检验审批列表
	 *
	 * @param json 查询条件
	 * @return 符合条件的查询结果
	 */
	@PostMapping("/parts/inspection/list/new")
	@ApiOperation("获取检验审批列表")
	public ServerResponse selectPartInspectionList(@RequestBody JSONObject json) {

		return producePartsApproveService.getPartInspectionList(json);
	}

}
