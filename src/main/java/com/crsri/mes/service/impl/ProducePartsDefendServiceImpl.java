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
import com.crsri.mes.dao.ProducePartsDefendApproveMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProducePartsDefendApprove;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.service.ProducePartsDefendService;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProducePartsDefendApproveVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;

@Service
public class ProducePartsDefendServiceImpl implements ProducePartsDefendService {

	private static final Logger log = LoggerFactory.getLogger(ProduceStockOutServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProducePartsProcessMapper producePartsProcessMapper;

	@Resource
	private ProducePartsDefendApproveMapper producePartsDefendApproveMapper;
	
	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse save(ProducePartsDefendApproveVO partsDefendApproveVO) {
		// 1、检验是否有重复的三防审批
		log.info(partsDefendApproveVO.toString());
		ServerResponse checkRes = partsDefendCheck(partsDefendApproveVO);
		// 2、发起审批
		ApproveInstanceVO approveInstanceVO = createApproveInstance(partsDefendApproveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		// 3、更新部件三防审批记录及相应的部件信息
		partsDefendApproveVO.setId(processInstanceId);
		return updatePartsDefendRecord(partsDefendApproveVO);
	}

	/**
	 * 更新部件三防审批记录及相应的部件信息
	 * 
	 * @param partsDefendApproveVO
	 * @return
	 */
	private ServerResponse updatePartsDefendRecord(ProducePartsDefendApproveVO partsDefendApproveVO) {
		partsDefendApproveVO.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		partsDefendApproveVO.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		ProducePartsDefendApprove entity = new ProducePartsDefendApprove();
		BeanUtils.copyProperties(partsDefendApproveVO, entity);
		try {
			producePartsDefendApproveMapper.insertSelective(entity);
			// 更新部件信息
			return partsProcessUpdate(partsDefendApproveVO);
		} catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByFailMessage("生产部件三防审批审批实例发起成功，审批记录保存到数据库失败");
		}
	}

	/**
	 * 更新部件过程信息
	 * 
	 * @param partsDefendApproveVO
	 * @return
	 */
	private ServerResponse partsProcessUpdate(ProducePartsDefendApproveVO approve) {
		try {
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsProcessList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsProcessList) {
				item.setDefendApproveId(approve.getId());
				item.setDefendApproveResult(approve.getApproveResult());
				item.setDefendApproveStatus(approve.getApproveStatus());
				item.setDefendOperator(approve.getOperator());
				item.setDefendStatus(0);
				item.setDefendStartTime(new Date());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("生产部件三防审批记录保存成功，生产部件流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("生产部件三防审批记录保存成功，生产部件流程记录保存失败");
		}
	}

	/**
	 * 构建审批实例
	 * 
	 * @param partsDefendApproveVO
	 * @return
	 */
	private ApproveInstanceVO createApproveInstance(ProducePartsDefendApproveVO partsDefendApproveVO) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_DEFEND_PROCESS_CODE);
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_DEFEND_PROCESS_CODE);
		approve.setAgentId(DingTalkConstant.AGENTID);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(partsDefendApproveVO.getDeptId());
		approve.setOriginatorUserId(partsDefendApproveVO.getUserId());
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue(partsDefendApproveVO.getTypeName());
		list.add(name);
		FormComponentValueVo number = new FormComponentValueVo();
		number.setName("数量");
		number.setValue(String.valueOf(partsDefendApproveVO.getNumber()));
		list.add(number);
		FormComponentValueVo operator = new FormComponentValueVo();
		operator.setName("操作人员");
		operator.setValue(partsDefendApproveVO.getOperator());
		list.add(operator);
		if (StringUtils.isNotBlank(partsDefendApproveVO.getImage())) {
			list.add(ApproveUtil.imageUtil(partsDefendApproveVO.getImage(), host));
		}
		list.add(ApproveUtil.remarkUtil(partsDefendApproveVO.getRemark()));

		approve.setFormComponentValueVos(list);
		return approve;
	}

	/**
	 * 检验三防审批是否重复
	 * 
	 * @param partsDefendApproveVO
	 * @return
	 */
	private ServerResponse partsDefendCheck(ProducePartsDefendApproveVO partsDefendApproveVO) {
		String[] codes = partsDefendApproveVO.getCodes().split(",");
		List<String> ids = new ArrayList<>();
		ids = producePartsProcessMapper.partsDefendCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("生产部件三防审批失败，部件重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleApproveCallBack(String processInstanceId, Integer status, Integer result) {
		// 1、根据审批的id查找对应的审批记录，并更新
		ProducePartsDefendApprove approve = producePartsDefendApproveMapper.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("该生产部件三防审批不存在，审批id：" + processInstanceId);
			return;
		}
		try {
			approve.setApproveStatus(status);
			approve.setApproveResult(result);
			producePartsDefendApproveMapper.updateByPrimaryKeySelective(approve);
			// 2、根据id更新对应物品的出库审批状态
			// 更新对应的部件信息
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsList) {
				item.setDefendApproveId(processInstanceId);
				item.setDefendStatus(1);
				item.setDefendApproveStatus(status);
				item.setDefendApproveResult(result);
				item.setDefendStopTime(new Date());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
		}catch (Exception e) {
			log.error("生产部件三防审批回调处理失败，异常信息：",e.getMessage());
		}
						
	}

}
