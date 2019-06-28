package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProduceProductInspectionApproveVO;
import com.crsri.mes.vo.ProduceProductProduceApproveVO;
import com.crsri.mes.vo.ProduceProductProduceVO;

/**
 * 生产产品流程相关的service接口
 * @author 2011102394
 *
 */
public interface ProduceProductProcessService {

	/**
	 * 生产产品装配
	 * @param produceVO
	 * @return
	 */
	ServerResponse productProduce(ProduceProductProduceVO produceVO);

	/**
	 * 生产产品装配审批
	 * @param approveVO
	 * @return
	 */
	ServerResponse productProduceApprove(ProduceProductProduceApproveVO approveVO);

	/**
	 * 获取装配完成待装配审批的产品列表
	 * @return
	 */
	ServerResponse queryWaittingProduceApproveList();
	
	/**
	 * 处理生产产品装配审批的结果
	 * @param processInstanceId 
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleProduceApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

	/**
	 * 获取待检验的生产产品列表
	 * @return
	 */
	ServerResponse queryWaittingInspectionApproveList();

	/**
	 * 生产产品检验审批
	 * @param approveVO
	 * @return
	 */
	ServerResponse productInspectionApprove(ProduceProductInspectionApproveVO approveVO);

	/**
	 * 处理生产产品检验审批的结果
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleInspectionApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

	/**
	 * 获取生产产品待入库列表
	 * @return
	 */
	ServerResponse queryWaittingStockInApproveList();

	/**
	 * 获取生产产品待出库列表
	 * @return
	 */
	ServerResponse queryWaittingStockOutApproveList();


}
