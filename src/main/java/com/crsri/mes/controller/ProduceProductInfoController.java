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
import com.crsri.mes.service.ProduceProductInfoService;
import com.crsri.mes.vo.ProduceProductVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 生产产品基本信息相关的接口
 * @author 2011102394
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产产品基本信息相关的接口")
public class ProduceProductInfoController {

	
	@Resource 
	private ProduceProductInfoService infoServive;
	
	
	/**
	 * 获取生产产品基本信息分类
	 * @return
	 */
	@GetMapping("/produce/productInfo/list")
	@ApiOperation("获取生产产品基本信息分类")
	public ServerResponse queryProduceProductInfoList() {
		return infoServive.queryProduceProductInfoList();
	}
	
	/**
	 * 获取指定条件的生产产品信息列表
	 * @param json
	 * @return
	 */
	@PostMapping("/produceProductInfo/list")
	@ApiOperation("获取指定条件的生产产品信息列表")
	public ServerResponse queryProduceProductInfoList(@RequestBody JSONObject json) {
		return infoServive.queryProduceProductInfoList(json);
	}
	
	/**
	 * 新增生产产品基本信息
	 * @param product
	 * @return
	 */
	@SystemControllerLog(description="新增生产产品基本信息")
	@PostMapping("/produceProductInfo")
	@ApiOperation("新增生产产品基本信息")
	public ServerResponse addProduceProductInfo(@RequestBody ProduceProductVO product) {
		return infoServive.addProduceProductInfo(product);
	}
	
	/**
	 * 编辑生产产品基本信息
	 * @param product
	 * @return
	 */
	@SystemControllerLog(description="编辑生产产品基本信息")
	@PutMapping("/produceProductInfo")
	@ApiOperation("编辑生产产品基本信息")
	public ServerResponse updateProduceProductInfo(@RequestBody ProduceProductVO product) {
		return infoServive.updateProduceProductInfo(product);
	}
	
	/**
	 * 根据id删除产品信息
	 * @param id
	 * @return
	 */
	@SystemControllerLog(description="删除生产产品信息")
	@DeleteMapping("/produceProductInfo/{id}")
	@ApiOperation("根据id删除产品信息")
	public ServerResponse deleteProduceProductInfo(@PathVariable String id) {
		return infoServive.deleteProduceProductInfo(id);
	}
}
