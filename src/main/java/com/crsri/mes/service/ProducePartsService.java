package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.ProduceParts;

/**
 * @author 2011102394
 *
 */
public interface ProducePartsService {

	/** 获取生产部件基础信息（id，name）的列表
	 * @return
	 */
	ServerResponse selectProducePartsListSimple();

	/**
	 * 获取在库存中的生产部件的列表
	 * @return
	 */
	ServerResponse queryProducePartsStockInList();

	/**
	 * 获取出库待三防处理的生产部件列表
	 * @return
	 */
	ServerResponse queryStockOutWaittingDefendPartsList();

	/**
	 * 获取已出库未进行软件灌装的生产部件的列表
	 * @return
	 */
	ServerResponse queryStockOutWaittingSoftInstallPartsList();

	/**
	 * 获取已出库未进行部件功能检测的部件列表
	 * @return
	 */
	ServerResponse queryStockOutWaittingFunctionInspcetionPartsList();

	/**
	 * 获取已出库未被使用的部件列表
	 * @return
	 */
	ServerResponse queryStockOutUnUsedPartsList();

	/**
	 * 按条件查询部件列表
	 * @param json
	 * @return
	 */
	ServerResponse getAllParts(JSONObject json);

	/**
	 * 更新生产部件信息
	 * @param parts
	 * @return
	 */
	ServerResponse updateProducePartsInfo(ProduceParts parts);

	/**
	 * 新增生产部件信息
	 * @param parts
	 * @return
	 */
	ServerResponse addProducePartsInfo(ProduceParts parts);

	/**
	 * 根据id删除部件信息
	 * @param id
	 * @return
	 */
	ServerResponse deleteProduceParts(String id);

}
