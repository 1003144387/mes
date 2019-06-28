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
import com.crsri.mes.service.ProduceCompontInfoService;
import com.crsri.mes.vo.ProduceComponentVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author 2011102394
 * 生产组件基础信息相关的接口
 *
 */
@RestController
@RequestMapping("/api")
@Api(tags="生产组件基础信息相关的接口")
public class ProduceComponentInfoController {

	@Resource
	private ProduceCompontInfoService produceInfoCompontService;

	
	/**
	 * 获取指定条件的生产组件信息列表
	 * @param json
	 * @return
	 */
	@PostMapping("/produceComponentInfo/list")
	@ApiOperation("获取指定条件的生产组件信息列表")
	public ServerResponse queryProduceComponentInfoList(@RequestBody JSONObject json) {
		return produceInfoCompontService.queryProduceComponentInfoList(json);
	}
	
	/**
	 * 获取指定条件的生产组件信息列表
	 * @param json
	 * @return
	 */
	@GetMapping("/produceComponentInfo/list")
	@ApiOperation("获取指定条件的生产组件信息列表")
	public ServerResponse queryProduceComponentInfoListApp() {
		return produceInfoCompontService.queryProduceComponentInfoList(null);
	}
	
	/**
	 * 新增生产组件基本信息
	 * @param component
	 * @return
	 */
	@SystemControllerLog(description="新增生产组件基本信息")
	@PostMapping("/produceComponentInfo")
	@ApiOperation("新增生产组件基本信息")
	public ServerResponse addProduceComponentInfo(@RequestBody ProduceComponentVO component) {
		return produceInfoCompontService.addProduceComponentInfo(component);
	}
	
	/**
	 * 编辑生产组件基本信息
	 * @param component
	 * @return
	 */
	@SystemControllerLog(description="编辑生产组件基本信息")
	@PutMapping("/produceComponentInfo")
	@ApiOperation("编辑生产组件基本信息")
	public ServerResponse updateProduceComponentInfo(@RequestBody ProduceComponentVO component) {
		return produceInfoCompontService.updateProduceComponentInfo(component);
	}
	
	/**
	 * 根据id删除组件信息
	 * @param id
	 * @return
	 */
	@SystemControllerLog(description="删除生产组件信息")
	@DeleteMapping("/produceComponentInfo/{id}")
	@ApiOperation("根据id删除组件信息")
	public ServerResponse deleteProduceComponentInfo(@PathVariable String id) {
		return produceInfoCompontService.deleteProduceComponentInfo(id);
	}
	
}























