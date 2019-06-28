package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.crsri.mes.common.constant.DingTalkApproveConstant;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ApproveMapper;
import com.crsri.mes.dao.ProducePartsFunctionInspectionApproveMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProducePartsFunctionInspectionApprove;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.entity.ProducePartsSoftInstallApprove;
import com.crsri.mes.service.ProducePartsFunctionInspectionApproveService;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProducePartsFunctionInspectionApproveVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;

@Service
public class ProducePartsFunctionInspectionApproveImpl implements ProducePartsFunctionInspectionApproveService {

	private static final Logger log = LoggerFactory.getLogger(ProducePartsSoftInstallApproveServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProducePartsProcessMapper producePartsProcessMapper;

	@Resource
	private ProducePartsFunctionInspectionApproveMapper producePartsFunctionInspectionApproveMapper;
	
	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse save(ProducePartsFunctionInspectionApproveVO partsFunctionInspectionApproveVO) {
		// 1、检测重复审批
		log.info(partsFunctionInspectionApproveVO.toString());
		ServerResponse checkRes = functionInspectionCheck(partsFunctionInspectionApproveVO);
		// 2、装配审批实例，发起审批
		ApproveInstanceVO approveInstanceVO = createApproveInstance(partsFunctionInspectionApproveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		// 3、保存审批记录，同时更新审批记录相关的部件流程信息
		partsFunctionInspectionApproveVO.setId(processInstanceId);
		return updateFunctionInspectionApproveRecord(partsFunctionInspectionApproveVO);
	}

	/**
	 * 保存审批记录，同时更新审批记录相关的部件流程信息
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse updateFunctionInspectionApproveRecord(ProducePartsFunctionInspectionApproveVO vo) {
		vo.setStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		vo.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		ProducePartsFunctionInspectionApprove entity = new ProducePartsFunctionInspectionApprove();
		BeanUtils.copyProperties(vo, entity);
		try {
			producePartsFunctionInspectionApproveMapper.insertSelective(entity);
			return updatePartsProcess(vo);
		} catch (Exception e) {
			log.error("生产部件功能检测审批实例发送成功，审批记录保存失败，异常信息：", e.getMessage());
			return ServerResponse.createByFailMessage("审批发送成功，审批记录保存失败");
		}

	}

	/**
	 * 更新生产部件流程信息
	 * 
	 * @param approve
	 * @return
	 */
	private ServerResponse updatePartsProcess(ProducePartsFunctionInspectionApproveVO approve) {
		try {
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsProcessList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsProcessList) {
				item.setFunctionInspectionApproveId(approve.getId());
				item.setFunctionInspectionApproveResult(approve.getApproveResult());
				item.setFunctionInspectionApproveStatus(approve.getApproveStatus());
				item.setFunctionInspectionOperator(approve.getOperator());
				item.setFunctionInspectionStartTime(new Date());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("生产部件功能检测审批记录保存成功，生产部件流程信息保存失败，异常信息", e.getMessage());
			return ServerResponse.createByFailMessage("生产部件功能检测审批记录保存成功，生产部件流程信息保存失败");
		}

	}

	/**
	 * 装配审批实例对象
	 * 
	 * @param vo
	 * @return
	 */
	private ApproveInstanceVO createApproveInstance(ProducePartsFunctionInspectionApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		approve.setAgentId(DingTalkConstant.AGENTID);
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_CODE);
		approve.setApprovers(approve2.getApproverList());
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_FUNCTION_INSPECTION_PROCESS_CODE);
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(vo.getDeptId());
		approve.setOriginatorUserId(vo.getUserId());
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo count = new FormComponentValueVo();
		count.setName("数量");
		count.setValue(String.valueOf(vo.getNumber()));
		list.add(count);
		FormComponentValueVo operator = new FormComponentValueVo();
		operator.setName("操作人员");
		operator.setValue(vo.getOperator());
		list.add(operator);
		if (StringUtils.isNotBlank(vo.getImage())) {
			list.add(ApproveUtil.imageUtil(vo.getImage(), host));
		}
		list.add(ApproveUtil.remarkUtil(vo.getRemark()));
		approve.setFormComponentValueVos(list);
		return approve;
	}

	/**
	 * 部件功能检测重复提交检测
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse functionInspectionCheck(ProducePartsFunctionInspectionApproveVO vo) {
		String[] codes = vo.getCodes().split(",");
		List<String> ids = producePartsProcessMapper.functionInspectionCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("生产部件部件功能检测审批失败，部件重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleApproveCallBack(String processInstanceId, Integer status, Integer result) {
		// 1、根据审批的id查找对应的审批记录，并更新
		ProducePartsFunctionInspectionApprove approve = producePartsFunctionInspectionApproveMapper
				.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("该生产部件功能检测审批不存在，审批id：" + processInstanceId);
			return;
		}
		try {
			approve.setApproveStatus(status);
			approve.setApproveResult(result);
			producePartsFunctionInspectionApproveMapper.updateByPrimaryKeySelective(approve);
			// 2、根据id更新对应物品的出库审批状态
			// 更新对应的部件信息
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsList) {
				item.setFunctionInspectionApproveId(processInstanceId);
				item.setFunctionInspectionSotpTime(new Date());
				item.setFunctionInspectionStatus(approve.getStatus());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
		} catch (Exception e) {
			log.error("生产部件功能检测回调处理失败，异常信息：", e.getMessage());
		}
	}

}
