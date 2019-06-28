package com.crsri.mes.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.crsri.mes.entity.CustomerTask;

public interface CustomerTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerTask record);

    int insertSelective(CustomerTask record);

    CustomerTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerTask record);

    int updateByPrimaryKey(CustomerTask record);

    /**
     * 按条件获取售后任务列表
     * @param customerCompany
     * @param customerProjectName
     * @param customerTaskStatus
     * @param customerOperator
     * @param startTime
     * @param stopTime
     * @return
     */
    List<CustomerTask> getCustomerTaskList(@Param("customerCompany") String customerCompany,
            @Param("customerProjectName") String customerProjectName,
            @Param("customerTaskStatus") Integer customerTaskStatus,
            @Param("customerOperator") String customerOperator,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime);

    /**
     * 按条件查询售后任务的统计数量
     * @param customerCompany
     * @param customerProjectName
     * @param customerOperator
     * @param startTime
     * @param stopTime
     * @param customerTaskStatus
     * @return
     */
    int getCustomerTaskCount(@Param("customerCompany") String customerCompany,
            @Param("customerProjectName") String customerProjectName,
            @Param("customerOperator") String customerOperator,
            @Param("startTime") Date startTime,
            @Param("stopTime") Date stopTime,
            @Param("customerTaskStatus") Integer customerTaskStatus);
}