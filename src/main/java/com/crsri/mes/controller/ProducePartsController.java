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
import com.crsri.mes.entity.ProduceParts;
import com.crsri.mes.service.ProducePartsService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产部件相关的接口")
public class ProducePartsController {
	
	@Resource
	private ProducePartsService producePartsService;

	/**
	 * 获取生产部件基本信息列表
	 * @return
	 */
	@ApiOperation("获取生产部件基本信息表")
	@GetMapping("/produceParts/list/simple")
	public ServerResponse selectProducePartsListSimple() {
		return producePartsService.selectProducePartsListSimple();
	}
	
	/**
	 * 按条件查询生产部件列表
	 * @param json 查询条件
	 * @return
	 */
    @PostMapping("/produceParts/all")
    @ApiOperation("按条件查询生产部件列表")
    public ServerResponse getAllParts(@RequestBody JSONObject json){
        return producePartsService.getAllParts(json);
    }
    
    /**
     * 新增生产部件信息
     * @param parts
     * @return
     */
    @PostMapping("/produceParts")
    @SystemControllerLog(description="新增生产部件信息")
    @ApiOperation("新增生产部件信息")
    public ServerResponse addProducePartsInfo(@RequestBody ProduceParts parts) {
    	return producePartsService.addProducePartsInfo(parts);
    }
    /**
     * 更新生产部件信息
     * @param parts
     * @return
     */
    @PutMapping("/produceParts")
    @SystemControllerLog(description="更新生产部件信息")
    @ApiOperation("更新生产部件信息")
    public ServerResponse updateProducePartsInfo(@RequestBody ProduceParts parts) {
    	return producePartsService.updateProducePartsInfo(parts);
    }
    
    /**
     * 删除指定id的生产部件
     * @param id
     * @return
     */
    @DeleteMapping("/produceParts/{id}")
    @SystemControllerLog(description="删除指定id的生产部件")
    @ApiOperation("删除指定id的生产部件")
    public ServerResponse deleteProduceParts(@PathVariable("id")String id) {
    	return producePartsService.deleteProduceParts(id);
    }
	/**
	 * 获取在库存中的生产部件列表
	 * @return
	 */
	@GetMapping("/produceParts/stock/in/list")
	@ApiOperation("获取在库存中的生产部件列表")
	public ServerResponse queryProducePartsStockInList() {
		return producePartsService.queryProducePartsStockInList();
	}
	
	/**
	 * 获取已出库未经过部件三防的生产部件列表
	 */
	@GetMapping("/produceParts/stockOutWaittingDefend/list")
	@ApiOperation("获取已出库未经过部件三防的生产部件列表")
	public ServerResponse queryStockOutWaittingDefendPartsList() {
		return producePartsService.queryStockOutWaittingDefendPartsList();
	}
	
	/**
	 * 获取已出库未经过软件灌装和设备校准的部件
	 * @return
	 */
	@GetMapping("/produceParts/stockOutUnSoftInstallParts/list")
	@ApiOperation("获取已出库未经过软件灌装和设备校准的部件")
	public ServerResponse queryStockOutWaittingSoftInstallPartsList() {
		return producePartsService.queryStockOutWaittingSoftInstallPartsList();
	}
	
	/**
	 * 获取已出库未经过部件功能检测的部件列表
	 */
	@GetMapping("/produceParts/stockOutUnFunctionInspectionParts/list")
	@ApiOperation("获取已出库未经过部件功能检测的部件列表")
	public ServerResponse queryStockOutWaittingFunctionInspcetionPartsList() {
		return producePartsService.queryStockOutWaittingFunctionInspcetionPartsList();
	}
	
	/**
	 * 获取已出库未使用的部件列表
	 * @return
	 */
	@GetMapping("/produceParts/stockOutUnUsed/list")
	@ApiOperation("获取已出库未使用的部件列表")
	public ServerResponse queryStockOutUnUsedPartsList() {
		return producePartsService.queryStockOutUnUsedPartsList();
	}
}

















