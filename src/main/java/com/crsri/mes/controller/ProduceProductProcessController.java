package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProduceProductProcessService;
import com.crsri.mes.vo.ProduceProductInspectionApproveVO;
import com.crsri.mes.vo.ProduceProductProduceApproveVO;
import com.crsri.mes.vo.ProduceProductProduceVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产产品流程操作相关的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产产品流程操作相关的接口")
public class ProduceProductProcessController {

	@Resource
	private ProduceProductProcessService processService;
	
	/**
	 * 生产产品装配
	 * @param produceVO
	 * @return
	 */
	@PostMapping("/produce/product/produce")
	@SystemControllerLog(description="生产产品装配")
	@ApiOperation("生产产品装配")
	public ServerResponse productProduce(ProduceProductProduceVO produceVO) {
		return processService.productProduce(produceVO);
	}
	
	/**
	 * 获取装配完成待审批的产品列表
	 */
	@GetMapping("/produce/product/waittingProduceApprove/list")
	@ApiOperation("获取装配完成待审批的产品列表")
	public ServerResponse queryWaittingProduceApproveList() {
		return processService.queryWaittingProduceApproveList();
	}
	
	/**
	 * 生产产品装配审批
	 * @param approveVO
	 * @return
	 */
	@PostMapping("/produce/product/produce/approve")
	@SystemControllerLog(description="生产产品装配审批")
	@ApiOperation("生产产品装配审批")
	public ServerResponse productProduceApprove(ProduceProductProduceApproveVO approveVO) {
		return processService.productProduceApprove(approveVO);
	}
	
	/**
	 * 获取生产产品待检验的产品列表
	 */
	@GetMapping("/produce/product/waittingInspectionApprove/list")
	@ApiOperation("获取生产产品待检验的产品列表")
	public ServerResponse queryWaittingInspectionApproveList() {
		return processService.queryWaittingInspectionApproveList();
	}
	
	/**
	 * 生产产品检验审批
	 * @param approveVO
	 * @return
	 */
	@PostMapping("/produce/product/inspection/approve")
	@ApiOperation("生产产品检验审批")
	@SystemControllerLog(description="生产产品检验审批")
	public ServerResponse productInspectionApprove(ProduceProductInspectionApproveVO approveVO) {
		return processService.productInspectionApprove(approveVO);
	}
	
	/**
	 * 获取生产产品待入库的产品列表
	 */
	@GetMapping("/produce/product/waittingStockInApprove/list")
	@ApiOperation("获取生产产品待入库的产品列表")
	public ServerResponse queryWaittingStockInApproveList() {
		return processService.queryWaittingStockInApproveList();
	}
	
	/**
	 * 获取生产产品待出库的产品列表
	 */
	@GetMapping("/produce/product/waittingStockOutApprove/list")
	@ApiOperation("获取生产产品待出库的产品列表")
	public ServerResponse queryWaittingStockOutApproveList() {
		return processService.queryWaittingStockOutApproveList();
	}
}














