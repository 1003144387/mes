package com.crsri.mes.service;

/**
 * 
 * @author 2011102394
 *
 */
public interface ProduceProductShipService {

	void handleInspectionApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

}
