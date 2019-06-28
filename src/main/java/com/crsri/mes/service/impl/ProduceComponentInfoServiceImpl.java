package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ProduceComponentMapper;
import com.crsri.mes.dao.ProduceComponentPartsMapper;
import com.crsri.mes.dao.ProducePartsMapper;
import com.crsri.mes.entity.ProduceComponent;
import com.crsri.mes.entity.ProduceComponentParts;
import com.crsri.mes.entity.ProduceParts;
import com.crsri.mes.service.ProduceCompontInfoService;
import com.crsri.mes.util.IdUtil;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.StringUtil;
import com.crsri.mes.vo.ProduceComponentVO;

/**
 * @author 2011102394
 *
 */
@Service
public class ProduceComponentInfoServiceImpl implements ProduceCompontInfoService {

	@Resource
	private ProduceComponentMapper produceComponentMapper;

	@Resource
	private ProduceComponentPartsMapper componentPartsMapper;

	@Resource
	private ProducePartsMapper partsMapper;

	@Value("${web.host}")
	private String host;

	@Override
	public ServerResponse queryProduceComponentInfoList(JSONObject json) {
		String name = null;
		String id = null;
		String model = null;
		if (json != null) {
			name = StringUtil.tirm(json.getString("name"));
			id = StringUtil.tirm(json.getString("id"));
			model = StringUtil.tirm(json.getString("model"));
		}
		List<ProduceComponent> components = produceComponentMapper.queryAll(name, id, model);
		if (CollectionUtils.isEmpty(components)) {
			return ServerResponse.createBySuccess(components);
		}
		List<ProduceComponentVO> componentVOs = new ArrayList<>();
		for (ProduceComponent produceComponent : components) {
			ProduceComponentVO vo = new ProduceComponentVO();
			BeanUtils.copyProperties(produceComponent, vo);
			vo.setImage(ImageUtil.imageUtil(vo.getImage(), host));
			List<ProduceComponentParts> componentParts = componentPartsMapper.queryByComponentId(vo.getId());
			vo.setParts(componentParts);
			componentVOs.add(vo);
		}
		return ServerResponse.createBySuccess(componentVOs);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServerResponse addProduceComponentInfo(ProduceComponentVO vo) {
		ProduceComponent component = new ProduceComponent();
		BeanUtils.copyProperties(vo, component);
		String id = IdUtil.getId();
		component.setId(id);
		// 根据组件名查找重名组件
		String componentName = StringUtil.tirm(vo.getName());
		ProduceComponent res = produceComponentMapper.queryByName(componentName);
		if (res != null) {
			return ServerResponse.createByFailMessage("新增组件失败，组件名重复请检查！");
		}
		// 保存生产组件进入数据库
		produceComponentMapper.insertSelective(component);
		// 保存生产组件的部件组成
		List<ProduceComponentParts> parts = vo.getParts();
		for (ProduceComponentParts item : parts) {
			item.setProduceComponentId(id);
			ProduceParts partsInfo = partsMapper.selectByPrimaryKey(item.getProducePartsId());
			String name = partsInfo.getName();
			item.setProducePartsName(name);
			componentPartsMapper.insertSelective(item);
		}
		return ServerResponse.createBySuccessMessage("新增生产组件成功！");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServerResponse deleteProduceComponentInfo(String id) {
		// 删除组件信息
		produceComponentMapper.deleteByPrimaryKey(id);
		// 删除该组件的组成信息
		componentPartsMapper.deleteByComponentId(id);
		return ServerResponse.createBySuccess("删除生产组件成功");
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public ServerResponse updateProduceComponentInfo(ProduceComponentVO vo) {
		// 更新组件信息
		ProduceComponent record = new ProduceComponent();
		BeanUtils.copyProperties(vo, record);
		produceComponentMapper.updateByPrimaryKeySelective(record);
		// 删除该组件的组成信息
		componentPartsMapper.deleteByComponentId(vo.getId());
		// 保存生产组件的部件组成
		List<ProduceComponentParts> parts = vo.getParts();
		for (ProduceComponentParts item : parts) {
			item.setProduceComponentId(vo.getId());
			ProduceParts partsInfo = partsMapper.selectByPrimaryKey(item.getProducePartsId());
			String name = partsInfo.getName();
			item.setProducePartsName(name);
			componentPartsMapper.insertSelective(item);
		}
		return ServerResponse.createBySuccessMessage("生产组件信息修改成功！");
	}

}
