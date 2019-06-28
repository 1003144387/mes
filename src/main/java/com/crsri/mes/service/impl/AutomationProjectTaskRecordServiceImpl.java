package com.crsri.mes.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crsri.mes.common.log.annontation.SystemServiceLog;
import com.crsri.mes.common.response.ServerResponse;
import com.crsri.mes.dao.AutomationProjectTaskRecordMapper;
import com.crsri.mes.entity.AutomationProjectTaskRecord;
import com.crsri.mes.service.AutomationProjectTaskRecordService;

import lombok.extern.slf4j.Slf4j;

/**
 * 〈一句话功能简述〉<br>
 * 〈自动化项目任务工作日志相关的service接口的实现〉
 *
 * @author zcj
 * @date 2018/11/30 16:12
 * @since 1.0.0
 */
@Service
@Slf4j
public class AutomationProjectTaskRecordServiceImpl implements AutomationProjectTaskRecordService {

    @Resource
    private AutomationProjectTaskRecordMapper mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SystemServiceLog(description = "新增自动化项目任务现场工作日志")
    public ServerResponse saveRecord(AutomationProjectTaskRecord record) {
        try{
            mapper.insertSelective(record);
            return ServerResponse.createBySuccess();
        }catch (Exception e){
            e.printStackTrace();
            log.error("新增自动化项目任务现场工作日志异常，异常信息：{}", Arrays.toString(e.getStackTrace()));
            return ServerResponse.createByFailMessage("新增工作日志失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SystemServiceLog(description = "删除自动化项目任务现场工作日志")
    public ServerResponse deleteRecord(Integer id) {
        try{
            mapper.deleteByPrimaryKey(id);
            return ServerResponse.createBySuccess();
        }catch (Exception e){
            e.printStackTrace();
            log.error("删除自动化项目任务现场工作日志异常，异常信息：{}", Arrays.toString(e.getStackTrace()));
            return ServerResponse.createByFailMessage("删除工作日志失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @SystemServiceLog(description = "更新自动化项目任务现场工作日志")
    public ServerResponse updateRecord(AutomationProjectTaskRecord record) {
        try{
            mapper.updateByPrimaryKeySelective(record);
            return ServerResponse.createBySuccess();
        }catch (Exception e){
            e.printStackTrace();
            log.error("更新自动化项目任务现场工作日志异常，异常信息：{}", Arrays.toString(e.getStackTrace()));
            return ServerResponse.createByFailMessage("更新工作日志失败");
        }
    }

    @Override
    public ServerResponse getRecord(Integer id) {
        return ServerResponse.createBySuccess(mapper.selectByPrimaryKey(id));
    }

    @Override
    public ServerResponse getRecordListByTaskId(String taskId) {
        List<AutomationProjectTaskRecord> list = mapper.selectByTaskId(taskId);
        return ServerResponse.createBySuccess(list);
    }
}
