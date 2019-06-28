package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProduceStockOutApproveVO;

/**
 * @author 2011102394
 *
 */
public interface ProduceStockOutService {

	/**
	 * 新增生产物品出库审批
	 * 
	 * @param produceStockOutApproveVO
	 * @return
	 */
	ServerResponse save(ProduceStockOutApproveVO produceStockOutApproveVO);

	/**
	 * 处理生产物品出库审批的结果回调
	 * 
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

	/**
	 * 按条件查询生产物品出库记录
	 * @param json
	 * @return
	 */
	ServerResponse getStockOutHistory(JSONObject json);

}
