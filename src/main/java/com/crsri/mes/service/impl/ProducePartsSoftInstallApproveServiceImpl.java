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
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.dao.ProducePartsSoftInstallApproveMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProducePartsDefendApprove;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.entity.ProducePartsSoftInstallApprove;
import com.crsri.mes.service.ProducePartsSoftInstallApproveService;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProducePartsSoftInstallApproveVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;

/**
 * 生产部件软件灌装和设备校准审批的service接口的实现
 * 
 * @author 2011102394
 *
 */
@Service
public class ProducePartsSoftInstallApproveServiceImpl implements ProducePartsSoftInstallApproveService {

	private static final Logger log = LoggerFactory.getLogger(ProducePartsSoftInstallApproveServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProducePartsProcessMapper producePartsProcessMapper;

	@Resource
	private ProducePartsSoftInstallApproveMapper producePartsSoftInstallApproveMapper;
	
	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse save(ProducePartsSoftInstallApproveVO partsSoftInstallApproveVO) {
		// 1、检查是否有重复提交的审批
		ServerResponse checkRes = partsSoftInstallCheck(partsSoftInstallApproveVO);
		if (!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、构建审批对象，发起审批实例
		ApproveInstanceVO approveInstanceVO = createApproveInstance(partsSoftInstallApproveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		// 3、保存生产部件软件灌装和设备校准审批的记录，同时更新生产部件的相关记录
		partsSoftInstallApproveVO.setId(processInstanceId);
		return updatePartsSoftInstallRecord(partsSoftInstallApproveVO);
	}

	/**
	 * 保存生产部件软件灌装和设备校准审批的记录，同时更新生产部件的相关记录
	 * 
	 * @param partsSoftInstallApproveVO
	 * @return
	 */
	private ServerResponse updatePartsSoftInstallRecord(ProducePartsSoftInstallApproveVO partsSoftInstallApproveVO) {
		partsSoftInstallApproveVO.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		partsSoftInstallApproveVO.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		ProducePartsSoftInstallApprove entity = new ProducePartsSoftInstallApprove();
		BeanUtils.copyProperties(partsSoftInstallApproveVO, entity);
		try {
			producePartsSoftInstallApproveMapper.insertSelective(entity);
			return partsProcessUpdate(partsSoftInstallApproveVO);
		} catch (Exception e) {
			// TODO 联系管理人员
			log.error("生产部件软件灌装和设备校准审批实例发送成功，审批记录保存到数据库失败。异常信息：", e.getMessage());
			return ServerResponse.createByFailMessage("审批实例发送成功，记录保存失败");
		}
	}

	/**
	 * 同步更新审批中的部件流程信息
	 * 
	 * @param partsSoftInstallApproveVO
	 * @return
	 */
	private ServerResponse partsProcessUpdate(ProducePartsSoftInstallApproveVO approve) {
		try {
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsProcessList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsProcessList) {
				item.setSoftInstallApproveId(approve.getId());
				item.setSoftInstallApproveResult(approve.getApproveResult());
				item.setSoftInstallApproveStatus(approve.getApproveStatus());
				item.setSoftInstallOperator(approve.getOperator());
				item.setSoftInstallStartTime(new Date());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("生产部件软件灌装和设备校准审批记录保存成功，生产部件流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("生产部件软件灌装和设备校准审批记录保存成功，生产部件流程记录保存失败");
		}
	}

	/**
	 * 装配审批实例
	 * 
	 * @param partsSoftInstallApproveVO
	 * @return
	 */
	private ApproveInstanceVO createApproveInstance(ProducePartsSoftInstallApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		approve.setAgentId(DingTalkConstant.AGENTID);
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_SOFT_INSTALL_PROCESS_CODE);
		approve.setApprovers(approve2.getApproverList());
		approve.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_SOFT_INSTALL_PROCESS_CODE);
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(vo.getDeptId());
		approve.setOriginatorUserId(vo.getUserId());
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue("软件灌装及设备校准审批");
		list.add(name);
		FormComponentValueVo number = new FormComponentValueVo();
		number.setName("数量");
		number.setValue(String.valueOf(vo.getNumber()));
		list.add(number);
		FormComponentValueVo softVersion = new FormComponentValueVo();
		softVersion.setName("软件版本");
		softVersion.setValue(vo.getSoftVersion());
		list.add(softVersion);
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
	 * 检查该审批是否是重复提交
	 * 
	 * @param partsSoftInstallApproveVO
	 * @return
	 */
	private ServerResponse partsSoftInstallCheck(ProducePartsSoftInstallApproveVO partsSoftInstallApproveVO) {
		String[] codes = partsSoftInstallApproveVO.getCodes().split(",");
		List<String> ids = producePartsProcessMapper.partsSoftInstallCheck(codes);
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("生产部件软件灌装和设备校准审批失败，部件重复，重复编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleApproveCallBack(String processInstanceId, Integer status, Integer result) {
		// 1、根据审批的id查找对应的审批记录，并更新
		ProducePartsSoftInstallApprove approve = producePartsSoftInstallApproveMapper.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("该生产部件软件灌装和设备校准审批不存在，审批id：" + processInstanceId);
			return;
		}
		try {
			approve.setApproveStatus(status);
			approve.setApproveResult(result);
			producePartsSoftInstallApproveMapper.updateByPrimaryKeySelective(approve);
			// 2、根据id更新对应物品的出库审批状态
			// 更新对应的部件信息
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsList) {
				item.setSoftInstallApproveId(processInstanceId);
				item.setSoftInstallStopTime(new Date());
				item.setSoftInstallApproveStatus(status);
				item.setSoftInstallApproveResult(result);
				item.setSoftInstallVersion(approve.getSoftVersion());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
		} catch (Exception e) {
			log.error("生产部件三防审批回调处理失败，异常信息：", e.getMessage());
		}
	}

}
