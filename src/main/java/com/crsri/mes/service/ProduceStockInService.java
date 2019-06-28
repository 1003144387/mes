package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProduceStockInApproveVO;

public interface ProduceStockInService {

	/**
	 * 创建生产物品入库审批
	 * @param produceStockInApproveVO
	 * @return
	 */
	ServerResponse save(ProduceStockInApproveVO produceStockInApproveVO);

	/**
	 * 处理审批结果的回调
	 * @param processInstanceId 审批id
	 * @param approveStatus 审批状态
	 * @param approveResult 审批结果
	 */
	void handleApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);


	/**
	 * 获取生产部件库存
	 * @return
	 */
	ServerResponse getPartsStock();

	/**
	 * 获取生产组件库存
	 * @return
	 */
	ServerResponse getComponentStock();

	/**
	 * 获取生产产品库存
	 * @return
	 */
	ServerResponse getProductStock();

	/**
	 * 按条件查询入库记录
	 * @param json 查询条件
	 * @return
	 */
	ServerResponse getStockInHistory(JSONObject json);

	/**
	 * 获取生产组件、部件、产品的实时库存
	 * @return
	 */
	ServerResponse stockStatus();

}
