package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.crsri.mes.common.constant.DingTalkApproveConstant;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ApproveMapper;
import com.crsri.mes.dao.ProduceComponentInspectionApproveMapper;
import com.crsri.mes.dao.ProduceComponentProcessMapper;
import com.crsri.mes.dao.ProduceComponentProduceApproveMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProduceComponentInspectionApprove;
import com.crsri.mes.entity.ProduceComponentProcess;
import com.crsri.mes.entity.ProduceComponentProduceApprove;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.service.ProduceComponentProcessService;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProduceComponentInspectionApproveVO;
import com.crsri.mes.vo.ProduceComponentProduceApproveVO;
import com.crsri.mes.vo.ProduceComponentProduceVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;

/**
 * 
 * @author 2011102394
 *
 */
@Service
public class ProduceComponentProcessServiceImpl implements ProduceComponentProcessService {

	private static final Logger log = LoggerFactory.getLogger(ProduceComponentProcessServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProducePartsProcessMapper partsProcessMapper;

	@Resource
	private ProduceComponentProcessMapper componentProcessMapper;

	@Resource
	private ProduceComponentProduceApproveMapper componentProduceApproveMapper;

	@Resource
	private ProduceComponentInspectionApproveMapper inspectionProduceApproveMapper;

	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse produceComponentProduce(ProduceComponentProduceVO vo) {

		ProduceComponentProcess componentProcess = new ProduceComponentProcess();
		BeanUtils.copyProperties(vo, componentProcess);
		ProduceComponentProcess res = componentProcessMapper.selectByPrimaryKey(vo.getId());
		if (res != null) {
			return ServerResponse.createByFailMessage("该组件已经装配完成，请勿重复装配提交");
		}
		try {
			componentProcess.setProduceTime(new Date());
			componentProcessMapper.insertSelective(componentProcess);
			String[] codes = vo.getPartsCode().split(",");
			List<ProducePartsProcess> parts = partsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : parts) {
				item.setUsed(1);
				partsProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccessMessage("组件装配成功！");
		} catch (Exception e) {
			log.error("组件装配信息保存失败");
			return ServerResponse.createByFailMessage("组件装配信息保存失败");
		}

	}

	@Override
	public ServerResponse componentProduceApprove(ProduceComponentProduceApproveVO vo) {
		// 1、检查是否是重复提交的装配审批
		ServerResponse checkRes = componentProduceApproveCheck(vo);
		if (!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、装配审批实例，发起审批
		ApproveInstanceVO approveInstanceVO = createProduceApproveInstance(vo);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		vo.setId(processInstanceId);
		// 3、保存审批记录，并更新相应的生产组件的记录
		return updateProduceComponentApproveRecord(vo);
	}

	/**
	 * 保存生产组件装配审批的审批记录
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse updateProduceComponentApproveRecord(ProduceComponentProduceApproveVO vo) {
		vo.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		vo.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		ProduceComponentProduceApprove entity = new ProduceComponentProduceApprove();
		BeanUtils.copyProperties(vo, entity);
		try {
			componentProduceApproveMapper.insertSelective(entity);
			return updateProduceComponentProcessAfterProduce(vo);
		} catch (Exception e) {
			log.error("组件装配审批实例发送成功，审批记录保存失败");
			return ServerResponse.createByFailMessage("组件装配审批实例发送成功，审批记录保存失败");
		}
	}

	/**
	 * 装配审批完成后更新生产组件的流程记录
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse updateProduceComponentProcessAfterProduce(ProduceComponentProduceApproveVO vo) {
		String[] codes = vo.getCodes().split(",");
		List<ProduceComponentProcess> componentProcesses = componentProcessMapper.queryByIds(codes);
		try {
			for (ProduceComponentProcess item : componentProcesses) {
				item.setProduceApproveId(vo.getId());
				item.setProduceApproveStartTime(new Date());
				item.setProduceApproveStatus(vo.getApproveStatus());
				item.setProduceApproveResult(vo.getApproveResult());
				componentProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("组件装配审批记录保存成功，更新组件流程记录失败");
			return ServerResponse.createByFailMessage("组件装配审批记录保存成功，更新组件流程记录失败");
		}
	}

	/**
	 * 装配生产组件装配审批的审批对象
	 * 
	 * @param vo
	 * @return
	 */
	private ApproveInstanceVO createProduceApproveInstance(ProduceComponentProduceApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		approve.setAgentId(DingTalkConstant.AGENTID);
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_COMPONENT_PRODUCE_PROCESS_CODE);
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_COMPONENT_PRODUCE_PROCESS_CODE);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(vo.getDeptId());
		approve.setOriginatorUserId(vo.getUserId());
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue(vo.getName());
		list.add(name);
		FormComponentValueVo number = new FormComponentValueVo();
		number.setName("数量");
		number.setValue(vo.getNumber().toString());
		list.add(number);
		FormComponentValueVo status = new FormComponentValueVo();
		status.setName("状态");
		status.setValue("合格");
		list.add(status);
		FormComponentValueVo operator = new FormComponentValueVo();
		operator.setName("装配人员");
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
	 * 检查是否有重复提交的审批
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse componentProduceApproveCheck(ProduceComponentProduceApproveVO vo) {
		String[] codes = vo.getCodes().split(",");
		List<String> ids = componentProcessMapper.componentProduceApproveCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("生产组件装配审批失败，组件重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleComponentProduceApproveCallBack(String processInstanceId, Integer status, Integer result) {
		ProduceComponentProduceApprove approve = componentProduceApproveMapper.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("生产组件装配审批不存在：审批id=" + processInstanceId);
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		componentProduceApproveMapper.updateByPrimaryKeySelective(approve);
		List<ProduceComponentProcess> processes = componentProcessMapper.queryByIds(approve.getCodes().split(","));
		try {
			for (ProduceComponentProcess item : processes) {
				item.setProduceApproveId(processInstanceId);
				item.setProduceApproveStatus(status);
				item.setProduceApproveResult(result);
				item.setProduceApproveStopTime(new Date());
				componentProcessMapper.updateByPrimaryKeySelective(item);
			}
		} catch (Exception e) {
			log.error("更新组件流程记录失败，审批id:" + processInstanceId);
		}
	}

	@Override
	public ServerResponse queryComponentsUnProduceApprove() {
		List<Map<String, Object>> res = componentProcessMapper.queryComponentsUnProduceApprove();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryComponentsProduceUnInspection() {
		List<Map<String, Object>> res = componentProcessMapper.queryComponentsProduceUnInspection();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse componentInspectionApprove(ProduceComponentInspectionApproveVO vo) {
		// 1、检查是否是重复提交的检验审批
		ServerResponse checkRes = componentInspectionApproveCheck(vo);
		if (!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、装配审批实例，发起审批
		ApproveInstanceVO approveInstanceVO = createInspectionApproveInstance(vo);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		vo.setId(processInstanceId);
		// 3、保存审批记录，并更新相应的生产组件的记录
		return updateInspectionComponentApproveRecord(vo);
	}

	/**
	 * 检查是否是重复提交的检验审批
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse componentInspectionApproveCheck(ProduceComponentInspectionApproveVO vo) {
		String[] codes = vo.getCodes().split(",");
		List<String> ids = componentProcessMapper.componentInspectionApproveCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("生产组件检验审批失败，组件重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	/**
	 * 装配组件检验审批的审批实例
	 * 
	 * @param vo
	 * @return
	 */
	private ApproveInstanceVO createInspectionApproveInstance(ProduceComponentInspectionApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		approve.setAgentId(DingTalkConstant.AGENTID);
		Approve approve2 = approveMapper
				.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_COMPONENT_INSPECTION_PROCESS_CODE);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(vo.getDeptId());
		approve.setOriginatorUserId(vo.getUserId());
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_COMPONENT_INSPECTION_PROCESS_CODE);
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue(vo.getComponentName());
		list.add(name);
		FormComponentValueVo count = new FormComponentValueVo();
		count.setName("数量");
		count.setValue(String.valueOf(vo.getNumber()));
		list.add(count);
		FormComponentValueVo status = new FormComponentValueVo();
		status.setName("状态");
		if (vo.getStatus() == 0) {
			status.setValue("合格");
		} else {
			status.setValue("不合格");
		}
		list.add(status);
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
	 * 保存组件检验的审批记录，并更新相应的生产组件的记录
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse updateInspectionComponentApproveRecord(ProduceComponentInspectionApproveVO vo) {
		vo.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		vo.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		ProduceComponentInspectionApprove entity = new ProduceComponentInspectionApprove();
		BeanUtils.copyProperties(vo, entity);
		try {
			inspectionProduceApproveMapper.insertSelective(entity);
			return updateInspectionComponentProcessAfterProduce(vo);
		} catch (Exception e) {
			log.error("组件检验审批实例发送成功，审批记录保存失败");
			return ServerResponse.createByFailMessage("组件检验审批实例发送成功，审批记录保存失败");
		}
	}

	private ServerResponse updateInspectionComponentProcessAfterProduce(ProduceComponentInspectionApproveVO vo) {
		String[] codes = vo.getCodes().split(",");
		List<ProduceComponentProcess> componentProcesses = componentProcessMapper.queryByIds(codes);
		try {
			for (ProduceComponentProcess item : componentProcesses) {
				item.setInspectionApproveId(vo.getId());
				item.setInspectionStatus(vo.getStatus());
				item.setInspectionApproveStartTime(new Date());
				item.setInspectionApproveStatus(vo.getApproveStatus());
				item.setInspectionOperator(vo.getOperator());
				item.setInspectionApproveResult(vo.getApproveResult());
				componentProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("组件检验审批记录保存成功，更新组件流程记录失败");
			return ServerResponse.createByFailMessage("组件检验审批记录保存成功，更新组件流程记录失败");
		}
	}

	@Override
	public void handleInspectionProduceApproveCallBack(String processInstanceId, Integer status, Integer result) {
		ProduceComponentInspectionApprove approve = inspectionProduceApproveMapper
				.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("生产组件检验审批不存在：审批id=" + processInstanceId);
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		inspectionProduceApproveMapper.updateByPrimaryKeySelective(approve);
		List<ProduceComponentProcess> processes = componentProcessMapper.queryByIds(approve.getCodes().split(","));
		try {
			for (ProduceComponentProcess item : processes) {
				item.setInspectionApproveId(processInstanceId);
				item.setInspectionApproveStatus(status);
				item.setInspectionApproveResult(result);
				item.setInspectionApproveStopTime(new Date());
				componentProcessMapper.updateByPrimaryKeySelective(item);
			}
		} catch (Exception e) {
			log.error("生产组件检验审批更新组件流程记录失败，审批id:" + processInstanceId);
		}

	}

	@Override
	public ServerResponse queryComponentsAfterInspection() {
		List<Map<String, Object>> res = componentProcessMapper.queryComponentsAfterInspection();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryComponentsInStock() {
		List<Map<String, Object>> res = componentProcessMapper.queryComponentsInStock();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryComponentsAfterInspectionUnused() {
		List<Map<String, Object>> res = componentProcessMapper.queryComponentsAfterInspectionUnused();
		return ServerResponse.createBySuccess(res);
	}

}
