package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DingTalkApproveConstant;
import com.crsri.mes.common.constant.DingTalkConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ApproveMapper;
import com.crsri.mes.dao.ProduceComponentProcessMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.dao.ProduceProductProcessMapper;
import com.crsri.mes.dao.ProduceShipApproveMapper;
import com.crsri.mes.dao.ProduceStockOutApproveMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProduceComponentProcess;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.entity.ProduceProductProcess;
import com.crsri.mes.entity.ProduceShipApprove;
import com.crsri.mes.entity.ProduceStockInApprove;
import com.crsri.mes.entity.ProduceStockOutApprove;
import com.crsri.mes.service.ProduceStockOutService;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.PageHelperUtil;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProduceStockOutApproveVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author 2011102394
 *
 */
@Service
public class ProduceStockOutServiceImpl implements ProduceStockOutService {

	private static final Logger log = LoggerFactory.getLogger(ProduceStockOutServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProducePartsProcessMapper producePartsProcessMapper;

	@Resource
	private ProduceStockOutApproveMapper produceStockOutApproveMapper;

	@Resource
	private ProduceComponentProcessMapper componentProcessMapper;

	@Resource
	private ProduceProductProcessMapper productProcessMapper;

	@Resource
	private ApproveMapper approveMapper;
	
	@Resource
	private ProduceShipApproveMapper produceShipApproveMapper;

	@Override
	public ServerResponse save(ProduceStockOutApproveVO produceStockOutApproveVO) {
		// 1、检查是否有重复的出库物品
		ServerResponse checkRes = goodsOutStockCheck(produceStockOutApproveVO);
		if (!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、发起物品出库审批
		ApproveInstanceVO approveInstanceVO = createApproveInsatnce(produceStockOutApproveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		// 新增生产物品出库记录，并更新相应的生产物品记录信息
		produceStockOutApproveVO.setId(processInstanceId);
		return updateGoodsStockOutRecord(produceStockOutApproveVO);
	}

	/**
	 * 新增生产物品出库记录，并更新相应的生产物品信息记录
	 * 
	 * @param produceStockOutApproveVO
	 * @return
	 */
	private ServerResponse updateGoodsStockOutRecord(ProduceStockOutApproveVO produceStockOutApproveVO) {
		try {
			produceStockOutApproveVO.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
			produceStockOutApproveVO.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
			if (produceStockOutApproveVO.getGoodsType() == 2) {
				//产品发货
				ProduceShipApprove produceShipApprove = new ProduceShipApprove();
				BeanUtils.copyProperties(produceStockOutApproveVO, produceShipApprove);
				produceShipApproveMapper.insertSelective(produceShipApprove);
			} else {
				// 部件或组件出库
				ProduceStockOutApprove entity = new ProduceStockOutApprove();
				BeanUtils.copyProperties(produceStockOutApproveVO, entity);
				produceStockOutApproveMapper.insertSelective(entity);
			}
			// 更新相应的物品信息
			Integer goodsType = produceStockOutApproveVO.getGoodsType();
			if (goodsType == 0) {
				// 更新出库部件的信息
				return updateProduceParts(produceStockOutApproveVO);
			} else if (goodsType == 1) {
				// 更新出库组件的流程信息
				return updateProduceComponents(produceStockOutApproveVO);
			} else if (goodsType == 2) {
				// 更新出库产品流程信息
				return updateProduceProduct(produceStockOutApproveVO);
			} else {
				return ServerResponse.createByFailMessage("物品类别错误");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerResponse.createByFailMessage("审批发起成功，记录保存到数据库失败");
		}
	}

	/**
	 * 更新出库产品流程信息
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse updateProduceProduct(ProduceStockOutApproveVO vo) {
		List<ProduceProductProcess> processes = productProcessMapper.queryByIds(vo.getCodes().split(","));
		try {
			for (ProduceProductProcess item : processes) {
				item.setStockOutApproveId(vo.getId());
				item.setStockOutApproveOperator(vo.getOperator());
				item.setStockOutApproveStartTime(new Date());
				item.setStockOutApproveStatus(vo.getApproveStatus());
				item.setStockOutApproveResult(vo.getApproveResult());
				productProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("物品出库审批记录保存成功，生产产品流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("物品出库审批记录保存成功，生产产品流程记录保存失败");
		}
	}

	/**
	 * 更新出库组件的流程信息
	 * 
	 * @param produceStockOutApproveVO
	 * @return
	 */
	private ServerResponse updateProduceComponents(ProduceStockOutApproveVO vo) {
		String[] codes = vo.getCodes().split(",");
		List<ProduceComponentProcess> componentProcesses = componentProcessMapper.queryByIds(codes);
		try {
			for (ProduceComponentProcess item : componentProcesses) {
				item.setStockOutApproveId(vo.getId());
				item.setStockOutApproveOperator(vo.getOperator());
				item.setStockOutApproveStartTime(new Date());
				item.setStockOutApproveStatus(vo.getApproveStatus());
				item.setStockOutApproveResult(vo.getApproveResult());
				componentProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("物品出库审批记录保存成功，生产组件流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("物品出库审批记录保存成功，生产组件流程记录保存失败");
		}
	}

	/**
	 * 更新生产部件的出库状态
	 * 
	 * @param produceStockOutApproveVO
	 * @return
	 */
	private ServerResponse updateProduceParts(ProduceStockOutApproveVO approve) {
		try {
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsProcessList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsProcessList) {
				item.setStockOutApproveId(approve.getId());
				item.setStockOutApproveResult(approve.getApproveResult());
				item.setStockOutStartTime(new Date());
				item.setStockOutApproveStatus(approve.getApproveStatus());
				item.setStockOutOperator(approve.getOperator());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("物品出库审批记录保存成功，生产部件流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("物品出库审批记录保存成功，生产部件流程记录保存失败");
		}
	}

	/**
	 * 构建审批对象
	 * 
	 * @param produceStockOutApproveVO
	 * @return
	 */
	private ApproveInstanceVO createApproveInsatnce(ProduceStockOutApproveVO produceStockOutApproveVO) {
		String processCode = "";
		if (produceStockOutApproveVO.getGoodsType() == 2) {
			processCode = DingTalkApproveConstant.processCode.PRODUCE_PRODUCT_SHIP_PROCESS_CODE;
		} else {
			processCode = DingTalkApproveConstant.processCode.GOODS_STOCK_OUT_PROCESS_CODE;
		}
		ApproveInstanceVO approve = new ApproveInstanceVO();
		// 获取审批对象
		Approve approve2 = approveMapper.queryByCode(processCode);
		approve.setAgentId(DingTalkConstant.AGENTID);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setDeptId(produceStockOutApproveVO.getDeptId());
		approve.setOriginatorUserId(produceStockOutApproveVO.getUserId());
		approve.setProcessCode(processCode);
		// 设置审批内容列表（这里的内容要和审批表单的内容保持一致）
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue(produceStockOutApproveVO.getTypeName());
		list.add(name);
		FormComponentValueVo number = new FormComponentValueVo();
		number.setName("数量");
		number.setValue(String.valueOf(produceStockOutApproveVO.getNumber()));
		list.add(number);
		FormComponentValueVo operator = new FormComponentValueVo();
		operator.setName("出库人员");
		operator.setValue(produceStockOutApproveVO.getOperator());
		list.add(operator);
		FormComponentValueVo receivedPerson = new FormComponentValueVo();
		receivedPerson.setName("收货人员");
		receivedPerson.setValue(produceStockOutApproveVO.getReceivePeople());
		list.add(receivedPerson);
		FormComponentValueVo receivedAddress = new FormComponentValueVo();
		receivedAddress.setName("收货地址");
		receivedAddress.setValue(produceStockOutApproveVO.getReceiveAddress());
		list.add(receivedAddress);
		if (StringUtils.isNotBlank(produceStockOutApproveVO.getImage())) {
			list.add(ApproveUtil.imageUtil(produceStockOutApproveVO.getImage(), host));
		}
		list.add(ApproveUtil.remarkUtil(produceStockOutApproveVO.getRemark()));
		approve.setFormComponentValueVos(list);
		return approve;
	}

	/**
	 * 检查是否有重复提交的出库申请
	 * 
	 * @param produceStockOutApproveVO
	 * @return
	 */
	private ServerResponse goodsOutStockCheck(ProduceStockOutApproveVO vo) {
		Integer goodsType = vo.getGoodsType();
		String[] codes = vo.getCodes().split(",");
		List<String> ids = new ArrayList<>();
		if (goodsType == 0) {
			// 部件出库
			ids = producePartsProcessMapper.goodsStockOutCheck(codes);
		} else if (goodsType == 1) {
			// 组件出库
			ids = componentProcessMapper.goodsStockOutCheck(codes);
		} else if (goodsType == 2) {
			// 产品出库
			ids = productProcessMapper.goodsStockOutCheck(codes);
		} else {
			return ServerResponse.createByFailMessage("出库物品类别错误");
		}
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("物品出库失败，物品重复，重复物品编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleApproveCallBack(String processInstanceId, Integer status, Integer result) {
		// 1、根据审批的id查找对应的审批记录，并更新
		ProduceStockOutApprove approve = produceStockOutApproveMapper.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("该出库审批不存在，审批id：" + processInstanceId);
			return;
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		produceStockOutApproveMapper.updateByPrimaryKeySelective(approve);
		try {
			// 2、根据id更新对应物品的出库审批状态
			Integer goodsType = approve.getGoodsType();
			if (goodsType == 0) {
				// 更新对应的部件信息
				String[] codes = approve.getCodes().split(",");
				List<ProducePartsProcess> partsList = producePartsProcessMapper.selectByIdBatch(codes);
				for (ProducePartsProcess item : partsList) {
					item.setStockOutApproveId(processInstanceId);
					item.setStockOutApproveStatus(status);
					item.setStockOutApproveResult(result);
					item.setStockOutStopTime(new Date());
					// 设置部件的库存状态 0 在库存中 1 不在库存中
					item.setStockStatus(1);
					producePartsProcessMapper.updateByPrimaryKeySelective(item);
				}
			} else if (goodsType == 1) {
				// 更新对应的组件信息
				String[] codes = approve.getCodes().split(",");
				List<ProduceComponentProcess> componentProcesses = componentProcessMapper.queryByIds(codes);
				for (ProduceComponentProcess item : componentProcesses) {
					item.setStockOutApproveId(processInstanceId);
					item.setStockOutApproveStatus(status);
					item.setStockOutApproveResult(result);
					item.setStockOutApproveStopTime(new Date());
					item.setStockOutApproveType(0);
					// 设置组件的库存状态 0 在库存中 1 不在库存中
					item.setStockStatus(1);
					componentProcessMapper.updateByPrimaryKeySelective(item);
				}
			}/* else if (goodsType == 2) {
				// 产品出库
				String[] codes = approve.getCodes().split(",");
				List<ProduceProductProcess> processes = productProcessMapper.queryByIds(codes);
				for (ProduceProductProcess item : processes) {
					item.setStockOutApproveId(processInstanceId);
					item.setStockOutApproveStatus(status);
					item.setStockOutApproveResult(result);
					item.setStockOutApproveStopTime(new Date());
					item.setStockOutApproveType(0);
					// 设置产品的库存状态 0 在库存中 1 不在库存中
					item.setStockStatus(1);
					productProcessMapper.updateByPrimaryKeySelective(item);
				}
			}*/ else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("生产物品出库审批回调处理失败，审批id:" + processInstanceId);
			// TODO 给维护人员发送钉钉信息
		}
	}

	@Override
	public ServerResponse getStockOutHistory(JSONObject json) {
		// 每页显示的记录数
		Integer pageSize = 10;
		// 当前页
		Integer pageNumber = 1;
		// 物品类型
		Integer goodsType = json.getInteger("goodsType");
		// 开始时间
		Date startTime = json.getDate("startTime");
		// 结束时间
		Date stopTime = json.getDate("stopTime");
		// 物品名称
		String name = json.getString("name");
		// 任务状态
		Integer status = json.getInteger("status");
		if (json.containsKey("pageSize")) {
			pageSize = json.getInteger("pageSize");
		}
		if (json.containsKey("pageNumber")) {
			pageNumber = json.getInteger("pageNumber");
		}
		Page page = PageHelper.startPage(pageNumber, pageSize);

		List<ProduceStockOutApprove> approves = produceStockOutApproveMapper.getStockOutHistory(goodsType, startTime,
				stopTime, name, status);
		for (ProduceStockOutApprove item : approves) {
			item.setImage(ImageUtil.imageUtil(item.getImage(), host));
		}
		Map<String, Object> pageInfo = PageHelperUtil.getPageInfo(page);
		Map<String, Object> res = new HashMap<>();
		res.put("page", pageInfo);
		res.put("list", approves);
		return ServerResponse.createBySuccess(res);
	}

}
