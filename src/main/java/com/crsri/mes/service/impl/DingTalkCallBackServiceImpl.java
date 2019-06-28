package com.crsri.mes.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Case;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkApproveConstant;
import com.crsri.mes.common.constant.DingTalkApproveConstant.callBackEventType;
import com.crsri.mes.service.DingTalkCallBackService;
import com.crsri.mes.service.ProduceComponentProcessService;
import com.crsri.mes.service.ProducePartsDefendService;
import com.crsri.mes.service.ProducePartsFunctionInspectionApproveService;
import com.crsri.mes.service.ProducePartsInspectionApproveService;
import com.crsri.mes.service.ProducePartsSoftInstallApproveService;
import com.crsri.mes.service.ProduceProductProcessService;
import com.crsri.mes.service.ProduceProductShipService;
import com.crsri.mes.service.ProduceStockInService;
import com.crsri.mes.service.ProduceStockOutService;
import com.crsri.mes.util.dingtalk.ApproveUtil;

@Service
public class DingTalkCallBackServiceImpl implements DingTalkCallBackService {

	@Resource
	private ProducePartsInspectionApproveService producePartsApproveService;

	@Resource
	private ProduceStockInService produceStockInService;

	@Resource
	private ProduceStockOutService produceStockOutService;
	
	@Resource
	private ProducePartsDefendService producePartsDefendService;

	@Resource
	private ProducePartsSoftInstallApproveService producePartsSoftInstallApproveService;
	
	@Resource
	private ProducePartsFunctionInspectionApproveService producePartsFunctionInspectionApproveService;
	
	@Resource
	private ProduceComponentProcessService produceComponentProcessService;
	
	@Resource
	private ProduceProductProcessService produceProductProcessService;
	
	@Resource
	private ProduceProductShipService produceProductShipService;
	
	@Override
	public void handleApproveCallBack(String processCode, JSONObject processBody) {

		// 获取审批id
		String processInstanceId = processBody.getString("processInstanceId");
		// 获取审批实例的状态
		String type = processBody.getString("type");
		// 审批实例的结果
		String result = null;
		if (processBody.containsKey("result")) {
			result = processBody.getString("result");
		}
		Map<String, Integer> res = ApproveUtil.getApproveResult(type, result);
		Integer approveStatus = res.get("status");
		Integer approveResult = res.get("result");
		switch (processCode) {
		// 生产部件来料检验
		case DingTalkApproveConstant.processCode.PRODUCE_PARTS_INSPECTION_CODE:
			producePartsApproveService.handleApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产物品入库审批
		case DingTalkApproveConstant.processCode.GOODS_STOCK_IN_PROCESS_CODE:
			produceStockInService.handleApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产物品出库审批
		case DingTalkApproveConstant.processCode.GOODS_STOCK_OUT_PROCESS_CODE:
			produceStockOutService.handleApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产部件三防审批
		case DingTalkApproveConstant.processCode.PRODUCE_PARTS_DEFEND_PROCESS_CODE:
			producePartsDefendService.handleApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产部件软件灌装和设备校准审批
		case DingTalkApproveConstant.processCode.PRODUCE_PARTS_SOFT_INSTALL_PROCESS_CODE:
			producePartsSoftInstallApproveService.handleApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产部件软件功能检测审批
		case DingTalkApproveConstant.processCode.PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_CODE:
			producePartsFunctionInspectionApproveService.handleApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产组件装配审批
		case DingTalkApproveConstant.processCode.PRODUCE_COMPONENT_PRODUCE_PROCESS_CODE:
			produceComponentProcessService.handleComponentProduceApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产组件检验审批
		case DingTalkApproveConstant.processCode.PRODUCE_COMPONENT_INSPECTION_PROCESS_CODE:
			produceComponentProcessService.handleInspectionProduceApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产产品装配审批
		case DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_PRODUCE_PROCESS_CODE:
			produceProductProcessService.handleProduceApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产产品检验审批
		case DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_INSPECTION_PROCESS_CODE:
			produceProductProcessService.handleInspectionApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		// 生产产品发货审批
		case DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_SHIP_PROCESS_CODE:
			produceProductShipService.handleInspectionApproveCallBack(processInstanceId, approveStatus, approveResult);
			break;
		default:
			break;
		}
	}

}
