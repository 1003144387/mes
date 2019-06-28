package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProducePartsApproveVO;

/**
 * 生产部件检验审批的service接口
 * @author 2011102394
 *
 */
public interface ProducePartsInspectionApproveService {

	/**
	 * 新增生产部件审批
	 * @param partsApprove
	 * @return
	 */
	ServerResponse save(ProducePartsApproveVO partsApproveVO);

	/**
	 * 
	 * @param processInstanceId 审批id
	 * @param approveStatus 审批实例的状态
	 * @param approveResult 审批的结果
	 */
	void handleApproveCallBack(String processInstanceId,Integer approveStatus,Integer approveResult);

	/**
	 * 获取待入库的部件的列表
	 * @return
	 */
	ServerResponse getProducePartsWaittingStockInList();

	/**
	 * 获取指定条件下生产部件来料检验的数量
	 * @param json 查询条件
	 * @return
	 */
	ServerResponse getPartsInspectionCount(JSONObject json);

	/**
	 * 获取符合条件的生产部件来料检验记录
	 * @param json 查询条件
	 * @return
	 */
	ServerResponse getPartInspectionList(JSONObject json);
}
