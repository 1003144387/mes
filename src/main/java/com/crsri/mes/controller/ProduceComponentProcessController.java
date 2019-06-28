package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.apache.catalina.Server;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProduceComponentProcessService;
import com.crsri.mes.vo.ProduceComponentInspectionApproveVO;
import com.crsri.mes.vo.ProduceComponentProduceApproveVO;
import com.crsri.mes.vo.ProduceComponentProduceVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产组件流程相关的接口
 * 
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产组件流程相关的接口")
public class ProduceComponentProcessController {

	@Resource
	private ProduceComponentProcessService service;

	/**
	 * 生产组件的装配
	 * 
	 * @return
	 */
	@PostMapping("/produceComponent/produce")
	@ApiOperation("生产组件的装配")
	@SystemControllerLog(description="装配组件")
	public ServerResponse produceComponentProduce(ProduceComponentProduceVO vo) {
		return service.produceComponentProduce(vo);
	}

	/**
	 * 获取装配完成未审批或再次审批的生产组件列表
	 */
	@GetMapping("/produceComponent/unProduceApproce/list")
	@ApiOperation("获取装配完成未审批或再次审批的生产组件列表")
	public ServerResponse queryComponentsUnProduceApprove() {
		return service.queryComponentsUnProduceApprove();
	}

	/**
	 * 生产组件装配审批
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping("/produceComponent/produceApprove")
	@ApiOperation("生产组件装配审批")
	@SystemControllerLog(description="生产组件装配审批")
	public ServerResponse componentProduceApprove(ProduceComponentProduceApproveVO vo) {
		return service.componentProduceApprove(vo);
	}

	/**
	 * 获取装配完成未检验的生产组件列表
	 */
	@GetMapping("/produceComponent/unInspection/list")
	@ApiOperation("获取装配完成未检验的生产组件列表")
	public ServerResponse queryComponentsProduceUnInspection() {
		return service.queryComponentsProduceUnInspection();
	}

	/**
	 * 生产组件检验审批
	 * 
	 * @param vo
	 * @return
	 */
	@PostMapping("/produceComponent/inspectionApprove")
	@SystemControllerLog(description="生产组件检验审批")
	@ApiOperation("生产组件检验审批")
	public ServerResponse componentInspectionApprove(ProduceComponentInspectionApproveVO vo) {
		return service.componentInspectionApprove(vo);
	}

	/**
	 * 获取检验合格未入库的组件列表
	 */
	@GetMapping("/produceComponent/afterInspection/list")
	@ApiOperation("获取检验合格未入库的组件列表")
	public ServerResponse queryComponentsAfterInspection() {
		return service.queryComponentsAfterInspection();
	}
	
	/**
	 * 获取在库存中的组件列表
	 */
	@GetMapping("/produceComponent/stockIn/list")
	@ApiOperation("获取在库存中的组件列表")
	public ServerResponse queryComponentsInStock() {
		return service.queryComponentsInStock();
	}
	
	/**
	 * 获取检验合格未被使用的组件列表
	 */
	@GetMapping("/produceComponent/afterInspectionUnuse/list")
	@ApiOperation("获取检验合格未被使用的组件列表")
	public ServerResponse queryComponentsAfterInspectionUnused() {
		return service.queryComponentsAfterInspectionUnused();
	}
}
