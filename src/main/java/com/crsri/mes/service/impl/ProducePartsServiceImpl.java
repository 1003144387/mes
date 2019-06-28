package com.crsri.mes.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ProduceComponentPartsMapper;
import com.crsri.mes.dao.ProducePartsMapper;
import com.crsri.mes.dao.ProducePartsProcessMapper;
import com.crsri.mes.dao.ProduceProductChildInfoMapper;
import com.crsri.mes.entity.ProduceParts;
import com.crsri.mes.service.ProducePartsService;
import com.crsri.mes.util.IdUtil;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.StringUtil;

/**
 * @author 2011102394
 *
 */
@Service
public class ProducePartsServiceImpl implements ProducePartsService {

	@Resource
	private ProducePartsMapper producePartsMapper;

	@Resource
	private ProducePartsProcessMapper producePartsProcessMapper;
	
	@Resource
	private ProduceComponentPartsMapper componentPartsMapper;
	
	@Resource
	private ProduceProductChildInfoMapper productPartsMapper;
	
	@Value("${web.host}")
	private String host;

	@Override
	public ServerResponse selectProducePartsListSimple() {
		List<Map<String, Object>> res = producePartsMapper.selectProducePartsListSimple();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryProducePartsStockInList() {
		List<Map<String, Object>> res = producePartsProcessMapper.queryProducePartsStockInList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryStockOutWaittingDefendPartsList() {
		List<Map<String, Object>> res = producePartsProcessMapper.queryStockOutWaittingDefendPartsList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryStockOutWaittingSoftInstallPartsList() {
		List<Map<String, Object>> res = producePartsProcessMapper.queryStockOutWaittingSoftInstallPartsList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryStockOutWaittingFunctionInspcetionPartsList() {
		List<Map<String, Object>> res = producePartsProcessMapper.queryStockOutWaittingFunctionInspcetionPartsList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse queryStockOutUnUsedPartsList() {
		List<Map<String, Object>> res = producePartsProcessMapper.queryStockOutUnUsedPartsList();
		return ServerResponse.createBySuccess(res);
	}

	@Override
	public ServerResponse getAllParts(JSONObject json) {
		String name = StringUtil.tirm(json.getString("name"));
		String id = StringUtil.tirm(json.getString("id"));
		String operator = StringUtil.tirm(json.getString("operator"));
		
		List<ProduceParts> list = producePartsMapper.queryAll(id,name,operator);
		for (ProduceParts produceParts : list) {
			produceParts.setImage(ImageUtil.imageUtil(produceParts.getImage(), host));
		}
		return ServerResponse.createBySuccess(list);
	}

	@Override
	public ServerResponse updateProducePartsInfo(ProduceParts parts) {
		producePartsMapper.updateByPrimaryKeySelective(parts);
		return ServerResponse.createBySuccessMessage("更新生产部件信息成功!");
	}

	@Override
	public ServerResponse addProducePartsInfo(ProduceParts parts) {
		String partsName = StringUtil.tirm(parts.getName());
		ProduceParts res = producePartsMapper.queryByName(partsName);
		if(res!=null) {
			return ServerResponse.createByFailMessage("新增生产部件失败，部件名称重复，请检查！");
		}
		parts.setId(IdUtil.getId());
		producePartsMapper.insertSelective(parts);
		return ServerResponse.createBySuccessMessage("新增生产部件成功！");
	}

	@Override
	public ServerResponse deleteProduceParts(String id) {
		//删除部件信息
		producePartsMapper.deleteByPrimaryKey(id);
		//删除与该部件相关的组件组成信息
		int row = componentPartsMapper.deleteByPartsId(id);
		//删除与该部件相关的产品组成信息
		productPartsMapper.deleteByChildId(id);
		return ServerResponse.createBySuccessMessage("删除部件成功！");
	}

}
