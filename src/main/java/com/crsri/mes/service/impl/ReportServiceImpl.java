package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.constant.DBConstant;
import com.crsri.mes.common.constant.ReportConstant;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.CustomerTaskMapper;
import com.crsri.mes.dao.ProduceComponentInspectionApproveMapper;
import com.crsri.mes.dao.ProduceComponentProduceApproveMapper;
import com.crsri.mes.dao.ProducePartsDefendApproveMapper;
import com.crsri.mes.dao.ProducePartsFunctionInspectionApproveMapper;
import com.crsri.mes.dao.ProducePartsInspectionApproveMapper;
import com.crsri.mes.dao.ProducePartsSoftInstallApproveMapper;
import com.crsri.mes.dao.ProduceProductInspectionApproveMapper;
import com.crsri.mes.dao.ProduceProductProduceApproveMapper;
import com.crsri.mes.dao.ProduceStockInApproveMapper;
import com.crsri.mes.dao.ProduceStockOutApproveMapper;
import com.crsri.mes.dao.RepairTaskMapper;
import com.crsri.mes.dao.ReportMapper;
import com.crsri.mes.service.AutomationProjectTaskService;
import com.crsri.mes.service.CustomerTaskService;
import com.crsri.mes.service.RepairTaskService;
import com.crsri.mes.service.ReportService;
import com.crsri.mes.util.ImageUtil;

/**
 * 〈一句话功能简述〉<br>
 * 〈报表相关的service接口的实现〉
 *
 * @author zcj
 * @date 2018/10/24 8:45
 * @since 1.0.0
 */
@Service
public class ReportServiceImpl implements ReportService {

	@Value("${web.host}")
	private String host;

	@Resource
	private ReportMapper reportMapper;

	@Resource
	private ProducePartsInspectionApproveMapper producePartsInspectionApproveMapper;

	@Resource
	private ProduceComponentInspectionApproveMapper produceComponentInspectionApproveMapper;

	@Resource
	private ProduceProductInspectionApproveMapper produceProductInspectionApproveMapper;
	
	@Resource
	private ProducePartsDefendApproveMapper producePartsDefendApproveMapper;

	@Resource
	private ProduceComponentProduceApproveMapper produceComponentProduceApproveMapper;

	@Resource
	private AutomationProjectTaskService automationProjectTaskService;
	
	@Resource
	private ProduceProductProduceApproveMapper produceProductProduceApproveMapper;
	
	@Resource
	private ProduceStockInApproveMapper produceStockInApproveMapper;
	
	@Resource
	private ProduceStockOutApproveMapper produceStockOutApproveMapper;
	
	@Resource
	private ProducePartsSoftInstallApproveMapper producePartsSoftInstallApproveMapper;
	
	@Resource
	private ProducePartsFunctionInspectionApproveMapper producePartsFunctionInspectionApproveMapper;
	
	@Resource
	private CustomerTaskService customerTaskService;
	
	@Resource
	private RepairTaskService repairTaskService;

	@Override
	public ServerResponse getReportSimple(JSONObject json) {
		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
		/**
		 * 获取检验部分的指定时间内的数量
		 */
		// 获取生产部件检验的数量
		Integer partsInspectionNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_PARTS_INSPECTION_APPROVE,
				startTime, stopTime, null);
		// 获取生产组件检验的数量
		Integer componentInspectionNumber = reportMapper
				.getCount(DBConstant.tableName.T_PRODUCE_COMPONENT_INSPECTION_APPROVE, startTime, stopTime, null);
		// 获取产品检验的数量
		Integer productInspectionNumber = reportMapper
				.getCount(DBConstant.tableName.T_PRODUCE_PRODUCT_INSPECTION_APPROVE, startTime, stopTime, null);
		/**
		 * 获取装配部分在指定时间内的数量
		 */
		// 三防刷漆的数量
		Integer partsDefendNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_PARTS_DEFEND_APPROVE,
				startTime, stopTime, null);
		// 软件灌装及设备校准的数量
		Integer partsSoftInstallNumber = reportMapper
				.getCount(DBConstant.tableName.T_PRODUCE_PARTS_SOFT_INSTALL_APPROVE, startTime, stopTime, null);
		// 部件功能检测的数量
		Integer partsFactoryInspectionNumber = reportMapper
				.getCount(DBConstant.tableName.T_PRODUCE_PARTS_FUNCTION_INSPECTION_APPROVE, startTime, stopTime, null);
		// 组件装配的数量
		Integer componentProduceNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_COMPONENT_PRODUCE_APPROVE,
				startTime, stopTime, null);
		// 产品装配的数量
		Integer productProduceNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_PRODUCT_PRODUCE_APPROVE,
				startTime, stopTime, null);
		/**
		 * 获取入库在指定时间内的数量
		 */
		// 部件入库
		Integer partsStockInNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_STOCK_IN_APPROVE, startTime,
				stopTime, 0);
		// 组件入库
		Integer componentStockInNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_STOCK_IN_APPROVE,
				startTime, stopTime, 1);
		// 产品入库
		Integer productStockInNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_STOCK_IN_APPROVE, startTime,
				stopTime, 2);
		/**
		 * 获取出库部分在指定时间内的数量
		 */
		// 部件出库
		Integer partsStockOutNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_STOCK_OUT_APPROVE, startTime,
				stopTime, 0);
		// 组件出库
		Integer componentStockOutNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_STOCK_OUT_APPROVE,
				startTime, stopTime, 1);
		// 产品出库
		Integer productStockOutNumber = reportMapper.getCount(DBConstant.tableName.T_PRODUCE_STOCK_OUT_APPROVE,
				startTime, stopTime, 2);
		Map<String, Integer> res = new HashMap<>();
		res.put("partsInspectionNumber", partsInspectionNumber);
		res.put("componentInspectionNumber", componentInspectionNumber);
		res.put("productInspectionNumber", productInspectionNumber);
		res.put("partsDefendNumber", partsDefendNumber);
		res.put("partsSoftInstallNumber", partsSoftInstallNumber);
		res.put("partsFactoryInspectionNumber", partsFactoryInspectionNumber);
		res.put("componentProduceNumber", componentProduceNumber);
		res.put("productProduceNumber", productProduceNumber);
		res.put("partsStockInNumber", partsStockInNumber);
		res.put("componentStockInNumber", componentStockInNumber);
		res.put("productStockInNumber", productStockInNumber);
		res.put("partsStockOutNumber", partsStockOutNumber);
		res.put("componentStockOutNumber", componentStockOutNumber);
		res.put("productStockOutNumber", productStockOutNumber);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse getReportList(JSONObject json) {
		Date startTime = json.getDate("startTime");
		Date stopTime = json.getDate("stopTime");
		String operator = json.getString("operator");
		String type = json.getString("type");
		String url = json.getString("url");
		// 获取报表列表
		List<Map<String, Object>> list = this.getList(startTime, stopTime, operator, type, url);
		// 获取指定时间内检验的报表部件类型
		List<String> types = this.getTypes(startTime, stopTime, url);
		// 获取指定时间内进行部件检验的操作人员
		List<String> operators = this.getOperators(startTime, stopTime, url);
		Map<String, Object> res = new HashMap<>();
		res.put("list", list);
		res.put("types", types);
		res.put("operators", operators);
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse getCount(JSONObject json) {
		// 获取指定时间内售后任务数量
		ServerResponse customerTaskCount = customerTaskService.getCustomerTaskCount(json);
		Object customerTask = customerTaskCount.getData();
		// 获取指定时间内维修任务数量
		ServerResponse repairTaskCount = repairTaskService.getRepairOrderCount(json);
		Object repairTaskCountData = repairTaskCount.getData();
		// 获取指定时间内自动化任务数量
		ServerResponse automationProjectTaskCount = automationProjectTaskService.getAutomationProjectTaskCount(json);
		Object data = automationProjectTaskCount.getData();
		Map<String, Object> res = new HashMap<>();
		res.put("customerTaskCount", customerTask);
		res.put("repairTaskCountData", repairTaskCountData);
		res.put("automationProjectTaskCount", data);
		return ServerResponse.createBySuccess(res);
	}

	/**
	 * 获取报表
	 *
	 * @param startTime 开始时间
	 * @param stopTime  结束时间
	 * @param operator  操作人员
	 * @param type      物品类型
	 * @param url       指定的url
	 * @return
	 */
	private List<Map<String, Object>> getList(Date startTime, Date stopTime, String operator, String type, String url) {
		List<Map<String, Object>> list = new ArrayList<>();
		switch (url) {
		// 获取部件检验报表
		case ReportConstant.PARTS_INSPCETION_REPORT_URL:
			list = producePartsInspectionApproveMapper.getPartsInspectionReport(startTime, stopTime, operator, type);
			break;
		// 获取组件检验报表
		case ReportConstant.COMPONENT_INSPECTION_REPORT_URL:
			list = produceComponentInspectionApproveMapper.getComponentInspectionReport(startTime, stopTime, operator, type);
			break;
		// 获取产品检验报表
		case ReportConstant.PRODUCT_INSPECTION_REPORT_URL:
			list = produceProductInspectionApproveMapper.getProductInspectionReport(startTime, stopTime, operator, type);
			break;
		// 获取三防刷漆报表
		case ReportConstant.PARTS_DEFEND_REPORT_URL:
			list = producePartsDefendApproveMapper.getPartsDefendReport(startTime, stopTime, operator, type);
			break;
		// 软件灌装报表
		case ReportConstant.PARTS_SOFT_INSTALL_REPORT_URL:
			list = producePartsSoftInstallApproveMapper.getPartsSoftInstallReport(startTime, stopTime, operator, type);
			break;
		// 部件功能检测报表
		case ReportConstant.PARTS_FACTORY_INSPECTION_REPORT_URL:
			list = producePartsFunctionInspectionApproveMapper.getPartsFactoryInspectionReport(startTime, stopTime, operator,
					type);
			break;
		// 组件装配报表
		case ReportConstant.COMPONENT_PRODUCE_REPORT_URL:
			list = produceComponentProduceApproveMapper.getComponentProduceReport(startTime, stopTime, operator, type);
			break;
		// 产品装配报表
		case ReportConstant.PRODUCT_PRODUCE_REPORT_URL:
			list = produceProductProduceApproveMapper.getProductProduceReport(startTime, stopTime, operator, type);
			break;
		// 部件入库报表
		case ReportConstant.PARTS_STOCK_IN_REPORT_URL:
			list = produceStockInApproveMapper.getStockInReport(0, startTime, stopTime, operator, type);
			break;
		// 组件入库报表
		case ReportConstant.COMPONENT_STOCK_IN_REPORT_URL:
			list = produceStockInApproveMapper.getStockInReport(1, startTime, stopTime, operator, type);
			break;
		// 产品入库报表
		case ReportConstant.PRODUCT_STOCK_IN_REPORT_URL:
			list = produceStockInApproveMapper.getStockInReport(2, startTime, stopTime, operator, type);
			break;
		// 部件出库报表
		case ReportConstant.PARTS_STOCK_OUT_REPORT_URL:
			list = produceStockOutApproveMapper.getStockOutReport(0, startTime, stopTime, operator, type);
			break;
		// 组件出库报表
		case ReportConstant.COMPONENT_STOCK_OUT_REPORT_URL:
			list = produceStockOutApproveMapper.getStockOutReport(1, startTime, stopTime, operator, type);
			break;
		// 产品出库报表
		case ReportConstant.PRODUCT_STOCK_OUT_REPORT_URL:
			list = produceStockOutApproveMapper.getStockOutReport(2, startTime, stopTime, operator, type);
			break;
		default:
			break;
		}
		// 对list进行处理
		if (list.isEmpty()) {
			return list;
		}
		for (Map map : list) {
			Object image = map.get("image");
			if (image == null) {
				continue;
			}
			String imageStr = String.valueOf(image);
			String collect = ImageUtil.imageUtil(imageStr, host);
			map.put("image", collect);
		}
		return list;
	}

	/**
	 * 获取 操作物品类型
	 *
	 * @param startTime 开始时间
	 * @param stopTime  结束时间
	 * @param url       url
	 * @return
	 */
	private List<String> getTypes(Date startTime, Date stopTime, String url) {
		List<String> types = new ArrayList<>();
		switch (url) {
		// 部件检验物品类型
		case ReportConstant.PARTS_INSPCETION_REPORT_URL:
			types = producePartsInspectionApproveMapper.getCategoryTypes(startTime, stopTime);
			break;
		// 组件检验的物品类型
		case ReportConstant.COMPONENT_INSPECTION_REPORT_URL:
			types = produceComponentInspectionApproveMapper.getCategoryTypes(startTime, stopTime);
			break;
		// 产品检验的物品类型
		case ReportConstant.PRODUCT_INSPECTION_REPORT_URL:
			types = produceProductInspectionApproveMapper.getCategoryTypes(startTime, stopTime);
			break;
		// 三防刷漆的物品类型
		case ReportConstant.PARTS_DEFEND_REPORT_URL:
			types = producePartsDefendApproveMapper.getCategoryTypes(startTime, stopTime);
			break;
		/*** ===软件灌装没有物品类型=== ***/
		case ReportConstant.PARTS_SOFT_INSTALL_REPORT_URL:
			break;
		/*** ===部件功能检测没有物品类型=== ***/
		// 组件装配的物品类型
		case ReportConstant.COMPONENT_PRODUCE_REPORT_URL:
			types = produceComponentProduceApproveMapper.getCategoryTypes(startTime, stopTime);
			break;
		// 产品装配的物品类型
		case ReportConstant.PRODUCT_PRODUCE_REPORT_URL:
			types = produceProductProduceApproveMapper.getCategoryTypes(startTime, stopTime);
			break;
		// 部件入库的物品类型
		case ReportConstant.PARTS_STOCK_IN_REPORT_URL:
			types = produceStockInApproveMapper.getCategoryTypes(0, startTime, stopTime);
			break;
		// 组件入库的组件类型
		case ReportConstant.COMPONENT_STOCK_IN_REPORT_URL:
			types = produceStockInApproveMapper.getCategoryTypes(1, startTime, stopTime);
			break;
		// 产品入库的产品类型
		case ReportConstant.PRODUCT_STOCK_IN_REPORT_URL:
			types = produceStockInApproveMapper.getCategoryTypes(2, startTime, stopTime);
			break;
		// 部件出库的部件类型
		case ReportConstant.PARTS_STOCK_OUT_REPORT_URL:
			types = produceStockOutApproveMapper.getCategoryTypes(0, startTime, stopTime);
			break;
		// 组件出库的组件类型
		case ReportConstant.COMPONENT_STOCK_OUT_REPORT_URL:
			types = produceStockOutApproveMapper.getCategoryTypes(1, startTime, stopTime);
			break;
		// 产品出库的产品类型
		case ReportConstant.PRODUCT_STOCK_OUT_REPORT_URL:
			types = produceStockOutApproveMapper.getCategoryTypes(2, startTime, stopTime);
			break;
		default:
			break;
		}
		return types;
	}

	/**
	 * 获取操作人员
	 *
	 * @param startTime 开始时间
	 * @param stopTime  结束时间
	 * @param url       url
	 * @return
	 */
	private List<String> getOperators(Date startTime, Date stopTime, String url) {
		List<String> operators = new ArrayList<>();
		switch (url) {
		// 部件检验操作人员
		case ReportConstant.PARTS_INSPCETION_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_PARTS_INSPECTION_APPROVE, startTime,
					stopTime, null);
			break;
		// 组件检验操作人员
		case ReportConstant.COMPONENT_INSPECTION_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_COMPONENT_INSPECTION_APPROVE,
					startTime, stopTime, null);
			break;
		// 产品检验操作人员
		case ReportConstant.PRODUCT_INSPECTION_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_PRODUCT_INSPECTION_APPROVE, startTime,
					stopTime, null);
			break;
		// 三防刷漆操作人员
		case ReportConstant.PARTS_DEFEND_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_PARTS_DEFEND_APPROVE, startTime,
					stopTime, null);
			break;
		// 软件灌装操作人员
		case ReportConstant.PARTS_SOFT_INSTALL_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_PARTS_SOFT_INSTALL_APPROVE, startTime,
					stopTime, null);
			break;
		// 部件功能检测操作人员
		case ReportConstant.PARTS_FACTORY_INSPECTION_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_PARTS_FUNCTION_INSPECTION_APPROVE,
					startTime, stopTime, null);
			break;
		// 组件装配操作人员
		case ReportConstant.COMPONENT_PRODUCE_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_COMPONENT_PRODUCE_APPROVE, startTime,
					stopTime, null);
			break;
		// 产品装配的操作人员
		case ReportConstant.PRODUCT_PRODUCE_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_PRODUCT_PRODUCE_APPROVE, startTime,
					stopTime, null);
			break;
		// 部件入库操作人员
		case ReportConstant.PARTS_STOCK_IN_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_STOCK_IN_APPROVE, startTime, stopTime,
					0);
			break;
		// 组件入库的操作人员
		case ReportConstant.COMPONENT_STOCK_IN_REPORT_URL:
			reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_STOCK_IN_APPROVE, startTime, stopTime, 1);
			break;
		// 产品入库操作人员
		case ReportConstant.PRODUCT_STOCK_IN_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_STOCK_IN_APPROVE, startTime, stopTime,
					2);
			break;
		// 部件出库的操作人员
		case ReportConstant.PARTS_STOCK_OUT_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_STOCK_OUT_APPROVE, startTime, stopTime,
					0);
			break;
		// 组价出库的操作人员
		case ReportConstant.COMPONENT_STOCK_OUT_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_STOCK_OUT_APPROVE, startTime, stopTime,
					1);
			break;
		// 产品出库的操作人员
		case ReportConstant.PRODUCT_STOCK_OUT_REPORT_URL:
			operators = reportMapper.getOperators(DBConstant.tableName.T_PRODUCE_STOCK_OUT_APPROVE, startTime, stopTime,
					2);
			break;
		default:
			break;
		}
		return operators;
	}
}
