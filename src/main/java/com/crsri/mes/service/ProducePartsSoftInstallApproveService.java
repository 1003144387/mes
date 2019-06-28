package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProducePartsSoftInstallApproveVO;

/**
 * 生产部件软件灌装和设备校准审批的service接口
 * @author 2011102394
 *
 */
public interface ProducePartsSoftInstallApproveService {

	/**
	 * 发起软件灌装和设备校准审批
	 * @param partsSoftInstallApproveVO
	 * @return
	 */
	ServerResponse save(ProducePartsSoftInstallApproveVO partsSoftInstallApproveVO);

	/**
	 * 处理软件灌装和设备校准审批的结果
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

}
