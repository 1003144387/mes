package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.CustomerTask;
import com.crsri.mes.vo.CustomerTaskVO;

public interface CustomerTaskService {

	/**
	 * 新增售后任务
	 * @return
	 */
	ServerResponse save(CustomerTaskVO vo);

	/**
	 * 按条件获取售后任务列表
	 * @param json
	 * @return
	 */
	ServerResponse getCustomerTaskList(JSONObject json);

	/**
	 * 按条件查询售后任务数量
	 * @param json
	 * @return
	 */
	ServerResponse getCustomerTaskCount(JSONObject json);

	/**
	 * 更新售后任务
	 * @param task
	 * @return
	 */
	ServerResponse updateCustomerTask(CustomerTask task);

	/**
	 * 根据id获取售后任务
	 * @param id
	 * @return
	 */
	ServerResponse getCustomerTask(String id);

	/**
	 * 删除售后任务
	 * @param id
	 * @return
	 */
	ServerResponse deleteCustomerTask(String id);

}
