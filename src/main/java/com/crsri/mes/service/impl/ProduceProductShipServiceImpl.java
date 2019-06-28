package com.crsri.mes.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crsri.mes.dao.ProduceProductProcessMapper;
import com.crsri.mes.dao.ProduceShipApproveMapper;
import com.crsri.mes.entity.ProduceComponentProcess;
import com.crsri.mes.entity.ProducePartsProcess;
import com.crsri.mes.entity.ProduceProductProcess;
import com.crsri.mes.entity.ProduceShipApprove;
import com.crsri.mes.service.ProduceProductShipService;

/**
 * 
 * @author 2011102394
 *
 */
@Service
public class ProduceProductShipServiceImpl implements ProduceProductShipService{

	
	private static final Logger log = LoggerFactory.getLogger(ProduceProductShipServiceImpl.class);

	
	@Resource
	private ProduceShipApproveMapper produceShipApproveMapper;
	
	@Resource
	private ProduceProductProcessMapper productProcessMapper;
	
	@Override
	public void handleInspectionApproveCallBack(String processInstanceId, Integer status,
			Integer result) {
		ProduceShipApprove approve = produceShipApproveMapper.selectByPrimaryKey(processInstanceId);
		if(approve == null) {
			log.info("该出库审批不存在，审批id：" + processInstanceId);
			return;
		}
		approve.setApproveStatus(status);
		approve.setApproveResult(result);
		produceShipApproveMapper.updateByPrimaryKeySelective(approve);
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			log.error("生产物品发货审批回调处理失败，审批id:" + processInstanceId);
			// TODO 给维护人员发送钉钉信息
		}
	}

}

















