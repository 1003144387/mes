package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.AutomationProjectTask;
import com.crsri.mes.vo.CountMonthVO;

public interface AutomationProjectTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(AutomationProjectTask record);

    int insertSelective(AutomationProjectTask record);

    AutomationProjectTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AutomationProjectTask record);

    int updateByPrimaryKey(AutomationProjectTask record);

    List<AutomationProjectTask> getAutomationProjectTaskList(
            @Param("projectName") String projectName,
            @Param("projectCategory") String projectCategory,
            @Param("company") String company,
            @Param("operator") String operator,
            @Param("status") Integer status,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);

    int getAutomationProjectTaskCountByStatus(
            @Param("projectName") String projectName,
            @Param("projectCategory") String projectCategory,
            @Param("company") String company,
            @Param("operator") String operator,
            @Param("status") Integer status,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);

    List<CountMonthVO> getAutomationProjectTaskCountGroupByMonth(@Param("status") Integer status,
                                                                 @Param("startTime") Date startTime,
                                                                 @Param("stopTime") Date stopTime);
}