package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProduceComponentInspectionApproveVO;
import com.crsri.mes.vo.ProduceComponentProduceApproveVO;
import com.crsri.mes.vo.ProduceComponentProduceVO;

/**
 * 生产组件流程相关的service接口
 * @author 2011102394
 *
 */
public interface ProduceComponentProcessService {

	/**
	 * 生产组件装配
	 * @param vo
	 * @return
	 */
	ServerResponse produceComponentProduce(ProduceComponentProduceVO vo);

	/**
	 * 生产组件装配审批
	 * @param vo
	 * @return
	 */
	ServerResponse componentProduceApprove(ProduceComponentProduceApproveVO vo);

	/**
	 * 处理组件装配审批的审批结果
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleComponentProduceApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

	/**
	 * 获取装配完成未审批的生产组件列表
	 * @return
	 */
	ServerResponse queryComponentsUnProduceApprove();

	/**
	 * 获取装配审批完成未检验的生产部件
	 * @return
	 */
	ServerResponse queryComponentsProduceUnInspection();

	/**
	 * 生产组件检验审批
	 * @param vo
	 * @return
	 */
	ServerResponse componentInspectionApprove(ProduceComponentInspectionApproveVO vo);

	/**
	 * 处理组件检验审批的审批结果
	 * @param processInstanceId
	 * @param approveStatus
	 * @param approveResult
	 */
	void handleInspectionProduceApproveCallBack(String processInstanceId, Integer approveStatus, Integer approveResult);

	/**
	 * 获取检验合格未入库的组件列表
	 * @return
	 */
	ServerResponse queryComponentsAfterInspection();

	/**
	 * 获取在库存中组件列表
	 * @return
	 */
	ServerResponse queryComponentsInStock();

	/**
	 * 获取检验合格未被使用的组件
	 * @return
	 */
	ServerResponse queryComponentsAfterInspectionUnused();

}
