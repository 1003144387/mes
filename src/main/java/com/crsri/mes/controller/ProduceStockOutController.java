package com.crsri.mes.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.log.annontation.SystemControllerLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.service.ProduceStockOutService;
import com.crsri.mes.vo.ProduceStockOutApproveVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产物品出库相关操作的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产物品出库相关操作的接口")
public class ProduceStockOutController {

	@Resource
	private ProduceStockOutService produceStockOutService;
	
	/**
	 * 新增生产物品出库审批
	 * @param produceStockOutApproveVO
	 * @return
	 */
	@PostMapping("/produce/stockOut/add")
	@SystemControllerLog(description="新增生产物品出库审批")
	@ApiOperation("新增生产物品出库审批")
	public ServerResponse save(ProduceStockOutApproveVO produceStockOutApproveVO) {
		return produceStockOutService.save(produceStockOutApproveVO);
	}
	
	/**
	 * 按条件查询生产物品出库记录
	 * 
	 * @param json
	 * @return
	 */
	@PostMapping("/stock/out/list")
	@ApiOperation("按条件查询生产物品出库记录")
	public ServerResponse getStockOutHistory(@RequestBody JSONObject json) {

		return produceStockOutService.getStockOutHistory(json);
	}
}
