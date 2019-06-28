package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProducePartsFunctionInspectionApproveVO;

/**
 * 生产部件功能检测审批的service接口
 * @author 2011102394
 *
 */
public interface ProducePartsFunctionInspectionApproveService {

	/**
	 * 发起生产部件功能检测审批
	 * @param partsFunctionInspectionApproveVO
	 * @return
	 */
	ServerResponse save(ProducePartsFunctionInspectionApproveVO partsFunctionInspectionApproveVO);

	/**
	 * 
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

}
