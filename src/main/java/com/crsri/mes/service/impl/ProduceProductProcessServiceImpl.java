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
import com.crsri.mes.dao.ProduceProductInspectionApproveMapper;
import com.crsri.mes.dao.ProduceProductProcessMapper;
import com.crsri.mes.dao.ProduceProductProduceApproveMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProduceProductInspectionApprove;
import com.crsri.mes.entity.ProduceProductProcess;
import com.crsri.mes.entity.ProduceProductProduceApprove;
import com.crsri.mes.service.ProduceProductProcessService;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProduceProductInspectionApproveVO;
import com.crsri.mes.vo.ProduceProductProduceApproveVO;
import com.crsri.mes.vo.ProduceProductProduceVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;

/**
 * 生产产品流程相关的service接口的实现
 * 
 * @author 2011102394
 *
 */
@Service
public class ProduceProductProcessServiceImpl implements ProduceProductProcessService {

	private static final Logger log = LoggerFactory.getLogger(ProduceProductProcessServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProduceProductProcessMapper productProcessMapper;
	
	@Resource
	private ProduceProductProduceApproveMapper productProduceApproveMapper;
	
	@Resource
	private ProduceProductInspectionApproveMapper productInspectionApproveMapper;
	
	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse productProduce(ProduceProductProduceVO produceVO) {
		// 1、检查是否是重复装配提交
		ProduceProductProcess productProcess = productProcessMapper.selectByPrimaryKey(produceVO.getId());
		if (productProcess != null) {
			// 不是第一次装配，检查该产品的装配审批结果
			Integer approveResult = productProcess.getProduceApproveResult();
			if (approveResult != 2) {
				return ServerResponse.createByFailMessage("装配提交失败，重复装配");
			}
			BeanUtils.copyProperties(produceVO, productProcess);
			productProcess.setProduceTime(new Date());
			productProcessMapper.updateByPrimaryKeySelective(productProcess);
			return ServerResponse.createBySuccess();
		}
		// 2、第一次装配，保存产品装配记录
		productProcess = new ProduceProductProcess();
		BeanUtils.copyProperties(produceVO, productProcess);
		productProcess.setProduceTime(new Date());
		productProcessMapper.insertSelective(productProcess);
		return ServerResponse.createBySuccess();
	}

	@Override
	public ServerResponse queryWaittingProduceApproveList() {
		List<Map<String, Object>> res = productProcessMapper.queryWaittingProduceApproveList();
		return ServerResponse.createBySuccess(res);
	}
	
	@Override
	public ServerResponse productProduceApprove(ProduceProductProduceApproveVO approveVO) {
		// 1、检验是否是重复的审批
		ServerResponse checkRes = productProductApproveCheck(approveVO);
		if (!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、封装审批实例，发起审批
		ApproveInstanceVO approveInstanceVO = createProduceApproveInstance(approveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		approveVO.setId(processInstanceId);
		// 3、保存产品生产审批记录，并更新相关的产品流程记录
		return saveProduceApproveRecord(approveVO);
	}

	/**
	 * 保存产品生产审批记录
	 * @param approveVO
	 * @return
	 */
	private ServerResponse saveProduceApproveRecord(ProduceProductProduceApproveVO approveVO) {
		try {
			approveVO.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
			approveVO.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
			ProduceProductProduceApprove record = new ProduceProductProduceApprove();
			BeanUtils.copyProperties(approveVO, record);
			productProduceApproveMapper.insertSelective(record);
			//更新相关的产品流程记录
			return updateProductProcessAfterProduceApprove(approveVO);
		}catch (Exception e) {
			log.error("产品装配审批实例提交成功，审批记录保存失败："+e.getMessage());
			return ServerResponse.createByFailMessage("产品装配审批实例提交成功，审批记录保存失败");
		}
	}

	/**
	 * 产品装配审批提交后更新产品的流程信息
	 * @param approveVO
	 * @return
	 */
	private ServerResponse updateProductProcessAfterProduceApprove(ProduceProductProduceApproveVO approveVO) {
		String[] codes = approveVO.getCodes().split(",");
		List<ProduceProductProcess> processes = productProcessMapper.queryByIds(codes);
		try {
			for (ProduceProductProcess item : processes) {
				item.setProduceApproveId(approveVO.getId());
				item.setProduceApproveOperator(approveVO.getOperator());
				item.setProduceApproveStartTime(new Date());
				item.setProduceApproveStatus(approveVO.getApproveStatus());
				item.setProduceApproveResult(approveVO.getApproveResult());
				productProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		}catch (Exception e) {
			log.error("产品装配审批记录保存成功，更新产品流程信息失败"+e.getMessage());
			return ServerResponse.createByFailMessage("产品装配审批记录保存成功，更新产品流程信息失败");
		}
	}

	/**
	 * 装配产品生产审批实例对象
	 * 
	 * @param vo 产品生产审批的VO对象
	 * @return
	 */
	private ApproveInstanceVO createProduceApproveInstance(ProduceProductProduceApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		approve.setAgentId(DingTalkConstant.AGENTID);
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_PRODUCE_PROCESS_CODE);
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_PRODUCE_PROCESS_CODE);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(vo.getDeptId());
		approve.setOriginatorUserId(vo.getUserId());
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue(vo.getProductName());
		list.add(name);
		FormComponentValueVo count = new FormComponentValueVo();
		count.setName("数量");
		count.setValue(String.valueOf(vo.getNumber()));
		list.add(count);
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
	 * 检验是否是重复的生产审批
	 * 
	 * @param approveVO
	 * @return
	 */
	private ServerResponse productProductApproveCheck(ProduceProductProduceApproveVO approveVO) {
		String[] codes = approveVO.getCodes().split(",");
		List<String> ids = productProcessMapper.productProductApproveCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("产品装配审批失败，产品重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleProduceApproveCallBack(String processInstanceId, Integer status, Integer result) {
		ProduceProductProduceApprove approve = productProduceApproveMapper.selectByPrimaryKey(processInstanceId);
		if(approve == null) {
			log.info("产品装配审批不存在，审批ID："+processInstanceId);
			return;
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		try {
			productProduceApproveMapper.updateByPrimaryKey(approve);
			List<ProduceProductProcess> processes = productProcessMapper.queryByIds(approve.getCodes().split(","));
			for (ProduceProductProcess item : processes) {
				item.setProduceApproveId(processInstanceId);
				item.setProduceApproveResult(result);
				item.setProduceApproveStatus(status);
				item.setProduceApproveStopTime(new Date());
				productProcessMapper.updateByPrimaryKeySelective(item);
			}
		}catch (Exception e) {
			log.error("产品装配审批审批完成更新记录失败，异常信息："+e.getMessage());
		}
		
	}

	@Override
	public ServerResponse queryWaittingInspectionApproveList() {
		List<Map<String, Object>> res = productProcessMapper.queryWaittingInspectionApproveList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse productInspectionApprove(ProduceProductInspectionApproveVO approveVO) {
		// 1、检验是否是重复审批
		ServerResponse checkRes = productInspectionApproveCheck(approveVO);
		if(!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、装配审批实例对象，发起审批
		ApproveInstanceVO approveInstanceVO = createInspectionApproveInstance(approveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		approveVO.setId(processInstanceId);
		// 3、保存产品检验审批记录，更新对应的产品流程信息
		return saveInspectionApproveRecord(approveVO);
	}

	/**
	 * 保存产品检验审批记录
	 * @param approveVO
	 * @return
	 */
	private ServerResponse saveInspectionApproveRecord(ProduceProductInspectionApproveVO approveVO) {
		approveVO.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		approveVO.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		ProduceProductInspectionApprove record = new ProduceProductInspectionApprove();
		BeanUtils.copyProperties(approveVO, record);
		try {
			productInspectionApproveMapper.insertSelective(record);
			return updateProductProcessAfterInspectionApprove(approveVO);
		}catch (Exception e) {
			log.error("产品检验审批发起成功，审批记录保存失败："+e.getMessage());
			return ServerResponse.createByFailMessage("产品检验审批发起成功，审批记录保存失败");
		}
	}

	/**
	 * 产品检验审批发起后更新产品流程记录
	 * @param approveVO
	 * @return
	 */
	private ServerResponse updateProductProcessAfterInspectionApprove(ProduceProductInspectionApproveVO approveVO) {
		String[] ids = approveVO.getCodes().split(",");
		List<ProduceProductProcess> processes = productProcessMapper.queryByIds(ids);
		try {
			for (ProduceProductProcess item : processes) {
				item.setInspectionApproveId(approveVO.getId());
				item.setInspectionApproveResult(approveVO.getApproveResult());
				item.setInspectionApproveStatus(approveVO.getApproveStatus());
				item.setInspectionApproveStartTime(new Date());
				productProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		}catch (Exception e) {
			log.error("产品检验审批记录保存成功，更新产品流程信息失败");
			return ServerResponse.createByFailMessage("产品检验审批记录保存成功，更新产品流程信息失败");
		}
	}

	/**
	 * 装配产品检验审批实例对象
	 * @param vo
	 * @return
	 */
	private ApproveInstanceVO createInspectionApproveInstance(ProduceProductInspectionApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_INSPECTION_PROCESS_CODE);
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_INSPECTION_PROCESS_CODE);
		approve.setAgentId(DingTalkConstant.AGENTID);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(vo.getDeptId());
		approve.setOriginatorUserId(vo.getUserId());
		
		//设置审批内容列表（这里的内容要和审批表单的内容保持一致）
        List<FormComponentValueVo> list = new ArrayList<>();
        FormComponentValueVo name = new FormComponentValueVo();
        name.setName("名称");
        name.setValue(vo.getProductName());
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
	 * 检查是否是重复的产品检验审批
	 * @param approveVO
	 * @return
	 */
	private ServerResponse productInspectionApproveCheck(ProduceProductInspectionApproveVO approveVO) {
		String[] codes = approveVO.getCodes().split(",");
		List<String> ids = productProcessMapper.productInspectionApproveCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("产品检验审批失败，产品重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleInspectionApproveCallBack(String processInstanceId, Integer status,
			Integer result) {
		ProduceProductInspectionApprove approve = productInspectionApproveMapper.selectByPrimaryKey(processInstanceId);
		if(approve == null) {
			log.info("产品检验审批不存在，审批ID："+processInstanceId);
			return;
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		try {
			productInspectionApproveMapper.updateByPrimaryKey(approve);
			List<ProduceProductProcess> processes = productProcessMapper.queryByIds(approve.getCodes().split(","));
			for (ProduceProductProcess item : processes) {
				item.setInspectionApproveId(processInstanceId);
				item.setInspectionApproveResult(result);
				item.setInspectionStatus(approve.getStatus());
				item.setInspectionApproveStatus(status);
				item.setInspectionApproveStopTime(new Date());
				productProcessMapper.updateByPrimaryKeySelective(item);
			}
		}catch (Exception e) {
			log.error("产品检验审批审批完成更新记录失败，异常信息："+e.getMessage());
		}
		
		
	}

	@Override
	public ServerResponse queryWaittingStockInApproveList() {
		List<Map<String, Object>> res = productProcessMapper.queryWaittingStockInApproveList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryWaittingStockOutApproveList() {
		List<Map<String, Object>> res = productProcessMapper.queryWaittingStockOutApproveList();
		return ServerResponse.createBySuccess(res);
	}

	

}
