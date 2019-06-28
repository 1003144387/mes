package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProduceProductVO;

/**
 * 生产产品基本信息相关的service接口
 * 
 * @author 2011102394
 *
 */
public interface ProduceProductInfoService {

	/**
	 * 获取生产产品基本信息列表
	 * 
	 * @return
	 */
	ServerResponse queryProduceProductInfoList();

	/**
	 * 获取指定条件的生产产品信息列表
	 * 
	 * @param json 查询条件
	 * @return
	 */
	ServerResponse queryProduceProductInfoList(JSONObject json);

	/**
	 * 新增生产产品基本信息
	 * @param product
	 * @return
	 */
	ServerResponse addProduceProductInfo(ProduceProductVO product);

	/**
	 * 编辑生产产品基本信息
	 * @param product
	 * @return
	 */
	ServerResponse updateProduceProductInfo(ProduceProductVO product);

	/**
	 * 根据id删除产品信息
	 * @param id
	 * @return
	 */
	ServerResponse deleteProduceProductInfo(String id);

}
