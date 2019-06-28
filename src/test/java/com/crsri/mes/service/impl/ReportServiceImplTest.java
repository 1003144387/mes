package com.crsri.mes.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crsri.mes.common.constant.DBConstant;
import com.crsri.mes.common.constant.ReportConstant;
import com.crsri.mes.dao.ReportMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReportServiceImplTest {

	@Resource
	private ReportMapper reportMapper;

	@Test
	public void testGetReportList() throws ParseException {
		List<String> operators = new ArrayList<>();
		String url = "/api/report/productStockOut";
		Date startTime = DateUtils.parseDate("2018-10-11 00:00:00", "yyyy-MM-dd HH:mm:ss");
		Date stopTime = DateUtils.parseDate("2019-10-11 00:00:00", "yyyy-MM-dd HH:mm:ss");
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
		for (String string : operators) {
			System.out.println(string);
		}
	}
}
