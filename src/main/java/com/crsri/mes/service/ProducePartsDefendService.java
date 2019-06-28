package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProducePartsDefendApproveVO;

/**
 * 生产部件三防审批的service接口
 * 
 * @author 2011102394
 *
 */
public interface ProducePartsDefendService {

	/**
	 * 新增生产部件三防审批
	 * 
	 * @param partsDefendApproveVO
	 * @return
	 */
	ServerResponse save(ProducePartsDefendApproveVO partsDefendApproveVO);

	/**
	 * 处理生产部件三防审批的审批结果
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

}
