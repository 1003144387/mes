package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.RepairTask;

public interface RepairTaskService {

	/**
	 * 创建维修单
	 * @param repairTask
	 * @return
	 */
	ServerResponse save(RepairTask repairTask);

	/**
	 * 获取维修任务的统计数量
	 * @param json
	 * @return
	 */
	ServerResponse getRepairOrderCount(JSONObject json);

	/**
	 * 获取维修任务列表
	 * @param json
	 * @return
	 */
	ServerResponse getRepairTaskList(JSONObject json);

	/**
	 * 跟新维修任务
	 * @param repairTask
	 * @return
	 */
	ServerResponse updateRepairTask(RepairTask repairTask);

	/**
	 * 根据id删除维修任务
	 * @param id
	 * @return
	 */
	ServerResponse deleteRepairTask(String id);


}
