package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.ProduceComponentMapper;
import com.crsri.mes.dao.ProducePartsMapper;
import com.crsri.mes.dao.ProduceProductChildInfoMapper;
import com.crsri.mes.dao.ProduceProductInfoMapper;
import com.crsri.mes.entity.ProduceComponent;
import com.crsri.mes.entity.ProduceComponentParts;
import com.crsri.mes.entity.ProduceParts;
import com.crsri.mes.entity.ProduceProductChildInfo;
import com.crsri.mes.entity.ProduceProductInfo;
import com.crsri.mes.service.ProduceProductInfoService;
import com.crsri.mes.util.IdUtil;
import com.crsri.mes.util.ImageUtil;
import com.crsri.mes.util.StringUtil;
import com.crsri.mes.vo.ProduceComponentVO;
import com.crsri.mes.vo.ProduceProductVO;

/**
 * 生产产品基本信息Service接口的实现
 * 
 * @author 2011102394
 *
 */
@Service
public class ProduceProductInfoServiceImpl implements ProduceProductInfoService {

	@Value("${web.host}")
	private String host;

	@Resource
	private ProduceProductInfoMapper productInforMapper;

	@Resource
	private ProduceProductChildInfoMapper productChildrenMapper;

	@Resource
	private ProducePartsMapper partsMapper;

	@Resource
	private ProduceComponentMapper componentMapper;

	@Override
	public ServerResponse queryProduceProductInfoList() {
		List<ProduceProductInfo> list = productInforMapper.queryProduceProductInfoList();
		return ServerResponse.createBySuccess(list);
	}

	@Override
	public ServerResponse queryProduceProductInfoList(JSONObject json) {
		String name = null;
		String id = null;
		String model = null;
		if (json != null) {
			name = StringUtil.tirm(json.getString("name"));
			id = StringUtil.tirm(json.getString("id"));
			model = StringUtil.tirm(json.getString("model"));
		}
		List<ProduceProductInfo> products = productInforMapper.queryAll(name, id, model);
		if (CollectionUtils.isEmpty(products)) {
			return ServerResponse.createBySuccess(products);
		}
		List<ProduceProductVO> produceProductVOs = new ArrayList<>();
		for (ProduceProductInfo product : products) {
			ProduceProductVO vo = new ProduceProductVO();
			BeanUtils.copyProperties(product, vo);
			vo.setImage(ImageUtil.imageUtil(vo.getImage(), host));
			// 获取组成产品的部件
			List<ProduceProductChildInfo> parts = productChildrenMapper.queryByProductId(vo.getId(), 0);
			vo.setParts(parts);
			// 获取组成产品的组件
			List<ProduceProductChildInfo> components = productChildrenMapper.queryByProductId(vo.getId(), 1);
			vo.setComponents(components);
			produceProductVOs.add(vo);
		}
		return ServerResponse.createBySuccess(produceProductVOs);
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public ServerResponse addProduceProductInfo(ProduceProductVO vo) {
		List<ProduceProductChildInfo> parts = vo.getParts();
		List<ProduceProductChildInfo> components = vo.getComponents();
		if(CollectionUtils.isEmpty(parts)&&CollectionUtils.isEmpty(components)) {
			return ServerResponse.createByFailMessage("新增产品失败，该产品至少包含一个部件或组件！");
		}
		ProduceProductInfo product = new ProduceProductInfo();
		BeanUtils.copyProperties(vo, product);
		String id = IdUtil.getId();
		product.setId(id);
		// 根据产品名查找重名产品
		String productName = StringUtil.tirm(vo.getName());
		ProduceProductInfo res = productInforMapper.queryByName(productName);
		if (res != null) {
			return ServerResponse.createByFailMessage("新增产品失败，产品名重复请检查！");
		}
		// 保存生产产品进入数据库
		productInforMapper.insertSelective(product);
		// 保存生产产品的部件组成
		if(CollectionUtils.isNotEmpty(parts)) {
			for (ProduceProductChildInfo item : parts) {
				item.setProductId(id);
				ProduceParts partsInfo = partsMapper.selectByPrimaryKey(item.getChildId());
				item.setChildName(partsInfo.getName());
				//设置产品组成类别标识
				item.setChildType(0);
				productChildrenMapper.insertSelective(item);
			}
		}
		// 保存生产产品的组件组成
		if(CollectionUtils.isNotEmpty(components)) {
			for (ProduceProductChildInfo item : components) {
				item.setProductId(id);
				ProduceComponent component = componentMapper.selectByPrimaryKey(item.getChildId());
				item.setChildName(component.getName());
				//设置产品组成类别标识
				item.setChildType(1);
				productChildrenMapper.insertSelective(item);
			}
		}
		return ServerResponse.createBySuccessMessage("新增生产产品成功！");
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public ServerResponse updateProduceProductInfo(ProduceProductVO vo) {
		List<ProduceProductChildInfo> parts = vo.getParts();
		List<ProduceProductChildInfo> components = vo.getComponents();
		if(CollectionUtils.isEmpty(parts)&&CollectionUtils.isEmpty(components)) {
			return ServerResponse.createByFailMessage("新增产品失败，该产品至少包含一个部件或组件！");
		}
		// 更新产品信息
		ProduceProductInfo productInfo = new ProduceProductInfo();
		BeanUtils.copyProperties(vo, productInfo);
		productInforMapper.updateByPrimaryKeySelective(productInfo);
		// 删除产品的组成信息
		productChildrenMapper.deleteByProductId(vo.getId());
		// 新增产品的组成信息
		// 保存生产产品的部件组成
		if(CollectionUtils.isNotEmpty(parts)) {
			for (ProduceProductChildInfo item : parts) {
				item.setProductId(vo.getId());
				ProduceParts partsInfo = partsMapper.selectByPrimaryKey(item.getChildId());
				//设置产品组成类别标识
				item.setChildType(0);
				item.setChildName(partsInfo.getName());
				productChildrenMapper.insertSelective(item);
			}
		}
		
		// 保存生产产品的组件组成
		if(CollectionUtils.isNotEmpty(components)) {
			for (ProduceProductChildInfo item : components) {
				item.setProductId(vo.getId());
				ProduceComponent component = componentMapper.selectByPrimaryKey(item.getChildId());
				//设置产品组成类别标识
				item.setChildType(1);
				item.setChildName(component.getName());
				productChildrenMapper.insertSelective(item);
			}
		}
		return ServerResponse.createBySuccessMessage("编辑生产产品信息成功！");
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public ServerResponse deleteProduceProductInfo(String id) {
		// 删除产品记录
		productInforMapper.deleteByPrimaryKey(id);
		// 删除产品的组成记录
		productChildrenMapper.deleteByProductId(id);
		return ServerResponse.createBySuccessMessage("删除产品信息成功！");
	}

}
