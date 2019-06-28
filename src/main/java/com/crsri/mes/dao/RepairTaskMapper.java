package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.RepairTask;

public interface RepairTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(RepairTask record);

    int insertSelective(RepairTask record);

    RepairTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RepairTask record);

    int updateByPrimaryKey(RepairTask record);

    int queryRepairTaskCountByStatus(
            @Param("status") Integer status,
            @Param("deviceId") String deviceId,
            @Param("deviceCategory") String deviceCategory,
            @Param("projectName") String projectName,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);

	
	List<RepairTask> queryRepairTaskList(@Param("repairTaskStatus") Integer repairTaskStatus,
            @Param("repairOperator") String repairOperator,
            @Param("projectName") String projectName,
            @Param("deviceCategory") String deviceCategory,
            @Param("deviceId") String deviceId,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);
    
}