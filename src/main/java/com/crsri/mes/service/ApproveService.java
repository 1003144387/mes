package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.Approve;

/**
 * @author 2011102394
 * 审批管理的service接口
 */
public interface ApproveService {

	/**
	 * 获取审批列表
	 * @return
	 */
	ServerResponse getApproveList();

	/**
	 * 更新审批信息
	 * @param approve
	 * @return
	 */
	ServerResponse updateApprove(Approve approve);
	
	

}
