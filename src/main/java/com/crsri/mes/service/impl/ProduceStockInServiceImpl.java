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
import com.crsri.mes.dao.ProduceComponentProcessMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.dao.ProduceProductProcessMapper;
import com.crsri.mes.dao.ProduceStockInApproveMapper;
import com.crsri.mes.entity.Approve;
import com.crsri.mes.entity.ProduceComponentProcess;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.entity.ProduceProductProcess;
import com.crsri.mes.entity.ProduceStockInApprove;
import com.crsri.mes.service.ProduceStockInService;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.PageHelperUtil;
import com.crsri.mes.util.dingtalk.ApproveUtil;
import com.crsri.mes.vo.ApproveInstanceVO;
import com.crsri.mes.vo.ProduceStockInApproveVO;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest.FormComponentValueVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ProduceStockInServiceImpl implements ProduceStockInService {

	private static final Logger log = LoggerFactory.getLogger(ProduceStockInServiceImpl.class);

	@Value("${web.host}")
	private String host;

	@Resource
	private ProducePartsProcessMapper producePartsProcessMapper;

	@Resource
	private ProduceStockInApproveMapper produceStockInApproveMapper;

	@Resource
	private ProduceComponentProcessMapper componentProcessMapper;

	@Resource
	private ProduceProductProcessMapper productProcessMapper;
	
	@Resource
	private ApproveMapper approveMapper;

	@Override
	public ServerResponse save(ProduceStockInApproveVO produceStockInApproveVO) {
		// 1、检查是否有重复的生产物品入库
		ServerResponse checkRes = goodsStockInCheck(produceStockInApproveVO);
		if (!checkRes.isSuccess()) {
			return checkRes;
		}
		// 2、发起物品入库审批
		ApproveInstanceVO approveInstanceVO = createApproveInsatnce(produceStockInApproveVO);
		ServerResponse<String> response = ApproveUtil.startProcessInstance(approveInstanceVO);
		if (!response.isSuccess()) {
			// 审批实例发送失败
			return response;
		}
		// 获取审批的id
		String processInstanceId = response.getData();
		produceStockInApproveVO.setId(processInstanceId);
		// 更新物品入库审批记录
		return updateGoodsRecord(produceStockInApproveVO);
	}

	/**
	 * 将物品入库审批记录存入数据库中，并更新相应的物品记录
	 */
	private ServerResponse updateGoodsRecord(ProduceStockInApproveVO vo) {
		try {
			ProduceStockInApprove entity = new ProduceStockInApprove();
			vo.setApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
			vo.setApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
			BeanUtils.copyProperties(vo, entity);
			// 设置审批状态和审批结果，保存审批记录
			produceStockInApproveMapper.insertSelective(entity);
			// 更新相应的物品记录
			Integer goodsType = vo.getGoodsType();
			if (goodsType == 0) {
				// 更新入库部件的信息
				return updateProduceParts(vo);
			} else if (goodsType == 1) {
				// 更新入库组件的信息
				return updateProduceComponent(vo);
			} else if (goodsType == 2) {
				// 更新入库产品信息
				return updateProduceProduct(vo);
			} else {
				return ServerResponse.createByFailMessage("入库物品类别错误");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return ServerResponse.createByFailMessage("入库审批发起成功，入库审批记录保存到数据库失败");
		}
	}

	private ServerResponse updateProduceProduct(ProduceStockInApproveVO vo) {
		List<ProduceProductProcess> processes = productProcessMapper.queryByIds(vo.getCodes().split(","));
		try {
			for (ProduceProductProcess item : processes) {
				item.setStockInApproveId(vo.getId());
				item.setStockInApproveOperator(vo.getOperator());
				item.setStockInApproveStartTime(new Date());
				item.setStockInApproveStatus(vo.getApproveStatus());
				item.setStockInApproveResult(vo.getApproveResult());
				productProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("物品入库审批记录保存成功，生产产品流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("物品入库审批记录保存成功，生产产品流程记录保存失败");
		}
	}

	/**
	 * 更新入库组件信息
	 * 
	 * @param vo
	 * @return
	 */
	private ServerResponse updateProduceComponent(ProduceStockInApproveVO vo) {
		List<ProduceComponentProcess> componentProcesses = componentProcessMapper.queryByIds(vo.getCodes().split(","));
		try {
			for (ProduceComponentProcess item : componentProcesses) {
				item.setStockInApproveId(vo.getId());
				item.setStockInApproveOperator(vo.getOperator());
				item.setStockInApproveResult(vo.getApproveStatus());
				item.setStockInApproveStartTime(new Date());
				item.setStockInApproveResult(vo.getApproveResult());
				item.setStockPosition(vo.getPosition());
				componentProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("物品入库审批记录保存成功，生产组件流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("物品入库审批记录保存成功，生产组件流程记录保存失败");
		}
	}

	/**
	 * 更新入库部件的信息
	 * 
	 * @param approve
	 * @return
	 */
	private ServerResponse updateProduceParts(ProduceStockInApproveVO approve) {
		try {
			String[] codes = approve.getCodes().split(",");
			List<ProducePartsProcess> partsProcessList = producePartsProcessMapper.selectByIdBatch(codes);
			for (ProducePartsProcess item : partsProcessList) {
				item.setStockInApproveId(approve.getId());
				item.setStockInApproveResult(DingTalkApproveConstant.approveResult.PROCEEDING);
				item.setStockInOperator(approve.getOperator());
				item.setStockInApproveStatus(DingTalkApproveConstant.approveStatus.APPROVE_RUNNING);
				item.setStockInStartTime(new Date());
				item.setStockPosition(approve.getPosition());
				producePartsProcessMapper.updateByPrimaryKeySelective(item);
			}
			return ServerResponse.createBySuccess();
		} catch (Exception e) {
			log.error("物品入库审批记录保存成功，生产部件流程记录保存失败:" + e.getMessage());
			return ServerResponse.createByFailMessage("物品入库审批记录保存成功，生产部件流程记录保存失败");
		}
	}

	/**
	 * 封装入库审批实例对象
	 * 
	 * @param produceStockInApproveVO
	 * @return
	 */
	private ApproveInstanceVO createApproveInsatnce(ProduceStockInApproveVO vo) {
		ApproveInstanceVO approve = new ApproveInstanceVO();
		Approve approve2 = approveMapper.queryByCode(DingTalkApproveConstant.processCode.GOODS_STOCK_IN_PROCESS_CODE);
		approve.setApprovers(approve2.getApproverList());
		approve.setCcList(approve2.getCcList());
		approve.setProcessCode(DingTalkApproveConstant.processCode.GOODS_STOCK_IN_PROCESS_CODE);
		approve.setOriginatorUserId(vo.getUserId());
		approve.setDeptId(vo.getDeptId());
		List<FormComponentValueVo> list = new ArrayList<>();
		FormComponentValueVo name = new FormComponentValueVo();
		name.setName("名称");
		name.setValue(vo.getTypeName());
		list.add(name);
		FormComponentValueVo number = new FormComponentValueVo();
		number.setName("数量");
		number.setValue(String.valueOf(vo.getNumber()));
		list.add(number);
		FormComponentValueVo position = new FormComponentValueVo();
		position.setName("存放位置");
		position.setValue(vo.getPosition());
		list.add(position);
		FormComponentValueVo operator = new FormComponentValueVo();
		operator.setName("入库人员");
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
	 * 检查入库物品是否有重复入库 produceStockInApproveVO
	 * 
	 * @return
	 */
	private ServerResponse goodsStockInCheck(ProduceStockInApproveVO produceStockInApproveVO) {
		Integer goodsType = produceStockInApproveVO.getGoodsType();
		String[] codes = produceStockInApproveVO.getCodes().split(",");
		List<String> ids = new ArrayList<>();
		if (goodsType == 0) {
			// 部件入库
			ids = producePartsProcessMapper.goodsStockInCheck(codes);
		} else if (goodsType == 1) {
			// 组件入库
			ids = componentProcessMapper.goodsStockInCheck(codes);
		} else if (goodsType == 2) {
			ids = productProcessMapper.goodsStockInCheck(codes);
		} else {
			return ServerResponse.createByFailMessage("入库物品类别错误");
		}
		if (ids.size() > 0) {
			return ServerResponse.createByFailMessage("物品入库失败，物品重复，重复物品编号" + String.join(",", ids));
		} else {
			return ServerResponse.createBySuccess();
		}
	}

	@Override
	public void handleApproveCallBack(String processInstanceId, Integer status, Integer result) {
		// 1、根据审批的id查找对应的审批记录，并更新
		ProduceStockInApprove approve = produceStockInApproveMapper.selectByPrimaryKey(processInstanceId);
		if (approve == null) {
			log.info("该入库审批不存在，审批id：" + processInstanceId);
			return;
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		produceStockInApproveMapper.updateByPrimaryKeySelective(approve);
		try {
			// 2、根据id更新对应物品的入库审批状态
			Integer goodsType = approve.getGoodsType();
			if (goodsType == 0) {
				// 更新对应的部件信息
				String[] codes = approve.getCodes().split(",");
				List<ProducePartsProcess> partsList = producePartsProcessMapper.selectByIdBatch(codes);
				for (ProducePartsProcess item : partsList) {
					//重新设置对应部件的审批id
					item.setStockInApproveId(processInstanceId);
					item.setStockInApproveStatus(status);
					item.setStockInApproveResult(result);
					item.setStockInStopTime(new Date());
					// 设置部件的库存状态 0 在库存中 1 不在库存中
					item.setStockStatus(0);
					producePartsProcessMapper.updateByPrimaryKeySelective(item);
				}
			} else if (goodsType == 1) {
				// 更新对应的组件信息
				String[] codes = approve.getCodes().split(",");
				List<ProduceComponentProcess> componentProcesses = componentProcessMapper.queryByIds(codes);
				for (ProduceComponentProcess item : componentProcesses) {
					item.setStockInApproveId(processInstanceId);
					item.setStockInApproveStatus(status);
					item.setStockInApproveResult(result);
					item.setStockInApproveStopTime(new Date());
					// 设置组件的库存状态 0 在库存中 1 不在库存中
					item.setStockStatus(0);
					componentProcessMapper.updateByPrimaryKeySelective(item);
				}
			} else if (goodsType == 2) {
				// 更新对应的产品信息
				String[] codes = approve.getCodes().split(",");
				List<ProduceProductProcess> processes = productProcessMapper.queryByIds(codes);
				for (ProduceProductProcess item : processes) {
					item.setStockInApproveId(processInstanceId);
					item.setStockInApproveStatus(status);
					item.setStockInApproveResult(result);
					item.setStockInApproveStopTime(new Date());
					// 设置组件的库存状态 0 在库存中 1 不在库存中
					item.setStockStatus(0);
					item.setStockPosition(approve.getPosition());
					productProcessMapper.updateByPrimaryKeySelective(item);
				}
			} else {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("生产物品入库审批回调处理失败，审批id:" + processInstanceId);
			// TODO 给维护人员发送钉钉信息
		}
	}

	@Override
	public ServerResponse getPartsStock() {
		List<Map<String, Integer>> data = producePartsProcessMapper.getPartsStock();
		return ServerResponse.createBySuccess(data);
	}

	@Override
	public ServerResponse getComponentStock() {
		List<Map<String, Integer>> data = componentProcessMapper.getComponentStock();
		return ServerResponse.createBySuccess(data);
	}

	@Override
	public ServerResponse getProductStock() {
		List<Map<String, Integer>> data = productProcessMapper.getProductStock();
		return ServerResponse.createBySuccess(data);
	}

	@Override
	public ServerResponse getStockInHistory(JSONObject json) {
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
		
		List<ProduceStockInApprove> approves = produceStockInApproveMapper.getStockInHistory(goodsType, startTime, stopTime, name,
				status);
		for (ProduceStockInApprove item : approves) {
			item.setImage(ImageUtil.imageUtil(item.getImage(), host));
		}
		Map<String, Object> pageInfo = PageHelperUtil.getPageInfo(page);
		Map<String, Object> res = new HashMap<>();
		res.put("page", pageInfo);
		res.put("list", approves);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse stockStatus() {
		//部件数量
		int partsCount = producePartsProcessMapper.queryProducePartsStockInNumber();
		//部件的分类
        int partsCategoryCount = producePartsProcessMapper.getCountGroupByPartsId();
        //组件的数量
        int componentCount = componentProcessMapper.queryComponentsInStock().size();
        //组件的种类数量
        int componentCategoryCount = componentProcessMapper.getCountGroupByComponentId();
        //产品数量
        int productCount = productProcessMapper.queryWaittingStockOutApproveList().size();
        //产品种类数量
        int productCategoryCount = productProcessMapper.getCountGroupByProductId();
        Map<String,Object> res = new HashMap<>();
        res.put("partsCount",partsCount);
        res.put("partsCategoryCount",partsCategoryCount);
        res.put("componentCount",componentCount);
        res.put("componentCategoryCount",componentCategoryCount);
        res.put("productCount",productCount);
        res.put("productCategoryCount",productCategoryCount);
        return ServerResponse.createBySuccess(res);
	}
}
