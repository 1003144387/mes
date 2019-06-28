package com.crsri.mes.service;

import com.alibaba.fastjson.JSONObject;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.AutomationProjectTask;

/**
 * 〈一句话功能简述〉<br>
 * 〈自动化项目任务相关的service接口〉
 *
 * @author zcj
 * @date 2018/11/26 16:35
 * @since 1.0.0
 */
public interface AutomationProjectTaskService {

    /**
     * 创建自动化项目任务
     * @param automationProjectTask
     * @return
     */
    ServerResponse save(AutomationProjectTask automationProjectTask);

    /**
     * 根据条件获取自动化项目任务列表
     * @param json 查找条件
     * @return
     */
    ServerResponse getAutomationProjectTaskList(JSONObject json);

    /**
     * 获取指定查询条件下自动化项目任务的统计数据
     * @param json 查找条件
     * @return
     */
    ServerResponse getAutomationProjectTaskCount(JSONObject json);

    /**
     * 根据id删除任务
     * @param id 任务id
     * @return
     */
    ServerResponse deleteAutomationProjectTaskById(String id);

    /**
     * 更新自动化项目任务
     * @param task
     * @return
     */
    ServerResponse updateAutomationProjectTask(AutomationProjectTask task);

    /**
     * 获取指定自动化项目任务的解决方案
     * @param id
     * @return
     */
    ServerResponse getAutomationProjectTaskSolution(String id);

    /**
     * 根据id获取指定自动化项目任务方案
     * @param id
     * @return
     */
    ServerResponse getAutomationProjectTaskById(String id);

    /**
     * 客户确认方案
     * @param task
     * @return
     */
    ServerResponse customerConfirmation(AutomationProjectTask task);

    /**
     * 按月获取自动化项目任务
     * @param json
     * @return
     */
    ServerResponse getAutomationProjectTaskCountGroupByMonth(JSONObject json);
}