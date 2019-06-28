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
import com.crsri.mes.service.ProduceStockInService;
import com.crsri.mes.vo.ProduceStockInApproveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产入库相关的接口
 * 
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产入库相关的接口")
public class ProduceStockInController {

	@Resource
	private ProduceStockInService produceStockInService;

	@ApiOperation("生产物品入库")
	@SystemControllerLog(description="生产物品入库")
	@PostMapping("/produce/stockIn/add")
	public ServerResponse save(ProduceStockInApproveVO produceStockInApproveVO) {
		return produceStockInService.save(produceStockInApproveVO);
	}

	/**
	 * 获取生产部件库存
	 * 
	 * @return
	 */
	@GetMapping("/stock/parts/list")
	@ApiOperation("获取生产部件库存")
	public ServerResponse getPartsStockStatus() {
		return produceStockInService.getPartsStock();
	}

	/**
	 * 获取生产组件库存
	 * 
	 * @return
	 */
	@GetMapping("/stock/component/list")
	@ApiOperation("获取生产组件库存")
	public ServerResponse getComponentStockStatus() {
		return produceStockInService.getComponentStock();
	}

	/**
	 * 获取生产产品库存
	 * 
	 * @return
	 */
	@GetMapping("/stock/product/list")
	@ApiOperation("获取生产产品库存")
	public ServerResponse getProductStockStatus() {
		return produceStockInService.getProductStock();
	}

	/**
	 * 按条件查询生产部件出库记录
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/stock/in/list")
	@ApiOperation("按条件查询生产部件出库记录")
	public ServerResponse getStockInHistory(@RequestBody JSONObject json) {

		return produceStockInService.getStockInHistory(json);
	}

	/**
	 * 获取生产部件、组件、产品库存
	 * @return
	 */
	@GetMapping("/stock/in/status")
	@ApiOperation("获取生产部件、组件、产品库存")
	public ServerResponse stockStatus() {
		return produceStockInService.stockStatus();
	}
}
