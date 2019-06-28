package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.vo.ProduceComponentVO;

/**
 * 生产组件基本信息的service接口
 * @author 2011102394
 *
 */
public interface ProduceCompontInfoService {


	/**
	 * 获取符合条件的生产组件列表
	 * @param json
	 * @return
	 */
	ServerResponse queryProduceComponentInfoList(JSONObject json);

	/**
	 * 新增生产组件
	 * @param component
	 * @return
	 */
	ServerResponse addProduceComponentInfo(ProduceComponentVO component);

	/**
	 * 删除组件
	 * @param id 组件id
	 * @return
	 */
	ServerResponse deleteProduceComponentInfo(String id);

	/**
	 * 更新组件
	 * @param component 组件信息
	 * @return
	 */
	ServerResponse updateProduceComponentInfo(ProduceComponentVO component);
}
