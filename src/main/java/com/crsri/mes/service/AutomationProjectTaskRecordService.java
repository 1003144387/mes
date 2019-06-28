package com.crsri.mes.service;

import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.entity.AutomationProjectTaskRecord;

/**
 * 〈一句话功能简述〉<br>
 * 〈自动化项目任务工作日志相关的Service接口〉
 *
 * @author zcj
 * @date 2018/11/30 16:11
 * @since 1.0.0
 */
public interface AutomationProjectTaskRecordService {

    /**
     * 新增现场工作日志
     * @param record
     * @return
     */
    ServerResponse saveRecord(AutomationProjectTaskRecord record);

    /**
     * 根据id删除日志
     * @param id
     * @return
     */
    ServerResponse deleteRecord(Integer id);

    /**
     * 更新工作日志
     * @param record
     * @return
     */
    ServerResponse updateRecord(AutomationProjectTaskRecord record);

    /**
     * 根据id查找工作日志
     * @param id
     * @return
     */
    ServerResponse getRecord(Integer id);

    /**
     * 根据taskId获取工作日志列表
     * @param taskId
     * @return
     */
    ServerResponse getRecordListByTaskId(String taskId);
}