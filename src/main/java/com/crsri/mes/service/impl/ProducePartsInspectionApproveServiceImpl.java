package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkApproveConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ApproveMapper;
import com.crsri.mes.dao.ProducePartsInspectionApproveMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.dao.UserMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProducePartsInspectionApprove;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.service.ProducePartsInspectionApproveService;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.PageHelperUtil;
import com.crsri.mes.util.StringUtil;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProducePartsApproveVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 生产部件检验审批的service接口的实现
 * @author 2011102394
 *
 */
@Service
public class ProducePartsInspectionApproveServiceImpl implements ProducePartsInspectionApproveService {
	
	
	private static final Logger log = LoggerFactory.getLogger(ProducePartsInspectionApproveServiceImpl.class);


	@Value("${web.host}")
	private String host;
	
	@Resource
	private ProducePartsProcessMapper partsProcessMapper;
	
	@Resource
	private ProducePartsInspectionApproveMapper producePartsApproveMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse save(ProducePartsApproveVO partsApproveVO) {
		// 1、检验是否重复的部件检验
		String[] codes = partsApproveVO.getPartsCodes().split(",");
		List<String> partsIdList = partsProcessMapper.checkReInspection(codes);
		if (partsIdList.size() > 0) {
			// 部件重复审批,返回重复的部件二维码值
			String code = String.join(",", partsIdList);
			return ServerResponse.createByFailMessage("部件重复，重复部件编号："+code);
		}
		// 2、发起部件审批，
		ApproveInstanceVO approveInstance = createProducePartsApproveVo(partsApproveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstance);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		// 3、保存生产部件审批记录
		ProducePartsInspectionApprove approve = new ProducePartsInspectionApprove();
		BeanUtils.copyProperties(partsApproveVO, approve);
		approve.setId(processInstanceId);
		//设置审批的状态和审批结果
		approve.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
		approve.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
		producePartsApproveMapper.insertSelective(approve);
		//创建生产部件记录
		createProduceParts(approve);
		return ServerResponse.createBySuccess(processInstanceId);
	}
	
	
	/**
	 * 装配来料检验审批对象
	 * @param partsApproveVO
	 * @return
	 */
	private ApproveInstanceVO createProducePartsApproveVo(ProducePartsApproveVO partsApproveVO) {
		ApproveInstanceVO vo = new ApproveInstanceVO();
		Approve approve = approveMapper.queryByCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_INSPECTION_CODE);
		vo.setApprovers(approve.getApproverList());
		vo.setCcList(approve.getCcList());
		vo.setOriginatorUserId(partsApproveVO.getUserId());
		vo.setDeptId(partsApproveVO.getDeptId());
		vo.setProcessCode(DingTalkApproveConstant.processCode.PRODUCE_PARTS_INSPECTION_CODE);
		// 设置审批内容列表
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("物料名称");
		name.setValue(partsApproveVO.getPartsName());
		list.add(name);
		FormComponentValueVo number = new FormComponentValueVo();
		number.setName("物料数量");
		number.setValue(partsApproveVO.getNumber().toString());
		list.add(number);
		FormComponentValueVo status = new FormComponentValueVo();
		status.setName("物料状态");
		if (partsApproveVO.getPartsStatus() == 0) {
			status.setValue("合格");
		} else {
			status.setValue("不合格");
		}
		list.add(status);
		if (StringUtils.isNotBlank(partsApproveVO.getImage())) {
			list.add(ApproveUtil.imageUtil(partsApproveVO.getImage(), host));
		}
		list.add(ApproveUtil.remarkUtil(partsApproveVO.getRemark()));
		vo.setFormComponentValueVos(list);
		return vo;
	}

	/**
	 * 装配生产部件，并存入数据库中
	 * @param approve
	 * @return
	 */
	private ServerResponse createProduceParts(ProducePartsInspectionApprove approve) {
		try {
			List<ProducePartsProcess> partsProcessList = new ArrayList<>();
			String[] codes = approve.getPartsCodes().split(",");
			for(String id : codes) {
				ProducePartsProcess partsProcess = new ProducePartsProcess();
				partsProcess.setId(id);
				partsProcess.setPartsId(approve.getPartsId());
				partsProcess.setPartsName(approve.getPartsName());
				partsProcess.setSpecification(approve.getSpecification());
				partsProcess.setInspectionStatus(approve.getApproveStatus());
				partsProcess.setInspectionOperator(approve.getOperator());
				partsProcess.setInspectionStartTime(new Date());
				partsProcess.setInspectionApproveId(approve.getId());
				partsProcess.setInspectionApproveStatus(approve.getApproveStatus());
				partsProcessList.add(partsProcess);
			}
			int rowCount = partsProcessMapper.insertSelectiveBatch(partsProcessList);
			return ServerResponse.createBySuccess();
		}catch (Exception e) {
			e.printStackTrace();
			return ServerResponse.createByFailMessage("创建部件记录信息失败");
		}

	}
	


	@Override
	public void handleApproveCallBack(String processInstanceId, Integer status, Integer result) {
		//根据审批id更新来料审批记录
		ProducePartsInspectionApprove approve = producePartsApproveMapper.selectByPrimaryKey(processInstanceId);
		if(approve==null) {
			log.info("该审批记录已删除");
			return;
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		approve.setUpdateTime(new Date());
		producePartsApproveMapper.updateByPrimaryKeySelective(approve);
		
		//更新生产部件的状态
		String[] partsCodes = approve.getPartsCodes().split(",");
		List<ProducePartsProcess> partsList = partsProcessMapper.selectByIdBatch(partsCodes);
		if(partsList.size()>0) {
			for (ProducePartsProcess item : partsList) {
				//设置来料检验审批结果
				//重新设置审批的id（因为如果是之前撤销或者是拒绝的审批，这里需要重新设置审批的id）
				item.setInspectionApproveId(processInstanceId);
				item.setInspectionApproveResult(result);
				//设置来料检验审批状态
				item.setInspectionApproveStatus(status);
				//设置来料检验审批结束时间
				item.setInspectionStopTime(new Date());
				partsProcessMapper.updateByPrimaryKeySelective(item);
			}
		}else {
			log.error("未找到该审批id相关的生产部件");
			return;
		}
	}

	@Override
	public ServerResponse getProducePartsWaittingStockInList() {
		List<Map<String, Object>> res = partsProcessMapper.getProducePartsWaittingStockInList();
		return ServerResponse.createBySuccess(res);
	}


	@Override
	public ServerResponse getPartsInspectionCount(JSONObject json) {
		String partsName = StringUtil.tirm(json.getString("partsName"));
		String operator = StringUtil.tirm(json.getString("operator"));
		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
		int totalNumber = producePartsApproveMapper.getPartsInspectionCountByProcessStatus(partsName, operator, null,
				startTime, stopTime);
		int processingNumber = producePartsApproveMapper.getPartsInspectionCountByProcessStatus(partsName, operator,
				DingTalkApproveConstant.approveStatus.APPROVE_RUNNING, startTime, stopTime);
		int solvedNumber = producePartsApproveMapper.getPartsInspectionCountByProcessStatus(partsName, operator,
				DingTalkApproveConstant.approveStatus.APPROVE_FINISHED, startTime, stopTime);
		Map<String, Integer> res = new HashMap<>();
		res.put("totalNumber", totalNumber);
		res.put("processingNumber", processingNumber);
		res.put("solvedNumber", solvedNumber);
		return ServerResponse.createBySuccess(res);
	}


	@Override
	public ServerResponse getPartInspectionList(JSONObject json) {
		String partsName = StringUtil.tirm(json.getString("partsName"));
		Integer processInstanceType = json.getInteger("processInstanceType");
		Integer processInstanceResult = json.getInteger("processInstanceResult");
		String operator = StringUtil.tirm(json.getString("operator"));
		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
//		分页参数
		Integer pageSize = 10;
		if(json.containsKey("pageSize")) {
			pageSize = json.getInteger("pageSize");
		}
		Integer pageNumber = 1;
		if (json.containsKey("pageNumber")) {
			pageNumber = json.getInteger("pageNumber");
		}
		Page<Object> page = PageHelper.startPage(pageNumber,pageSize);
		List<ProducePartsInspectionApprove> list = producePartsApproveMapper.getPartInspectionList(partsName, processInstanceType,
				processInstanceResult, operator, startTime, stopTime);
		for (ProducePartsInspectionApprove item : list) {
			String image = item.getImage();
			String imagePath = ImageUtil.imageUtil(image, host);
			item.setImage(imagePath);
		}
		Map<String, Object> pageInfo = PageHelperUtil.getPageInfo(page);
		Map<String, Object> res = new HashMap<>();
		res.put("page", pageInfo);
		res.put("list", list);
		return ServerResponse.createBySuccess(res);
	}

}
