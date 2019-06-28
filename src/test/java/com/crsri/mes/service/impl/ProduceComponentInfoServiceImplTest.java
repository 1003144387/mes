package com.crsri.mes.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.crsri.mes.dao.ProduceComponentMapper;
import com.crsri.mes.dao.ProduceComponentPartsMapper;
import com.crsri.mes.entity.ProduceComponent;
import com.crsri.mes.entity.ProduceComponentParts;
import com.crsri.mes.vo.ProduceComponentVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduceComponentInfoServiceImplTest {

	
	private static final Logger log = LoggerFactory.getLogger(ProduceComponentInfoServiceImplTest.class);


	@Resource
	private ProduceComponentMapper produceComponentMapper;
	
	@Resource
	private ProduceComponentPartsMapper componentPartsMapper;

	@Test
	public void testQueryProduceComponentInfoList() {
		List<ProduceComponent> components = produceComponentMapper.queryAll(null,null,null);
		if(CollectionUtils.isEmpty(components)) {
			log.error("未设置生产组件信息");
		}
		List<ProduceComponentVO> componentVOs = new ArrayList<>();
		for (ProduceComponent produceComponent : components) {
			ProduceComponentVO vo = new ProduceComponentVO();
			BeanUtils.copyProperties(produceComponent, vo);
			List<ProduceComponentParts> componentParts = componentPartsMapper.queryByComponentId(vo.getId());
			vo.setParts(componentParts);
			componentVOs.add(vo);
		}
		
	}
}
