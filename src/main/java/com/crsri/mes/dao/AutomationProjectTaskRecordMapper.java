package com.crsri.mes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.AutomationProjectTaskRecord;

public interface AutomationProjectTaskRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AutomationProjectTaskRecord record);

    int insertSelective(AutomationProjectTaskRecord record);

    AutomationProjectTaskRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AutomationProjectTaskRecord record);

    int updateByPrimaryKey(AutomationProjectTaskRecord record);

    List<AutomationProjectTaskRecord> selectByTaskId(@Param("taskId") String taskId);
}